package com.example.carecaremarkets.daos;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.carecaremarkets.tables.OrderProducts;
import com.example.carecaremarkets.tables.UserOrders;

@Dao
public interface OrderProductsDAO {

    @Insert
    void orderProducts(OrderProducts orderProducts);
}
