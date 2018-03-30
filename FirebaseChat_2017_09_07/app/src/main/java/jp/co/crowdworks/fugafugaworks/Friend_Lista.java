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

    private static final String TAG = "Friend_Lista";

    private String uuid;

    Toast toast;

    private TextView text;

    String selectedItem = "";

    String FID = "  ";

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



        //uAdapter = new FirebaseListAdapter<Friend>(this, Friend.class,android.R.layout.simple_list_item_1, getUsersRef().child(uuid).child("friend")) {

        uAdapter = new FirebaseListAdapter<Friend>(this, Friend.class, R.layout.frend_list_item, getUsersRef().child(uuid).child("friend")) {
            @Override
            protected void populateView(View v, Friend friend, int position) {
                //((TextView) v).setText(model.FName);
                TextView tvName = (TextView)v.findViewById(R.id.tvName);
                tvName.setText(friend.getFName());

                TextView tvId = (TextView)v.findViewById(R.id.tvId);
                tvId.setText(friend.getFriendId());


//                Object a = uAdapter.getItem(position);
//                Log.d("www", ""+a.toString());


                 //Log.i("      qwer",friend.getFriendId().toString());
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
                //selectedItem = (String)((TextView) view).ge();

                TextView tvId = (TextView)view.findViewById(R.id.tvId);
                TextView tvName = (TextView)view.findViewById(R.id.tvName);

                selectedItem = tvName.getText().toString();
                /*
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference();

                // TODO フレンドIDを取得する
                Query query = ref.child("users").child(uuid).child("friend_ID").child(selectedItem).child("friendID");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // ユーザ名取得
                        FID  = dataSnapshot.getValue().toString();

                        Log.i(TAG, "MyName: " + dataSnapshot + "wwwwwwwwwwwwwwwwwwwwwww  "+FID);


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                */

               // toast = Toast.makeText(Friend_Lista.this, FID, Toast.LENGTH_SHORT);
                //toast.show();

                Toast.makeText(getApplicationContext(),"俺:"+ tvId.getText().toString(), Toast.LENGTH_LONG).show();

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
