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
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String MESSAGE_STORE = "message";
    private static final String USER_STORE = "users";
    private static final String TAMESI_STORE = "zentaitamesi";
    private FirebaseListAdapter<Message> mAdapter;
    private FirebaseListAdapter<Message> tAdapter;
    private FirebaseListAdapter<Users> uAdapter;


    ArrayList<SnapshotData> arrDataSnapshot = new ArrayList<SnapshotData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        serchData();
        ListaData();
        setupComposer();
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
                //intent.putExtra("Lista",mAdapter);
                startActivity(intent2);
                break;
            default:
                Intent intent3= new Intent(getApplication(),MainActivity.class);
                startActivity(intent3);
        }
        return true;
    }

  /*  public void optionlist(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case R.id.action_frend:
                Intent intent = new Intent(getApplication(),Friend_Lista.class);
                startActivity(intent);
                break;
            case R.id.action_searchfrends:
                Intent intent2= new Intent(getApplication(),Search_Friend.class);
                //intent.putExtra("Lista",mAdapter);
                startActivity(intent2);
                break;
            default:
                Intent intent3= new Intent(getApplication(),MainActivity.class);
                startActivity(intent3);
        }
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        //ここログイン
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null) {
            new UserLoginDialogFragment().show(getSupportFragmentManager(),"login");
        }
        else {
            new UserLogoutDialogFragment().show(getSupportFragmentManager(), "logout");
        }


        mAdapter = new FirebaseListAdapter<Message>(this, Message.class, android.R.layout.simple_list_item_1, getMessageRef()) {
            @Override
            protected void populateView(View v, Message model, int position) {
                ((TextView) v).setText(model.UUID+": "+model.Message);
                // Log.i("      test",position);
            }
        };

        //リストビューにFirebaseのメッセージをいれてる？

        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(mAdapter);

        //UUIDAdd();

    }

    @Override
    protected void onPause() {
        if (mAdapter!=null) {
            mAdapter.cleanup();
            mAdapter = null;
        }
        super.onPause();
    }

    //データベースメッセージ
    private DatabaseReference getMessageRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(MESSAGE_STORE);
    }

    private DatabaseReference getUsersRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(USER_STORE);
    }

    private DatabaseReference getTamesiRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(TAMESI_STORE);
    }



    //メッセージを入力ボックスにあるか確認してSendMessageに渡す
    private void setupComposer() {
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = getTextString(R.id.txt_content);
                if(TextUtils.isEmpty(content)) return;

                sendMessage(content);
            }
        });
    }

    //
    private String getTextString(@IdRes int txt) {
        return ((TextView) findViewById(txt)).getText().toString();
    }

    //usersのUUIDのFUUIDに追加
    private void UUIDAdd(){
        //getUsersRef().child("test01").child("friend").push().setValue(new Users("test01"));
        getUsersRef().push().setValue(new Users("MyName"));
        /*
        getTamesiRef().push().setValue(new Tamesi("001","test01"));
        getTamesiRef().push().setValue(new Tamesi("002","test02"));
        getTamesiRef().push().setValue(new Tamesi("003","test03"));
        getTamesiRef().push().setValue(new Tamesi("004","test04"));
        getTamesiRef().push().setValue(new Tamesi("005","test05"));
        getTamesiRef().push().setValue(new Tamesi("006","test06"));
        getTamesiRef().push().setValue(new Tamesi("007","test07"));
        getTamesiRef().push().setValue(new Tamesi("008","test08"));
        getTamesiRef().push().setValue(new Tamesi("009","test09"));
        */
    }

    private void sendMessage(String content) {
        //ここでFirebaseにログイン
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //user.getUid();
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


        getMessageRef().push().setValue(new Message(user.getUid(), content)).continueWith(new Continuation<Void, Object>() {
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

        /*
        for (SnapshotData data : arrDataSnapshot){
            Log.i("      test",data.getUuid());
            Log.i("      test2",data.getMessage());
        }
        */
    }

    public void serchData(){
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference();

                Query query = ref.child("message").orderByChild("UUID").equalTo("p48LnTPoSJQLag8NjUuNc1BvUTO2");
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {
                        String sender = dataSnapshot.child("Message").getValue().toString();
                        String body = dataSnapshot.child("UUID").getValue().toString();
                        Log.d("Firebase", String.format("Message:%s, UUID:%s", sender, body));
                       // Log.d("Firebase", String.format("wwwwwwwwwwwww:%s", dataSnapshot));
                       // SnapshotData snapshotData = new SnapshotData();
                        //snapshotData.setUuid(dataSnapshot.child("UUID").getValue().toString());
                        //snapshotData.setMessage(dataSnapshot.child("Message").getValue().toString());
                        //arrDataSnapshot.add(snapshotData);


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    public  void ListaData(){

        findViewById(R.id.listabtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String lista_text = mAdapter.getView().toString();
                Intent intent = new Intent(getApplication(),Friend_Lista.class);
                //intent.putExtra("Lista",mAdapter);
                startActivity(intent);

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

