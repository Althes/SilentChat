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
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//ログイン
public class UserLoginDialogFragment extends DialogFragment{
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setView(R.layout.input_id_pass)
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String email = getTextString(R.id.txt_email);
                        String password = getTextString(R.id.txt_password);




                        //入力ボックスが空以外なら通す
                        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) return;
                        //ここでログインをしている
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).continueWith(new Continuation<AuthResult, Object>() {
                            @Override
                            public Object then(@NonNull Task<AuthResult> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    //ここからエラーメソッド呼び出した方がよさげ
                                }
                                return null;
                            }
                        });

                       // new Thread()




                        move();
                    }
                })
                .setNeutralButton("会員登録", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new UserRegistrationDialogFragment().show(getFragmentManager(), "register");
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


}
