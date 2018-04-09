package jp.co.crowdworks.fugafugaworks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by 4163201 on 2018/04/02.
 */

public class userData_Update extends AppCompatActivity {
    //private static String USER_STORE;
    private static final String USERE_STORE = "users";
    private FirebaseListAdapter<Users> uAdapter;

    //データベースメッセージ
    private DatabaseReference getUsersRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(USERE_STORE);
    }

    private String uuid;

    Button btnName;
    Button btnGm;
    Button btnPw;
    TextView txtNm;
    TextView txtGm;
    TextView txtPw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_mypage);


        btnName = (Button)findViewById(R.id.btnUN);
        btnGm   = (Button)findViewById(R.id.btnGM);
        btnPw   = (Button)findViewById(R.id.btnPW);
        txtNm = (TextView)findViewById(R.id.textUserName);//ユーザーの名前
        txtGm = (TextView)findViewById(R.id.textGmail);
        txtPw = (TextView)findViewById(R.id.textPassWord);

        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();    //ログインの人のパスワードとかを変更する
        //USER_STORE = "users/" + user.getUid() + "/MyName";               //自分のユーザーネームまで参照するためのルート

        //serchNameData();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();    //ログインしているか確認

        if (user==null) {
            new UserLoginDialogFragment().show(getSupportFragmentManager(),"login");
        }
        else {
            new UserLogoutDialogFragment().show(getSupportFragmentManager(), "logout");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }



//    private void MyNameDisplay(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();    //ログインしているか確認
//
//        if (user != null) {
//            // TODO ユーザ名を取得する
//            Query query = ref.child("users").child(user.getUid()).child("MyName");
//            query.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    // ユーザ名取得
//                    String myName = dataSnapshot.getValue().toString();
//                    txtNm.setText(myName);
//                    Log.i("sample : ", "MyName: " + dataSnapshot + "wwwwwwwwwwwwwwwwwwwwwww" + myName);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                }
//            });
//        } else return;
//    }
//    //自分の名前を変更
//    private DatabaseReference getUserNameRef() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        return database.getReference(USER_STORE);
//    }

//    public void serchNameData(){
//        findViewById(R.id.btnUN).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference ref = database.getReference();
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();    //ログインしているか確認
//
//                if (user != null) {
//                    // TODO ユーザ名を取得する
//                    Query query = ref.child("users").child(user.getUid()).child("MyName");
//                    query.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            // ユーザ名取得
//                            String myName = dataSnapshot.getValue().toString();
//                            txtNm.setText(myName);
//                            Log.i("sample : ", "MyName: " + dataSnapshot + "wwwwwwwwwwwwwwwwwwwwwww" + myName);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                        }
//                    });
//                }
//                else return;
//            }
//        });
//
//    }

}
