package com.example.safehaven.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String countryTitle;

    public Country() {
    }


    public Country(Integer id, String countryTitle) {
        this.id = id;
        this.countryTitle = countryTitle;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getCountry() {
        return countryTitle;
    }

    public void setCountry(String countryTitle) {
        this.countryTitle = countryTitle;
    }
}
