//package com.example.expensetracker.Activities;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.expensetracker.Utilities.DataClass;
//import com.example.expensetracker.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.ArrayList;
//
//public class update_activity extends AppCompatActivity {
//
//    // Declare UI elements
//    Button updateButton;
//    EditText upAmount, upTitle, upDesc;
//    Spinner categorySpinner;
//
//    // Declare variables to store the key and database reference
//    String key;
//    DatabaseReference databaseReference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update);
//
//        // Initialize UI elements
//        initViews();
//    }
//
//    // Initialize UI elements
//    private void initViews() {
//        // Find UI elements by their IDs
//        upAmount = findViewById(R.id.updateTextAmount);
//        upTitle = findViewById(R.id.updateExpenseName);
//        upDesc = findViewById(R.id.updateDescription);
//        categorySpinner = findViewById(R.id.updatespinnerCategory);
//        setupSpinner(); // Set up the spinner with categories
//
//        // Find the update button
//        updateButton = findViewById(R.id.btnSubmitUpdate);
//
//        // Register for activity result (not used in this code)
//        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult o) {
//
//                    }
//                }
//        );
//
//        // Get the bundle from the intent
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            // Get the key, amount, title, description, and category from the bundle
//            key = bundle.getString("key");
//            upAmount.setText(bundle.getString("amount"));
//            upTitle.setText(bundle.getString("title"));
//            upDesc.setText(bundle.getString("description"));
//            categorySpinner.setSelection(bundle.getInt("category"));
//        }
//
//        // Get the Firebase database reference
//        databaseReference = FirebaseDatabase.getInstance().getReference("Expense Tracker");
//
//        // Set the update button's click listener
//        updateButton.setOnClickListener(v -> {
//             // Save the data (not implemented correctly)
//            showToast("Data Updated"); // Show a toast message
//        });
//    }
//
//
//
//    // Show a toast message
//    private void showToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//
//    // Update the data in the Firebase database
//    public void updateData(){
//        // Get the input values
//        String amount = upAmount.getText().toString();
//        String title = upTitle.getText().toString();
//        String description = upDesc.getText().toString();
//        int category = categorySpinner.getSelectedItemPosition();
//
//        // Create a DataClass object with the input values
//        DataClass dataClass = new DataClass(amount, title, description, category);
//
//        // Get the Firebase database reference with the key
//        databaseReference = FirebaseDatabase.getInstance().getReference("Expense Tracker").child(key);
//
//        // Update the data in the Firebase database
//        databaseReference.child(key).setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                showToast("Updated"); // Show a toast message
//                finish(); // Finish the activity
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                showToast(e.getMessage()); // Show an error message
//            }
//        });
//    }
//
//    // Set up the spinner with categories
//    private void setupSpinner() {
//        ArrayList<String> categories = new ArrayList<>();
//        categories.add("Select Category");
//        categories.add("Food and Groceries");
//        categories.add("Housing");
//        categories.add("Transportation");
//        categories.add("com/example/expensetracker/Utilities");
//        categories.add("Healthcare");
//        categories.add("Personal Care");
//        categories.add("Entertainment");
//        categories.add("Debts");
//        categories.add("Education");
//        categories.add("Savings");
//        categories.add("Gifts and Donations");
//        categories.add("Travel");
//        categories.add("Insurance");
//        categories.add("Miscellaneous");
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
//        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
//
//        categorySpinner.setAdapter(adapter);
//    }
//}