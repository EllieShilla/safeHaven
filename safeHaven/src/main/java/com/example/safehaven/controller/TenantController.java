package com.example.safehaven.controller;

import com.example.safehaven.bean.HttpSessionBean;
import com.example.safehaven.dao.*;
import com.example.safehaven.model.*;
import com.example.safehaven.repo.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;

@Controller
@PreAuthorize("hasAuthority('TENANT')")
public class TenantController {
    private final HttpSessionBean httpSessionBean;
    private final RentRepo rentRepo;
    private final UserRepo userRepo;
    private final Rating_Apartment_Repo ratingApartmentRepo;
    private final ApartmentRepo apartmentRepo;
    private final PhotoRepo photoRepo;
    private final Rating_Landlord_Repo rating_landlord_repo;
    Search search;
    LoginUser loginUser;
    RedirectAttributes redirectAttributes;
    RatingApartmentModel rating_apartment_model;
    ApartmentModel apartmentModel;
    WorkWithImg workWithImg;
    WorkWithDate workWithDate;
    RatingLandlordModel ratingLandlordModel;


    public TenantController(HttpSessionBean httpSessionBean, RentRepo rentRepo, UserRepo userRepo, Rating_Apartment_Repo ratingApartmentRepo, ApartmentRepo apartmentRepo, PhotoRepo photoRepo, Rating_Landlord_Repo rating_landlord_repo) {
        this.httpSessionBean = httpSessionBean;
        this.rentRepo = rentRepo;
        this.userRepo = userRepo;
        this.ratingApartmentRepo = ratingApartmentRepo;
        this.apartmentRepo = apartmentRepo;
        this.photoRepo = photoRepo;
        this.rating_landlord_repo = rating_landlord_repo;
        search=new Search();
        loginUser=new LoginUser();
        rating_apartment_model = new RatingApartmentModel();
        apartmentModel=new ApartmentModel();
        workWithImg=new WorkWithImg();
        workWithDate=new WorkWithDate();
        ratingLandlordModel=new RatingLandlordModel();
    }

    @PostMapping("/rent")
    public String rent(@ModelAttribute Rent rent, Model model) {

        if (search.IsFree(rentRepo, httpSessionBean.getApartment(), rent)) {
            rent.setLandlord(httpSessionBean.getApartment().getUser());
            rent.setApartment(httpSessionBean.getApartment());
            rent.setUser(loginUser.IsLog(userRepo));
            rentRepo.save(rent);
            model.addAttribute("info", "Аренда успешно оформлена");
        } else if (search.IsFree(rentRepo, httpSessionBean.getApartment(), rent) == false) {
            model.addAttribute("info", "Указанный временной период занят");
            model.addAttribute("isLog", loginUser.IsLog(userRepo));
            return "RentPage";
        }

        model.addAttribute("isLog", loginUser.IsLog(userRepo));
        return "InfoPage";
    }

    @GetMapping("/tenant")
    public String home(Model model) throws ParseException {
        model.addAttribute("isLog", loginUser.IsLog(userRepo));
        model.addAttribute("futureRent", search.futureRent(rentRepo, loginUser.IsLog(userRepo)));
        model.addAttribute("nowRent", search.nowRent(rentRepo, loginUser.IsLog(userRepo)));
        model.addAttribute("pastRent", search.pastRent(rentRepo, loginUser.IsLog(userRepo)));
//        model.addAttribute("canRemove", search.canRemove(rentRepo, userRepo.findById(ID_User).isPresent() ? userRepo.findById(ID_User).get() : new User()));
        model.addAttribute("rent", new Rent());
        model.addAttribute("num", new Integer[]{1, 2, 3, 4, 5});
        model.addAttribute("ratingsUser", ratingLandlordModel.findByTenantId(rating_landlord_repo, loginUser.IsLog(userRepo).getId()));
        model.addAttribute("ratingsApartments", rating_apartment_model.findByTenantId(ratingApartmentRepo,loginUser.IsLog(userRepo).getId()));
        model.addAttribute("ratingApartment",new Rating_Apartment());
        model.addAttribute("Rating_Landlord",new Rating_Landlord());

        return "tenant_home";
    }

    @RequestMapping(value = "/ratingApartment/{apartmentId}", method = RequestMethod.GET)
    public String ratingApartment(@PathVariable Integer apartmentId, Model model) {
        model.addAttribute("photo", new Photo());
        model.addAttribute("apartment", apartmentModel.GetById(apartmentRepo, apartmentId));
        model.addAttribute("img", workWithImg.img(photoRepo));
        model.addAttribute("isLog", loginUser.IsLog(userRepo));

        model.addAttribute("num", new Integer[]{1, 2, 3, 4, 5});
        model.addAttribute("ratingLand", new Rating_Landlord());
        model.addAttribute("ratingApartment", new Rating_Apartment());
//        model.addAttribute("comment", new Comment());


        model.addAttribute("ratingLandlord", ratingLandlordModel.findByTenantAndApartment(rating_landlord_repo,apartmentRepo, loginUser.IsLog(userRepo).getId(),apartmentId));
        model.addAttribute("ratingApartment_", rating_apartment_model.findByTenantAndApartment(ratingApartmentRepo,loginUser.IsLog(userRepo).getId(),apartmentId));
        return "RatingApartment";
    }

    @PostMapping("/ratingApartment")
    public String ratingApartment(@ModelAttribute Rating_Apartment rating_apartment, Model model) throws ParseException {
        rating_apartment.setUser(loginUser.IsLog(userRepo));
        rating_apartment.setDatetime(workWithDate.Today());
        rating_apartment.setApartment(apartmentModel.GetById(apartmentRepo, rating_apartment.getApartmentId()));
        ratingApartmentRepo.save(rating_apartment);
        return "redirect:/tenant";
    }

    @PostMapping("/ratingLandLord")
    public String ratingUser(@ModelAttribute Rating_Landlord rating_landlord, Model model) throws ParseException {
        User user = userRepo.findById(rating_landlord.getUserId()).isPresent() ? userRepo.findById(rating_landlord.getUserId()).get() : new User();
        Rating_Landlord ratingUser = new Rating_Landlord(rating_landlord.getStars(), workWithDate.Today(),loginUser.IsLog(userRepo), user);
        rating_landlord_repo.save(ratingUser);
        ratingLandlordModel.BanLandlord(ratingLandlordModel.RatingLandlord(rating_landlord_repo,user.getId()),userRepo,user.getId());

        return "redirect:/tenant";
    }

}
