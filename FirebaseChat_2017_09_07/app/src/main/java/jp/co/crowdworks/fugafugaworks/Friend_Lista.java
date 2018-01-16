package jp.co.crowdworks.fugafugaworks;

import android.content.Intent;
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

    private static final String USERE_STORE = "tamesi";
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

        buttan();
    }

    @Override
    protected void onResume() {
        super.onResume();


        uAdapter = new FirebaseListAdapter<Users>(this, Users.class, android.R.layout.simple_list_item_1, getUsersRef().child("UUID").child("FUUID")) {
            @Override
            protected void populateView(View v, Users model, int position) {
                ((TextView) v).setText(model.Name);
                // Log.i("      test",position);
            }
        };

        //リストビューにFirebaseのメッセージをいれてる？

        ListView listview = (ListView) findViewById(R.id.listview_friend);
        listview.setAdapter(uAdapter);
    }

    public  void buttan(){

        findViewById(R.id.button001).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ボタンををしてからの処理
                //String lista_text = mAdapter.getView().toString();
                Intent intent = new Intent(getApplication(),Search_Friend.class);
                //intent.putExtra("Lista",mAdapter);
                startActivity(intent);
            }
        });
    }


}
