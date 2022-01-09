package com.example.safehaven.repo;

import com.example.safehaven.dao.Apartment;
import com.example.safehaven.dao.Rating_Apartment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Rating_Apartment_Repo extends CrudRepository<Rating_Apartment,Long> {
    List<Rating_Apartment>findByApartment(Apartment apartment);
}
