package com.example.expensetracker.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.expensetracker.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class expense extends Fragment {

    Spinner spinCategory, spinMethod;
    TextInputLayout inputLayoutAmount, inputLayoutDescription;
    TextInputEditText inputAmount, inputDescription;
    TextView displayCategory, displayMethod;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense, container, false);



        initViews(view);
        setupSpinner1();
        setupSpinner2();


        return view;
    }

    private void setupSpinner2() {
        ArrayList<String> methods = new ArrayList<>();
        methods.add("Select Method");
        methods.add("Cash");
        methods.add("Debit");
        methods.add("Credit");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, methods);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinMethod.setAdapter(adapter);

        spinMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    displayMethod.setText("Category: " + methods.get(position));
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
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
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
        inputLayoutAmount = view.findViewById(R.id.textInputLayout);
        inputAmount = view.findViewById(R.id.expenseAmount);
        displayCategory = view.findViewById(R.id.displayCategory);
        displayMethod = view.findViewById(R.id.txtDisplayMeth);
        inputLayoutDescription = view.findViewById(R.id.inputLayoutDescription);
        inputDescription = view.findViewById(R.id.expenseDesc);
    }
}