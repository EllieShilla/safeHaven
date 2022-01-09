package com.example.safehaven.dao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(length = 1000)
    @NotBlank(message = "Address is mandatory")
    private String address;
    @Column(length = 1000)
    @NotBlank(message = "Title is mandatory")
    private String title;
    private Integer price_for_1_day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_country")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_city")
    private City city;
    @NotBlank(message = "Check in time is mandatory")
    private String check_IN_time;
    @NotBlank(message = "Check out time is mandatory")
    private String check_OUT_time;
    //    @NotBlank(message = "Number of person is mandatory")
    private Integer number_of_person;
    @Column(length = 15000)
    @NotBlank(message = "About is mandatory")
    private String about;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_owner")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_type")
    private ApartmentType apartmentType;

    public Apartment() {
    }

    public Apartment(Integer id, String title,  String address, Integer price_for_1_day, Country country, City city, String check_IN_time, String check_OUT_time, Integer number_of_person, String about, User user, ApartmentType apartmentType) {
        this.id = id;
        this.title=title;
        this.address = address;
        this.price_for_1_day = price_for_1_day;
        this.country = country;
        this.city = city;
        this.check_IN_time = check_IN_time;
        this.check_OUT_time = check_OUT_time;
        this.number_of_person = number_of_person;
        this.about = about;
        this.user = user;
        this.apartmentType = apartmentType;
    }

    public Integer getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPrice_for_1_day() {
        return price_for_1_day;
    }

    public void setPrice_for_1_day(Integer price_for_1_day) {
        this.price_for_1_day = price_for_1_day;
    }

    public String getCheck_IN_time() {
        return check_IN_time;
    }

    public void setCheck_IN_time(String check_IN_time) {
        this.check_IN_time = check_IN_time;
    }

    public String getCheck_OUT_time() {
        return check_OUT_time;
    }

    public void setCheck_OUT_time(String check_OUT_time) {
        this.check_OUT_time = check_OUT_time;
    }

    public Integer getNumber_of_person() {
        return number_of_person;
    }

    public void setNumber_of_person(Integer number_of_person) {
        this.number_of_person = number_of_person;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }
}
