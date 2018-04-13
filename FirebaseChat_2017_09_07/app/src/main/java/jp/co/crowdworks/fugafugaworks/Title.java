package jp.co.crowdworks.fugafugaworks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by 4163211 on 2017/11/16.
 */

public class Title extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);

        //ここログイン
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            new UserLoginDialogFragment().show(getSupportFragmentManager(), "login");
        } else {
            new UserLogoutDialogFragment().show(getSupportFragmentManager(), "logout");
        }

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

        findViewById(R.id.title_btn02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ボタンををしてからの処理
                new UserLoginDialogFragment().show(getSupportFragmentManager(), "login");
            }
        });
    }

    public void move (){
        Intent intent = new Intent(getApplication(),Friend_Lista.class);
        startActivity(intent);
    }
}
