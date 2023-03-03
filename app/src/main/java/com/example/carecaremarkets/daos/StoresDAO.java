package com.example.carecaremarkets.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.carecaremarkets.tables.ServiceProviders;
import com.example.carecaremarkets.tables.Stores;

import java.util.List;

@Dao
public interface StoresDAO {
    @Query("SELECT * FROM Stores WHERE governorate = :governorate LIMIT 20")
    List<Stores> getTop20Stores(String governorate);



    @Query("SELECT * FROM Stores WHERE name_AR Like :store OR name_EN Like :store AND governorate = :governorate")
    List<Stores> getStores(String store,String governorate);


    @Query("SELECT * FROM Stores WHERE governorate = :governorate ORDER by rate AND reviews_count DESC LIMIT 10")
    List<Stores> getTopStores(String governorate);


    @Query("SELECT * FROM Stores ORDER by rate AND reviews_count DESC LIMIT 60")
    List<Stores> getTopStoresWithoutGovernorate();
}
