package com.example.carecaremarkets.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.carecaremarkets.tables.ServiceProviders;
import com.example.carecaremarkets.tables.Users;

import java.util.List;

@Dao
public interface ServiceProvidersDAO {

    @Query("SELECT * FROM ServiceProviders WHERE governorate = :governorate ORDER by rate DESC LIMIT 10")
    List<ServiceProviders> getTop10ServiceProviders(String governorate);

    @Query("SELECT * FROM ServiceProviders WHERE name_AR Like :serviceProvider OR name_EN Like :serviceProvider AND governorate = :governorate")
    List<ServiceProviders> getServiceProviders(String serviceProvider,String governorate);


    @Query("SELECT * FROM ServiceProviders WHERE governorate = :governorate ORDER by rate AND reviews_count DESC LIMIT 10")
    List<ServiceProviders> getTopServiceProviders(String governorate);


    @Query("SELECT * FROM ServiceProviders ORDER by rate AND reviews_count DESC LIMIT 60")
    List<ServiceProviders> getTopServiceProvidersWithoutGovernorate();

}
