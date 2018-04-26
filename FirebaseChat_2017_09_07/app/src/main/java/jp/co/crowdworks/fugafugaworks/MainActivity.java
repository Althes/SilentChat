package jp.co.crowdworks.fugafugaworks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class MainActivity extends AppCompatActivity {
    private static final String MESSAGE_STORE = "messagess";
    private static final String ROOMS_STORE = "rooms";
    private static final String USERS_STORE = "users";
    private FirebaseListAdapter<Message> mAdapter;
    private String tvFriendUid;
    String sender = "名無し";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupComposer();
        Intent intent = getIntent();
        tvFriendUid = intent.getStringExtra("DATA1");
        Log.i("DATA1",tvFriendUid);
        setDeleteButton();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);    //スクリーンショット制限
        //ここログイン
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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
                Intent intent2 = new Intent(getApplication(),Search_Friend.class);
                startActivity(intent2);
                break;
            default:
                Intent intent3 = new Intent(getApplication(),userData_Update.class);
                startActivity(intent3);
                break;
        }
        return true;
    }


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

    private void setDeleteButton() {
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
    }

    //
    private String getTextString(@IdRes int txt) {
        return ((TextView) findViewById(txt)).getText().toString();
    }

    private void sendMessage(String content,String friendUid) {
        //ここでFirebaseにログイン
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String roomName = roomCheck(user.getUid(), friendUid);

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


    //変数roomNameがルームの名前
    public void  deleteDatabaseMessage(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String roomName = roomCheck(user.getUid(), tvFriendUid);
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

    private void dialog (){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("トークの削除")
                .setMessage("一度消すと元に戻すことはできません")
                .setPositiveButton("削除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDatabaseMessage();
                    }
                })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}