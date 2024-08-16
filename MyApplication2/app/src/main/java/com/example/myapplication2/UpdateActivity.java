package com.example.myapplication2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateActivity extends AppCompatActivity {

    EditText name_input, type_input, amount_input;
    Button update_button, delete_button;
    String id, name, type, amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name_input = findViewById(R.id.name_input2);
        type_input = findViewById(R.id.type_input2);
        amount_input = findViewById(R.id.amount_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //And only then we call this
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                name = name_input.getText().toString().trim();
                type = type_input.getText().toString().trim();
                amount = amount_input.getText().toString().trim();
                myDB.updateData(id, name, type, amount);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        Button homeButton = findViewById(R.id.button_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về trang chủ
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("type") && getIntent().hasExtra("amount")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            type = getIntent().getStringExtra("type");
            amount = getIntent().getStringExtra("amount");

            //Setting Intent Data
            name_input.setText(name);
            type_input.setText(type);
            amount_input.setText(amount);
            Log.d("stev", name+" "+type+" "+amount);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?" );
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}