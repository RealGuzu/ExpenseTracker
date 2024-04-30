package com.example.expensetracker;

public class DataClass {
    public String title;
    public String amount;
    public String description;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getTitle() {
        return title;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

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

