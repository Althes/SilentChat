package jp.co.crowdworks.fugafugaworks;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String MESSAGE_STORE = "messagess";
    private static final String ROOMS_STORE = "rooms";
    private static final String USERS_STORE = "users";
    private FirebaseListAdapter<Rooms> rAdapter;
    private String friendUid = "OdPB9gmEWpQRViYf9tirlFQcEQE2";
    private static final String TAMESI_STORE = "zentaitamesi";
    private FirebaseListAdapter<Message> mAdapter;
    private FirebaseListAdapter<Message> tAdapter;
    private FirebaseListAdapter<Users> uAdapter;
    ArrayList<SnapshotData> arrDataSnapshot = new ArrayList<SnapshotData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setupComposer();
        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        serchData();
        ListaData();
        setupComposer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //ここログイン
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            new UserLoginDialogFragment().show(getSupportFragmentManager(), "login");
        } else {
            new UserLogoutDialogFragment().show(getSupportFragmentManager(), "logout");
        }
        getMessage(user.getUid()+"@"+friendUid);
        mAdapter = new FirebaseListAdapter<Message>(this, Message.class, android.R.layout.simple_list_item_1, getMessageRef()) {
            @Override
            protected void populateView(View v, Message model, int position) {
                ((TextView) v).setText(model.UUID+": "+model.Message);
                // Log.i("      test",position);
            }
        };

        //リストビューにFirebaseのメッセージをいれてる？
//        ListView mListview = (ListView) findViewById(R.id.listview);
//        mListview.setAdapter(mAdapter);
        ListView rListview = (ListView) findViewById(R.id.listview);
        rListview.setAdapter(rAdapter);
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
    private DatabaseReference getTamesiRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(TAMESI_STORE);
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

                sendMessage(content);
            }
        });
    }


    public void onClick(View view){
        switch (view.getId()) {
            case R.id.button1:
//                sendUserRoom();
                roomCheck("");
                break;
            case R.id.button2:
                sendUser();
                break;
        }
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
        getMessageRef().child(user.getUid() + "@" + friendUid).push().setValue(new Message(user.getUid(), content)).continueWith(new Continuation<Void, Object>() {
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
        getMessageRef().child(friendUid + "@" + user.getUid()).push().setValue(new Message(user.getUid(), content)).continueWith(new Continuation<Void, Object>() {
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

    private void sendUserRoom() {
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        getRoomsRef().child(user.getUid()+"@"+friendUid).child("member").setValue(new Rooms(user.getUid(),friendUid)).continueWith(new Continuation<Void, Object>() {
            @Override
            public Object then(@NonNull Task<Void> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.e("FirebaseChat","error01", task.getException());
                    return null;
                }
                return null;
            }
        });
        getRoomsRef().child(friendUid + "@" + user.getUid()).child("member").setValue(new Rooms(user.getUid(),friendUid)).continueWith(new Continuation<Void, Object>() {
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

    private void sendUser() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        getUsersRef().child(user.getUid()).child("MyName").setValue(new Users("さかわ")).continueWith(new Continuation<Void, Object>() {
            @Override
            public Object then(@NonNull Task<Void> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.e("FirebaseChat","error01", task.getException());
                    return null;
                }
                return null;
            }
        });
        getUsersRef().child(user.getUid()).child("friend").child(friendUid).setValue(new UsersF("masaya")).continueWith(new Continuation<Void, Object>() {
            @Override
            public Object then(@NonNull Task<Void> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.e("FirebaseChat","error01", task.getException());
                    return null;
                }
                return null;
            }
        });

        getUsersRef().child(user.getUid()).child("rooms").setValue(new UsersR(user.getUid() + "@" + friendUid)).continueWith(new Continuation<Void, Object>() {
            @Override
            public Object then(@NonNull Task<Void> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.e("FirebaseChat","error01", task.getException());
                    return null;
                }
                return null;
            }
        });

        getUsersRef().child(user.getUid()).child("rooms").setValue(new UsersR( friendUid + "@" + user.getUid())).continueWith(new Continuation<Void, Object>() {
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


    //ルームメンバー
    public void getRoom(String roomName){
        rAdapter = new FirebaseListAdapter<Rooms>(this, Rooms.class, android.R.layout.simple_list_item_1, getRoomsRef().child(roomName)) {
            @Override
            protected void populateView(View v, Rooms model, int position) {
                ((TextView) v).setText(model.member01+":"+model.member02);
            }
        };
    }
    //メッセージ取得
    public void getMessage(String roomName){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        mAdapter = new FirebaseListAdapter<Message>(this, Message.class, android.R.layout.simple_list_item_1, getMessageRef().child(roomName)) {
            @Override
            protected void populateView(View v, Message model, int position) {

                ((TextView) v).setText(model.UUID+": "+model.Message);
            }
        };
    }



    //Friendルームチェック
    public void roomCheck(String roomName){
//        Log.i("rooom","getClass:" + getRoomsRef().getClass());
//        Log.i("rooom","getRef:" + getRoomsRef().getRef());
//        Log.i("rooom","getKey:" + getRoomsRef().getKey());
        Log.i("rooom",":" + getRoomsRef().child("rooms"));
//        Log.i("rooom","getRoot:" + getRoomsRef().getRoot());
//        Log.i("rooom","getDatabase:" + getRoomsRef().getDatabase());
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
