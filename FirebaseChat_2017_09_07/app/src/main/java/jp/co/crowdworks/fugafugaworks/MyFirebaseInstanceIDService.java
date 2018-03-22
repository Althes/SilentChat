package jp.co.crowdworks.fugafugaworks;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by 4163201 on 2017/11/16.
 */
    //登録トークン取得サービス
public class MyFirebaseInstanceIDService  extends FirebaseInstanceIdService{
    //登録トークン更新時によばれる    登録トークン->ユーザー、一人一人に一意に登録されるトークン　これで特定の人にだけメッセージを送ることができる
    String token;

    @Override
    public void onTokenRefresh(){
        token = FirebaseInstanceId.getInstance().getToken();     //トークン取得
        Log.d("debug", "onTokenRefresh>>>>" + token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token){
        //サーバーに送信
    }

    public String sendToken(){
        onTokenRefresh();
        return token;
    }
}
