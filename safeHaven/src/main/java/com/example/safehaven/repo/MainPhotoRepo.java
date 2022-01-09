package com.example.safehaven.repo;

import com.example.safehaven.dao.MainPhoto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MainPhotoRepo extends CrudRepository<MainPhoto,Long> {
    List<MainPhoto> findAll();
    List<MainPhoto> findByApartmentId(Integer i);
}
