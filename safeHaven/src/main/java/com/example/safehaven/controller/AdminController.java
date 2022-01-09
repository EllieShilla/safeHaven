package com.example.safehaven.controller;

import com.example.safehaven.dao.ApartmentType;
import com.example.safehaven.dao.City;
import com.example.safehaven.dao.Country;
import com.example.safehaven.model.LoginUser;
import com.example.safehaven.model.Question;
import com.example.safehaven.model.Registr;
import com.example.safehaven.repo.ApartmentTypeRepo;
import com.example.safehaven.repo.CityRepo;
import com.example.safehaven.repo.CountryRepo;
import com.example.safehaven.repo.UserRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserRepo userRepo;
    private final CityRepo cityRepo;
    private final CountryRepo countryRepo;
    private final ApartmentTypeRepo apartmentTypeRepo;
    Registr registr;
    LoginUser loginUser;


    public AdminController(UserRepo userRepo, CityRepo cityRepo, CountryRepo countryRepo, ApartmentTypeRepo apartmentTypeRepo) {
        this.userRepo = userRepo;
        this.cityRepo = cityRepo;
        this.countryRepo = countryRepo;
        this.apartmentTypeRepo = apartmentTypeRepo;
        registr = new Registr();
        loginUser=new LoginUser();

    }

    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("isLogAdm", loginUser.IsLog(userRepo));

        return "AdminPanel";
    }

    @GetMapping("/workWithUsers")
    public String workWithUsers(Model model) {

        model.addAttribute("isLogAdm", loginUser.IsLog(userRepo));

        return "workWithUsers";
    }

    @GetMapping("/workWithData")
    public String workWithData(Model model) {
        model.addAttribute("countries", countryRepo.findAll());
        model.addAttribute("cities", cityRepo.findAll());
        model.addAttribute("types", apartmentTypeRepo.findAll());
        model.addAttribute("question_", new Question());
        model.addAttribute("cntry", new Country());

        model.addAttribute("city", new City());
        model.addAttribute("type", new ApartmentType());
        model.addAttribute("apartmentType", new ApartmentType());
        model.addAttribute("isLogAdm", loginUser.IsLog(userRepo));

        return "workWithData";
    }

    @PostMapping("/newCountry")
    public String newCountry(@ModelAttribute Question question, Model model) throws ParseException {

        if (countryRepo.findByCountryTitle(question.getQuestion()) == null) {
            Country country1 = new Country();
            country1.setCountry(question.getQuestion());
            System.out.println(country1.getCountry());
            countryRepo.save(country1);
        }
        return "redirect:/workWithData";
    }

    @PostMapping("/newCity")
    public String newCity(@ModelAttribute City city, Model model) throws ParseException {
        Integer id = Integer.valueOf(city.getCity().split(",")[0]);
        String name = city.getCity().split(",")[1];
        if (countryRepo.findById(id) != null) {
            if (cityRepo.findByCity(name).size() > 0) {
                for (City i : cityRepo.findByCity(name)) {
                    if (i.getCountry().getId() == id) {
                        return "workWithData";
                    }
                }
            } else {
                City city1 = new City();
                city1.setCity(name);
                city1.setCountry(countryRepo.findById(id).isPresent() ? countryRepo.findById(id).get() : new Country());
                cityRepo.save(city1);
            }
        }
        return "redirect:/workWithData";
    }

    @PostMapping("/newApartmentType")
    public String newApartmentType(@ModelAttribute ApartmentType apartmentType, Model model) throws ParseException {
        if (apartmentTypeRepo.findByType(apartmentType.getType()) == null)
            apartmentTypeRepo.save(apartmentType);
        return "redirect:/workWithData";
    }

    @PostMapping("/deleteCountry")
    public String deleteCountry(@ModelAttribute Country country, Model model) throws ParseException {
        countryRepo.delete(countryRepo.findById(country.getId()).isPresent() ? countryRepo.findById(country.getId()).get() : new Country());
        return "redirect:/workWithData";
    }

    @PostMapping("/deleteCity")
    public String deleteCity(@ModelAttribute Question question, Model model) throws ParseException {
        cityRepo.delete(cityRepo.findById(question.getId()).isPresent() ? cityRepo.findById(question.getId()).get() : new City());
        return "redirect:/workWithData";
    }

    @PostMapping("/deleteApartmentType")
    public String deleteType(@ModelAttribute Question question, Model model) throws ParseException {
        apartmentTypeRepo.delete(apartmentTypeRepo.findById(question.getId()).isPresent() ? apartmentTypeRepo.findById(question.getId()).get() : new ApartmentType());
        return "redirect:/workWithData";
    }
}
