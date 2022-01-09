package com.example.safehaven.repo;

import com.example.safehaven.dao.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CountryRepo extends CrudRepository<Country, Long> {
    Iterable<Country> findAll();

    Optional<Country> findById(Integer i);

    Country findByCountryTitle(String str);
}