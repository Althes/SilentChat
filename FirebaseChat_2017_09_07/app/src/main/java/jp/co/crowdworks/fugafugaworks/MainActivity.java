package jp.co.crowdworks.fugafugaworks;

import android.app.AlertDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String MESSAGE_STORE = "messagess";
    private static final String ROOMS_STORE = "rooms";
    private static final String USERS_STORE = "users";
    private FirebaseListAdapter<Message> mAdapter;
    private FirebaseListAdapter<Rooms> rAdapter;
    private String friendUid = "OdPB9gmEWpQRViYf9tirlFQcEQE2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setupComposer();
        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);


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
        getMessage(friendUid+"@"+ user.getUid());
        Log.i("yfufiuffiuyf"  ,friendUid+"@"+ user.getUid());

        //リストビューにFirebaseのメッセージをいれてる？
        ListView mListview = (ListView) findViewById(R.id.listview);
        mListview.setAdapter(mAdapter);
//        ListView rListview = (ListView) findViewById(R.id.listview);
//        rListview.setAdapter(rAdapter);

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
               sendUserRoom();
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

    private void sendMessage(String content) {
        //ここでFirebaseにログイン
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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

    private void sendUserRoom() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String roomName = roomCheck(user.getUid(),friendUid);
        getRoomsRef().child(roomName).child("member").setValue(new Rooms(user.getUid(),friendUid)).continueWith(new Continuation<Void, Object>() {
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
        String roomName = roomCheck(user.getUid(),friendUid);
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
        mAdapter = new FirebaseListAdapter<Message>(this, Message.class, android.R.layout.simple_list_item_1, getMessageRef().child(roomName)) {
            @Override
            protected void populateView(View v, Message model, int position) {
                ((TextView) v).setText(model.Sender+": "+model.Message);
            }
        };
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