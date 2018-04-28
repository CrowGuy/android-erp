package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.user.myapplication.app.InventoryInAdapter;
import com.example.user.myapplication.data.model.Inventory;
import com.example.user.myapplication.data.repo.InventoryRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryIn extends AppCompatActivity implements View.OnClickListener {

    private String productId, clientId;
    ListView listview;
    InventoryInAdapter ListViewAdapter;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_in);
        initView();
    }

    private void initView() {
        Intent IntentObj = getIntent(); /* 取得傳入的 Intent 物件 */
        Bundle bundle = IntentObj.getExtras();
        productId = bundle.getString("productId");
        clientId = bundle.getString("clientId");
        //Toast.makeText(InventoryIn.this, productId, Toast.LENGTH_SHORT).show();
        InventoryRepo inventoryRepo = new InventoryRepo();
        List<Inventory> inventories = inventoryRepo.getAllInventories(clientId, productId);
        ArrayList<String> clientName = new ArrayList<String>();
        ArrayList<String> productName = new ArrayList<String>();
        ArrayList<String> Color = new ArrayList<String>();
        ArrayList<String> Number = new ArrayList<String>();
        ArrayList<String> InventoryId = new ArrayList<String>();

        for (int i = 0; i < inventories.size(); i++) {

            clientName.add("客戶: " + inventories.get(i).getClientId());
            productName.add("品項: " + inventories.get(i).getProductId());
            Color.add("顏色: " + inventories.get(i).get_Color());
            Number.add("庫存: " + inventories.get(i).getInventory_num());
            InventoryId.add(inventories.get(i).getInventoryId());
        }

        listview = (ListView) findViewById(R.id.listView2);
        ListViewAdapter = new InventoryInAdapter(this, clientName, productName, Color, Number, InventoryId);
        listview.setAdapter(ListViewAdapter);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
    }

    private void listViewPostBack() {
        InventoryRepo inventoryRepo = new InventoryRepo();
        List<Inventory> inventories = inventoryRepo.getAllInventories(clientId, productId);
        ArrayList<String> clientName = new ArrayList<String>();
        ArrayList<String> productName = new ArrayList<String>();
        ArrayList<String> Color = new ArrayList<String>();
        ArrayList<String> Number = new ArrayList<String>();
        ArrayList<String> InventoryId = new ArrayList<String>();

        for (int i = 0; i < inventories.size(); i++) {

            clientName.add("客戶: " + inventories.get(i).getClientId());
            productName.add("品項: " + inventories.get(i).getProductId());
            Color.add("顏色: " + inventories.get(i).get_Color());
            Number.add("庫存: " + inventories.get(i).getInventory_num());
            InventoryId.add(inventories.get(i).getInventoryId());
        }

        ListViewAdapter = new InventoryInAdapter(this, clientName, productName, Color, Number, InventoryId);
        listview.setAdapter(ListViewAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view ==findViewById(R.id.btnUpdate)){
            HashMap<String, String> mapValues = (HashMap<String, String>) listview.getAdapter().getItem(0);
            InventoryRepo inventoryRepo = new InventoryRepo();
            for (Map.Entry<String, String> entry : mapValues.entrySet()) {
                inventoryRepo.updateNumber(entry.getKey(),entry.getValue());
            }
            listViewPostBack();
        }
    }

}
