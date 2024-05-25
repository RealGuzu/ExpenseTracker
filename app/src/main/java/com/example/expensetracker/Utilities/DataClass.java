package com.example.expensetracker.Utilities;

public class DataClass {
    private String title;
    private String amount;
    private String description;
    private String category;
    private String method;
    private String key;

    // Empty constructor needed for Firebase
    public DataClass() {
    }

    // Constructor with all fields
    public DataClass(String title, String amount, String description, String category, String method) {
        this.title = title;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.method = method;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
