package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {

    EditText name_input, type_input, amount_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name_input = findViewById(R.id.name_input);
        type_input = findViewById(R.id.type_input);
        amount_input = findViewById(R.id.amount_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addBudget(name_input.getText().toString().trim(),
                        type_input.getText().toString().trim(),
                        Integer.valueOf(amount_input.getText().toString().trim()));
            }
        });

        Button homeButton = findViewById(R.id.button_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về trang chủ
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}