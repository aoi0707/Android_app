package com.example.mytestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytestapplication.admin.AdminPageActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText mTextEmail;              // Email
    private EditText mTextPassword;           // Password

    private TextView mTextKome01;             // Emailの※印
    private TextView mTextKome02;             // Passwordの※印

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.title_login);

        findViews();        // 各部品の結びつけ処理

        init();             //初期値設定

        Button sendButton = findViewById(R.id.button_login);
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
        mTextEmail = (EditText) findViewById(R.id.edit_text_hint_email);     // Email
        mTextPassword = (EditText) findViewById(R.id.edit_text_hint_password);     // Password

        mTextKome01 = (TextView) findViewById(R.id.text_Kome_email);             // Email※印
        mTextKome02 = (TextView) findViewById(R.id.text_Kome_password);             // Passwordの※印
    }

    /**
     * 初期値設定 (EditTextの入力欄は空白、※印は消す)
     * init()
     */
    private void init() {
        mTextEmail.setText("");
        mTextPassword.setText("");

        mTextKome01.setText("");
        mTextKome02.setText("");

        mTextEmail.requestFocus();      // フォーカスを品名のEditTextに指定
    }
    /**
     * EditTextに入力したテキストをDBに登録
     * saveDB()
     */
    private void saveList() {

        // 各EditTextで入力されたテキストを取得
        String strEmail = mTextEmail.getText().toString();
        String strPassword = mTextPassword.getText().toString();

        // EditTextが空白の場合
        if ( strEmail.equals("") || strPassword.equals("")) {

            if (strEmail.equals("")) {
                mTextKome01.setText("※");     // Emailが空白の場合、※印を表示
            } else {
                mTextKome01.setText("");      // 空白でない場合は※印を消す
            }

            if (strPassword.equals("")) {
                mTextKome02.setText("※");     // Passwordが空白の場合、※印を表示
            } else {
                mTextKome02.setText("");      // 空白でない場合は※印を消す
            }

            Toast.makeText(LoginActivity.this, "※の箇所を入力して下さい", Toast.LENGTH_SHORT).show();

        } else{        // EditTextが全て入力されている場合

            Toast.makeText(LoginActivity.this, "ログインしました", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplication(), AdminPageActivity.class);
            startActivity(intent);

            init();     // 初期値設定

        }


    }




}
