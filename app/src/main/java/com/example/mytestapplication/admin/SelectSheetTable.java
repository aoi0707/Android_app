package com.example.mytestapplication.admin;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytestapplication.R;

/**
 * TableLayout画面に関連するクラス
 * SelectSheetTable
 */
public class SelectSheetTable extends AppCompatActivity  implements View.OnFocusChangeListener, SearchView.OnQueryTextListener {
    DBAdapter dbAdapter;

    private SearchView mSearchView;           // 検索窓
    private TableLayout mTableLayoutList;     //データ表示用TableLayout

    private int colorFlg = 1;                 //背景切り替え用フラグ

    private final static int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final static int GCH = Gravity.CENTER_HORIZONTAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_sheet_table);
        setTitle(R.string.title_selectsheet_tabel);    // タイトルバーのタイトルをセット

        dbAdapter = new DBAdapter(this);

        findViews();           // 各部品の結び付け

        // 検索窓を開いた状態にする(設定していない場合はアイコンをクリックしないと入力箇所が開かない)
        mSearchView.setIconified(false);
        // 検索窓のイベント処理
        mSearchView.setOnQueryTextListener(this);
    }

/**
 * 各部品の結びつけ処理
 * findViews()
 */
private void findViews() {
        mSearchView = (SearchView) findViewById(R.id.search_view_hint_text); // 検索窓
        mTableLayoutList = (TableLayout) findViewById(R.id.layout_list);     //データ表示用TableLayout
        }

/**
 * SearchViewの各イベント処理
 */
@Override
public void onFocusChange(View v, boolean hasFocus) {

        }

// 検索を始める時
@Override
public boolean onQueryTextSubmit(String query) {

        dbAdapter.readDB();         // DBの読み込み(読み込みの方)

        mSearchView.clearFocus();   // 検索窓のフォーカスを外す(=キーボードを非表示)

        TableRow rowHeader = new TableRow(this);                  // 行を作成
        rowHeader.setPadding(16, 12, 16, 12);       // 行のパディングを指定(左, 上, 右, 下)

        TextView headerName = setTextItem("名前", GCH);          // ヘッダー：名前
        TableRow.LayoutParams paramsName = setParams(0.3f);
        TextView headerEmail = setTextItem("Email", GCH);       // ヘッダー：Email
        TableRow.LayoutParams paramsEmail = setParams(0.5f);
        TextView headerPassword = setTextItem("Password", GCH); // ヘッダー：Password
        TableRow.LayoutParams paramsPassword = setParams(0.3f);

        // rowHeaderにヘッダータイトルを追加
        rowHeader.addView(headerName, paramsName);             // ヘッダー：名前
        rowHeader.addView(headerEmail, paramsEmail);           // ヘッダー：Email
        rowHeader.addView(headerPassword, paramsPassword);     // ヘッダー：Password
        rowHeader.setBackgroundResource(R.drawable.row_deco1); // 背景

        // TableLayoutにrowHeaderを追加
        mTableLayoutList.addView(rowHeader);

        String column = "name";              //検索対象のカラム名
        String[] name = {query};             //検索対象の文字

        // DBの検索データを取得 入力した文字列を参照してDBの名前から検索
        Cursor c = dbAdapter.searchDB(null, column, name);

        if (c.moveToFirst()) {
        do {

        TableRow row = new TableRow(this);          // 行を作成
        row.setPadding(16, 12, 16, 12);             // 行のパディングを指定(左, 上, 右, 下)

        //名前
        TextView textName = setTextItem(c.getString(1), GCH);     // TextViewのカスタマイズ処理
        // Email
        TextView textEmail = setTextItem(c.getString(2), GCH);      // TextViewのカスタマイズ処理
        // Password
        TextView textPassword = setTextItem(c.getString(3), GCH);      // TextViewのカスタマイズ処理

        // rowHeaderに各項目(DBから取得した名前,Email,Password)を追加
        row.addView(textName, paramsName);         // 名前
        row.addView(textEmail, paramsEmail);       // Email
        row.addView(textPassword, paramsPassword); // Password

        mTableLayoutList.addView(row);            // TableLayoutにrowHeaderを追加

        // 交互に行の背景を変える
        if (colorFlg % 2 != 0) {
        row.setBackgroundResource(R.drawable.row_deco2);
        } else {
        row.setBackgroundResource(R.drawable.row_deco1);
        }
        colorFlg++;

        } while (c.moveToNext());
        } else {
        Toast.makeText(this, "検索結果 0件", Toast.LENGTH_SHORT).show();
        }
        c.close();
        dbAdapter.closeDB();        // DBを閉じる

        return false;
        }

// 検索窓のテキストが変わった時
@Override
public boolean onQueryTextChange(String newText) {

        mTableLayoutList.removeAllViews();        // TableLayoutのViewを全て消す

        return false;
        }

/**
 * 行の各項目のTextViewカスタマイズ処理
 * setTextItem()
 *
 * @param str     String
 * @param gravity int
 * @return title TextView タイトル
 */
private TextView setTextItem(String str, int gravity) {
        TextView title = new TextView(this);
        title.setTextSize(10.0f);           // テキストサイズ
        title.setTextColor(Color.BLACK);    // テキストカラー
        title.setGravity(gravity);          // テキストのGravity
        title.setText(str);                 // テキストのセット

        return title;
        }

/**
 * 行の各項目のLayoutParamsカスタマイズ処理
 * setParams()
 */
private TableRow.LayoutParams setParams(float f) {
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, WC);
        params.weight = f;      //weight(行内でのテキストごとの比率)

        return params;
        }

}