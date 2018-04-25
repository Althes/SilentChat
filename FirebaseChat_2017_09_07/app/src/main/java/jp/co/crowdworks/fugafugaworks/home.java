package jp.co.crowdworks.fugafugaworks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by 4163208 on 2018/04/04.
 */

   public class home extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.home);

            // ListViewに表示する項目を生成
            ArrayList<String> nowList= new ArrayList<>();
           nowList.add("こん");
            nowList.add("tya");

            /**
             * Adapterを生成
             * android.R.layout.simple_list_item_1 : リストビュー自身のレイアウト。今回はAndroid標準のレイアウトを使用。
             * noodleList : Adapterのコンストラクタの引数としてListViewに表示する項目のリストを渡す。
             */
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nowList);

            // idがlistのListViewを取得
            ListView listView = (ListView) findViewById(R.id.nowList);
            listView.setAdapter(arrayAdapter);

        }

    }
