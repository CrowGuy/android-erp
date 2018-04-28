package com.example.user.myapplication.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.myapplication.data.DatabaseManager;
import com.example.user.myapplication.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepo {

    private User user;

    public UserRepo() {
        user = new User();
    }

    public static String createTable() {
        return  "CREATE TABLE " + User.TABLE  + "("
                + User.KEY_Account  + " TEXT PRIMARY KEY, "
                + User.KEY_Password + " TEXT , "
                + User.KEY_Priority + " TEXT )";
    }

    public void insert(User user) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_Account, user.getAccount());
        values.put(User.KEY_Password, user.getPassword());
        values.put(User.KEY_Priority, user.getPriority());

        // Inserting Row
        db.insert(User.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<User> getAllUser() {

        User user = new User();
        List<User> users = new ArrayList<User>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + User.KEY_Account + ", "
                + User.KEY_Password + ", " + User.KEY_Priority + " FROM " + User.TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setAccount(cursor.getString(cursor.getColumnIndex(User.KEY_Account)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(User.KEY_Password)));
                user.setPriority(cursor.getString(cursor.getColumnIndex(User.KEY_Priority)));

                users.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return users;
    }

    public Boolean checkUser(String account, String password) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + User.KEY_Account + ", "
                + User.KEY_Priority + " FROM " + User.TABLE
                + " WHERE " + User.KEY_Account + "='" + account
                + "' AND " + User.KEY_Password + "='" + password + "';";

        Cursor cursor = db.rawQuery(selectQuery, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public Boolean checkUser(String account) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + User.KEY_Account + ", "
                + User.KEY_Priority + " FROM " + User.TABLE
                + " WHERE " + User.KEY_Account + "='" + account
                + "' AND " + User.KEY_Priority + "='0';";

        Cursor cursor = db.rawQuery(selectQuery, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public Boolean checkAccount(String account) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + User.KEY_Account + ", "
                + User.KEY_Priority + " FROM " + User.TABLE
                + " WHERE " + User.KEY_Account + "='" + account
                + "';";

        Cursor cursor = db.rawQuery(selectQuery, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(User.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(String account) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(User.TABLE, User.KEY_Account +"=?", new String[]{account});
        DatabaseManager.getInstance().closeDatabase();
    }
}
