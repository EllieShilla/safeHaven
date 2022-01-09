package com.example.safehaven.repo;

import com.example.safehaven.dao.Photo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhotoRepo extends CrudRepository<Photo,Long> {
    List<Photo> findAll();
    List<Photo> findByApartmentId(Integer i);
}