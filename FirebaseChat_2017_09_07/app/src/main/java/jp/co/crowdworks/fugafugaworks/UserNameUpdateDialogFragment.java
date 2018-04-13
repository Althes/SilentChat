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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by 4163201 on 2018/04/11.
 */

public class UserNameUpdateDialogFragment extends DialogFragment {
    private static final String USERE_STORE = "users";
    String uuid;


    //データベースメッセージ
    private DatabaseReference getUsersRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(USERE_STORE);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setView(R.layout.user_updatename)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = getTextString(R.id.textUserName);
                        //入力ボックスが空以外なら通す
                        if(TextUtils.isEmpty(newName)) return;

                        //名前変更した時の処理
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        uuid = user.getUid().toString();
                        getUsersRef().child(uuid).child("MyName").setValue(new Users(newName));

                    }
                })
                .create();
    }

    private String getTextString(@IdRes int txt) {
        return ((TextView) getDialog().findViewById(txt)).getText().toString();
    }
}
