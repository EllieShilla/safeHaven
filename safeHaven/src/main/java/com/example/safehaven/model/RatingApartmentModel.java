package com.example.safehaven.model;

import com.example.safehaven.dao.Apartment;
import com.example.safehaven.dao.Rating_Apartment;
import com.example.safehaven.repo.ApartmentRepo;
import com.example.safehaven.repo.Rating_Apartment_Repo;

import java.util.ArrayList;
import java.util.List;

public class RatingApartmentModel {

    public List<Rating_Apartment> findByTenantId(Rating_Apartment_Repo ratingApartmentRepo, Integer id) {
        List<Rating_Apartment> rating_apartments = new ArrayList<>();
        for (Rating_Apartment v : ratingApartmentRepo.findAll())
            if (v.getUser().getId() == id)
                rating_apartments.add(v);
        return rating_apartments;
    }

    public Rating_Apartment findByTenantAndApartment(Rating_Apartment_Repo rating_apartmentRepo, Integer tenant_id, Integer apartment_id) {
        Rating_Apartment rating_apartment = new Rating_Apartment();
        for (Rating_Apartment v : rating_apartmentRepo.findAll())
            if (v.getUser().getId() == tenant_id)
                if (v.getApartment().getId() == apartment_id) {
                    rating_apartment.setId(v.getId());
                    rating_apartment.setApartment(v.getApartment());
                    rating_apartment.setUser(v.getUser());
                    rating_apartment.setStars(v.getStars());
                    rating_apartment.setDatetime(v.getDatetime());
                }
        return rating_apartment;
    }

    public List<Rating> ApartmentRating(Rating_Apartment_Repo rating_apartmentRepo, ApartmentRepo apartmentRepo) {

        List<Rating> rating_apartments = new ArrayList<>();
        List<Apartment> apartments = new ArrayList<>();
        for (Apartment j : apartmentRepo.findAll()) {
            if (rating_apartmentRepo.findByApartment(j).size() > 0) {
                Rating rating = new Rating();
                for (Rating_Apartment i : rating_apartmentRepo.findByApartment(j)) {
                    rating.setCount(1);
                    rating.setApartmentId(j.getId());
                    rating.setStars(i.getStars());
                }
                rating_apartments.add(rating);
            }
        }

        for(Rating q:rating_apartments){
            q.setRating(q.getStars()/q.getCount());
        }

        return rating_apartments;
    }
}
