package com.example.carecaremarkets.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.carecaremarkets.tables.Products;
import com.example.carecaremarkets.tables.ServiceProviders;

import java.util.List;

@Dao
public interface ProductsDAO {
    @Query("SELECT * FROM Products ORDER by rate DESC LIMIT 20")
    List<Products> getTop20Products();

    @Query("SELECT * FROM Products WHERE product_id = :product_id")
    Products getProduct(int product_id);

    @Query("SELECT * FROM Products\n" +
            "ORDER BY random() \n" +
            "LIMIT 15;")
    List<Products> getRandomProducts();

}
