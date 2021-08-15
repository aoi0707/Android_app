package com.example.mytestapplication.purchaseList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytestapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView表示画面に関連するクラス
 * SelectSheetListView
 */
public class SelectSheetListViews extends AppCompatActivity {
    private DBAdapters dbAdapters;
    private MyBaseAdapter myBaseAdapter;
    private List<MyListItems> items;
    private ListView mListView;
    protected MyListItems myListItems;

    // 参照するDBのカラム：ID,品名,産地,個数,単価の全部なのでnullを指定
    private final String[] columns = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_sheet_listview);
        setTitle(R.string.title_selectsheet_product_list);

        // DBAdapterのコンストラクタ呼び出し
        dbAdapters = new DBAdapters(this);

        // itemsのArrayList生成
        items = new ArrayList<>();

        // MyBaseAdapterのコンストラクタ呼び出し(myBaseAdapterのオブジェクト生成)
        myBaseAdapter = new MyBaseAdapter(this, items);

        // ListViewの結び付け
        mListView = (ListView) findViewById(R.id.list_item);

        loadMyList();   // DBを読み込む＆更新する処理

        // 行を長押しした時の処理
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                // アラートダイアログ表示
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectSheetListViews.this);
                builder.setTitle("削除");
                builder.setMessage("削除しますか？");
                // OKの時の処理
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // IDを取得する
                        myListItems = items.get(position);
                        int listId = myListItems.getId();

                        dbAdapters.openDB();     // DBの読み込み(読み書きの方)
                        dbAdapters.selectDelete(String.valueOf(listId));     // DBから取得したIDが入っているデータを削除する
                        Log.d("Long click : ", String.valueOf(listId));
                        dbAdapters.closeDB();    // DBを閉じる
                        loadMyList();
                    }
                });

                builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                // ダイアログの表示
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });
    }

    /**
     * DBを読み込む＆更新する処理
     * loadMyList()
     */
    private void loadMyList() {

        //ArrayAdapterに対してListViewのリスト(items)の更新
        items.clear();

        dbAdapters.openDB();     // DBの読み込み(読み書きの方)

        // DBのデータを取得
        Cursor c = dbAdapters.getDB(columns);

        if (c.moveToFirst()) {
            do {
                // MyListItemのコンストラクタ呼び出し(myListItemのオブジェクト生成)
                myListItems = new MyListItems(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5));

                Log.d("取得したCursor(ID):", String.valueOf(c.getInt(0)));
                Log.d("取得したCursor(品名):", c.getString(1));
                Log.d("取得したCursor(ブランド名):", c.getString(2));
                Log.d("取得したCursor(産地):", c.getString(3));
                Log.d("取得したCursor(個数):", c.getString(4));
                Log.d("取得したCursor(値段):", c.getString(5));

                items.add(myListItems);          // 取得した要素をitemsに追加

            } while (c.moveToNext());
        }
        c.close();
        dbAdapters.closeDB();                    // DBを閉じる
        mListView.setAdapter(myBaseAdapter);  // ListViewにmyBaseAdapterをセット
        myBaseAdapter.notifyDataSetChanged();   // Viewの更新
    }

    /**
     * BaseAdapterを継承したクラス
     * MyBaseAdapter
     */
    public class MyBaseAdapter extends BaseAdapter {

        private final Context context;
        private final List<MyListItems> items;

        // 毎回findViewByIdをする事なく、高速化が出来るようするholderクラス
        private class ViewHolder {
            TextView textProduct;
            TextView textBrand;
            TextView textMadeIn;
            TextView textNumber;
            TextView textPrice;
        }

        // コンストラクタの生成
        public MyBaseAdapter(Context context, List<MyListItems> items) {
            this.context = context;
            this.items = items;
        }

        // Listの要素数を返す
        @Override
        public int getCount() {
            return items.size();
        }

        // indexやオブジェクトを返す
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        // IDを他のindexに返す
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 新しいデータが表示されるタイミングで呼び出される
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;
            ViewHolder holder;

            // データを取得
            myListItems = items.get(position);


            if (view == null) {
                LayoutInflater inflater =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_sheet_listviews, parent, false);

                TextView textProduct = (TextView) view.findViewById(R.id.text_data_product);      // 品名のTextView
                TextView textBrand = (TextView) view.findViewById(R.id.text_data_brand);          // ブランド名のTextView
                TextView textMadeIn = (TextView) view.findViewById(R.id.text_data_maidin);        // 産地のTextView
                TextView textNumber = (TextView) view.findViewById(R.id.text_data_number);        // 個数のTextView
                TextView textPrice = (TextView) view.findViewById(R.id.text_data_price);          // 単価のTextView

                // holderにviewを持たせておく
                holder = new ViewHolder();
                holder.textProduct = textProduct;
                holder.textBrand = textBrand;
                holder.textMadeIn = textMadeIn;
                holder.textNumber = textNumber;
                holder.textPrice = textPrice;
                view.setTag(holder);

            } else {
                // 初めて表示されるときにつけておいたtagを元にviewを取得する
                holder = (ViewHolder) view.getTag();
            }

            // 取得した各データを各TextViewにセット
            holder.textProduct.setText(myListItems.getProduct());
            holder.textBrand.setText(myListItems.getBrand());
            holder.textMadeIn.setText(myListItems.getMadeIn());
            holder.textNumber.setText(myListItems.getNumber());
            holder.textPrice.setText(myListItems.getPrice());

            return view;

        }
    }
}