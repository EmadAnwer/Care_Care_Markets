package com.example.carecaremarkets.tables;

import static com.example.carecaremarkets.constants.EN;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.carecaremarkets.constants;

import java.sql.Blob;
@Entity(tableName = "ServiceProviders")
public class ServiceProviders {
    /*
    "sp_id"	INTEGER UNIQUE,
	"name_AR"	TEXT,
	"name_EN"	TEXT,
	"address_AR"	TEXT,
	"address_EN"	TEXT,
	"email"	TEXT,
	"phone"	TEXT,
	"SP_picture"	BLOB,
	"rate"	REAL,
	"reviews_count"	INTEGER,
     */
    @PrimaryKey(autoGenerate = true)
    public int sp_id;
    public String name_AR;
    public String governorate;
    public String name_EN;
    public String address_AR;
    public String address_EN;
    public String email;
    public String phone;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] SP_picture;
    public double rate;
    public int reviews_count;

   // protected String name;

    /*public ServiceProviders(int sp_id, String name_AR, String name_EN, String address_AR, String address_EN, String email, String phone, byte[] SP_picture, double rate, int reviews_count) {
        this.sp_id = sp_id;
        this.name_AR = name_AR;
        this.name_EN = name_EN;
        this.address_AR = address_AR;
        this.address_EN = address_EN;
        this.email = email;
        this.phone = phone;
        this.SP_picture = SP_picture;
        this.rate = rate;
        this.reviews_count = reviews_count;
    }*/

    public ServiceProviders(int sp_id, String name_AR, String governorate, String name_EN, String address_AR, String address_EN, String email, String phone, byte[] SP_picture, double rate, int reviews_count) {
        this.sp_id = sp_id;
        this.name_AR = name_AR;
        this.governorate = governorate;
        this.name_EN = name_EN;
        this.address_AR = address_AR;
        this.address_EN = address_EN;
        this.email = email;
        this.phone = phone;
        this.SP_picture = SP_picture;
        this.rate = rate;
        this.reviews_count = reviews_count;
    }

    public int getSp_id() {
        return sp_id;
    }

    public String getName_AR() {
        return name_AR;
    }

    public String getName_EN() {
        return name_EN;
    }

 /*   public String getName()
    {
        if(constants.LANGUAGE == EN)
            this.name = name_EN;
        else
            this.name = name_AR;
        return name;
    }*/

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
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

    public byte[] getSP_picture() {
        return SP_picture;
    }

    public float getRate() {
        return (float) rate;
    }

    public int getReviews_count() {
        return reviews_count;
    }
}
