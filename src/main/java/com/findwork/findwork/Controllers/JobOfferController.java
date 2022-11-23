package com.findwork.findwork.Controllers;

import com.findwork.findwork.Entities.JobOffer;
import com.findwork.findwork.Requests.CreateJobOfferRequest;
import com.findwork.findwork.Requests.EditJobOfferRequest;
import com.findwork.findwork.Services.OfferService;
import com.findwork.findwork.Services.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/offer")
public class JobOfferController {
    private final ValidationService validationService;
    private final OfferService offerService;

    @GetMapping("/all")
    public String getAllOffers(Model model) {
        List<JobOffer> offers = offerService.getAllOffers();
        model.addAttribute("offers", offers);
        return "homepage";
    }

    @GetMapping ("/create") String getCreateOfferPage() {return "editOffer";}

    @PostMapping("/create")
    public String createOffer(CreateJobOfferRequest request, Model model) {
        try
        {
            validationService.validateCreateJobOfferRequest(request);
            offerService.createOffer(request);
        }
        catch (Exception e)
        {
            model.addAttribute("error", e.getMessage());
            return "createOffer";
        }

        model.addAttribute("success", "Bravo, pich - Suzdade obqva!");
        return "company" /* + request.getCompanyId();*/;
    }

    @GetMapping ("/{id}/remove") String getRemoveOfferPage() {return "editOffer";}

    @DeleteMapping("/{id}/remove")
    public String removeOffer(@PathVariable UUID id, Model model) {
        try {
            offerService.removeOffer(id);
        }
        catch (Exception e)
        {
            model.addAttribute("error", e.getMessage());
            return "editOffer";
        }
        model.addAttribute("success", "Bravo, pich - Iztri obqva!");
        return "company";
    }

    @GetMapping ("/{id}/edit") String getEditOfferPage() {return "editOffer";}

    @PutMapping("/{id}/edit")
    public String editOffer(@PathVariable UUID id, EditJobOfferRequest request, Model model) {
        try
        {
            validationService.validateEditJobOfferRequest(request);
            offerService.editOffer(id, request);
        }
        catch (Exception e)
        {
            model.addAttribute("error", e.getMessage());
            return "editOffer";
        }
        model.addAttribute("success", "Bravo, pich - Promeni obqvata!");
        return "company";
    }

}
