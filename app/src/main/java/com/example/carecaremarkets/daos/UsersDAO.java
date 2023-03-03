package com.example.carecaremarkets.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carecaremarkets.tables.Users;

@Dao
public interface UsersDAO {
    // check user
    @Query("SELECT * FROM Users WHERE email = :email AND password = :password")
    Users login(String email,String password);

    @Query("SELECT * FROM Users WHERE email = :email")
    Users checkEmail(String email);

    @Query("SELECT profile_picture FROM Users WHERE user_id = :user_id")
    byte[] getUserPhoto(int user_id);



    @Query("UPDATE Users SET profile_picture = :profile_picture WHERE user_id = :user_id")
    void updateProfilePic(byte[] profile_picture, int user_id);

    @Query("UPDATE Users SET first_name = :name,phone = :phone,address = :address,governorate = :governorate, profile_picture = :profile_picture WHERE user_id = :user_id")
    void UpdateAccount(int user_id,byte[] profile_picture,String name,String phone,String address,String governorate);
    @Insert
    void createAnAccount(Users users);


}
