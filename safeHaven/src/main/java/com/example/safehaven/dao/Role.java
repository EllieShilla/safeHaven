package com.example.safehaven.dao;

public enum Role {
    USER (""),
    ADMIN (""),
    LANDLORD ("Арендодатель"),
    TENANT("Арендатор");
    private String title;

    Role(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
