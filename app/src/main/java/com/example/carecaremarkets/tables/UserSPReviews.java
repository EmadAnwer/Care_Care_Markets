package com.example.carecaremarkets.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserSPReviews")
public class UserSPReviews {
    /*
    	"user_id"	INTEGER,
	    "SP_id"	INTEGER,
	    "rate"	REAL,
	    "review"	TEXT
     */
    @PrimaryKey(autoGenerate = true)
    public int review_id;
    public int user_id;
    public int SP_id;
    public double rate;
    public String review;


    public UserSPReviews(int user_id, int SP_id, double rate, String review) {
        this.user_id = user_id;
        this.SP_id = SP_id;
        this.rate = rate;
        this.review = review;
    }


}


