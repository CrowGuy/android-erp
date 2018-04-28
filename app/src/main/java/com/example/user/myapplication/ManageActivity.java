package com.example.user.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.myapplication.data.repo.UserRepo;

public class ManageActivity extends ListActivity {

    private String[] INDIAN_STATE = { "帳戶管理", "客戶基本檔","品項基本檔","庫存基本檔"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_manage, INDIAN_STATE);
        setListAdapter(adapter);
    }

    private void verifyUser() {
        Intent IntentObj = getIntent(); /* 取得傳入的 Intent 物件 */
        Bundle bundle = IntentObj.getExtras();
        String account = bundle.getString("account");
        UserRepo userRepo = new UserRepo();
        if (userRepo.checkUser(account)) {
            Intent userIntent = new Intent(this, UserActivity.class);
            startActivity(userIntent);
        }
        else {
            Toast.makeText(ManageActivity.this, "您的帳號無此權限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String str = l.getItemAtPosition(position).toString();
        //Toast.makeText(ManageActivity.this, str, Toast.LENGTH_SHORT).show();

        switch(str) {
            case "帳戶管理":
                verifyUser();
                break;
            case "客戶基本檔":
                Intent clientIntent = new Intent(this, ClientActivity.class);
                startActivity(clientIntent);
                break;
            case "品項基本檔":
                Intent productIntent = new Intent(this, ProductActivity.class);
                startActivity(productIntent);
                break;
            case "庫存基本檔":
                Intent inventoryIntent = new Intent(this, InventoryActivity.class);
                startActivity(inventoryIntent);
                break;
            default:
                break;
        }
    }
}
