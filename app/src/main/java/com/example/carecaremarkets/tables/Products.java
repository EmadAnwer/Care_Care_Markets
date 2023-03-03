package com.example.carecaremarkets.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Blob;

@Entity(tableName = "Products")
public class Products {
    /*
    "product_id"	INTEGER UNIQUE,
	"name_AR"	TEXT,
	"name_EN"	TEXT,
	"price"	INTEGER,
	"product_picture"	BLOB,
	"rate"	REAL,
	"reviews_count"	INTEGER,
     */

    @PrimaryKey(autoGenerate = true)
    public int product_id;
    public String name_AR;
    public String name_EN;
    public int price;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] product_picture;
    public double rate;
    public int reviews_count;

    public Products(int product_id, String name_AR, String name_EN, int price, byte[] product_picture, double rate, int reviews_count) {
        this.product_id = product_id;
        this.name_AR = name_AR;
        this.name_EN = name_EN;
        this.price = price;
        this.product_picture = product_picture;
        this.rate = rate;
        this.reviews_count = reviews_count;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getName_AR() {
        return name_AR;
    }

    public String getName_EN() {
        return name_EN;
    }

    public int getPrice() {
        return price;
    }

    public byte[] getProduct_picture() {
        return product_picture;
    }

    public double getRate() {
        return rate;
    }

    public int getReviews_count() {
        return reviews_count;
    }
}
