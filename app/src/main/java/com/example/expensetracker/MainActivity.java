package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

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
        String amount = getIntent().getStringExtra("ammount");

        TextView amountView = findViewById(R.id.txt_spent);

        amountView.setText(amount);

        FirebaseDatabase.getInstance().getReference("Expense Tracker").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if task.isSuccessful() {
                    DataSnapshot snapshot = task.getResult();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        DataClass dataClass = childSnapshot.getValue(DataClass.class);
                        String amount = dataClass.getAmount();


                    }
                    else)
                }
            }
        })
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