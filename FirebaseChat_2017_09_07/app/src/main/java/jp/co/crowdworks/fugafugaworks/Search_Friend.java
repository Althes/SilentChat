package jp.co.crowdworks.fugafugaworks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by 4163211 on 2017/12/19.
 */

public class Search_Friend extends AppCompatActivity {

    EditText searchid;
    TextView resultid;

    String idtext,sender;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_friend);

        searchid = (EditText)findViewById(R.id.searchtext);
        resultid =(TextView)findViewById(R.id.seatchtext01);

        SearchProcess();
    }


    public  void SearchProcess(){

        findViewById(R.id.searchbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ボタンををしてからの処理
                idtext = searchid.getText().toString();
                resultid.setText(idtext);
                serchData();
            }
        });
    }

    public void serchData(){
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference();

                Query query = ref.child("tamesi").orderByChild("ID").equalTo("000");//.child("UUID").child("FUUID")
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {
                        sender = dataSnapshot.child("ID").getValue().toString();
                        //String body = dataSnapshot.child("UUID").getValue().toString();
                        //Log.d("Firebase", String.format("Message:%s, UUID:%s", sender, body));
                        Log.d("Firebase", String.format("wwwwwwwwwwwww:%s", sender));
                        //SnapshotData snapshotData = new SnapshotData();
                        //snapshotData.setUuid(dataSnapshot.child("UUID").getValue().toString());
                        //snapshotData.setMessage(dataSnapshot.child("Message").getValue().toString());
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }

}
