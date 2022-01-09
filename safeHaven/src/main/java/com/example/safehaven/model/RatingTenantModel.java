package com.example.safehaven.model;

import com.example.safehaven.dao.City;
import com.example.safehaven.dao.Rating_Landlord;
import com.example.safehaven.dao.Rating_Tenant;
import com.example.safehaven.dao.User;
import com.example.safehaven.repo.ApartmentRepo;
import com.example.safehaven.repo.Rating_Landlord_Repo;
import com.example.safehaven.repo.Rating_Tenant_Repo;
import com.example.safehaven.repo.UserRepo;

import java.util.ArrayList;
import java.util.List;

public class RatingTenantModel {
    public List<Rating_Tenant> findByLandlordId(Rating_Tenant_Repo rating_tenant_repo, Integer Tid,Integer Lid){
        List<Rating_Tenant>rating_tenants=new ArrayList<>();
        for(Rating_Tenant v:rating_tenant_repo.findAll())
            if(v.getId_tenant().getId()==Tid && v.getId_Landlord().getId()==Lid)
                rating_tenants.add(v);

        return  rating_tenants;
    }


    public double RatingTenant(Rating_Tenant_Repo rating_tenant_repo, Integer id){
        int count=0;
        int stars=0;
        for(Rating_Tenant i:rating_tenant_repo.findAll()){
            if(i.getId_tenant().getId()==id){
                count+=1;
                stars+=i.getStars();
            }
        }
        double rating=stars/count;
        return  rating;
    }

    public void BanTenant(double rating, UserRepo userRepo, Integer id){
        if(rating<2.0){
            User user = userRepo.findById(id).isPresent() ? userRepo.findById(id).get() : new User();
            user.setActive(false);
            userRepo.save(user);
        }
    }
}
