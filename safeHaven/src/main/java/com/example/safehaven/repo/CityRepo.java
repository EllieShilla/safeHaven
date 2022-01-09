package com.example.safehaven.repo;

import com.example.safehaven.dao.City;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepo  extends CrudRepository<City,Integer> {
    List<City> findAll();
    Optional<City> findById(Integer i);
    List<City>findByCountryId(Integer i);
    List<City>findByCity(String str);
}

