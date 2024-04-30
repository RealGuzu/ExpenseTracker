package com.example.expensetracker;

public class DataClass {
    public String title;
    public String amount;
    public String description;
    public String category;

    // Empty constructor needed for Firebase
    public DataClass() {

    }

    public DataClass(String title, String amount, String description, String category) {
        this.title = title;
        this.amount = amount;
        this.description = description;
        this.category = category;
    }
}
