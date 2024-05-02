package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class add_expense extends AppCompatActivity {

    Spinner spinner;
    Button btn;
    EditText setAmount, setTitle, setDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        spinner = findViewById(R.id.spinnerCategory);
        btn = findViewById(R.id.btnSubmit);
        setAmount = findViewById(R.id.editTextAmount);
        setTitle = findViewById(R.id.editExpenseName);
        setDesc = findViewById(R.id.editDescription);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
                openHome();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select Category");
        arrayList.add("Food and Groceries");
        arrayList.add("Housing");
        arrayList.add("Transportation");
        arrayList.add("Utilities");
        arrayList.add("Healthcare");
        arrayList.add("Personal Care");
        arrayList.add("Entertainment");
        arrayList.add("Debts");
        arrayList.add("Education");
        arrayList.add("Savings");
        arrayList.add("Gifts and Donations");
        arrayList.add("Travel");
        arrayList.add("Insurance");
        arrayList.add("Miscellaneous");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
    }

    private void openHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void uploadData() {
        String title = setTitle.getText().toString();
        String amount = setAmount.getText().toString();
        String desc = setDesc.getText().toString();
        String category = spinner.getSelectedItem().toString();

        DataClass dataClass = new DataClass(title, amount, desc, category);

        FirebaseDatabase.getInstance().getReference("Expense Tracker").child(title)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showToast("Saved");
                            finish();
                        } else {
                            showToast("Failed to save data");
                            // Log the exception
                            if (task.getException() != null) {
                                Log.e("Firebase", "Error: " + task.getException().getMessage());
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Failed to save data");
                        // Log the exception
                        Log.e("Firebase", "Error: " + e.getMessage());
                    }
                });
    }


    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}