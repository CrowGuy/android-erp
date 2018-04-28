package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.data.model.Inventory;
import com.example.user.myapplication.data.repo.ClientRepo;
import com.example.user.myapplication.data.repo.InventoryRepo;
import com.example.user.myapplication.data.repo.ProductRepo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InventoryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView valueInventoryNo;
    private Button btnView, btnInsert;
    private Spinner spinnerClientName, spinnerProductName;
    private EditText valueColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        initView();
        loadSpinnerData();
        // Initial ClientNO
        InventoryRepo inventoryRepo = new InventoryRepo();
        String inventoryNO = inventoryRepo.initInventoryId();
        valueInventoryNo.setText(inventoryNO);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        // Initial ClientNO
        InventoryRepo inventoryRepo = new InventoryRepo();
        String inventoryNO = inventoryRepo.initInventoryId();
        spinnerClientName.setSelection(0);
        spinnerProductName.setSelection(0);
        valueColor.setText("");
        valueInventoryNo.setText(inventoryNO);
    }

    protected void initView() {
        valueInventoryNo = (TextView) findViewById(R.id.valueInventoryNo);
        btnView = (Button) findViewById(R.id.btnView);
        btnView.setOnClickListener(this);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(this);
        spinnerClientName = (Spinner) findViewById(R.id.spinnerClientName);
        spinnerProductName = (Spinner) findViewById(R.id.spinnerProductName);
        valueColor = (EditText) findViewById(R.id.valueColor);
    }

    private void loadSpinnerData() {
        ClientRepo clientRepo = new ClientRepo();
        ProductRepo productRepo = new ProductRepo();
        List<String> clientNames = clientRepo.getClientNames();
        List<String> productNames = productRepo.getProductNames();

        // Creating adapter for spinner
        ArrayAdapter<String> clientAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        clientAdapter.add("請選擇客戶");
        clientAdapter.addAll(clientNames);
        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        productAdapter.add("請選擇品項");
        productAdapter.addAll(productNames);

        // attaching data adapter to spinner
        spinnerClientName.setAdapter(clientAdapter);
        spinnerClientName.setSelection(0);
        spinnerProductName.setAdapter(productAdapter);
        spinnerProductName.setSelection(0);
    }

    @Override
    public void onClick(View view) {
        if (view ==findViewById(R.id.btnView)) {
            //Toast.makeText(InventoryActivity.this, "View mode", Toast.LENGTH_SHORT).show();
            InventoryRepo inventoryRepo = new InventoryRepo();
            // query from client repo
            List<Inventory> inventories = inventoryRepo.getAllInventories();
            ArrayList<String> inventoryId = new ArrayList<String>();
            ArrayList<String> clientName = new ArrayList<String>();
            ArrayList<String> productName = new ArrayList<String>();
            ArrayList<String> Color = new ArrayList<String>();
            ArrayList<String> Number = new ArrayList<String>();
            ArrayList<String> Date = new ArrayList<String>();

            for (int i = 0; i < inventories.size(); i++) {

                inventoryId.add(inventories.get(i).getInventoryId());
                clientName.add("客戶: " + inventories.get(i).getClientId());
                productName.add("品項: " + inventories.get(i).getProductId());
                Color.add("顏色: " + inventories.get(i).get_Color());
                Number.add("庫存: " + inventories.get(i).getInventory_num());
                Date.add("日期: " + inventories.get(i).getEditDate());
            }

            Intent viewIntent = new Intent(this, ViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("numCols", 5);
            bundle.putInt("title", R.string.inventory_basic_view);
            bundle.putStringArrayList("inventoryId", inventoryId);
            bundle.putStringArrayList("clientName", clientName);
            bundle.putStringArrayList("productName", productName);
            bundle.putStringArrayList("Color", Color);
            bundle.putStringArrayList("Number", Number);
            bundle.putStringArrayList("Date", Date);
            viewIntent.putExtras(bundle);
            InventoryActivity.this.startActivity(viewIntent);
        }
        else if(view ==findViewById(R.id.btnInsert)) {
            InventoryRepo inventoryRepo = new InventoryRepo();

            // Check the exist of EditText with value
            if (spinnerClientName.getSelectedItem() == "請選擇客戶") {
                Toast.makeText(InventoryActivity.this, "請選擇客戶名稱", Toast.LENGTH_SHORT).show();
            }
            else if(spinnerProductName.getSelectedItem() == "請選擇品項") {
                Toast.makeText(InventoryActivity.this, "請選擇品項名稱", Toast.LENGTH_SHORT).show();
            }
            else if(valueColor.getText().toString().equals("")) {
                Toast.makeText(InventoryActivity.this, "請輸入顏色名稱", Toast.LENGTH_SHORT).show();
            }
            else{
                if (!inventoryRepo.checkInventory(spinnerClientName.getSelectedItem().toString().trim(),
                        spinnerProductName.getSelectedItem().toString().trim(),
                        valueColor.getText().toString().trim())) {
                    Inventory inventory = new Inventory();
                    inventory.setInventoryId(valueInventoryNo.getText().toString());
                    ClientRepo clientRepo = new ClientRepo();
                    String clientId = clientRepo.getClientId(spinnerClientName.getSelectedItem().toString().trim());
                    inventory.setClientId(clientId);
                    ProductRepo productRepo = new ProductRepo();
                    String productId = productRepo.getProductId(spinnerProductName.getSelectedItem().toString().trim());
                    inventory.setProductId(productId);
                    inventory.set_Color(valueColor.getText().toString());
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    String date = df.format(Calendar.getInstance().getTime());
                    inventory.setEditDate(date);
                    inventory.setNum("0.0");
                    inventoryRepo.insert(inventory);
                    Toast.makeText(InventoryActivity.this, "已新增庫存單號:" + inventory.getInventoryId(), Toast.LENGTH_SHORT).show();

                    // Reset
                    spinnerClientName.setSelection(0);
                    spinnerProductName.setSelection(0);
                    valueColor.setText("");
                    String inventoryNO = inventoryRepo.initInventoryId();
                    valueInventoryNo.setText(inventoryNO);
                }
                else {
                    Toast.makeText(InventoryActivity.this, "此客戶品項所對應之顏色已存在", Toast.LENGTH_SHORT).show();
                    // Reset
                    spinnerClientName.setSelection(0);
                    spinnerProductName.setSelection(0);
                    valueColor.setText("");
                    String inventoryNO = inventoryRepo.initInventoryId();
                    valueInventoryNo.setText(inventoryNO);
                }
            }
        }
    }
}
