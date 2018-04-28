package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.user.myapplication.app.SelectorAdapter;
import com.example.user.myapplication.data.model.Product;
import com.example.user.myapplication.data.repo.InventoryRepo;

import java.util.ArrayList;
import java.util.List;

public class ProductSelector extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lview;
    SelectorAdapter lviewAdapter;
    ArrayList<String> productName;
    ArrayList<String> productId;
    String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        initView();
    }

    private void initView() {

        Intent IntentObj = getIntent(); /* 取得傳入的 Intent 物件 */
        Bundle bundle = IntentObj.getExtras();
        clientId = bundle.getString("Id").replace("編號:","").trim();
        //Toast.makeText(ProductSelector.this, clientId, Toast.LENGTH_SHORT).show();
        lview = (ListView) findViewById(R.id.listView2);

        //Init Product list
        InventoryRepo inventoryRepo = new InventoryRepo();
        List<Product> products = inventoryRepo.getProducts(clientId);
        productName = new ArrayList<String>();
        productId = new ArrayList<String>();
        for (int i = 0; i < products.size(); i++) {

            productName.add("名稱: " + products.get(i).getProductName());
            productId.add("編號: " + products.get(i).getProductId());
        }

        lviewAdapter = new SelectorAdapter(this, productName, productId);
        lview.setAdapter(lviewAdapter);
        lview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        // TODO Auto-generated method stub
        //Toast.makeText(ProductSelector.this, "品項名稱:" + productName.get(position), Toast.LENGTH_SHORT).show();
        Intent inventoryIntent = new Intent(this,InventoryIn.class);
        Bundle bundle = new Bundle();
        bundle.putString("productId", productId.get(position).replace("編號:","").trim());
        bundle.putString("clientId", clientId);
        inventoryIntent.putExtras(bundle);
        startActivity(inventoryIntent);
    }
}
