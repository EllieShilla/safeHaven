package com.example.safehaven.model;

import com.example.safehaven.dao.Apartment;
import com.example.safehaven.repo.ApartmentRepo;

import java.util.ArrayList;
import java.util.List;

public class ApartmentModel {

    public Apartment GetById(ApartmentRepo apartmentRepo, Integer id) {
        Apartment apartment1 = apartmentRepo.findById(id).isPresent() ? apartmentRepo.findById(id).get() : new Apartment();
        return apartment1;
    }

    public List<Apartment> GetWithoutBan(ApartmentRepo apartmentRepo){
        List<Apartment>apartments=new ArrayList<>();

        for(Apartment i:apartmentRepo.findAll()){
            if(i.getUser().isActive()==true)
                apartments.add(i);
        }
        return apartments;
    }
}
