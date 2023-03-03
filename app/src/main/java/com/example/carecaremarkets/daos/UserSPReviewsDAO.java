package com.example.carecaremarkets.daos;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.carecaremarkets.tables.UserSPReviews;

import java.util.List;

@Dao
public interface UserSPReviewsDAO {

    @Query("SELECT UserSPReviews.review,UserSPReviews.rate,UserSPReviews.SP_id,Users.first_name,Users.profile_picture,Users.user_id \n" +
            "FROM UserSPReviews\n" +
            "INNER JOIN Users\n" +
            "ON UserSPReviews.user_id = Users.user_id WHERE sp_id = :sp_id")
    List<UserSPR> getAllReviews(int sp_id);




    @Insert
    void insertReview(UserSPReviews userSPReviews);

}

