package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.data.model.Client;
import com.example.user.myapplication.data.repo.ClientRepo;

import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView valueClientNo;
    private Button btnView, btnInsert;
    private EditText valueClientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initView();
        // Initial ClientNO
        ClientRepo clientRepo = new ClientRepo();
        String clientNO = clientRepo.initClientId();
        valueClientNo.setText(clientNO);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        ClientRepo clientRepo = new ClientRepo();
        String clientNO = clientRepo.initClientId();
        valueClientName.setText("");
        valueClientNo.setText(clientNO);
    }

    protected void initView() {
        valueClientNo = (TextView) findViewById(R.id.valueClientNo);
        btnView = (Button) findViewById(R.id.btnView);
        btnView.setOnClickListener(this);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(this);
        valueClientName = (EditText) findViewById(R.id.valueClientName);
    }

    @Override
    public void onClick(View view) {
        if (view ==findViewById(R.id.btnView)) {
            //Toast.makeText(ClientActivity.this, "View mode", Toast.LENGTH_SHORT).show();
            ClientRepo clientRepo = new ClientRepo();
            // query from client repo
            List<Client> clients = clientRepo.getAllClient();
            ArrayList<String> name = new ArrayList<String>();
            ArrayList<String> id = new ArrayList<String>();

            for (int i = 0; i < clients.size(); i++) {

                name.add("名稱: " + clients.get(i).getClientName());
                id.add("編號: " + clients.get(i).getClientId());
            }

            Intent viewIntent = new Intent(this, ViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("numCols", 2);
            bundle.putInt("title", R.string.client_basic_view);
            bundle.putStringArrayList("Name", name);
            bundle.putStringArrayList("Id", id);
            viewIntent.putExtras(bundle);
            ClientActivity.this.startActivity(viewIntent);
        }
        else if(view ==findViewById(R.id.btnInsert)) {
            ClientRepo clientRepo = new ClientRepo();

            // Check the exist of EditText with value
            if (valueClientName.getText().toString().equals("")) {
                Toast.makeText(ClientActivity.this, "請輸入客戶名稱", Toast.LENGTH_SHORT).show();
            }
            else{
                if(!clientRepo.checkClientName(valueClientName.getText().toString().trim())) {
                    Client client = new Client();
                    client.setClientId(valueClientNo.getText().toString());
                    client.setClientName(valueClientName.getText().toString());
                    clientRepo.insert(client);
                    Toast.makeText(ClientActivity.this, "已新增客戶:" + client.getClientName(), Toast.LENGTH_SHORT).show();

                    // Reset
                    valueClientName.setText("");
                    String clientNO = clientRepo.initClientId();
                    valueClientNo.setText(clientNO);
                }
                else{
                    Toast.makeText(ClientActivity.this, "該客戶名稱已存在", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
