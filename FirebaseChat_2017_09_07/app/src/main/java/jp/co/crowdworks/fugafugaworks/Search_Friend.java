package jp.co.crowdworks.fugafugaworks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by 4163211 on 2017/12/19.
 */

public class Search_Friend extends AppCompatActivity {

    private static final String TAG = "Search_Friend";
    MainActivity main = new MainActivity();
    private static final String USERE_STORE = "users";
   // private static final String SEARCH_STONE = "users/";
    private FirebaseListAdapter<Search> uAdapter;

    EditText searchid;
    TextView resultid;
    ListView searchlist;

    private String uuid;

    String idtext = "  ";
    String sender = "  ";

    String MyNameTtext = "MyName" ;
    //Search_Listview senderA;


    Search_Listview senderA = new Search_Listview("name");

    private ArrayList<String> list = new ArrayList<>();

    //データベースメッセージ
    private DatabaseReference getUsersRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(USERE_STORE);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState){
        Log.i(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_friend);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        uuid = user.getUid().toString();

        searchid = (EditText)findViewById(R.id.searchtext);
        resultid =(TextView)findViewById(R.id.seatchtext01);
        searchlist = (ListView) findViewById(R.id.searchlist);

        SearchProcess();
        ListviewProcess(sender);

        TapProcess();


    }
    public boolean onCreateOptionsMenu(Menu menu){
        //menuにmenu.xmlレイアアウトを適用
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    //メニュー選択時の処理
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_frend:
                Intent intent = new Intent(getApplication(),Friend_Lista.class);
                startActivity(intent);
                break;
            case R.id.action_searchfrends:
                Intent intent2= new Intent(getApplication(),Search_Friend.class);
                //intent.putExtra("Lista",mAdapter);
                startActivity(intent2);
                break;
            default:
                Intent intent3= new Intent(getApplication(),MainActivity.class);
                startActivity(intent3);
        }
        return true;
    }


    public void ListviewProcess(String name ){
//        ListView listView = new ListView(this);
//        setContentView(listView);

        // Search_Listview senderA = new Search_Listview("name");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);


        list.clear();
        list.add(name);

        searchlist.setAdapter(arrayAdapter);


    }



    public void TapProcess(){
        searchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(sender != "  ") {
                    getUsersRef().child(uuid).child("friend").child(idtext).setValue(new Search(sender));//検索した人をフレンドに加える

                }
            }
        });
    }

    public  void SearchProcess(){

        findViewById(R.id.searchbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //入力されたユーザIDを取得する
                idtext = searchid.getText().toString();



                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference();

                // TODO ユーザ名を取得する
                Query query = ref.child("users").child(idtext).child("MyName");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // ユーザ名取得
                        String myName = dataSnapshot.getValue().toString();
                        Log.i(TAG, "MyName: " + dataSnapshot + "wwwwwwwwwwwwwwwwwwwwwww"+myName);

                        sender = myName;

                        ListviewProcess(sender);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
