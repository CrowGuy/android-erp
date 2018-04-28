package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.myapplication.data.repo.UserRepo;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextAccountToLogin, editTextPasswordToLogin;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextAccountToLogin = (EditText)findViewById(R.id.editTextAccountToLogin) ;
        editTextPasswordToLogin = (EditText)findViewById(R.id.editTextPasswordToLogin);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
    }

    private void verifyFromSQLite() {
        UserRepo userRepo = new UserRepo();

        if (userRepo.checkUser(editTextAccountToLogin.getText().toString().trim()
                , editTextPasswordToLogin.getText().toString().trim())) {

            Intent manageIntent = new Intent(this, ManageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("account", editTextAccountToLogin.getText().toString().trim());
            manageIntent.putExtras(bundle);
            emptyInputEditText();
            startActivity(manageIntent);
        }
        else {
            Toast.makeText(LoginActivity.this, "請確認帳號/密碼是否輸入正確", Toast.LENGTH_SHORT).show();
            emptyInputEditText();
        }
    }

    @Override
    public void onClick(View view) {
        if (view ==findViewById(R.id.btnSignIn)){
            verifyFromSQLite();
        }
    }

    private void emptyInputEditText() {
        editTextAccountToLogin.setText(null);
        editTextPasswordToLogin.setText(null);
    }

}
