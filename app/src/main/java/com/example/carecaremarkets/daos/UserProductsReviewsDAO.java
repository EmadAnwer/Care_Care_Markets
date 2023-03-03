package com.example.carecaremarkets.daos;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.carecaremarkets.tables.UserProductsReviews;
import com.example.carecaremarkets.tables.UserSPReviews;

import java.util.List;

@Dao
public interface UserProductsReviewsDAO {

    @Query("SELECT product_picture FROM Products WHERE product_id = :product_id")
    byte[] getProductPicture(int product_id);

    @Query("SELECT UserProductsReviews.review,UserProductsReviews.rate,UserProductsReviews.product_id,Users.first_name,Users.profile_picture,Users.user_id \n" +
            "FROM UserProductsReviews\n" +
            "INNER JOIN Users\n" +
            "ON UserProductsReviews.user_id = Users.user_id WHERE product_id = :product_id")
    List<UserPR> getAllReviews(int product_id);


    @Insert
    void insertReview(UserProductsReviews UserProductsReviews);
}

