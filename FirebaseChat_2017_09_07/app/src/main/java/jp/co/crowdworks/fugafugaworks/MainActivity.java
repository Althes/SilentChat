package jp.co.crowdworks.fugafugaworks;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String MESSAGE_STORE = "messagess";
    private static final String ROOMS_STORE = "rooms";
    private static final String USERS_STORE = "users";
    private FirebaseListAdapter<Message> mAdapter;
    private Utils mUtils;
    private String uuid;

    //TODO----------------------------------------------------------------

    //1111111--ここにフレンドリストからもらったフレンドIDをもらっていれる--11111111111
    //Friend_ListaクラスのtvIdと連携させる
    private String tvFriendUid = "OdPB9gmEWpQRViYf9tirlFQcEQE2";
    //11111111111111111111111111111111111111111111111111111111111111111111111111111111

    //222222222222--ここに登録画面で設定したマイネームをもらっていれる--22222222222222
    private String tvMyName = "さかわ";
    //22222222222222222222222222222222222222222222222222222222222222222222222222222222

    String sender = "名無し";

    ArrayList<SnapshotData> arrDataSnapshot = new ArrayList<SnapshotData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setupComposer();


        mUtils = new Utils(MainActivity.this);

        Intent intent = getIntent();
        tvFriendUid = intent.getStringExtra("DATA1");
        Log.i("DATA1",tvFriendUid);
        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

    }

    @Override
    //オプションメニュー作成
    public boolean onCreateOptionsMenu(Menu menu){
        //menuにmenu.xmlレイアアウトを適用
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    //メニュー選択時の処理
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_frend:
                Intent intent = new Intent(getApplication(),Friend_Lista.class);
                startActivity(intent);
                break;
            case R.id.action_searchfrends:
                Intent intent2= new Intent(getApplication(),Search_Friend.class);
                startActivity(intent2);
                break;
            default:
                Intent intent3= new Intent(getApplication(),userData_Update.class);
                startActivity(intent3);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);    //スクリーンショット制限

        //ここログイン
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            new UserLoginDialogFragment().show(getSupportFragmentManager(), "login");
        } else {
            new UserLogoutDialogFragment().show(getSupportFragmentManager(), "logout");
        }
        //ここで自分の名前とフレンドの名前を送ってABC順で比較してルーム名を作る
        //ABBBBC と　ABBBBAだったら
        //ABBBBA@ABBBBC
        String roomName = roomCheck(user.getUid(), tvFriendUid);
        SearchProcess();
        //ルーム名を入れてメッセージ取得
        getMessage(roomName);
        //リストビューにFirebaseのメッセージをいれてる？
        ListView mListview = (ListView) findViewById(R.id.listview);
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void onPause() {
        if (mAdapter!=null) {
            mAdapter.cleanup();
            mAdapter = null;
        }
        super.onPause();
    }

    //TODO>>////////////////////////////////////////////////////////
    //セッター
    public void  setFriendUid(String friendUid){
        tvFriendUid = friendUid;
    }
    public void  setMyname(){
        tvMyName = sender;
    }
    //TODO<<END/////////////////////////////////////////////////////


    //messageというテーブル取得
    private DatabaseReference getMessageRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(MESSAGE_STORE);
    }

    //roomsというテーブル取得
    private DatabaseReference getRoomsRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(ROOMS_STORE);
    }

    //usersというテーブル取得
    private DatabaseReference getUsersRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(USERS_STORE);
    }

    //メッセージを入力ボックスにあるか確認してSendMessageに渡す
    private void setupComposer() {
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = getTextString(R.id.txt_content);
                if(TextUtils.isEmpty(content)) return;

                sendMessage(content,tvFriendUid);
            }
        });
    }


    public void onClick(View view){
        switch (view.getId()) {
            case R.id.button1:
//                deleteDatabaseMessage(tvFriendUid);
                mUtils.progressShow("通信中", "描画データを読み込み中です");
                MyThread myThread = new MyThread();
                myThread.target = mUtils.mProgressDialog;
                myThread.uuid = uuid;
                myThread.start();
                break;
            case R.id.button2:
                uuid = "konnni";
                sendUserMyName(tvMyName);
                break;
        }
    }

    //
    private String getTextString(@IdRes int txt) {
        return ((TextView) findViewById(txt)).getText().toString();
    }

    private void sendMessage(String content,String friendUid) {
        //ここでFirebaseにログイン
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String roomName = roomCheck(user.getUid(), friendUid);
        //ログインできていないときメッセージ送信はしない
        if(user==null){
            Log.d("SendMessage","NotLogin");
            new AlertDialog.Builder(this)
                    .setTitle("エラー")
                    .setMessage("ログインしていません")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        getMessageRef().child(roomName).push().setValue(new Message(sender, content)).continueWith(new Continuation<Void, Object>() {
            @Override
            public Object then(@NonNull Task<Void> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.e("FirebaseChat","error", task.getException());
                    return null;
                }
                ((TextView) findViewById(R.id.txt_content)).setText("");
                return null;
            }
        });

    }
    //TODO ここでユーザーの名前を登録している
    //変数myNameが登録する名前
    private void sendUserMyName(String myName) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        getUsersRef().child(user.getUid()).setValue(new Users(myName)).continueWith(new Continuation<Void, Object>() {
            @Override
            public Object then(@NonNull Task<Void> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.e("FirebaseChat", "error01", task.getException());
                    return null;
                }
                return null;
            }
        });
    }
    //TODO ここでユーザーのルームを登録している
    public void sendUserRoom(String friendUid) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String roomName = roomCheck(user.getUid(),friendUid);
        //変数roomNameが登録する名前
        getUsersRef().child(user.getUid()).child("rooms").setValue(new UsersR(roomName)).continueWith(new Continuation<Void, Object>() {
            @Override
            public Object then(@NonNull Task<Void> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.e("FirebaseChat","error01", task.getException());
                    return null;
                }
                return null;
            }
        });

    }

    //メッセージ取得
    public void getMessage(String roomName){

        mAdapter = new FirebaseListAdapter<Message>(this, Message.class, android.R.layout.simple_list_item_1, getMessageRef().child(roomName)) {
            @Override
            protected void populateView(View v, Message model, int position) {
                ((TextView) v).setText(model.UUID+": "+model.Message);
            }
        };
    }


    //TODO ここで自分と相手のメッセージをしているルームを消す
    //変数roomNameがルームの名前
    public void  deleteDatabaseMessage(String friendUid){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String roomName = roomCheck(user.getUid(), friendUid);
        getMessageRef().child(roomName).removeValue();
    }


    //Friendルームチェック比較
    public String roomCheck(String myRoom,String fRoom) {

        int result = myRoom.compareTo(fRoom);
        String roomName = null;
        if (result < 0){
            roomName = myRoom + "@" + fRoom;
        }else if (result > 0){
            roomName = fRoom + "@" + myRoom;
        }else if (result == 0){
            roomName = myRoom + "@" + fRoom;
        }

        return roomName;
    }

    public  void SearchProcess(){
        //入力されたユーザIDを取得する
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query query = ref.child("users").child(user.getUid()).child("MyName").child("MyName");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // ユーザ名取得
                String myName = dataSnapshot.getValue().toString();
                sender = myName;
            }
            @Override public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //ログインチェックメソッド
    public void LoginCheck(){
        //ここでFirebaseにログイン
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Log.d("ログインチェック","NotLogin");
            new AlertDialog.Builder(this)
                    .setTitle("エラー")
                    .setMessage("ログインしていません")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

}