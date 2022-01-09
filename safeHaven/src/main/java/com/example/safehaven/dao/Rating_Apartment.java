package com.example.safehaven.dao;

import javax.persistence.*;

@Entity
public class Rating_Apartment {
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;

    private Integer stars;
    private String datetime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_apartment")
    private Apartment apartment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_user")
    private User user;
    @Transient
    private Integer userId;
    @Transient
    private Integer apartmentId;

    public Rating_Apartment() {
    }

    public Rating_Apartment(Integer id, Integer stars, String datetime, Apartment apartment, User user) {
        Id = id;
        this.stars = stars;
        this.datetime = datetime;
        this.apartment = apartment;
        this.user = user;
        this.userId = userId;
        this.apartmentId = apartmentId;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getId() {
        return Id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }
}
