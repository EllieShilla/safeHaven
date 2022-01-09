package com.example.safehaven.model;

import com.example.safehaven.dao.*;
import com.example.safehaven.repo.CityRepo;
import com.example.safehaven.repo.CountryRepo;

import java.util.Collections;

public class Registr {

    public User Registration(CityRepo cityRepo, CountryRepo countryRepo, User user) {
        City city1 = cityRepo.findById(user.getCity().getId()).isPresent() ? cityRepo.findById(user.getCity().getId()).get() : new City();
        Country country1 = countryRepo.findById(city1.getCountry().getId()).isPresent() ? countryRepo.findById(city1.getCountry().getId()).get() : new Country();
        user.setCity(city1);
        user.setCountry(country1);
        user.setActive(true);
        return user;
    }
}
