package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class update_activity extends AppCompatActivity {

    Button updateButton;
    EditText upAmount, upTitle, upDesc;
    Spinner spinner;

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
        spinner = findViewById(R.id.updatespinnerCategory);
        updateButton = findViewById(R.id.btnUpdate);

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
            spinner.setSelection(bundle.getInt("category"));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Expense Tracker");

        updateButton.setOnClickListener(v -> {
            saveData();
            showToast("Data Updated");
        });
    }

    private void saveData() {
        Intent intent = new Intent(update_activity.this, MainActivity.class);
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

    public void updateData() {
        String amount = upAmount.getText().toString().trim();
        String title = upTitle.getText().toString().trim();
        String description = upDesc.getText().toString().trim();
        int category = spinner.getSelectedItemPosition();


        DataClass dataClass = new DataClass(amount, title, description, category);
        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
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
}