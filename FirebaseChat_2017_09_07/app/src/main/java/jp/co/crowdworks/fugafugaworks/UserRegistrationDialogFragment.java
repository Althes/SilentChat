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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

//新規登録
public class UserRegistrationDialogFragment extends DialogFragment {

    String hikakuName = "000000000";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setView(R.layout.input_id_pass)
                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = getTextString(R.id.txt_email);
                        String password = getTextString(R.id.txt_password);
                        String username = getTextString(R.id.txt_username);




                        //入力されたユーザIDを取得する




                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference();

                        // TODO ユーザ名を取得する
                        Query query = ref.child("users").child(username).child("MyName");
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // ユーザ名取得
                                String myName = dataSnapshot.getValue().toString();
                                Log.i(TAG, "MyName: " + dataSnapshot + "wwwwwwwwwwwwwwwwwwwwwww"+myName);

                                hikakuName = myName;


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });


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
}
