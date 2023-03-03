package com.example.carecaremarkets.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.carecaremarkets.tables.Services;
import com.example.carecaremarkets.tables.Stores;

import java.util.List;

@Dao
public interface ServicesDAO {
    @Query("SELECT * FROM Services WHERE sp_id = :sp_id")
    List<Services> getServices(int sp_id);
}
