package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.myapplication.data.model.User;
import com.example.user.myapplication.data.repo.UserRepo;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnView, btnInsert;
    private EditText valueUserAccount, valueUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
    }

    protected void initView() {
        btnView = (Button) findViewById(R.id.btnView);
        btnView.setOnClickListener(this);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(this);
        valueUserAccount = (EditText) findViewById(R.id.valueUserAccount);
        valueUserPassword = (EditText) findViewById(R.id.valueUserPassword);
    }

    @Override
    public void onClick(View view) {
        if (view ==findViewById(R.id.btnView)) {
            //Toast.makeText(UserActivity.this, "View mode", Toast.LENGTH_SHORT).show();
            UserRepo userRepo = new UserRepo();
            // query from user repo
            List<User> users = userRepo.getAllUser();
            ArrayList<String> account = new ArrayList<String>();
            ArrayList<String> password = new ArrayList<String>();

            for (int i = 0; i < users.size(); i++) {

                account.add(users.get(i).getAccount());
                password.add(users.get(i).getPassword());
            }
            Intent viewIntent = new Intent(this, ViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("numCols", 2);
            bundle.putInt("title", R.string.user_manage_view);
            bundle.putStringArrayList("Name", account);
            bundle.putStringArrayList("Id", password);
            viewIntent.putExtras(bundle);
            UserActivity.this.startActivity(viewIntent);
        }
        else if(view ==findViewById(R.id.btnInsert)) {
            UserRepo userRepo = new UserRepo();

            // Check the exist of EditText with value
            if (valueUserAccount.getText().toString().trim().equals("") || valueUserPassword.getText().toString().trim().equals("")) {
                Toast.makeText(UserActivity.this, "請輸入帳號及密碼", Toast.LENGTH_SHORT).show();
            }
            else{
                if (!userRepo.checkAccount(valueUserAccount.getText().toString().trim())) {

                    User user = new User();
                    user.setAccount(valueUserAccount.getText().toString().trim());
                    user.setPassword(valueUserPassword.getText().toString().trim());
                    user.setPriority("1");
                    userRepo.insert(user);
                    valueUserAccount.setText("");
                    valueUserPassword.setText("");
                    Toast.makeText(UserActivity.this, "已新增帳戶:" + user.getAccount(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(UserActivity.this, "該帳戶名稱已存在", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
