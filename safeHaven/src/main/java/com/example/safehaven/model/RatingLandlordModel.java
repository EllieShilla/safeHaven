package com.example.safehaven.model;

import com.example.safehaven.dao.Rating_Landlord;
import com.example.safehaven.dao.User;
import com.example.safehaven.repo.ApartmentRepo;
import com.example.safehaven.repo.Rating_Landlord_Repo;
import com.example.safehaven.repo.UserRepo;

import java.util.ArrayList;
import java.util.List;

public class RatingLandlordModel {
    public List<Rating_Landlord> findByTenantId(Rating_Landlord_Repo rating_landlord_repo, Integer id){
        List<Rating_Landlord>rating_landlords=new ArrayList<>();
        for(Rating_Landlord v:rating_landlord_repo.findAll())
            if(v.getId_tenant().getId()==id)
                rating_landlords.add(v);

        return  rating_landlords;
    }

    public List<Rating_Landlord> findByTenantAndApartment(Rating_Landlord_Repo rating_landlord_repo, ApartmentRepo apartmentRepo, Integer tenant_id, Integer apartment_id){
        List<Rating_Landlord>rating_landlords=new ArrayList<>();
        ApartmentModel apartmentModel=new ApartmentModel();

        for(Rating_Landlord v:rating_landlord_repo.findAll())
            if(v.getId_tenant().getId()==tenant_id)
                if(v.getId_Landlord().getId()==apartmentModel.GetById(apartmentRepo,apartment_id).getUser().getId())
                    rating_landlords.add(v);

        return  rating_landlords;
    }

    public double RatingLandlord(Rating_Landlord_Repo rating_landlord_repo, Integer id){
        int count=0;
        int stars=0;
        for(Rating_Landlord i:rating_landlord_repo.findAll()){
            if(i.getId_Landlord().getId()==id){
                count+=1;
                stars+=i.getStars();
            }
        }
        double rating=stars/count;
        return  rating;
    }

    public void BanLandlord(double rating, UserRepo userRepo, Integer id){
        if(rating<2.0){
            User user = userRepo.findById(id).isPresent() ? userRepo.findById(id).get() : new User();
            user.setActive(false);
            userRepo.save(user);
        }
    }
}
