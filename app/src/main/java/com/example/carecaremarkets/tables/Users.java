package com.example.carecaremarkets.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import org.jetbrains.annotations.NotNull;

import java.sql.Blob;
import java.util.Arrays;

@Entity(tableName = "Users")
public class Users {
 /*
    "user_id"	INTEGER UNIQUE,
	"first_name"	TEXT NOT NULL,
	"mid_name"	TEXT,
	"last_name"	TEXT NOT NULL,
	"email"	TEXT,
	"password"	TEXT NOT NULL,
	"phone"	TEXT NOT NULL,
	"governorate"	TEXT NOT NULL,
	"address"	TEXT,
	"profile_picture"	BLOB,
  */

    @PrimaryKey(autoGenerate = true)

    public int user_id;
    public String first_name;
    public String mid_name;
    public String last_name;
    public String email;
    public String password;
    public String phone;
    public String governorate;
    public String address;
   @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] profile_picture;

    public Users() {
    }


    public Users(String first_name, String email, String password, String phone) {
        this.first_name = first_name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Users(int user_id, String first_name, String mid_name, String last_name, String email, String password, String phone, String governorate, String address, byte[] profile_picture) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.mid_name = mid_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.governorate = governorate;
        this.address = address;
        this.profile_picture = profile_picture;
    }

    @Override
    public String toString() {
        return "Users{" +
                "user_id=" + user_id +
                ", first_name='" + first_name + '\'' +
                ", mid_name='" + mid_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", governorate='" + governorate + '\'' +
                ", address='" + address + '\'' +
                ", profile_picture=" + Arrays.toString(profile_picture) +
                '}';
    }
}
