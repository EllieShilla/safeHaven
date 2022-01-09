package com.example.safehaven.model;

public class Rating {
    private int count;
    private int stars;
    private Integer ApartmentId;
    private double rating;

    public Rating() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count += count;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars += stars;
    }

    public Integer getApartmentId() {
        return ApartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        ApartmentId = apartmentId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
