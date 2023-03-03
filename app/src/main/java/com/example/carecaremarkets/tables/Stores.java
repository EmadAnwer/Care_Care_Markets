package com.example.carecaremarkets.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Blob;
@Entity(tableName = "Stores")
public class Stores {
    /*
    "store_id"	INTEGER NOT NULL UNIQUE,
	"name_AR"	TEXT NOT NULL,
	"name_EN"	TEXT NOT NULL,
	"address_AR"	TEXT NOT NULL,
	"address_EN"	TEXT NOT NULL,
	"email"	TEXT NOT NULL,
	"phone"	TEXT NOT NULL,
	"store_picture"	BLOB,
	"rate"	REAL,
	"reviews_count"	INTEGER,
     */

    @PrimaryKey(autoGenerate = true)
    public int store_id;
    public String name_AR;
    public String name_EN;
    public String address_AR;
    public String governorate;
    public String address_EN;
    public String email;
    public String phone;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] store_picture;
    public double rate;
    public int reviews_count;

/*    public Stores(int store_id, String name_AR, String name_EN, String address_AR, String address_EN, String email, String phone, byte[] store_picture, double rate, int reviews_count) {
        this.store_id = store_id;
        this.name_AR = name_AR;
        this.name_EN = name_EN;
        this.address_AR = address_AR;
        this.address_EN = address_EN;
        this.email = email;
        this.phone = phone;
        this.store_picture = store_picture;
        this.rate = rate;
        this.reviews_count = reviews_count;
    }*/

    public Stores(int store_id, String name_AR, String name_EN, String address_AR, String governorate, String address_EN, String email, String phone, byte[] store_picture, double rate, int reviews_count) {
        this.store_id = store_id;
        this.name_AR = name_AR;
        this.name_EN = name_EN;
        this.address_AR = address_AR;
        this.governorate = governorate;
        this.address_EN = address_EN;
        this.email = email;
        this.phone = phone;
        this.store_picture = store_picture;
        this.rate = rate;
        this.reviews_count = reviews_count;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }
    public int getStore_id() {
        return store_id;
    }

    public String getName_AR() {
        return name_AR;
    }

    public String getName_EN() {
        return name_EN;
    }

    public String getAddress_AR() {
        return address_AR;
    }

    public String getAddress_EN() {
        return address_EN;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public byte[] getStore_picture() {
        return store_picture;
    }

    public double getRate() {
        return rate;
    }

    public int getReviews_count() {
        return reviews_count;
    }
}
