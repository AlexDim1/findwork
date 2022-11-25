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

    @GetMapping("/")
    public String getAllOffers(Model model) {
            List<JobOffer> offers = offerService.getAllOffers();
            model.addAttribute("offers", offers);
            return "offers";
    }

    @GetMapping ("/create") String getCreateOfferPage() {return "createOffer";}

    @PostMapping("/create")
    public String createOffer(CreateJobOfferRequest request, Model model) {
        JobOffer questionableOffer;
        try
        {
            validationService.validateCreateJobOfferRequest(request);
            questionableOffer = offerService.createOffer(request);
        }
        catch (Exception e)
        {
            model.addAttribute("error", e.getMessage());
            return "createOffer";
        }
        return "redirect:/offer/" + questionableOffer.getId();
    }

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
        return "redirect:/";
    }

    @GetMapping ("/{id}/edit") String getEditOfferPage(@PathVariable UUID id, Model model)
    {
        model.addAttribute("offer", offerService.loadOfferById(id));
        return "editOffer";
    }

    @GetMapping ("/{id}") String getOfferPage(@PathVariable UUID id, Model model)
    {
        model.addAttribute("offer", offerService.loadOfferById(id));
        return "offer";
    }

    @PutMapping("/{id}")
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
        return "redirect:/offer/" + id;
    }

}
