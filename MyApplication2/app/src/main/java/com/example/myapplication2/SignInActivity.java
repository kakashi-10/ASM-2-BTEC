package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.Model.User;
import com.example.myapplication2.SQLite.UserDB;

public class SignInActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btnSignIn, btnSignUp;
    private UserDB userDb;
    private TextView tvError;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        edtUsername = findViewById(R.id.edtUsernameSignIn);
        edtPassword = findViewById(R.id.edtPassSignIn);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnRegister);
        userDb = new UserDB(SignInActivity.this);
        tvError = findViewById(R.id.tvError);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    private void login(){
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            edtUsername.setError("Username can be not empty");
            return;
        }
        if(TextUtils.isEmpty(password)){
            edtPassword.setError("Password can be not empty");
            return;
        }
        User infoUser = userDb.getSingleUser(username, password);
        assert infoUser != null;
        if (infoUser.getEmail() != null && infoUser.getPhone() != null){
            // tai khoan thuc su co ton tai trong database
            tvError.setText("");
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // dang nhap sai
            tvError.setText("Account invalid");
            return;
        }

    }
}