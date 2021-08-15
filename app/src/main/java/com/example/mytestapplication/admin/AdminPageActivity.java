package com.example.mytestapplication.admin;

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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytestapplication.MainActivity;
import com.example.mytestapplication.R;
import com.example.mytestapplication.purchaseList.SelectSheetListViews;
import com.example.mytestapplication.purchaseList.SelectSheetTables;
import com.example.mytestapplication.purchaseList.TopActivity;

/**
 * 管理者ページ画面に関連するクラス
 * AdminPageActivity
 */
public class AdminPageActivity<context> extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener{

private EditText mTextName;               // 名前
private EditText mTextEmail;              // Email
private EditText mTextPassword;           // Password

private TextView mTextKome01;             // 名前の※印
private TextView mTextKome02;             // Emailの※印
private TextView mTextKome03;             // Passwordの※印


private Button mButtonRegist;             // 登録ボタン
private Button mButtonShow;               // 表示ボタン

private RadioGroup mRadioGroupShow;       // 選択用ラジオボタングループ

private Intent intent;                      // インテント

    public Menu toggleMenu;
    boolean isflag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        setTitle(R.string.title_admin_page);

        findViews();        // 各部品の結びつけ処理

        init();             //初期値設定

        // ラジオボタン選択時
        mRadioGroupShow.setOnCheckedChangeListener(this);

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
    }

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

    public void toggleItem(){
        MenuItem item1= toggleMenu.findItem(R.id.menu_top);
        MenuItem item2= toggleMenu.findItem(R.id.menu_product_list);
        MenuItem item3= toggleMenu.findItem(R.id.menu_product_name_list);
        MenuItem item4= toggleMenu.findItem(R.id.menu_search_list);
        MenuItem item5= toggleMenu.findItem(R.id.menu_end);
        if(isflag)
        {
            item1.setVisible(true);
            item2.setVisible(false);
            item3.setVisible(false);
            item4.setVisible(false);
            item5.setVisible(true);
        }
        else
        {
            item1.setVisible(false);
            item2.setVisible(true);
            item3.setVisible(true);
            item4.setVisible(true);
            item5.setVisible(true);
        }
    }
       @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_top:
                toggleItem();
                Intent intent = new Intent(getApplication(), TopActivity.class);
                Toast.makeText(AdminPageActivity.this, "購入管理画面へ遷移します", Toast.LENGTH_SHORT).show();
                isflag = false;
                startActivity(intent);
                return true;

            case R.id.menu_product_list:
                toggleItem();
                Intent intentList = new Intent(getApplication(), MainActivity.class);
                Toast.makeText(AdminPageActivity.this, "品名一覧へ遷移します", Toast.LENGTH_SHORT).show();
                startActivity(intentList);
                return true;

            case R.id.menu_product_name_list:
                toggleItem();
                Intent intentNameList = new Intent(getApplication(), SelectSheetListViews.class);
                Toast.makeText(AdminPageActivity.this, "品名リスト一覧へ遷移します", Toast.LENGTH_SHORT).show();
                startActivity(intentNameList);
                return true;

            case R.id.menu_search_list:
                toggleItem();
                Intent intentSerchList = new Intent(getApplication(), SelectSheetTables.class);
                Toast.makeText(AdminPageActivity.this, "検索一覧へ遷移します", Toast.LENGTH_SHORT).show();
                startActivity(intentSerchList);
                return true;

            case R.id.menu_end:
                toggleItem();
                this.finish();
                this.moveTaskToBack(true);
                Toast.makeText(AdminPageActivity.this, "アプリを終了します", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 各部品の結びつけ処理
     * findViews()
     */
    private void findViews() {

        mTextName = (EditText) findViewById(R.id.edit_text_hint_name);             // 名前
        mTextEmail = (EditText) findViewById(R.id.edit_text_hint_email);           // Email
        mTextPassword = (EditText) findViewById(R.id.edit_text_hint_password);     // Password

        mTextKome01 = (TextView) findViewById(R.id.text_Kome_name);                // 名前の※印
        mTextKome02 = (TextView) findViewById(R.id.text_Kome_email);               // Email※印
        mTextKome03 = (TextView) findViewById(R.id.text_Kome_password);            // Passwordの※印

        mButtonRegist = (Button) findViewById(R.id.button_regist);                 // 登録ボタン
        mButtonShow = (Button) findViewById(R.id.button_show);                     // 表示ボタン

        mRadioGroupShow = (RadioGroup) findViewById(R.id.radioGroup01);            // 選択用ラジオボタングループ

    }

    /**
     * 初期値設定 (EditTextの入力欄は空白、※印は消す)
     * init()
     */
    private void init() {
        mTextName.setText("");
        mTextEmail.setText("");
        mTextPassword.setText("");

        mTextKome01.setText("");
        mTextKome02.setText("");
        mTextKome03.setText("");

        mTextName.requestFocus();      // フォーカスを品名のEditTextに指定
    }

    /**
     * ラジオボタン選択処理
     * onCheckedChanged()
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.button_name:         // 氏名一覧(ListView×ArrayAdapter)を選択した場合
                intent = new Intent(AdminPageActivity.this, SelectSheetName.class);
                break;
            case R.id.button_regist_list:        // ListView表示を選択した場合
                intent = new Intent(AdminPageActivity.this, SelectSheetListView.class);
                break;
            case R.id.button_search_table:     // TableLayout表示を選択した場合
                intent = new Intent(AdminPageActivity.this, SelectSheetTable.class);
                break;
        }
    }

    /**
     * EditTextに入力したテキストをDBに登録
     * saveDB()
     */
    private void saveList() {

        // 各EditTextで入力されたテキストを取得
        String strName = mTextName.getText().toString();
        String strEmail = mTextEmail.getText().toString();
        String strPassword = mTextPassword.getText().toString();

        // EditTextが空白の場合
        if (strName.equals("") || strEmail.equals("") || strPassword.equals("")) {

            if (strName.equals("")) {
                mTextKome01.setText("※");     // 名前が空白の場合、※印を表示
            } else {
                mTextKome01.setText("");      // 空白でない場合は※印を消す
            }

            if (strEmail.equals("")) {
                mTextKome02.setText("※");     // Emailが空白の場合、※印を表示
            } else {
                mTextKome02.setText("");      // 空白でない場合は※印を消す
            }

            if (strPassword.equals("")) {
                mTextKome03.setText("※");     // Passwordが空白の場合、※印を表示
            } else {
                mTextKome03.setText("");      // 空白でない場合は※印を消す
            }

            Toast.makeText(AdminPageActivity.this, "※の箇所を入力して下さい", Toast.LENGTH_SHORT).show();

        } else {        // EditTextが全て入力されている場合
            Toast.makeText(AdminPageActivity.this, "登録しました", Toast.LENGTH_SHORT).show();

            int iPassword = Integer.parseInt(strPassword);

            // DBへの登録処理
            DBAdapter dbAdapter = new DBAdapter(this);
            dbAdapter.openDB();                                         // DBの読み書き
            dbAdapter.saveDB(strName, strEmail, iPassword);   // DBに登録
            dbAdapter.closeDB();                                        // DBを閉じる

            init();     // 初期値設定

        }

    }


}