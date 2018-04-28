package com.example.user.myapplication.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.myapplication.data.DatabaseManager;
import com.example.user.myapplication.data.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepo {

    private Product product;

    public ProductRepo(){

        product= new Product();

    }

    public static String createTable(){
        return "CREATE TABLE " + Product.TABLE  + "("
                + Product.KEY_ProductId + " TEXT  PRIMARY KEY, "
                + Product.KEY_Name + " TEXT )";
    }

    public void insert(Product product) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Product.KEY_ProductId, product.getProductId());
        values.put(Product.KEY_Name, product.getProductName());

        // Inserting Row
        db.insert(Product.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<Product> getAllProduct() {

        Product product = new Product();
        List<Product> products = new ArrayList<Product>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + Product.KEY_ProductId + ", "
                + Product.KEY_Name + " FROM " + Product.TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                product = new Product();
                product.setProductId(cursor.getString(cursor.getColumnIndex(Product.KEY_ProductId)));
                product.setProductName(cursor.getString(cursor.getColumnIndex(Product.KEY_Name)));

                products.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return products;
    }

    public List<String> getProductNames() {
        List<String> productNames = new ArrayList<String>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + Product.KEY_Name + " FROM " + Product.TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                productNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return productNames;
    }

    public String getProductId(String Name) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + Product.KEY_ProductId + " FROM " + Product.TABLE
                + " WHERE " + Product.KEY_Name + "='" + Name + "';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            String productName = cursor.getString(cursor.getColumnIndex(Product.KEY_ProductId));
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            return productName;
        }
        else{
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            return "Null";
        }
    }

    public String initProductId() {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + Product.KEY_ProductId + ", "
                + Product.KEY_Name + " FROM " + Product.TABLE
                + " ORDER BY CAST(SUBSTR(" + Product.KEY_ProductId + ",2) AS INT) DESC LIMIT 1";

        Cursor cursor = db.rawQuery(selectQuery, null);
        int cursorCount = cursor.getCount();
        if (cursorCount == 0) {
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            return "P1";
        }
        else{
            cursor.moveToFirst();
            String productNo = cursor.getString(cursor.getColumnIndex(Product.KEY_ProductId));
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            int currentIndex = Integer.parseInt(productNo.replace("P",""));
            currentIndex += 1;
            return "P" + String.valueOf(currentIndex);
        }
    }

    public Boolean checkProductName(String name) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + Product.KEY_Name + " FROM " + Product.TABLE
                + " WHERE " + Product.KEY_Name + "='" + name
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
        db.delete(Product.TABLE, null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(String id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Product.TABLE, Product.KEY_ProductId +"=?", new String[]{id});
        DatabaseManager.getInstance().closeDatabase();
    }
}
