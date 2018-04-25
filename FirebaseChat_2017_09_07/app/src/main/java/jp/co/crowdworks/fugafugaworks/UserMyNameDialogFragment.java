package jp.co.crowdworks.fugafugaworks;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//ログイン
public class UserMyNameDialogFragment extends DialogFragment{
    private static final String USERE_STORE = "users";
    private String uuid;
    //String myname;

    private String myname;

    private boolean flag01 = true;


    //データベースメッセージ
    private DatabaseReference getUsersRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(USERE_STORE);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setView(R.layout.myname_registration)
                .setPositiveButton("登録", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myname = getTextString(R.id.txt_name);

                        if(TextUtils.isEmpty(myname)) return;


                        //入力ボックスが空以外なら通す
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user == null) {
                            Toast.makeText(getActivity(), "登録できませんでした。", Toast.LENGTH_LONG).show();
                        }else {
                            uuid = user.getUid().toString();
                            getUsersRef().child(user.getUid().toString()).child("MyName").setValue(new Users(myname));//検索した人をフレンドに加える
                        }





                       //move();
                    }
                })
                .create();
    }

    private String getTextString(@IdRes int txt) {
        return ((TextView) getDialog().findViewById(txt)).getText().toString();
    }

    public  void move(){
        Intent intent = new Intent(getActivity(),Friend_Lista.class);
        startActivity(intent);

    }

    public void UserCheck(){
        // 1秒待ってから描画データを更新する
        new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // DBが更新されるまで待機
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(user == null){
                    Log.i("qwer",myname);
                }else {
                    flag01 = false;
                    uuid = user.getUid().toString();
                    getUsersRef().child(user.getUid().toString()).child("MyName").setValue(new Users(myname));//検索した人をフレンドに加える

                    move();
                }
            }
        }).start();
    }
}
