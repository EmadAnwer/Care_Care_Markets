package com.example.carecaremarkets.tables;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "OrderProducts")
public class OrderProducts {
    /*
    "order_id"	INTEGER,
	"product_id"	INTEGER
     */

    @PrimaryKey(autoGenerate = true)
    public int orderProducts_id;
    public int  order_id;
    public int product_id;

    public OrderProducts(int order_id, int product_id) {
        this.order_id = order_id;
        this.product_id = product_id;
    }
}
