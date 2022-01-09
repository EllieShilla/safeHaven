package com.example.safehaven.controller;

import com.example.safehaven.dao.*;
import com.example.safehaven.model.*;
import com.example.safehaven.repo.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.text.ParseException;

@Controller
@PreAuthorize("hasAuthority('LANDLORD')")
public class LandlordController {

    private final CityRepo cityRepo;
    private final CountryRepo countryRepo;
    private final ApartmentTypeRepo apartmentTypeRepo;
    private final ApartmentRepo apartmentRepo;
    private final UserRepo userRepo;
    private final PhotoRepo photoRepo;
    private final RentRepo rentRepo;
    private final MainPhotoRepo mainPhotoRepo;
    private final Rating_Apartment_Repo ratingApartmentRepo;
    private final Rating_Landlord_Repo rating_landlord_repo;
    private final Rating_Tenant_Repo rating_tenant_repo;
    RatingApartmentModel ratingApartmentModel;
    RatingLandlordModel ratingLandlordModel;
    WorkWithDate workWithDate;
    RatingTenantModel ratingTenantModel;



    ApartmentModel apartmentModel;
    WorkWithImg workWithImg;
    LoginUser loginUser;
    public LandlordController(CityRepo cityRepo, CountryRepo countryRepo, ApartmentTypeRepo apartmentTypeRepo,
                              ApartmentRepo apartmentRepo, UserRepo userRepo, PhotoRepo photoRepo, RentRepo rentRepo, MainPhotoRepo mainPhotoRepo, Rating_Apartment_Repo ratingApartmentRepo, Rating_Landlord_Repo rating_landlord_repo, Rating_Tenant_Repo rating_tenant_repo) {
        this.cityRepo = cityRepo;
        this.countryRepo = countryRepo;
        this.apartmentTypeRepo = apartmentTypeRepo;
        this.apartmentRepo = apartmentRepo;
        this.userRepo = userRepo;
        this.photoRepo = photoRepo;
        this.rentRepo = rentRepo;
        this.mainPhotoRepo = mainPhotoRepo;
        this.ratingApartmentRepo = ratingApartmentRepo;
        this.rating_landlord_repo = rating_landlord_repo;
        this.rating_tenant_repo = rating_tenant_repo;
        apartmentModel = new ApartmentModel();
        workWithImg = new WorkWithImg();
        loginUser=new LoginUser();
        ratingApartmentModel=new RatingApartmentModel();
        ratingLandlordModel=new RatingLandlordModel();
        workWithDate=new WorkWithDate();
        ratingTenantModel=new RatingTenantModel();
    }

    @GetMapping("/landlord")
    public String home(Model model) {
        model.addAttribute("isLogLandlord", loginUser.IsLog(userRepo));
        model.addAttribute("RatingLandlord",ratingLandlordModel.RatingLandlord(rating_landlord_repo,loginUser.IsLog(userRepo).getId()));
        return "landlord_home";
    }

    @GetMapping(path = "/addApartment")
    public String AddHome(Model model) {

        model.addAttribute("isLogLandlord", loginUser.IsLog(userRepo));
        model.addAttribute("home", new Apartment());
        model.addAttribute("city", cityRepo.findAll());
        model.addAttribute("apartmentType", apartmentTypeRepo.findAll());
        model.addAttribute("photo");
        model.addAttribute("RatingLandlord",ratingLandlordModel.RatingLandlord(rating_landlord_repo,loginUser.IsLog(userRepo).getId()));

        return "NewApartment";
    }

    @PostMapping("/addApartment")
    public String AddHome(@RequestParam("file") MultipartFile[] file, @ModelAttribute @Valid Apartment apartment, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            ErrorMap errorMap = new ErrorMap();
            model.mergeAttributes(errorMap.bindingError(bindingResult));
            model.addAttribute("city", cityRepo.findAll());
            model.addAttribute("apartmentType", apartmentTypeRepo.findAll());
            return "NewApartment";
        } else {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = ((UserDetails) principal).getUsername();

            City city1 = cityRepo.findById(apartment.getCity().getId()).isPresent() ? cityRepo.findById(apartment.getCity().getId()).get() : new City();
            Country country1 = countryRepo.findById(city1.getCountry().getId()).isPresent() ? countryRepo.findById(city1.getCountry().getId()).get() : new Country();
            User user = userRepo.findByUsername(username);
            ApartmentType apartmentType = apartmentTypeRepo.findByType(apartment.getApartmentType().getType());

            apartment.setCity(city1);
            apartment.setCountry(country1);
            apartment.setUser(user);
            apartment.setApartmentType(apartmentType);
            apartmentRepo.save(apartment);

            int Id_buff = 0;
            for (Apartment i : apartmentRepo.findAll())
                Id_buff = i.getId();

            for (int i = 0; i < file.length; i++) {
                if (i == 0) {
                    MainPhoto mainPhoto = new MainPhoto(file[i].getOriginalFilename(), file[i].getBytes(), apartmentModel.GetById(apartmentRepo, Id_buff));
                    mainPhotoRepo.save(mainPhoto);
                }
                Photo photo = new Photo(file[i].getOriginalFilename(), file[i].getBytes(), apartmentModel.GetById(apartmentRepo, Id_buff));
                photoRepo.save(photo);
            }
        }
        return "redirect:/allApartment";
    }

    @GetMapping(path = "/allApartment")
    public String allApartment(Model model) {
        model.addAttribute("isLogLandlord", loginUser.IsLog(userRepo));
        model.addAttribute("apartments", apartmentRepo.findByUser(loginUser.IsLog(userRepo)));
        model.addAttribute("apartment", new Apartment());
        model.addAttribute("photo", new MainPhoto());
//        model.addAttribute("img", workWithImg.img(photoRepo));
        model.addAttribute("img", mainPhotoRepo.findAll());
        model.addAttribute("ratingApartments",ratingApartmentModel.ApartmentRating(ratingApartmentRepo,apartmentRepo));
        model.addAttribute("ratingApartment",new Rating());
        model.addAttribute("RatingLandlord",ratingLandlordModel.RatingLandlord(rating_landlord_repo,loginUser.IsLog(userRepo).getId()));

        return "AllApartment";
    }


    @RequestMapping(value = "/delete/{apartmentId}", method = RequestMethod.GET)
    public String delete(@PathVariable Integer apartmentId, Model model) {
        Integer size = photoRepo.findByApartmentId(apartmentId).size();
        for (int i = size - 1; i > -1; i--)
            photoRepo.delete(photoRepo.findByApartmentId(apartmentId).get(i));

        apartmentRepo.delete(apartmentModel.GetById(apartmentRepo, apartmentId));
        return "redirect:/allApartment";
    }

    @RequestMapping(value = "/edit/{apartmentId}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer apartmentId, Model model) {
        model.addAttribute("isLogLandlord", loginUser.IsLog(userRepo));

        Apartment apartment = apartmentRepo.findById(apartmentId).isPresent() ? apartmentRepo.findById(apartmentId).get() : new Apartment();
        model.addAttribute("photo", new Photo());
        model.addAttribute("apartment", apartment);
        model.addAttribute("city", cityRepo.findAll());
        model.addAttribute("apartmentType", apartmentTypeRepo.findAll());

        model.addAttribute("img", workWithImg.img(photoRepo));
        model.addAttribute("RatingLandlord",ratingLandlordModel.RatingLandlord(rating_landlord_repo,loginUser.IsLog(userRepo).getId()));

        return "updateApartment";
    }

    @PostMapping("/edit/{apartmentId}")
    public String edit(@RequestParam("file") MultipartFile[] file, @PathVariable Integer apartmentId, @ModelAttribute @Valid Apartment apartment, BindingResult bindingResult, Model model) throws Exception {
        Apartment apartment1 = apartmentRepo.findById(apartmentId).isPresent() ? apartmentRepo.findById(apartmentId).get() : new Apartment();
        City city1 = cityRepo.findById(apartment.getCity().getId()).isPresent() ? cityRepo.findById(apartment.getCity().getId()).get() : new City();
        Country country1 = countryRepo.findById(city1.getCountry().getId()).isPresent() ? countryRepo.findById(city1.getCountry().getId()).get() : new Country();
        ApartmentType apartmentType = apartmentTypeRepo.findByType(apartment.getApartmentType().getType());

        apartment1.setCity(city1);
        apartment1.setCountry(country1);
        apartment1.setAddress(apartment.getAddress());
        apartment1.setAbout(apartment.getAbout());
        apartment1.setApartmentType(apartmentType);
        apartment1.setNumber_of_person(apartment.getNumber_of_person());
        apartment1.setCheck_IN_time(apartment.getCheck_IN_time());
        apartment1.setCheck_OUT_time(apartment.getCheck_OUT_time());
        apartment1.setPrice_for_1_day(apartment.getPrice_for_1_day());
        apartment1.setTitle(apartment.getTitle());
        apartmentRepo.save(apartment1);


        Integer size = photoRepo.findByApartmentId(apartment1.getId()).size();
        boolean isAdd = false;
        if (size != 0) {
            for (int i = size - 1; i > -1; i--) {
                if (!file[i].getOriginalFilename().equals("")) {
                    photoRepo.delete(photoRepo.findByApartmentId(apartment1.getId()).get(i));
                }
            }
            mainPhotoRepo.delete(mainPhotoRepo.findByApartmentId(apartment1.getId()).get(0));
        }
        for (int i = 0; i < file.length; i++) {
            if (!file[i].getOriginalFilename().equals("")) {
                if (i == 0) {
                    MainPhoto mainPhoto = new MainPhoto(file[i].getOriginalFilename(), file[i].getBytes(), apartmentModel.GetById(apartmentRepo, apartment1.getId()));
                    mainPhotoRepo.save(mainPhoto);
                }
                Photo photo = new Photo(file[i].getOriginalFilename(), file[i].getBytes(), apartmentModel.GetById(apartmentRepo, apartment1.getId()));
                photoRepo.save(photo);
            }
        }
        return "redirect:/allApartment";
    }

    @GetMapping(path = "/rentApartment")
    public String rentApartment(Model model) {
        model.addAttribute("isLogLandlord", loginUser.IsLog(userRepo));
        model.addAttribute("apartments", apartmentRepo.findByUser(loginUser.IsLog(userRepo)));
        model.addAttribute("rents", rentRepo.findByLandlord(loginUser.IsLog(userRepo)));
        model.addAttribute("apartment", new Apartment());
        model.addAttribute("rent", new Rent());
        model.addAttribute("RatingLandlord",ratingLandlordModel.RatingLandlord(rating_landlord_repo,loginUser.IsLog(userRepo).getId()));
        model.addAttribute("ratingsUser", ratingLandlordModel.findByTenantId(rating_landlord_repo, loginUser.IsLog(userRepo).getId()));
        model.addAttribute("Rating_Tenant",new Rating_Tenant());
        return "RentApartment";
    }

    @RequestMapping(value = "/moreInfo_/{apartmentId}", method = RequestMethod.GET)
    public String ApartmentExample(@PathVariable Integer apartmentId, Model model) {
        model.addAttribute("photo", new Photo());
        model.addAttribute("apartment", apartmentModel.GetById(apartmentRepo, apartmentId));
        model.addAttribute("img", workWithImg.img(photoRepo));
        model.addAttribute("isLogLandlord", loginUser.IsLog(userRepo));


        return "ApartmentExample";
    }

    @RequestMapping(value = "/ratingTenant/{rentId}", method = RequestMethod.GET)
    public String ratingTenant(@PathVariable Integer rentId, Model model) {
        Rent rent = rentRepo.findById(rentId).isPresent() ? rentRepo.findById(rentId).get() : new Rent();

        model.addAttribute("photo", new Photo());
        model.addAttribute("apartment", apartmentModel.GetById(apartmentRepo, rent.getApartment().getId()));
        model.addAttribute("img", workWithImg.img(photoRepo));
        model.addAttribute("isLogLandlord", loginUser.IsLog(userRepo));
        model.addAttribute("num", new Integer[]{1, 2, 3, 4, 5});
        model.addAttribute("ratingTenant", new Rating_Tenant());
        model.addAttribute("rent",rent);
        model.addAttribute("ratingTenants", ratingTenantModel.findByLandlordId(rating_tenant_repo, rent.getUser().getId(),loginUser.IsLog(userRepo).getId()));
        model.addAttribute("RatingLandlord",ratingLandlordModel.RatingLandlord(rating_landlord_repo,loginUser.IsLog(userRepo).getId()));

        return "RatingTenant";
    }

    @PostMapping("/ratingTenant")
    public String ratingTenant(@ModelAttribute Rating_Tenant rating_tenant, Model model) throws ParseException {
        User user = userRepo.findById(rating_tenant.getUserId()).isPresent() ? userRepo.findById(rating_tenant.getUserId()).get() : new User();
        Rating_Tenant ratingUser = new Rating_Tenant(rating_tenant.getStars(), workWithDate.Today(),user,loginUser.IsLog(userRepo));
        rating_tenant_repo.save(ratingUser);
        ratingTenantModel.BanTenant(ratingTenantModel.RatingTenant(rating_tenant_repo,rating_tenant.getUserId()),userRepo,rating_tenant.getUserId());
        model.addAttribute("isLogLandlord", loginUser.IsLog(userRepo));
        return "redirect:/allApartment";
    }
}
