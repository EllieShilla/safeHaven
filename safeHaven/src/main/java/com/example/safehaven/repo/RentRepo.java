package com.example.safehaven.repo;

import com.example.safehaven.dao.Apartment;
import com.example.safehaven.dao.Rent;
import com.example.safehaven.dao.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RentRepo extends CrudRepository<Rent,Long> {
    List<Rent> findByApartment(Apartment a);

    List<Rent> findByLandlord(User u);

    List<Rent> findByUser(User u);

    Optional<Rent> findById(Integer id);

}

