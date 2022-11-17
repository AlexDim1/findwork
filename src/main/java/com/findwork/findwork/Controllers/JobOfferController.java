package com.findwork.findwork.Controllers;

import com.findwork.findwork.Entities.JobOffer;
import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Requests.CreateJobOfferRequest;
import com.findwork.findwork.Requests.EditCompanyRequest;
import com.findwork.findwork.Requests.EditJobOfferRequest;
import com.findwork.findwork.Services.OfferService;
import com.findwork.findwork.Services.ValidationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/offer")
public class JobOfferController {
    private final ValidationService validationService;
    private final OfferService offerService;

    public JobOfferController(ValidationService validationService, OfferService offerService) {
        this.validationService = validationService;
        this.offerService = offerService;
    }

    @GetMapping("/fetchAll")
    public ModelAndView fetchOffers() {
        ModelAndView view = new ModelAndView("homepage");
        List<JobOffer> offers = offerService.getAllOffers();
        view.addObject("offers", offers);
        return view;
    }

    @GetMapping("/fetchCompany")
    public ModelAndView fetchCompanyOffers(@RequestParam(required = true) String companyUsername) {
        ModelAndView view = new ModelAndView("fetchCompanyOffers");
        List<JobOffer> offers = new ArrayList<>();
        try {offers = offerService.getCompanyOffers(companyUsername);}
        catch (Exception e)
        {
            view.addObject("error", e.getMessage());
            return view;
        }
        view.addObject("offers", offers);
        return view;
    }

    @PostMapping("/create")
    public ModelAndView createOffer(@RequestBody CreateJobOfferRequest request) {
        ModelAndView view = new ModelAndView("createOffer");
        try{validationService.validateCreateJobOfferRequest(request);}
        catch (Exception e)
        {
            view.addObject("error", e.getMessage());
            return view;
        }
        try {
            offerService.createOffer(request);}
        catch (Exception e)
        {
            view.addObject("error", e.getMessage());
            return view;
        }
        view.setViewName("editOffer");
        view.addObject("success", "Bravo, pich - Suzdade obqva!");
        return view;
    }

    @PostMapping("/remove")
    public ModelAndView removeOffer(@RequestParam(required = true) String title) {
        ModelAndView view = new ModelAndView("editOffer");
        try {
            offerService.removeOffer(title);
        }
        catch (Exception e)
        {
            view.addObject("error", e.getMessage());
            return view;
        }
        view.addObject("success", "Bravo, pich - Iztri obqva!");
        return view;
    }

    @PostMapping("/edit")
    public ModelAndView editOffer(EditJobOfferRequest request) {
        ModelAndView view = new ModelAndView("editOffer");
        try{validationService.validateEditJobOfferRequest(request);}
        catch (Exception e)
        {
            view.addObject("error", e.getMessage());
            return view;
        }
        try {
            offerService.editOffer(request);}
        catch (Exception e)
        {
            view.addObject("error", e.getMessage());
            return view;
        }
        view.setViewName("editOffer");
        view.addObject("success", "Bravo, pich - Suzdade obqva!");
        return view;
    }

}
