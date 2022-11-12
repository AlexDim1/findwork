package com.findwork.findwork.Controllers;

import com.findwork.findwork.Requests.CreateJobOfferRequest;
import com.findwork.findwork.Services.OfferService;
import com.findwork.findwork.Services.ValidationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        ModelAndView view = new ModelAndView("fetchOffers");
        return view;
    }

    @GetMapping("/fetchCompany")
    public ModelAndView fetchCompanyOffers() {
        ModelAndView view = new ModelAndView("fetchCompanyOffers");
        return view;
    }

    @PostMapping("/create")
    public ModelAndView createOffer(@RequestBody CreateJobOfferRequest request) {
        ModelAndView view = new ModelAndView("createOffer");
        if(!validationService.validateCreateJobOfferRequest(request))
        {
            view.addObject("error", "Invalid data");
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
    public ModelAndView removeOffer() {
        ModelAndView view = new ModelAndView("removeOffer");
        return view;
    }

    @PostMapping("/edit")
    public ModelAndView editOffer() {
        ModelAndView view = new ModelAndView("editOffer");
        return view;
    }
}
