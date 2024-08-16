package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.SQLite.UserDB;

public class SignUpActivity extends AppCompatActivity {
    private EditText editUsername, editPassword, editEmail,editPhone;
    private Button btnSubmit;
    private UserDB userDb;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activty_signup);
        editUsername = findViewById(R.id.edtUser);
        editPassword  = findViewById(R.id.edtPass);
        editEmail = findViewById(R.id.edtEmail);
        editPhone = findViewById(R.id.edtPhoneNumber);
        btnSubmit = findViewById(R.id.btnSignUp);
        userDb = new UserDB(SignUpActivity.this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertUser();
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
    private  void insertUser(){
        String user = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        if (TextUtils.isEmpty(user)){
            editUsername.setError("Username can be not empty");
            return;
        }
        if(TextUtils.isEmpty(password)){
            editPassword.setError("Password can mot empty");
            return;
        }
        if (TextUtils.isEmpty(email)){
            editEmail.setError("Email can not empty");
            return;
        }
        if (TextUtils.isEmpty(phone)){
            editPhone.setError("phone can not be empty");
            return;
        }
        long result = userDb.addNewUser(user,password,email,phone);
        if (result == -1){
            Toast.makeText(SignUpActivity.this,"Sign up Falure",Toast.LENGTH_SHORT).show();
            return;
        }else {
            Toast.makeText(SignUpActivity.this,"Sign up Successfully",Toast.LENGTH_SHORT).show();
        }
    }
}
