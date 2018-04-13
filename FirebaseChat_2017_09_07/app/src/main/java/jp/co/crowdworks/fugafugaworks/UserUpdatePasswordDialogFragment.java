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

/**
 * Created by 4163201 on 2018/04/13.
 */

public class UserUpdatePasswordDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setView(R.layout.user_updatepass)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPass = getTextString(R.id.textPass);
                        //入力ボックスが空以外なら通す
                        if (TextUtils.isEmpty(newPass)) return;

                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String mailAdress = user.getEmail();

                        auth.sendPasswordResetEmail(mailAdress);

                    }
                })
                .create();
    }

    private String getTextString(@IdRes int txt) {
        return ((TextView) getDialog().findViewById(txt)).getText().toString();
    }
}
