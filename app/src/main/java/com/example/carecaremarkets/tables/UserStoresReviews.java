package com.example.carecaremarkets.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserStoresReviews")
public class UserStoresReviews {

    /*
    "user_id"	INTEGER,
	"store_id"	INTEGER,
	"rate"	REAL,
	"review"	TEXT
     */
    @PrimaryKey(autoGenerate = true)
    public int review_id;
    public int user_id;
    public int store_id;
    public double rate;
    public String review;

    public UserStoresReviews(int user_id, int store_id, double rate, String review) {
        this.user_id = user_id;
        this.store_id = store_id;
        this.rate = rate;
        this.review = review;
    }
}
