package com.example.user.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.user.myapplication.data.model.Client;
import com.example.user.myapplication.data.model.Product;
import com.example.user.myapplication.data.model.User;
import com.example.user.myapplication.data.repo.ClientRepo;
import com.example.user.myapplication.data.repo.ProductRepo;
import com.example.user.myapplication.data.repo.UserRepo;

/**
 * Created by user on 2018/3/27.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnManage, btnInventory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnManage = (Button) findViewById(R.id.btnManage);
        btnManage.setOnClickListener(this);
        btnInventory = (Button) findViewById(R.id.btnInventory);
        btnInventory.setOnClickListener(this);
        initUserAccount();
        //testDataNumber();
    }

    private void testDataNumber() {

        // Add 500 rows for Client
        ClientRepo clientRepo = new ClientRepo();
        for (int i = 0; i < 500; i++) {
            String client_id = clientRepo.initClientId();
            String client_name = "Client" + Integer.toString(i);
            Client client = new Client();
            client.setClientId(client_id);
            client.setClientName(client_name);
            clientRepo.insert(client);
        }

        // Add 500 rows for Product
        ProductRepo productRepo = new ProductRepo();
        for (int i = 0; i < 500; i++) {
            String product_id = productRepo.initProductId();
            String product_name = "Product" + Integer.toString(i);
            Product product = new Product();
            product.setProductId(product_id);
            product.setProductName(product_name);
            productRepo.insert(product);
        }
    }

    public void initUserAccount() {

        UserRepo userRepo = new UserRepo();
        if (!userRepo.checkAccount("admin")) {
            User user = new User();
            user.setAccount("admin");
            user.setPassword("admin");
            user.setPriority("0");
            userRepo.insert(user);
        }
    }

    @Override
    public void onClick(View view) {
        if (view ==findViewById(R.id.btnManage)){
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
        else if (view ==findViewById(R.id.btnInventory)){
            Intent selectIntent = new Intent(this, ClientSelector.class);
            startActivity(selectIntent);
        }
    }
}
