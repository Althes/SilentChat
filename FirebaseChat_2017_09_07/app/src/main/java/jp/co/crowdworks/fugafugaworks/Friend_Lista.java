package jp.co.crowdworks.fugafugaworks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by 4163211 on 2017/11/16.
 */

public class Friend_Lista extends AppCompatActivity {

    private static final String USERE_STORE = "users";
    private FirebaseListAdapter<Users> uAdapter;

    //データベースメッセージ
    private DatabaseReference getUsersRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(USERE_STORE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_lista);
    }

    @Override
    protected void onResume() {
        super.onResume();


        uAdapter = new FirebaseListAdapter<Users>(this, Users.class, android.R.layout.simple_list_item_1, getUsersRef().child("test01").child("friend")) {
            @Override
            protected void populateView(View v, Users model, int position) {
                ((TextView) v).setText(model.user);
                // Log.i("      test",position);
            }
        };

        //リストビューにFirebaseのメッセージをいれてる？

        ListView listview = (ListView) findViewById(R.id.listview_friend);
        listview.setAdapter(uAdapter);
    }


}
