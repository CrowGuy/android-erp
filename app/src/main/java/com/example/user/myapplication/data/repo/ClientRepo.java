package com.example.user.myapplication.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.myapplication.data.DatabaseManager;
import com.example.user.myapplication.data.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientRepo {

    private Client client;

    public ClientRepo(){

        client= new Client();

    }

    public static String createTable(){
        return "CREATE TABLE " + Client.TABLE  + "("
                + Client.KEY_ClientId + " TEXT  PRIMARY KEY, "
                + Client.KEY_Name + " TEXT )";
    }

    public void insert(Client client) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Client.KEY_ClientId, client.getClientId());
        values.put(Client.KEY_Name, client.getClientName());

        // Inserting Row
        db.insert(Client.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<Client> getAllClient() {

        Client client = new Client();
        List<Client> clients = new ArrayList<Client>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + Client.KEY_ClientId + ", "
                + Client.KEY_Name + " FROM " + Client.TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                client = new Client();
                client.setClientId(cursor.getString(cursor.getColumnIndex(Client.KEY_ClientId)));
                client.setClientName(cursor.getString(cursor.getColumnIndex(Client.KEY_Name)));

                clients.add(client);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return clients;
    }

    public List<String> getClientNames() {
        List<String> clientNames = new ArrayList<String>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + Client.KEY_Name + " FROM " + Client.TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                clientNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return clientNames;
    }

    public String getClientId(String Name) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + Client.KEY_ClientId + " FROM " + Client.TABLE
                + " WHERE " + Client.KEY_Name + "='" + Name + "';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            String clientName = cursor.getString(cursor.getColumnIndex(Client.KEY_ClientId));
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            return clientName;
        }
        else{
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            return "Null";
        }
    }

    public String initClientId() {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + Client.KEY_ClientId + ", "
                + Client.KEY_Name + " FROM " + Client.TABLE
                + " ORDER BY  CAST(SUBSTR(" + Client.KEY_ClientId + ",3) AS INT) DESC LIMIT 1";

        Cursor cursor = db.rawQuery(selectQuery, null);
        int cursorCount = cursor.getCount();
        if (cursorCount == 0) {
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            return "CL1";
        }
        else{
            cursor.moveToFirst();
            String clientNo = cursor.getString(cursor.getColumnIndex(Client.KEY_ClientId));
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
            int currentIndex = Integer.parseInt(clientNo.replace("CL",""));
            currentIndex += 1;
            return "CL" + currentIndex;
        }
    }

    public Boolean checkClientName(String name) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + Client.KEY_Name + " FROM " + Client.TABLE
                + " WHERE " + Client.KEY_Name + "='" + name
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
        db.delete(Client.TABLE, null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(String id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Client.TABLE, Client.KEY_ClientId +"=?", new String[]{id});
        DatabaseManager.getInstance().closeDatabase();
    }
}
