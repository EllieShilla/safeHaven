package com.example.safehaven.repo;

import com.example.safehaven.dao.Apartment;
import com.example.safehaven.dao.City;
import com.example.safehaven.dao.Country;
import com.example.safehaven.dao.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ApartmentRepo extends CrudRepository<Apartment,Long> {
    Optional<Apartment> findById(Integer i);
    List<Apartment> findAll();
    List<Apartment> findByUser(User user);

    List<Apartment> findByCity(City city);
    List<Apartment>findByCountry(Country country);
}