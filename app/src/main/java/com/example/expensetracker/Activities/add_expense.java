package com.example.expensetracker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetracker.Utilities.DataClass;
import com.example.expensetracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class add_expense extends AppCompatActivity {

    private Spinner categorySpinner;
    private Spinner paymentMethodSpinner;
    private Button submitButton;
    private EditText amountEditText;
    private EditText titleEditText;
    private EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        initViews();
        setupCategorySpinner();
        setupPaymentMethodSpinner();
        setupSubmitButton();
    }

    private void initViews() {
        categorySpinner = findViewById(R.id.spinnerCategory);
        paymentMethodSpinner = findViewById(R.id.spinMethod);
        submitButton = findViewById(R.id.btnSubmit);
        amountEditText = findViewById(R.id.editTextAmount);
        titleEditText = findViewById(R.id.editExpenseName);
        descriptionEditText = findViewById(R.id.editDescription);
    }

    private void setupCategorySpinner() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Select Category");
        categories.add("Food and Groceries");
        categories.add("Housing");
        categories.add("Transportation");
        categories.add("Healthcare");
        categories.add("Personal Care");
        categories.add("Entertainment");
        categories.add("Debts");
        categories.add("Education");
        categories.add("Savings");
        categories.add("Gifts and Donations");
        categories.add("Travel");
        categories.add("Insurance");
        categories.add("Miscellaneous");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        categorySpinner.setAdapter(adapter);
    }

    private void setupPaymentMethodSpinner() {
        ArrayList<String> paymentMethods = new ArrayList<>();
        paymentMethods.add("Select Payment Method");
        paymentMethods.add("Cash");
        paymentMethods.add("Credit Card");
        paymentMethods.add("Debit Card");
        paymentMethods.add("Bank Transfer");
        paymentMethods.add("Mobile Payment");
        paymentMethods.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paymentMethods);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        paymentMethodSpinner.setAdapter(adapter);
    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
                openHome();
            }
        });
    }

    private void openHome() {
        String amount = amountEditText.getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("amount", amount);
        startActivity(intent);
    }

    private void uploadData() {
        String title = titleEditText.getText().toString();
        String amount = amountEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();
        String paymentMethod = paymentMethodSpinner.getSelectedItem().toString();
        DataClass dataClass = new DataClass(title, amount, description, category, paymentMethod);

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Expenses").child(currentDate)
                .setValue(dataClass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showToast("Saved");
                        } else {
                            showToast("Failed to save data");
                            Log.e("Firebase", "Error: " + task.getException().getMessage());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Failed to save data");
                        Log.e("Firebase", "Error: " + e.getMessage());
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
