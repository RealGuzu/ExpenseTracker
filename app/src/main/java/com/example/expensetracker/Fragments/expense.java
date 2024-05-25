package com.example.expensetracker.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetracker.Activities.MainActivity;
import com.example.expensetracker.R;
import com.example.expensetracker.Utilities.DataClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class expense extends Fragment {

    Spinner spinCategory, spinMethod;
    EditText inputAmount, inputTitle, inputDescription;
    TextView displayCategory, displayMethod;
    String title, desc, method, category, amount;
    Button saveButton;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        initViews(view);
        setupSpinner1();
        setupSpinner2();

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Expenses");

        // Set up the save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        return view;
    }

    private void setupSpinner2() {
        ArrayList<String> paymentMethods = new ArrayList<>();
        paymentMethods.add("Select Payment Method");
        paymentMethods.add("Cash");
        paymentMethods.add("Credit Card");
        paymentMethods.add("Debit Card");
        paymentMethods.add("Bank Transfer");
        paymentMethods.add("Mobile Payment");
        paymentMethods.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, paymentMethods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMethod.setAdapter(adapter);

        spinMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    displayMethod.setText("Method: " + paymentMethods.get(position));
                } else {
                    displayMethod.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                displayMethod.setText("");
            }
        });
    }

    private void setupSpinner1() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Select Category");
        categories.add("Food and Groceries");
        categories.add("Housing");
        categories.add("Transportation");
        categories.add("Utilities");
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(adapter);

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    displayCategory.setText("Category: " + categories.get(position));
                } else {
                    displayCategory.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                displayCategory.setText("");
            }
        });
    }

    private void initViews(View view) {
        spinCategory = view.findViewById(R.id.selectCategory);
        spinMethod = view.findViewById(R.id.spinMethod);
        inputAmount = view.findViewById(R.id.expenseAmount);
        displayCategory = view.findViewById(R.id.displayCategory);
        displayMethod = view.findViewById(R.id.txtDisplayMeth);
        inputDescription = view.findViewById(R.id.expenseDesc);
        inputTitle = view.findViewById(R.id.expenseTitle);
        saveButton = view.findViewById(R.id.saveBtn);
    }

    private void openMain() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void updateData() {
        title = inputTitle.getText().toString().trim();
        desc = inputDescription.getText().toString().trim();
        method = spinMethod.getSelectedItem().toString();
        category = spinCategory.getSelectedItem().toString();
        amount = inputAmount.getText().toString().trim();

        // Validation
        if (title.isEmpty() || desc.isEmpty() || method.equals("Select Payment Method") || category.equals("Select Category") || amount.isEmpty()) {
            showToast("Please fill all fields");
            return;
        }

        DataClass dataClass = new DataClass(title, amount, desc, category, method);

        // Initialize a unique database reference for each new data entry
        DatabaseReference newRef = databaseReference.push();

        newRef.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    showToast("Data Updated");
                    openMain();
                } else {
                    showToast("Failed to Update Data");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToast("Failed to Update Data: " + e.getMessage());
            }
        });
    }
}
