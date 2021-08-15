package com.example.mytestapplication.purchaseList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytestapplication.R;

/**
 * メイン画面に関連するクラス
 * MainActivity
 */
public class TopActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener{

    public Object checkedID;
    private EditText mEditTextProduct;        // 品名
    private EditText mEditTextBrand;          //ブランド名
    private EditText mEditTextMadeIn;         //産地
    private EditText mEditTextNumber;         // 個数
    private EditText mEditTextPrice;          // 値段

    private TextView mTextKome01;             // 品名の※印
    private TextView mTextKome02;             // ブランドの※印
    private TextView mTextKome03;             // 産地の※印
    private TextView mTextKome04;             // 個数の※印
    private TextView mTextKome05;             // 値段の※印

    private Button mButtonRegist;             // 登録ボタン
    private Button mButtonShow;               // 表示ボタン

    private RadioGroup mRadioGroupShow;       // 選択用ラジオボタングループ
    private RadioGroup mRadioGroup;       // 選択用ラジオボタングループ

    private RadioButton mRadioButtonBrand;    //RadioButton(ブランド名)
    private RadioButton mRadioButtonMadeIn;   //RadioButton(産地名)

    private Intent intent;                      // インテント

    public Menu toggleMenu;
    boolean isflag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        setTitle(R.string.title_Top_page);

        findViews();        // 各部品の結びつけ処理

        init();             //初期値設定

        // ラジオボタン選択時
        mRadioGroupShow.setOnCheckedChangeListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);

        // 登録ボタン押下時処理
        mButtonRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // キーボードを非表示
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // DBに登録
                saveList();

            }
        });

        // 表示ボタン押下時処理
        mButtonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent != null) {
                    startActivity(intent);      // 各画面へ遷移
                }
            }
        });
//        ブランド名または産地のラジオボタン押下時の処理
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.button_brand_name:
                        mEditTextBrand.setVisibility(View.VISIBLE);
                        mEditTextMadeIn.setVisibility(View.GONE);
                        break;

                    case R.id.button_madein:
                        mEditTextMadeIn.setVisibility(View.VISIBLE);
                        mEditTextBrand.setVisibility(View.GONE);
                        break;
                }

            }
        });
    }
    //　　　menuの情報
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //main.xmlの内容を読み込む
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        toggleMenu = menu;
        toggleItem();
        return super.onPrepareOptionsMenu(menu);
    }
//menuの表示・非表示設定
    public void toggleItem(){
        MenuItem item1= toggleMenu.findItem(R.id.menu_top);
        MenuItem item2= toggleMenu.findItem(R.id.menu_product_list);
        MenuItem item3= toggleMenu.findItem(R.id.menu_product_name_list);
        MenuItem item4= toggleMenu.findItem(R.id.menu_search_list);
        MenuItem item5= toggleMenu.findItem(R.id.menu_end);
        if(isflag)//デフォルト：false
        {
            item1.setVisible(true);//menu項目の購入管理画面を表示
            item2.setVisible(false);//menu項目の品名一覧を非表示
            item3.setVisible(false);//menu項目の品名リスト一覧を非表示
            item4.setVisible(false);//menu項目の検索一覧を非表示
            item5.setVisible(true);//menu項目の終了を表示
        }
        else
        {
            item1.setVisible(false);//menu項目の購入管理画面を非表示
            item2.setVisible(true);//menu項目の品名一覧を表示
            item3.setVisible(true);//menu項目の品名リスト一覧を表示
            item4.setVisible(true);//menu項目の検索一覧を表示
            item5.setVisible(true);//menu項目の終了を表示
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_top:
                toggleItem();
                Intent intent = new Intent(getApplication(), TopActivity.class);
                Toast.makeText(TopActivity.this, "購入管理画面へ遷移します", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                return true;

            case R.id.menu_product_list:
                toggleItem();
                Intent intentList = new Intent(getApplication(), SelectSheetProducts.class);
                Toast.makeText(TopActivity.this, "品名一覧へ遷移します", Toast.LENGTH_SHORT).show();
                startActivity(intentList);
                return true;

            case R.id.menu_product_name_list:
                toggleItem();
                Intent intentNameList = new Intent(getApplication(), SelectSheetListViews.class);
                Toast.makeText(TopActivity.this, "品名リスト一覧へ遷移します", Toast.LENGTH_SHORT).show();
                startActivity(intentNameList);
                return true;

            case R.id.menu_search_list:
                toggleItem();
                Intent intentSerchList = new Intent(getApplication(), SelectSheetTables.class);
                Toast.makeText(TopActivity.this, "検索一覧へ遷移します", Toast.LENGTH_SHORT).show();
                startActivity(intentSerchList);
                return true;

            case R.id.menu_end:
                toggleItem();
                this.finish();
                this.moveTaskToBack(true);
                Toast.makeText(TopActivity.this, "アプリを終了します", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 各部品の結びつけ処理
     * findViews()
     */
    private void findViews() {

        mEditTextProduct = (EditText) findViewById(R.id.edit_text_hint_product);     // 品名
        mEditTextBrand =  (EditText) findViewById(R.id.edit_text_hint_brand_name);   //ブランド名
        mEditTextMadeIn =  (EditText) findViewById(R.id.edit_text_hint_madein);      //産地
        mEditTextNumber = (EditText) findViewById(R.id.edit_text_hint_quantity);     // 個数
        mEditTextPrice = (EditText) findViewById(R.id.edit_text_hint_price);         // 単価

        mTextKome01 = (TextView) findViewById(R.id.text_Kome_product);               // 品名の※印
        mTextKome02 = (TextView) findViewById(R.id.text_Kome_brand_name);            // ブランド※印
        mTextKome03 = (TextView) findViewById(R.id.text_Kome_madein);                // 産地※印
        mTextKome04 = (TextView) findViewById(R.id.text_Kome_quantity);              // 個数の※印
        mTextKome05 = (TextView) findViewById(R.id.text_Kome_price);                 // 値段の※印

        mButtonRegist = (Button) findViewById(R.id.button_regist);                   // 登録ボタン
        mButtonShow = (Button) findViewById(R.id.button_show);                       // 表示ボタン

        mRadioButtonBrand = (RadioButton) findViewById(R.id.button_brand_name);      //RadioButton(ブランド名)
        mRadioButtonMadeIn = (RadioButton) findViewById(R.id.button_madein);         //RadioButton(産地名)

        mRadioGroupShow = (RadioGroup) findViewById(R.id.radio_group);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group_brand_madein);      // 選択用ラジオボタングループ

    }

    /**
     * 初期値設定 (EditTextの入力欄は空白、※印は消す)
     * init()
     */
    private void init() {
        mEditTextProduct.setText("");
        mEditTextBrand.setText("");
        mEditTextMadeIn.setText("");
        mEditTextNumber.setText("");
        mEditTextPrice.setText("");

        mTextKome01.setText("");
        mTextKome02.setText("");
        mTextKome03.setText("");
        mTextKome04.setText("");
        mTextKome05.setText("");
        mEditTextProduct.requestFocus();      // フォーカスを品名のEditTextに指定
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.button_product:         // 品名一覧(ListView×ArrayAdapter)を選択した場合
                intent = new Intent(TopActivity.this, SelectSheetProducts.class);
                break;
            case R.id.button_product_list:        // ListView表示を選択した場合
                intent = new Intent(TopActivity.this, SelectSheetListViews.class);
                break;
            case R.id.button_search_table:     // TableLayout表示を選択した場合
                intent = new Intent(TopActivity.this, SelectSheetTables.class);
                break;
        }
    }

    /**
     * EditTextに入力したテキストをDBに登録
     * saveDB()
     */
    private void saveList() {

        // 各EditTextで入力されたテキストを取得
        String strProduct = mEditTextProduct.getText().toString();
        String strBrand = mEditTextBrand.getText().toString();
        String strMaidIn = mEditTextMadeIn.getText().toString();
        String strNumber = mEditTextNumber.getText().toString();
        String strPrice = mEditTextPrice.getText().toString();



        // EditTextが空白の場合
        if (strProduct.equals("") || strNumber.equals("") || strPrice.equals("")) {

            if (strProduct.equals("")) {
                mTextKome01.setText("※");     // 品名が空白の場合、※印を表示
            } else {
                mTextKome01.setText("");      // 空白でない場合は※印を消す
            }
            if (strNumber.equals("")) {
                mTextKome04.setText("※");     // 産地が空白の場合、※印を表示
            } else {
                mTextKome04.setText("");      // 空白でない場合は※印を消す
            }

            if (strPrice.equals("")) {
                mTextKome05.setText("※");     // 個数が空白の場合、※印を表示
            } else {
                mTextKome05.setText("");      // 空白でない場合は※印を消す
            }

            Toast.makeText(TopActivity.this, "※の箇所を入力して下さい。", Toast.LENGTH_SHORT).show();

        } else if(strBrand.equals("") && strMaidIn.equals("") ){

            if (strBrand.equals("")) {
                mTextKome02.setText("※");     // 品名が空白の場合、※印を表示
            } else {
                mTextKome02.setText("");      // 空白でない場合は※印を消す
            }
            if (strMaidIn.equals("")) {
                mTextKome03.setText("※");     // 品名が空白の場合、※印を表示
            } else {
                mTextKome03.setText("");      // 空白でない場合は※印を消す
            }

            Toast.makeText(TopActivity.this, "※の箇所を入力して下さい。", Toast.LENGTH_SHORT).show();

            }else{
            // 入力された単価と個数は文字列からint型へ変換
            int iNumber = Integer.parseInt(strNumber);
            int iPrice = Integer.parseInt(strPrice);

            // DBへの登録処理
            DBAdapters dbAdapters = new DBAdapters(this);
            dbAdapters.openDB();                                         // DBの読み書き
            dbAdapters.saveDB(strProduct, strBrand, strMaidIn, iNumber , iPrice);   // DBに登録
            dbAdapters.closeDB();                                        // DBを閉じる

            Toast.makeText(TopActivity.this, "登録しました", Toast.LENGTH_SHORT).show();

            init();     // 初期値設定

        }

    }

}