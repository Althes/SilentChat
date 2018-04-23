package jp.co.crowdworks.fugafugaworks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by 4163201 on 2018/04/02.
 */

public class userData_Update extends AppCompatActivity {

    Button btnName;
    Button btnPw;
    TextView txtNm;
    TextView txtid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_mypage);


        btnName = (Button) findViewById(R.id.btnUN);
        btnPw = (Button) findViewById(R.id.btnPW);
        txtNm = (TextView) findViewById(R.id.textUserName);//ユーザーの名前
        txtid = (TextView) findViewById(R.id.textID);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();    //ログインしているか確認

        if (user==null) {
            new UserLoginDialogFragment().show(getSupportFragmentManager(),"login");
        }
        else {
            new UserLogoutDialogFragment().show(getSupportFragmentManager(), "logout");
        }

        setNewUserName();
        setNewPassword();
    }

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();    //ログインしているか確認

        txtid.setText(user.getUid().toString());

        if (user != null) {
            // TODO ユーザ名を取得する
            Query query = ref.child("users").child(user.getUid()).child("MyName").child("MyName");
            query.addValueEventListener(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)  {
                    // ユーザ名取得
                    String myName = dataSnapshot.getValue().toString();
                    txtNm.setText(myName);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else return;
    }

    private void setNewUserName(){
        findViewById(R.id.btnUN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserNameUpdateDialogFragment().show(getSupportFragmentManager(), "NewUserName");
            }
        });
    }

    private void setNewPassword(){
        findViewById(R.id.btnPW).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();    //ログイン中のユーザーを取得
                String mailAdress = user.getEmail();                                //ログインしている人のメールアドレスを取得

                Toast.makeText(userData_Update.this, "登録したメールアドレスに変更用のメールを送信しました", Toast.LENGTH_LONG).show();

                auth.sendPasswordResetEmail(mailAdress);            //新しいパスワードに変更するためのメールが取得したメールアドレスに届く

            }
        });
    }

}
