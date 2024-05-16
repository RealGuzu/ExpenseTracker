package com.example.expensetracker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetracker.Utilities.DataClass;
import com.example.expensetracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class update_activity extends AppCompatActivity {

    Button updateButton;
    EditText upAmount, upTitle, upDesc;
    Spinner categorySpinner;

    String key;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        initViews();


    }

    private void initViews() {
        upAmount = findViewById(R.id.updateTextAmount);
        upTitle = findViewById(R.id.updateExpenseName);
        upDesc = findViewById(R.id.updateDescription);
        categorySpinner = findViewById(R.id.updatespinnerCategory);
        setupSpinner();

        updateButton = findViewById(R.id.btnSubmitUpdate);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {

                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            key = bundle.getString("key");
            upAmount.setText(bundle.getString("amount"));
            upTitle.setText(bundle.getString("title"));
            upDesc.setText(bundle.getString("description"));
            categorySpinner.setSelection(bundle.getInt("category"));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Expense Tracker");

        updateButton.setOnClickListener(v -> {
            saveData();
            showToast("Data Updated");
        });
    }

    private void saveData() {
        Intent intent = new Intent(update_activity.this, ViewExpenses.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void updateData(){
        String amount = upAmount.getText().toString();
        String title = upTitle.getText().toString();
        String description = upDesc.getText().toString();
        int category = categorySpinner.getSelectedItemPosition();


        DataClass dataClass = new DataClass(amount, title, description, category);

        databaseReference = FirebaseDatabase.getInstance().getReference("Expense Tracker").child(key);
        databaseReference.child(key).setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                showToast("Updated");
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToast(e.getMessage());
            }
        });
    }
    private void setupSpinner() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Select Category");
        categories.add("Food and Groceries");
        categories.add("Housing");
        categories.add("Transportation");
        categories.add("com/example/expensetracker/Utilities");
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
}