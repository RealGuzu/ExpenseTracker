package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fabAdd);
        btn = findViewById(R.id.btnExpense);
        fab.setOnClickListener(v -> openAddExpense());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewExpense();
            }
        });
    }

    private void openViewExpense() {
        Intent intent = new Intent(this, ViewExpenses.class);
        startActivity(intent);
    }

    private void openAddExpense() {
        Intent intent = new Intent(this, add_expense.class);
        startActivity(intent);
    }
}