package jp.co.crowdworks.fugafugaworks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

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
    MainActivity main = new MainActivity();
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
    //オプションメニュー作成
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

    @Override
    protected void onResume() {
        super.onResume();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);

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
