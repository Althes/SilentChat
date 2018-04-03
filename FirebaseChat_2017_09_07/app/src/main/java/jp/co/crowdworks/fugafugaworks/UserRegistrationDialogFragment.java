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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//新規登録
public class UserRegistrationDialogFragment extends DialogFragment {
    private static final String USERE_STORE = "users";
    private String uuid;
    String myname;


    //データベースメッセージ
    private DatabaseReference getUsersRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(USERE_STORE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return new AlertDialog.Builder(getContext())
                .setView(R.layout.registration)
                .setPositiveButton("Register", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = getTextString(R.id.txt_email);
                        String password = getTextString(R.id.txt_password);
                        myname = getTextString(R.id.txt_myname);



                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if(TextUtils.isEmpty(myname))

                        uuid = user.getUid().toString();



                        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) return;

                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).continueWith(new Continuation<AuthResult, Object>() {
                            @Override
                            public Object then(@NonNull Task<AuthResult> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getContext(),"signup error!",Toast.LENGTH_SHORT).show();
                                }
                                return null;
                            }
                        });
                    }

                })
                .create();
    }

    private String getTextString(@IdRes int txt) {
        return ((TextView) getDialog().findViewById(txt)).getText().toString();
    }

    public void setphut(){
        //getUsersRef().child("users").child(uuid).setValue(new Users(myname));
    }
}
