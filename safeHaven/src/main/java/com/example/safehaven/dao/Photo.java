package com.example.safehaven.dao;


import javax.persistence.*;
import java.sql.Blob;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;
    private byte[] photo;
    //    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_apartment")
    private Apartment apartment;


    public Photo() {
    }

    public Photo(String title, Apartment apartment) {
        this.title = title;
        this.apartment = apartment;
    }

    public Photo(String title, byte[] photo, Apartment apartment) {
        this.title = title;
        this.photo = photo;
        this.apartment = apartment;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
