package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.data.model.Product;
import com.example.user.myapplication.data.repo.ProductRepo;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView valueProductNo;
    private Button btnView, btnInsert;
    private EditText valueProductName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initView();
        // Initial ProductNO
        ProductRepo productRepo = new ProductRepo();
        String productNO = productRepo.initProductId();
        valueProductNo.setText(productNO);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        ProductRepo productRepo = new ProductRepo();
        String productNO = productRepo.initProductId();
        valueProductName.setText("");
        valueProductNo.setText(productNO);
    }

    protected void initView() {
        valueProductNo = (TextView) findViewById(R.id.valueProductNo);
        btnView = (Button) findViewById(R.id.btnView);
        btnView.setOnClickListener(this);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(this);
        valueProductName = (EditText) findViewById(R.id.valueProductName);
    }

    @Override
    public void onClick(View view) {
        if (view ==findViewById(R.id.btnView)) {
            //Toast.makeText(ProductActivity.this, "View mode", Toast.LENGTH_SHORT).show();
            ProductRepo productRepo = new ProductRepo();
            // query from client repo
            List<Product> prodcuts = productRepo.getAllProduct();
            ArrayList<String> name = new ArrayList<String>();
            ArrayList<String> id = new ArrayList<String>();

            for (int i = 0; i < prodcuts.size(); i++) {

                name.add("名稱: " + prodcuts.get(i).getProductName());
                id.add("編號: " + prodcuts.get(i).getProductId());
            }

            Intent viewIntent = new Intent(this, ViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("numCols", 2);
            bundle.putInt("title", R.string.product_basic_view);
            bundle.putStringArrayList("Name", name);
            bundle.putStringArrayList("Id", id);
            viewIntent.putExtras(bundle);
            ProductActivity.this.startActivity(viewIntent);
        }
        else if(view ==findViewById(R.id.btnInsert)) {
            ProductRepo productRepo = new ProductRepo();

            // Check the exist of EditText with value
            if (valueProductName.getText().toString().equals("")) {
                Toast.makeText(ProductActivity.this, "請輸入品項名稱", Toast.LENGTH_SHORT).show();
            }
            else{
                if(!productRepo.checkProductName(valueProductName.getText().toString().trim())) {
                    Product product = new Product();
                    product.setProductId(valueProductNo.getText().toString());
                    product.setProductName(valueProductName.getText().toString());
                    productRepo.insert(product);
                    Toast.makeText(ProductActivity.this, "已新增品項:" + product.getProductName(), Toast.LENGTH_SHORT).show();

                    // Initial ProductNO
                    valueProductName.setText("");
                    String productNO = productRepo.initProductId();
                    valueProductNo.setText(productNO);
                }
                else{
                    Toast.makeText(ProductActivity.this, "該品項名稱已存在", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
