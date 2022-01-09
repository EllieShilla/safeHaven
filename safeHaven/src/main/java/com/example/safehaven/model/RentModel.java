package com.example.safehaven.model;

import com.example.safehaven.dao.Apartment;
import com.example.safehaven.dao.Rent;
import com.example.safehaven.repo.RentRepo;

import java.util.ArrayList;
import java.util.List;

public class RentModel {

    public List<Rent> GetByApartment(RentRepo rentRepo, Apartment apartment) {
        List<Rent>rentList=new ArrayList<>();
        rentList.addAll(rentRepo.findByApartment(apartment));
        return rentList;
    }

    public void DeleteRent(RentRepo rentRepo, Integer id){
        Rent rent = rentRepo.findById(id).isPresent() ? rentRepo.findById(id).get() : new Rent();
        rentRepo.delete(rent);
    }
}