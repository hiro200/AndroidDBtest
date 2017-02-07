package com.example.hiro.dbtest;

/**
 * Created by hiro on 2017-01-19.
 */

public class ListItem {
    private String[] mData;

    public ListItem(String[] data) {
        mData = data;
    }

    public ListItem(String name, String address) {
        mData = new String[2];

        mData[0] = name;

        mData[1] = address;


    }

    public String[] getData() {

        return mData;
    }

    public String getData(int index) {

        return mData[index];

    }

    public void setData(String[] data) {

        mData = data;

    }
}
