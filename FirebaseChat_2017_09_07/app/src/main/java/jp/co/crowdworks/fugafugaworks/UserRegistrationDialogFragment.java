package jp.co.crowdworks.fugafugaworks;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//新規登録
public class UserRegistrationDialogFragment extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return new AlertDialog.Builder(getContext())
                .setView(R.layout.input_id_pass)
                //.setPositiveButton("Register", new DialogInterface.OnClickListener() {
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String email = getTextString(R.id.txt_email);
                        String password = getTextString(R.id.txt_password);

                        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) return;

                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).continueWith(new Continuation<AuthResult, Object>() {
                            @Override
                            public Object then(@NonNull Task<AuthResult> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getContext(),"signup error!", Toast.LENGTH_SHORT).show();
                                }

                                return null;
                            }
                        });

//                        if(user != null) {
//                            new UserMyNameDialogFragment().show(getFragmentManager(), "next");
//                            //Toast.makeText(getApplicationContext(),"ID:     "+ tvId.getText().toString(), Toast.LENGTH_LONG).show();
//                            Toast.makeText(getActivity(),"userがない", Toast.LENGTH_LONG).show();
//                            return;
//                        }else {
//                            new UserRegistrationDialogFragment().show(getFragmentManager(), "register");
//                            //return;
//                        }


                        new UserMyNameDialogFragment().show(getFragmentManager(), "next");
                    }
                })
                .create();

    }


    private String getTextString(@IdRes int txt) {
        return ((TextView) getDialog().findViewById(txt)).getText().toString();
    }
}
