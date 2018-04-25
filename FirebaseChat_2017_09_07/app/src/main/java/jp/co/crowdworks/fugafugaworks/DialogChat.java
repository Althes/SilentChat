//package jp.co.crowdworks.fugafugaworks;
//
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.annotation.IdRes;
//import android.support.annotation.NonNull;
//import android.support.v4.app.DialogFragment;
//import android.support.v7.app.AlertDialog;
//import android.widget.TextView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
///**
// * Created by 4163101 on 2018/04/23.
// */
//
//public class DialogChat extends DialogFragment {
//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        new AlertDialog.Builder(getActivity())
//                .setTitle("title")
//                .setMessage("message")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // OK button pressed
//                    }
//                })
//                .setNegativeButton("Cancel", null)
//                .show();
//    }
//}
