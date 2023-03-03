package com.example.carecaremarkets.daos;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.carecaremarkets.tables.UserOrders;
import com.example.carecaremarkets.tables.Users;

@Dao
public interface UserOrdersDAO {

    @Insert
    long order(UserOrders userOrders);
}
