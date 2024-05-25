package com.example.expensetracker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.Utilities.DataClass;
import com.example.expensetracker.Utilities.MyAdapter;
import com.example.expensetracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    TextView txtViewAll;
    private MyAdapter adapter;
    private List<DataClass> dataList;
    private DatabaseReference databaseReference;
    RecyclerView recyclerView;
    private String deletedExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab_add);
        txtViewAll = findViewById(R.id.txtViewAll);
        fab.setOnClickListener(v -> openAddExpense());
        recyclerView = findViewById(R.id.preview_list);

        txtViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewExpense();
            }
        });

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Expenses");

        // Set up RecyclerView
        setupRecyclerView();

        // Attach ValueEventListener to listen for changes in the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    dataList.add(dataClass);
                    dataClass.setKey(itemSnapshot.getKey());
                }
                adapter.notifyDataSetChanged(); // Notify adapter after data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
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

    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();
        adapter = new MyAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

        // Attach ItemTouchHelper here
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private class ItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {
        private MyAdapter adapter;

        public ItemTouchHelperCallback(MyAdapter adapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.adapter = adapter;
        }



        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            String expenseKeyToDelete = dataList.get(position).getKey(); // Get the key of the expense to be deleted

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    deletedExpense = String.valueOf(dataList.get(position));
                    dataList.remove(position);
                    adapter.notifyItemRemoved(position);
                    Snackbar.make(recyclerView, deletedExpense, Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DataClass newDataClass = new DataClass();
                                    try {
                                        newDataClass = new Gson().fromJson(deletedExpense, DataClass.class);
                                    } catch (JsonSyntaxException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        DataClass dataClass = new Gson().fromJson(deletedExpense, DataClass.class);
                                        dataList.add(position, dataClass);
                                        adapter.notifyItemInserted(position);
                                    } catch (JsonSyntaxException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                    // Delete the expense from the database
                    deleteExpenseFromDatabase(expenseKeyToDelete);
                    break;

                case ItemTouchHelper.RIGHT:
                    break;
            }
        }
    }

    private void deleteExpenseFromDatabase(String expenseKey) {
        DatabaseReference expenseRef = FirebaseDatabase.getInstance().getReference("Expenses").child(expenseKey);
        expenseRef.removeValue();
    }
}