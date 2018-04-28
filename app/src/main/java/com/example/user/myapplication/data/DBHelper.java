package com.example.user.myapplication.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.myapplication.app.App;
import com.example.user.myapplication.data.model.Client;
import com.example.user.myapplication.data.model.Inventory;
import com.example.user.myapplication.data.model.Product;
import com.example.user.myapplication.data.model.User;
import com.example.user.myapplication.data.repo.ClientRepo;
import com.example.user.myapplication.data.repo.InventoryRepo;
import com.example.user.myapplication.data.repo.ProductRepo;
import com.example.user.myapplication.data.repo.UserRepo;

public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION =1;
    // Database Name
    private static final String DATABASE_NAME = "sqliteDBMultiTbl.db";
    private static final String TAG = DBHelper.class.getSimpleName().toString();

    public DBHelper( ) {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        db.execSQL(UserRepo.createTable());
        db.execSQL(ClientRepo.createTable());
        db.execSQL(ProductRepo.createTable());
        db.execSQL(InventoryRepo.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        // Drop table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Client.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Product.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Inventory.TABLE);
        onCreate(db);
    }

}
