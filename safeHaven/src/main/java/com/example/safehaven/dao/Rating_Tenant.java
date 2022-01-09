package com.example.safehaven.dao;

import javax.persistence.*;

@Entity
public class Rating_Tenant {
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;

    private Integer stars;
    private String date_of_issue;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_tenant")
    private User Id_tenant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Landlord")
    private User Id_Landlord;
    @Transient
    private Integer userId;

    public Rating_Tenant() {
    }

    public Rating_Tenant(Integer stars, String date_of_issue, User id_who_put, User id_to_whom) {
        this.stars = stars;
        this.date_of_issue = date_of_issue;
        Id_tenant = id_who_put;
        Id_Landlord = id_to_whom;
    }

    public Integer getId() {
        return Id;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getDate_of_issue() {
        return date_of_issue;
    }

    public void setDate_of_issue(String date_of_issue) {
        this.date_of_issue = date_of_issue;
    }

    public User getId_tenant() {
        return Id_tenant;
    }

    public void setId_tenant(User id_tenant) {
        Id_tenant = id_tenant;
    }

    public User getId_Landlord() {
        return Id_Landlord;
    }

    public void setId_Landlord(User id_Landlord) {
        Id_Landlord = id_Landlord;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
