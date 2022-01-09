package com.example.safehaven.controller;
import com.example.safehaven.bean.HttpSessionBean;
import com.example.safehaven.dao.*;
import com.example.safehaven.model.*;
import com.example.safehaven.repo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
public class MainController {

    private final UserRepo userRepo;
    private final CityRepo cityRepo;
    private final CountryRepo countryRepo;
    private final ApartmentRepo apartmentRepo;
    private final PhotoRepo photoRepo;
    private final MainPhotoRepo mainPhotoRepo;
    private final RentRepo rentRepo;
    private final HttpSessionBean httpSessionBean;
    private final Rating_Apartment_Repo ratingApartmentRepo;
    RatingApartmentModel ratingApartmentModel;


    Registr registr;
    WorkWithDate workWithDate;
    WorkWithImg workWithImg;
    Question question;
    Search search;
    Rent buffRent;
    ApartmentModel apartmentModel;
    LoginUser loginUser;

    public MainController(UserRepo userRepo, CityRepo cityRepo, CountryRepo countryRepo, ApartmentRepo apartmentRepo, PhotoRepo photoRepo, MainPhotoRepo mainPhotoRepo, RentRepo rentRepo, HttpSessionBean httpSessionBean, Rating_Apartment_Repo ratingApartmentRepo) {
        this.userRepo = userRepo;
        this.cityRepo = cityRepo;
        this.countryRepo = countryRepo;
        this.apartmentRepo = apartmentRepo;
        this.photoRepo = photoRepo;
        this.mainPhotoRepo = mainPhotoRepo;
        this.rentRepo = rentRepo;
        this.httpSessionBean = httpSessionBean;
        this.ratingApartmentRepo = ratingApartmentRepo;
        registr=new Registr();
        workWithDate = new WorkWithDate();
        workWithImg=new WorkWithImg();
        question=new Question();
        search=new Search();
        buffRent=new Rent();
        apartmentModel=new ApartmentModel();
        loginUser=new LoginUser();
        buffRent.setCount(1);
        ratingApartmentModel=new RatingApartmentModel();
    }

    @GetMapping("/")
    public String index(Model model) throws ParseException {
        model.addAttribute("apartments", apartmentModel.GetWithoutBan(apartmentRepo));
        model.addAttribute("apartment", new Apartment());
        model.addAttribute("photo", new Photo());
        model.addAttribute("img", workWithImg.imgFirst(mainPhotoRepo));
//        ratingApartmentModel.ApartmentRating(ratingApartmentRepo);
        if (question.getTo() == null || question.getFrom() == null) {
            question.setFrom(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
            question.setTo(workWithDate.NextDay(1));
            buffRent.setEntry(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
            buffRent.setDeparture(workWithDate.NextDay(1));
        }
        model.addAttribute("question_", question);
        model.addAttribute("ratingApartments",ratingApartmentModel.ApartmentRating(ratingApartmentRepo,apartmentRepo));
        model.addAttribute("ratingApartment",new Rating());

        return "index";
    }

    @GetMapping("/registration")
    public String addNew(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("city", cityRepo.findAll());
        model.addAttribute("role", Role.values());
        model.addAttribute("role", Role.values());

        return "registration";
    }

    @PostMapping("/registration")
    public String addTenant(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
        User check = userRepo.findByUsername(user.getUsername());
        if (bindingResult.hasErrors()) {
            ErrorMap errorMap = new ErrorMap();
            model.mergeAttributes(errorMap.bindingError(bindingResult));
            model.addAttribute("city", cityRepo.findAll());
            return "registration";
        } else {
            if (userRepo.findByUsername(user.getUsername())!=null) {
                model.addAttribute("city", cityRepo.findAll());
                model.addAttribute("info", "Login must be unique");
                return "registration";
            } else {
                userRepo.save(registr.Registration(cityRepo,countryRepo,user));
            }
        }
        return "redirect:/";
    }


    @RequestMapping(value = "/moreInfo/{apartmentId}", method = RequestMethod.GET)
    public String moreInfoAboutApartment(@PathVariable Integer apartmentId, Model model) {
        buffRent.setApartment(apartmentModel.GetById(apartmentRepo, apartmentId));


        LocalDate date1 = LocalDate.parse(buffRent.getDeparture());
        LocalDate date2 = LocalDate.parse(buffRent.getEntry());
        buffRent.setDaysBetween(DAYS.between(date2, date1));
        buffRent.setPrice(buffRent.getApartment().getPrice_for_1_day() * buffRent.getDaysBetween());

        model.addAttribute("photo", new Photo());
        model.addAttribute("apartment", apartmentModel.GetById(apartmentRepo, apartmentId));
        model.addAttribute("img", workWithImg.img(photoRepo));
        model.addAttribute("rent", buffRent);
        model.addAttribute("isLog", loginUser.IsLog(userRepo));


        httpSessionBean.setApartment(apartmentModel.GetById(apartmentRepo, apartmentId));
        httpSessionBean.setDaysBetween(DAYS.between(date2, date1));
        httpSessionBean.setPrice(buffRent.getApartment().getPrice_for_1_day() * buffRent.getDaysBetween());
//        httpSessionBean.setUser(loginUser.IsLog(userRepo));

        return "MoreInfoAboutApartment";
    }


    @PostMapping("/search")
    public String search(@ModelAttribute Question question, Model model) throws ParseException {
        model.addAttribute("apartments", search.search(question, cityRepo, apartmentRepo, countryRepo, rentRepo));
        model.addAttribute("apartment", new Apartment());
        model.addAttribute("photo", new Photo());
        model.addAttribute("img", workWithImg.imgFirst(mainPhotoRepo));
        model.addAttribute("question_", question);
        buffRent.setCount(question.getPersons());
        buffRent.setCity(question.getCity());
        buffRent.setEntry(question.getFrom());
        buffRent.setDeparture(question.getTo());
        model.addAttribute("isLog", buffRent.getUser());

        return "index";
    }

    @RequestMapping(value = "/rent/{apartmentId}", method = RequestMethod.GET)
    public String rent(@PathVariable Integer apartmentId, Model model) {
        LocalDate date1 = LocalDate.parse(buffRent.getDeparture());
        LocalDate date2 = LocalDate.parse(buffRent.getEntry());

        buffRent.setApartment(apartmentModel.GetById(apartmentRepo, apartmentId));
        buffRent.setDaysBetween(DAYS.between(date2, date1));
        buffRent.setPrice(buffRent.getApartment().getPrice_for_1_day() * buffRent.getDaysBetween());
        model.addAttribute("rent", buffRent);
        model.addAttribute("isLog", loginUser.IsLog(userRepo));

        return "RentPage";
    }
}
