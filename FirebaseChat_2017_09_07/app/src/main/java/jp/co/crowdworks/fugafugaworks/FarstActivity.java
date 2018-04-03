package jp.co.crowdworks.fugafugaworks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FarstActivity extends AppCompatActivity implements View.OnClickListener{

    Intent intent6;
    boolean a = true;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            intent6 = new Intent(this,MainActivity.class);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_farstactivity);
            findViewById(R.id.btn).setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //ここログイン
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null) {
            new UserLoginDialogFragment().show(getSupportFragmentManager(),"login");
        }
        else {
            new UserLogoutDialogFragment().show(getSupportFragmentManager(), "logout");
        }
    }

    public void onClick(View view){
        if(a){
            a=false;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent6);
                    a = true;
                }
            }, 2600);

        }
    }
}







