package com.findwork.findwork.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller                                                                                                       //TODO
@RequestMapping("/offer")
public class JobOfferController {

    @GetMapping("/fetchAll")
    public ModelAndView fetchOffers() {
        ModelAndView view = new ModelAndView("fetchOffers");
        return view;
    }

    @GetMapping("/fetchCompany")
    public ModelAndView fetchCompanyOffers() {
        ModelAndView view = new ModelAndView("fetchOffers");
        return view;
    }

    @PostMapping("/create")
    public ModelAndView createOffer() {
        ModelAndView view = new ModelAndView("createOffer");
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
