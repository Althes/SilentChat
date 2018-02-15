package jp.co.crowdworks.fugafugaworks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
                    getUsersRef().child(uuid).child("friend").child(idtext).setValue(new Search(sender));
                    //getUsersRef().child("test01").child("friend").push().setValue(new Users("test01"));
                    //Log.i(TAG,"wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
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

//
//                // TODO ユーザの検索
//                Query findUserQuery = ref.child("users");
//                findUserQuery.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });



                //Query query = ref.child("users").child(idtext).orderByChild(MyNameTtext).equalTo("つだ");//.child//.child("UUID").child("FUUID")//検索の奴～



                //Query query = ref.child("users").child(idtext).equalTo(idtext);//.child//.child("UUID").child("FUUID")//検索の奴～
//                query.addChildEventListener(new ChildEventListener() {
//
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {
//
//                        Object profile = dataSnapshot.getValue();
//
//                        // ユーザ名を取得
//                        Log.i(TAG,"profile:"+profile);
//
//                        // ユーザの電話番号を取得
//
//
//                        sender = dataSnapshot.child(MyNameTtext).getValue().toString();
//                        //Log.d("Firebase", String.format("wwwwwwwwwwwww:%s", sender));
//
//                        resultid.setText(sender);//テキストの表示
//                        //onResume();
//                        ListviewProcess(sender);
//                    }

//                    @Override
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                    }
//
//                    @Override
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//                    }
//
//                    @Override
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });




            }
        });
    }


    /*
    @Override
    protected void onResume() {
        super.onResume();


       uAdapter = new FirebaseListAdapter<Search>(this, Search.class, android.R.layout.simple_list_item_1, getUsersRef().child(idtext).child("MyName")) {
            @Override
            protected void populateView(View v, Search model, int position) {
                ((TextView) v).setText(model.MyName);
                // Log.i("      test",position);
            }
        };
      /*
        uAdapter = new FirebaseListAdapter<Friend>(this, Friend.class, android.R.layout.simple_list_item_1, getUsersRef().child("p48LnTPoSJQLag8NjUuNc1BvUTO2").child("friend")) {
            @Override
            protected void populateView(View v, Friend model, int position) {
                ((TextView) v).setText(model.FName);
                // Log.i("      test",position);
            }
        };

        //リストビューにFirebaseのメッセージをいれてる？

        ListView listview = (ListView) findViewById(R.id.searchlist);
        listview.setAdapter(uAdapter);
    }
    */


}
