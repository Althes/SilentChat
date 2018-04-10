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

//ログイン
public class UserMyNameDialogFragment extends DialogFragment{
    private static final String USERE_STORE = "users";
    private String uuid;
    //String myname;


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
                        String myname = getTextString(R.id.txt_name);
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        uuid = user.getUid().toString();


                        //入力ボックスが空以外なら通す
                        if(TextUtils.isEmpty(myname)) return;

                        getUsersRef().child(uuid).setValue(new Users(myname));//検索した人をフレンドに加える
                    }
                })
                .create();
    }

    private String getTextString(@IdRes int txt) {
        return ((TextView) getDialog().findViewById(txt)).getText().toString();
    }
}
