package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.user.myapplication.app.InventoryListViewAdapter;
import com.example.user.myapplication.app.ListViewAdapter;
import com.example.user.myapplication.app.UserListViewAdapter;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    ListView lview;
    ListViewAdapter lviewAdapter;
    UserListViewAdapter userlviewAdapter;
    InventoryListViewAdapter InvlviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        initView();
    }

    protected void initView() {

        Intent IntentObj = getIntent(); /* 取得傳入的 Intent 物件 */
        Bundle bundle = IntentObj.getExtras();
        int numCols = bundle.getInt("numCols");
        int title = bundle.getInt("title");

        switch(numCols) {
            case 2:
                ArrayList<String> name = bundle.getStringArrayList("Name");
                ArrayList<String> id = bundle.getStringArrayList("Id");
                lview = (ListView) findViewById(R.id.listView2);
                lviewAdapter = new ListViewAdapter(this, name, id);
                lview.setAdapter(lviewAdapter);
                setTitle(title);
                break;
            case 3:
                ArrayList<String> account = bundle.getStringArrayList("Account");
                ArrayList<String> password = bundle.getStringArrayList("Password");
                ArrayList<String> priority = bundle.getStringArrayList("Priority");
                lview = (ListView) findViewById(R.id.listView2);
                userlviewAdapter = new UserListViewAdapter(this, account, password, priority);
                lview.setAdapter(userlviewAdapter);
                setTitle(title);
                break;
            case 5:
                ArrayList<String> inventoryId = bundle.getStringArrayList("inventoryId");
                ArrayList<String> clientName = bundle.getStringArrayList("clientName");
                ArrayList<String> productName = bundle.getStringArrayList("productName");
                ArrayList<String> Color = bundle.getStringArrayList("Color");
                ArrayList<String> Number = bundle.getStringArrayList("Number");
                ArrayList<String> Date = bundle.getStringArrayList("Date");
                lview = (ListView) findViewById(R.id.listView2);
                InvlviewAdapter = new InventoryListViewAdapter(this, inventoryId, clientName, productName, Color, Number, Date);
                lview.setAdapter(InvlviewAdapter);
                setTitle(title);
                break;
            default:
                break;
        }
    }
}
