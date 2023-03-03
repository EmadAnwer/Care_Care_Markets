package com.example.carecaremarkets;


import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.carecaremarkets.daos.OrderProductsDAO;
import com.example.carecaremarkets.daos.ProductsDAO;
import com.example.carecaremarkets.daos.ServiceProvidersDAO;
import com.example.carecaremarkets.daos.ServicesDAO;
import com.example.carecaremarkets.daos.StoresDAO;
import com.example.carecaremarkets.daos.UserOrdersDAO;
import com.example.carecaremarkets.daos.UserProductsReviewsDAO;
import com.example.carecaremarkets.daos.UserSPReviewsDAO;
import com.example.carecaremarkets.daos.UserStoresReviewsDAO;
import com.example.carecaremarkets.daos.UsersDAO;
import com.example.carecaremarkets.tables.OrderProducts;
import com.example.carecaremarkets.tables.Products;
import com.example.carecaremarkets.tables.ServiceProviders;
import com.example.carecaremarkets.tables.Services;
import com.example.carecaremarkets.tables.Stores;
import com.example.carecaremarkets.tables.UserOrders;
import com.example.carecaremarkets.tables.UserProductsReviews;
import com.example.carecaremarkets.tables.UserSPReviews;
import com.example.carecaremarkets.tables.UserStoresReviews;
import com.example.carecaremarkets.tables.Users;

@Database(
        entities = {
                Users.class,
                OrderProducts.class,
                Products.class,
                ServiceProviders.class,
                Services.class,
                Stores.class,
                UserOrders.class,
                UserProductsReviews.class,
                UserSPReviews.class,
                UserStoresReviews.class}, version = 2)



public abstract class CareCareMarketsDatabase extends RoomDatabase {
    public abstract OrderProductsDAO orderProductsDAO();
    public abstract ProductsDAO productsDAO();
    public abstract ServiceProvidersDAO serviceProvidersDAO();
    public abstract ServicesDAO servicesDAO();
    public abstract StoresDAO storesDAO();
    public abstract UserOrdersDAO userOrdersDAO();
    public abstract UserProductsReviewsDAO userProductsReviewsDAO();
    public abstract UsersDAO usersDAO();
    public abstract UserSPReviewsDAO userSPReviewsDAO();
    public abstract UserStoresReviewsDAO userStoresReviewsDAO();

    private static CareCareMarketsDatabase ourInterface;

    public static CareCareMarketsDatabase getInstance(Context context){

        if(ourInterface == null)
        {
            ourInterface = Room.databaseBuilder(context,
                            CareCareMarketsDatabase.class, "carcaremarket.db").
                    createFromAsset("databases/carcaremarket.db").
                    allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return  ourInterface;

    }

}
