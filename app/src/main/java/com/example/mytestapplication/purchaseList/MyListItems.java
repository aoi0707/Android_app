package com.example.mytestapplication.purchaseList;

import android.util.Log;

public class MyListItems {
    protected int id;           // ID
    protected String product;   // 品名
    protected String brand;     //ブランド名
    protected String madeIn;    // 産地
    protected String number;    // 個数
    protected String price;     // 単価

    /**
     * MyListItems()
     *
     * @param id      int ID
     * @param product String 品名
     * @param brand   String ブランド名
     * @param madeIn  String 産地
     * @param number  String 個数
     * @param price   String 単価
     */
    public MyListItems(int id, String product, String brand, String madeIn, String number, String price) {
        this.id = id;
        this.product = product;
        this.brand = brand;
        this.madeIn = madeIn;
        this.number = number;
        this.price = price;
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
     * 品名を取得
     * getProduct()
     *
     * @return product String 品名
     */
    public String getProduct() {
        return product;
    }

    /**
     * ブランド名を取得
     * getbrand()
     *
     * @return madeIn String 産地
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 産地を取得
     * getMadeIn()
     *
     * @return madeIn String 産地
     */
    public String getMadeIn() {
        return madeIn;
    }

    /**
     * 個数を取得
     * getNumber()
     *
     * @return number String 個数
     */
    public String getNumber() {
        return number;
    }

    /**
     * 値段を取得
     * getPrice()
     *
     * @return price String 値段
     */
    public String getPrice() {
        return price;
    }
}

