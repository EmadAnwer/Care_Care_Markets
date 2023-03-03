package com.example.carecaremarkets.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserProductsReviews")
public class UserProductsReviews {
    /*
    	"user_id"	INTEGER,
	    "product_id"	INTEGER,
	    "rate"	REAL,
	    "review"	TEXT
     */
    @PrimaryKey(autoGenerate = true)
    public int review_id;
    public int user_id;
    public int product_id;
    public double rate;
    public String review;

    public UserProductsReviews(int user_id, int product_id, double rate, String review) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.rate = rate;
        this.review = review;
    }
}
