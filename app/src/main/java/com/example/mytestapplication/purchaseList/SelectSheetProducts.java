package com.example.mytestapplication.purchaseList;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytestapplication.R;

import java.util.ArrayList;

public class SelectSheetProducts extends AppCompatActivity {

    private DBAdapters dbAdapters;                // DBAdapters
    private ArrayAdapter<String> adapter;         // ArrayAdapter
    private ArrayList<String> items;              // ArrayList

    private ListView mListViewProduct;         // ListView
    private Button mButtonAllDelete;           // 全削除ボタン

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_sheet_name);
        setTitle(R.string.title_selectsheet_product);

        dbAdapters = new DBAdapters(this);
        dbAdapters.openDB();     // DBの読み込み(読み書きの方)

        findViews();            // 各部品の結び付け

        // ArrayListを生成
        items = new ArrayList<>();

        // DBのデータを取得
        String[] columns = {DBAdapters.COL_PRODUCT};     // DBのカラム：品名
        Cursor c = dbAdapters.getDB(columns);

        if (c.moveToFirst()) {
            do {
                items.add(c.getString(0));
                Log.d("取得したCursor:", c.getString(0));
            } while (c.moveToNext());
        }
        c.close();
        dbAdapters.closeDB();    // DBを閉じる

        // ArrayAdapterのコンストラクタ
        // 第1引数：Context
        // 第2引数：リソースとして登録されたTextViewに対するリソースID 今回は元々用意されている定義済みのレイアウトファイルのID
        // 第3引数：一覧させたいデータの配列
        adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, items);

        mListViewProduct.setAdapter(adapter);     //ListViewにアダプターをセット(=表示)

        // ArrayAdapterに対してListViewのリスト(items)の更新
        adapter.notifyDataSetChanged();

        // 全削除ボタン押下時処理
        mButtonAllDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!items.isEmpty()) {
                    dbAdapters.openDB();     // DBの読み込み(読み書きの方)
                    dbAdapters.allDelete();  // DBのレコードを全削除
                    dbAdapters.closeDB();    // DBを閉じる

                    //ArrayAdapterに対してListViewのリスト(items)の更新
                    adapter.clear();
                    adapter.addAll(items);
                    adapter.notifyDataSetChanged(); // // Viewの更新

                    Toast.makeText(SelectSheetProducts.this, "登録したデータをすべて削除しました", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(SelectSheetProducts.this, "登録されているデータがありません", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * 各部品の結びつけ処理
     * findViews()
     */
    private void findViews() {
        mListViewProduct = (ListView) findViewById(R.id.list_item);       // 品名一覧用のListView
        mButtonAllDelete = (Button) findViewById(R.id.button_all_delete);         // 全削除ボタン
    }

}