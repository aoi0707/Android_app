package com.example.mytestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytestapplication.admin.DBAdapter;
import com.example.mytestapplication.purchaseList.TopActivity;

public class SignUpActivity extends AppCompatActivity {
    String toastMessage;
    private EditText mTextName;
    private EditText mTextEmail;              // Email
    private EditText mTextPassword;           // Password

    private TextView mTextKome01;
    private TextView mTextKome02;             // Emailの※印
    private TextView mTextKome03;             // Passwordの※印

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle(R.string.text_title_signup);

        findViews();

        init();

        Button sendButton = findViewById(R.id.button_signup);
        sendButton.setOnClickListener(v -> {
            saveList();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    /**
     * 各部品の結びつけ処理
     * findViews()
     */
    private void findViews() {

        mTextName = (EditText) findViewById(R.id.edit_text_hint_name);
        mTextEmail = (EditText) findViewById(R.id.edit_text_hint_email);
        mTextPassword = (EditText) findViewById(R.id.edit_text_hint_password);

        mTextKome01 = (TextView) findViewById(R.id.text_Kome_name);
        mTextKome02 = (TextView) findViewById(R.id.text_Kome_email);
        mTextKome03 = (TextView) findViewById(R.id.text_Kome_password);

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
     * saveDB()
     */
    private void saveList() {

        // 各EditTextで入力されたテキストを取得
        String strName = mTextName.getText().toString();
        String strEmail = mTextEmail.getText().toString();
        String strPassword = mTextPassword.getText().toString();

        // EditTextが空白の場合
        if ( strName.equals("") || strEmail.equals("") || strPassword.equals("")) {

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

            Toast.makeText(SignUpActivity.this, "※の箇所を入力して下さい", Toast.LENGTH_SHORT).show();

        } else{        // EditTextが全て入力されている場合
            int iPassword = Integer.parseInt(strPassword);
            // DBへの登録処理
            DBAdapter dbAdapter = new DBAdapter(this);
            dbAdapter.openDB();                                         // DBの読み書き
            dbAdapter.saveDB(strName, strEmail, iPassword);   // DBに登録
            dbAdapter.closeDB();                                        // DBを閉じる

            Toast.makeText(SignUpActivity.this, "新規登録しました", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplication(), TopActivity.class);
            startActivity(intent);

            init();     // 初期値設定

        }


    }

}
