package com.example.safehaven.repo;

import com.example.safehaven.dao.ApartmentType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ApartmentTypeRepo extends CrudRepository<ApartmentType,Long> {
    List<ApartmentType> findAll();
    ApartmentType findByType(String type);
    Optional<ApartmentType> findById(Integer i);

}
