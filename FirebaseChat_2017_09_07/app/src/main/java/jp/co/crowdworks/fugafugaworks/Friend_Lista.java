package jp.co.crowdworks.fugafugaworks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by 4163211 on 2017/11/16.
 */

public class Friend_Lista extends AppCompatActivity {

    private static final String USERE_STORE = "users";
    private FirebaseListAdapter<Friend> uAdapter;

    private String uuid;

    private TextView text;

    //データベースメッセージ
    private DatabaseReference getUsersRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(USERE_STORE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_lista);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        uuid = user.getUid().toString();
        /*
        text = (TextView)findViewById(R.id.textlist);

        text.setText(uuid);
        */
        buttan();
    }

    @Override
    protected void onResume() {
        super.onResume();


        uAdapter = new FirebaseListAdapter<Friend>(this, Friend.class, android.R.layout.simple_list_item_1, getUsersRef().child(uuid).child("friend")) {
            @Override
            protected void populateView(View v, Friend model, int position) {
                ((TextView) v).setText(model.FName);
                // Log.i("      test",position);
            }
        };

        //リストビューにFirebaseのメッセージをいれてる？

        ListView listview = (ListView) findViewById(R.id.listview_friend);
        listview.setAdapter(uAdapter);

        // リスト項目をクリックした時の処理
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            /**
             * @param parent ListView
             * @param view 選択した項目
             * @param position 選択した項目の添え字
             * @param id 選択した項目のID
             */
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 選択した項目をTextViewにキャストした後、Stringにキャストする
                String selectedItem = (String)((TextView) view).getText();
                Toast toast = Toast.makeText(Friend_Lista.this, selectedItem, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
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
