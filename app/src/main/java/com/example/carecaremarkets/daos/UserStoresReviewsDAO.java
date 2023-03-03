package com.example.carecaremarkets.daos;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.carecaremarkets.tables.UserProductsReviews;
import com.example.carecaremarkets.tables.UserStoresReviews;

import java.util.List;

@Dao
public interface UserStoresReviewsDAO {

    @Query("SELECT UserStoresReviews.review,UserStoresReviews.rate,UserStoresReviews.store_id,Users.first_name,Users.profile_picture,Users.user_id \n" +
            "FROM UserStoresReviews\n" +
            "INNER JOIN Users\n" +
            "ON UserStoresReviews.user_id = Users.user_id WHERE store_id = :store_id")
    List<UserSR> getAllReviews(int store_id);


    @Insert
    void insertReview(UserStoresReviews UserStoresReviews);
}

