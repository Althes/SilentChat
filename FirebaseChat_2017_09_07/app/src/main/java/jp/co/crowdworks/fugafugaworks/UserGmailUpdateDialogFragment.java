package jp.co.crowdworks.fugafugaworks;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by 4163201 on 2018/04/11.
 */

public class UserGmailUpdateDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setView(R.layout.user_updategmail)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newGmail = getTextString(R.id.textGmail);
                        //入力ボックスが空以外なら通す
                        if (TextUtils.isEmpty(newGmail)) return;

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(newGmail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {                          //Gmailの変更に成功したとき
                                            Log.d("FirebaseChat", "sucsess");
                                            Toast.makeText(getActivity(), "メールアドレスの変更が完了しました", Toast.LENGTH_SHORT).show();
                                        }
                                        else {                                              //失敗したとき
                                            Log.d("FirebaseChat", "error");              //落ちるときここに来てる
                                            Toast.makeText(getActivity(), "変更に失敗しました", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                })
                .create();
    }

    private String getTextString(@IdRes int txt) {
        return ((TextView) getDialog().findViewById(txt)).getText().toString();
    }

//    private void sendEmail(){
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.sendEmailVerification()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//
//                        }
//                    }
//                });
//    }
}
