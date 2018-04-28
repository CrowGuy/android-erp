package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.user.myapplication.app.SelectorAdapter;
import com.example.user.myapplication.data.model.Client;
import com.example.user.myapplication.data.repo.ClientRepo;

import java.util.ArrayList;
import java.util.List;

public class ClientSelector extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lview;
    SelectorAdapter lviewAdapter;
    ArrayList<String> clientName;
    ArrayList<String> clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        initView();
    }

    private void initView() {
        lview = (ListView) findViewById(R.id.listView2);

        //Init Client list
        ClientRepo clientRepo = new ClientRepo();
        List<Client> clients = clientRepo.getAllClient();
        clientName = new ArrayList<String>();
        clientId = new ArrayList<String>();
        for (int i = 0; i < clients.size(); i++) {

            clientName.add("名稱: " + clients.get(i).getClientName());
            clientId.add("編號: " + clients.get(i).getClientId());
        }

        lviewAdapter = new SelectorAdapter(this, clientName, clientId);
        lview.setAdapter(lviewAdapter);
        lview.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        // TODO Auto-generated method stub
        //Toast.makeText(ClientSelector.this, "客戶名稱:" + clientId.get(position), Toast.LENGTH_SHORT).show();
        Intent productIntent = new Intent(this,ProductSelector.class);
        Bundle bundle = new Bundle();
        bundle.putString("Id", clientId.get(position).trim());
        productIntent.putExtras(bundle);
        startActivity(productIntent);
    }
}
