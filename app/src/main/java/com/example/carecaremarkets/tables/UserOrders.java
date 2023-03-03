package com.example.carecaremarkets.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "UserOrders")
public class UserOrders {
    /*
    "order_id"	INTEGER UNIQUE,
	"user_id"	INTEGER,
     */
    @PrimaryKey(autoGenerate = true)
    public int order_id;
    public int user_id;

    public UserOrders(int user_id) {
        this.user_id = user_id;
    }
}
