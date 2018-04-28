package com.example.user.myapplication.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.myapplication.data.DatabaseManager;
import com.example.user.myapplication.data.model.Client;
import com.example.user.myapplication.data.model.Inventory;
import com.example.user.myapplication.data.model.Product;

import java.util.ArrayList;
import java.util.List;

public class InventoryRepo {
    private Inventory inventory;

    public InventoryRepo(){

        inventory = new Inventory();

    }

    public static String createTable(){
        return "CREATE TABLE " + Inventory.TABLE  + "("
                + Inventory.KEY_InventoryId + " TEXT  PRIMARY KEY, "
                + Inventory.KEY_ClientId + " TEXT, "
                + Inventory.KEY_ProductId + " TEXT, "
                + Inventory.KEY_Color + " TEXT, "
                + Inventory.KEY_Number + " TEXT, "
                + Inventory.KEY_Date + " TEXT )";
    }

    public void insert(Inventory inventory) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Inventory.KEY_InventoryId, inventory.getInventoryId());
        values.put(Inventory.KEY_ClientId, inventory.getClientId());
        values.put(Inventory.KEY_ProductId, inventory.getProductId());
        values.put(Inventory.KEY_Color, inventory.get_Color());
        values.put(Inventory.KEY_Number, inventory.getInventory_num());
        values.put(Inventory.KEY_Date, inventory.getEditDate());

        // Inserting Row
        db.insert(Inventory.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void updateNumber(String inventoryId, String inventoryNum) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Inventory.KEY_Number, inventoryNum);
        String[] whereArgs  = {inventoryId};
        db.update(Inventory.TABLE, values, Inventory.KEY_InventoryId + "=?", whereArgs);
    }

    public List<Inventory> getAllInventories() {
        Inventory inventory = new Inventory();
        List<Inventory> inventories = new ArrayList<Inventory>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Inventory." + Inventory.KEY_ClientId
                + ", Client." + Client.KEY_Name
                + ", Inventory." + Inventory.KEY_ProductId
                + ", Product." + Product.KEY_Name
                + ", Inventory." + Inventory.KEY_Color
                + ", Inventory." + Inventory.KEY_Number
                + ", Inventory." + Inventory.KEY_Date
                + ", Inventory." + Inventory.KEY_InventoryId
                + " FROM " + Inventory.TABLE
                + " INNER JOIN " + Client.TABLE + " ON Client." + Client.KEY_ClientId + "=Inventory." + Inventory.KEY_ClientId
                + " INNER JOIN " + Product.TABLE + " ON Product." + Product.KEY_ProductId + "=Inventory." + Inventory.KEY_ProductId;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                inventory = new Inventory();
                inventory.setInventoryId(cursor.getString(cursor.getColumnIndex(Inventory.KEY_InventoryId)));
                inventory.setClientId(cursor.getString(cursor.getColumnIndex(Client.KEY_Name)));
                inventory.setProductId(cursor.getString(cursor.getColumnIndex(Product.KEY_Name)));
                inventory.set_Color(cursor.getString(cursor.getColumnIndex(Inventory.KEY_Color)));
                inventory.setNum(cursor.getString(cursor.getColumnIndex(Inventory.KEY_Number)));
                inventory.setEditDate(cursor.getString(cursor.getColumnIndex(Inventory.KEY_Date)));

                inventories.add(inventory);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return inventories;
    }

    public List<Inventory> getAllInventories(String clientId, String productId) {
        Inventory inventory = new Inventory();
        List<Inventory> inventories = new ArrayList<Inventory>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Inventory." + Inventory.KEY_InventoryId
                + ", Inventory." + Inventory.KEY_ClientId
                + ", Client." + Client.KEY_Name
                + ", Inventory." + Inventory.KEY_ProductId
                + ", Product." + Product.KEY_Name
                + ", Inventory." + Inventory.KEY_Color
                + ", Inventory." + Inventory.KEY_Number
                + " FROM " + Inventory.TABLE
                + " INNER JOIN " + Client.TABLE + " ON Client." + Client.KEY_ClientId + "=Inventory." + Inventory.KEY_ClientId
                + " INNER JOIN " + Product.TABLE + " ON Product." + Product.KEY_ProductId + "=Inventory." + Inventory.KEY_ProductId
                + " WHERE Inventory." + Inventory.KEY_ProductId + "='" + productId
                + "' AND Inventory." + Inventory.KEY_ClientId + "='" + clientId + "';";

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                inventory = new Inventory();
                inventory.setInventoryId(cursor.getString(cursor.getColumnIndex(Inventory.KEY_InventoryId)));
                inventory.setClientId(cursor.getString(cursor.getColumnIndex(Client.KEY_Name)));
                inventory.setProductId(cursor.getString(cursor.getColumnIndex(Product.KEY_Name)));
                inventory.set_Color(cursor.getString(cursor.getColumnIndex(Inventory.KEY_Color)));
                inventory.setNum(cursor.getString(cursor.getColumnIndex(Inventory.KEY_Number)));

                inventories.add(inventory);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return inventories;
    }

    public float getInventoryNum(String inventoryId) {
        String inventoryNum = "";
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Inventory." + Inventory.KEY_Number
                + " FROM " + Inventory.TABLE
                + " WHERE Inventory." + Inventory.KEY_InventoryId + "='" + inventoryId + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            inventoryNum = cursor.getString((cursor.getColumnIndex(Inventory.KEY_Number)));
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return Float.parseFloat(inventoryNum);
    }

    public List<Product> getProducts(String clientId) {
        Product product = new Product();
        List<Product> products = new ArrayList<Product>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Inventory." + Inventory.KEY_ProductId
                + ", Product." + Product.KEY_Name
                + " FROM " + Inventory.TABLE
                + " INNER JOIN " + Product.TABLE + " ON Product." + Product.KEY_ProductId + "=Inventory." + Inventory.KEY_ProductId
                + " WHERE Inventory." + Inventory.KEY_ClientId + "='" + clientId
                + "' GROUP BY Inventory." + Inventory.KEY_ProductId;

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

    public Boolean checkInventory(String clientName, String productName, String color) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Inventory." + Inventory.KEY_InventoryId
                + " FROM " + Inventory.TABLE
                + " INNER JOIN " + Client.TABLE + " ON Client." + Client.KEY_ClientId + "=Inventory." + Inventory.KEY_ClientId
                + " INNER JOIN " + Product.TABLE + " ON Product." + Product.KEY_ProductId + "=Inventory." + Inventory.KEY_ProductId
                + " WHERE Product." + Product.KEY_Name + "='" + productName
                + "' AND Inventory." + Inventory.KEY_Color + "='" + color
                + "' AND Client." + Client.KEY_Name + "='" + clientName + "';";

        Cursor cursor = db.rawQuery(selectQuery, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public String initInventoryId() {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + Inventory.KEY_InventoryId + " FROM " + Inventory.TABLE
                + " ORDER BY CAST(SUBSTR(" + Inventory.KEY_InventoryId + ",4) AS INT) DESC LIMIT 1";

        Cursor cursor = db.rawQuery(selectQuery, null);
        int cursorCount = cursor.getCount();
        if (cursorCount == 0) {
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            return "INV1";
        }
        else{
            cursor.moveToFirst();
            String inventoryNo = cursor.getString(cursor.getColumnIndex(Inventory.KEY_InventoryId));
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            int currentIndex = Integer.parseInt(inventoryNo.replace("INV",""));
            currentIndex += 1;
            return "INV" + currentIndex;
        }
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Inventory.TABLE, null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(String InventoryId) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Inventory.TABLE, Inventory.KEY_InventoryId +"=?", new String[]{InventoryId});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void deleteClient(String clientId) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Inventory.TABLE, Inventory.KEY_ClientId +"=?", new String[]{clientId});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void deleteProduct(String productId) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Inventory.TABLE, Inventory.KEY_ProductId +"=?", new String[]{productId});
        DatabaseManager.getInstance().closeDatabase();
    }
}
