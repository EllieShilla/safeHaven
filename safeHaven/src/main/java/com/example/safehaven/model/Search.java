package com.example.safehaven.model;

import com.example.safehaven.dao.Apartment;
import com.example.safehaven.dao.City;
import com.example.safehaven.dao.Rent;
import com.example.safehaven.dao.User;
import com.example.safehaven.repo.ApartmentRepo;
import com.example.safehaven.repo.CityRepo;
import com.example.safehaven.repo.CountryRepo;
import com.example.safehaven.repo.RentRepo;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Search {

    public List<Apartment> search(Question question, CityRepo cityRepo, ApartmentRepo apartmentRepo, CountryRepo countryRepo, RentRepo rentRepo) throws ParseException {
        RentModel rentModel = new RentModel();
        List<Apartment> apartmentList = new ArrayList<>();

        for (City i : cityRepo.findByCity(question.getCity().toLowerCase())) {
            for (Apartment j : apartmentRepo.findByCity(i)) {
                if (j.getNumber_of_person() >= question.getPersons())
                    apartmentList.add(j);
            }
        }

        List<Apartment> checkDataList = new ArrayList<>();
        for (Apartment i : apartmentList) {
            if (rentModel.GetByApartment(rentRepo, i).size() == 0)
                checkDataList.add(i);
            else
                for (Rent j : rentModel.GetByApartment(rentRepo, i)) {
                    if (LocalDate.parse(question.getFrom()).compareTo(LocalDate.parse(j.getDeparture())) > 0 || LocalDate.parse(question.getTo()).compareTo(LocalDate.parse(j.getEntry())) < 0) {
                        checkDataList.add(i);
                        break;
                    }
                }
        }

        List<Apartment> withoutBan = new ArrayList<>();
        for(Apartment i:checkDataList){
            if(i.getUser().isActive()==true)
                withoutBan.add(i);
        }

        return withoutBan;
    }

    public boolean IsFree(RentRepo rentRepo, Apartment apartment, Rent rent) {
        RentModel rentModel = new RentModel();
        boolean isFree = false;

        if (rentModel.GetByApartment(rentRepo, apartment).size() == 0)
            isFree = true;
        else {
            for (Rent i : rentModel.GetByApartment(rentRepo, apartment)) {
                if (LocalDate.parse(rent.getEntry()).compareTo(LocalDate.parse(i.getDeparture())) > 0 || LocalDate.parse(rent.getDeparture()).compareTo(LocalDate.parse(i.getEntry())) < 0) {
                    System.out.println("good");
                    isFree = true;
                }
            }
        }
        return isFree;
    }

    public List<Rent> futureRent(RentRepo rentRepo, User user) throws ParseException {
        List<Rent>futureRent=new ArrayList<>();
        WorkWithDate workWithDate=new WorkWithDate();
        for(Rent i:rentRepo.findByUser(user)){
            if(LocalDate.parse(workWithDate.Today()).compareTo(LocalDate.parse(i.getEntry()))< 0)
                futureRent.add(i);
        }
        return futureRent;
    }

    public List<Rent> pastRent(RentRepo rentRepo, User user) throws ParseException {
        List<Rent>futureRent=new ArrayList<>();
        WorkWithDate workWithDate=new WorkWithDate();
        for(Rent i:rentRepo.findByUser(user)){
            if(LocalDate.parse(workWithDate.Today()).compareTo(LocalDate.parse(i.getDeparture()))> 0)
                futureRent.add(i);
        }
        return futureRent;
    }

    public List<Rent> nowRent(RentRepo rentRepo, User user) throws ParseException {
        List<Rent>futureRent=new ArrayList<>();
        WorkWithDate workWithDate=new WorkWithDate();
        for(Rent i:rentRepo.findByUser(user)){
            if((LocalDate.parse(workWithDate.Today()).compareTo(LocalDate.parse(i.getEntry()))> 0 && LocalDate.parse(workWithDate.Today()).compareTo(LocalDate.parse(i.getDeparture()))< 0)||LocalDate.parse(workWithDate.Today()).compareTo(LocalDate.parse(i.getEntry()))== 0||LocalDate.parse(workWithDate.Today()).compareTo(LocalDate.parse(i.getDeparture()))== 0)
                futureRent.add(i);
        }
        return futureRent;
    }

//    public List<Rent> canRemove(RentRepo rentRepo, User user) throws ParseException {
//        List<Rent>canDeleteRent=new ArrayList<>();
//        WorkWithDate workWithDate=new WorkWithDate();
//        for(Rent i:rentRepo.findByUser(user)){
//            if(LocalDate.parse(workWithDate.NextDay(2)).compareTo(LocalDate.parse(i.getEntry()))< 0)
//                canDeleteRent.add(i);
//        }
//        return canDeleteRent;
//    }
}
