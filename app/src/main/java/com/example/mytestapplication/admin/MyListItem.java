package com.example.mytestapplication.admin;

import android.util.Log;

/**
 * SelectSheetListViewに必要なデータを取得するクラス
 * MyListItem
 */
public class MyListItem {
    protected int id;           // ID
    protected String name;      // 名前
    protected String email;     // Email
    protected String password;  // Password

    /**
     * MyListItem()
     *
     * @param id        int ID
     * @param name      String 名前
     * @param email     String Email
     * @param password  String Password
     */
    public MyListItem(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * IDを取得
     * getId()
     *
     * @return id int ID
     */
    public int getId() {
        Log.d("取得したID：", String.valueOf(id));
        return id;
    }

    /**
     * 名前を取得
     * getName()
     *
     * @return name String 名前
     */
    public String getName() {
        return name;
    }

    /**
     * 産地を取得
     * getEmail()
     *
     * @return email String Email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 個数を取得
     * getPassword()
     *
     * @return password String Password
     */
    public String getPassword() {
        return password;
    }

}
