package com.example.carecaremarkets.daos;

import androidx.room.ColumnInfo;

public class UserSR {

    @ColumnInfo(name = "review")
    public String review;

    @ColumnInfo(name = "rate")
    public double rate;


    @ColumnInfo(name = "first_name")
    public String name;

    @ColumnInfo(name = "profile_picture")
    public byte[] profilePicture;

    @ColumnInfo(name = "user_id")
    public int id;

    @ColumnInfo(name = "store_id")
    public int store_id;

    public double getRate() {
        return rate;
    }
}
