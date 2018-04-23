package jp.co.crowdworks.fugafugaworks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by 4163211 on 2017/11/16.
 */

public class Title extends AppCompatActivity {

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);



        //ここログイン
        user = FirebaseAuth.getInstance().getCurrentUser();

        buttan();
    }


    public  void buttan(){
        findViewById(R.id.title_btn01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ボタンををしてからの処理
                new UserRegistrationDialogFragment().show(getSupportFragmentManager(), "register");
            }
        });

        Button btnname = (Button) findViewById(R.id.title_btn02);

        if(user == null){
            btnname.setText("ログイン");
            findViewById(R.id.title_btn02).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ボタンををしてからの処理
                    new UserLoginDialogFragment().show(getSupportFragmentManager(), "login");
                }
            });
        } else {
            btnname.setText("ログアウト");
            findViewById(R.id.title_btn02).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ボタンををしてからの処理
                    new UserLogoutDialogFragment().show(getSupportFragmentManager(), "logout");
                }
            });
        }




        findViewById(R.id.title_btn03).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(user == null){
                    Toast.makeText(getApplicationContext(),"ログインしてください。", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    Intent intent = new Intent(getApplication(),Friend_Lista.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void move (){
        Intent intent = new Intent(getApplication(),Friend_Lista.class);
        startActivity(intent);
    }
}
