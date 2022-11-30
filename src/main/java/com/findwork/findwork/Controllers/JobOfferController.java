package com.findwork.findwork.Controllers;

import com.findwork.findwork.Entities.JobOffer;
import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Entities.Users.UserPerson;
import com.findwork.findwork.Enums.Category;
import com.findwork.findwork.Enums.JobLevel;
import com.findwork.findwork.Requests.CreateJobOfferRequest;
import com.findwork.findwork.Requests.EditJobOfferRequest;
import com.findwork.findwork.Services.OfferService;
import com.findwork.findwork.Services.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("levels", JobLevel.values());
        model.addAttribute("categories", Category.values());
        return "offers";
    }

    @GetMapping("/create")
    String getCreateOfferPage(Model model) {
        model.addAttribute("levels", JobLevel.values());
        model.addAttribute("categories", Category.values());
        return "createOffer";
    }

    @PostMapping("/create")
    public String createOffer(Authentication auth, CreateJobOfferRequest request, Model model) {
        UserCompany company = (UserCompany) auth.getPrincipal();
        JobOffer questionableOffer;
        try {
            validationService.validateCreateJobOfferRequest(request);
            questionableOffer = offerService.createOffer(request, company);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "createOffer";
        }
        return "redirect:/offer/" + questionableOffer.getId();
    }

    @DeleteMapping("/{id}/remove")
    public String removeOffer(@PathVariable UUID id, Model model) {
        try {
            offerService.removeOffer(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/offer" + id;
        }
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    String getEditOfferPage(@PathVariable UUID id, Model model) {
        JobOffer offer = offerService.loadOfferById(id);
        model.addAttribute("offer", offer);
        model.addAttribute("levels", JobLevel.values());
        model.addAttribute("categories", Category.values());
        return "editOffer";
    }

    @GetMapping("/{id}")
    String getOfferPage(@PathVariable UUID id, Model model, Authentication auth) {
        if (auth.isAuthenticated()) {
            boolean saved = offerService.checkSaved((UserPerson) auth.getPrincipal(), id);
            model.addAttribute("saved", saved);
        }

        model.addAttribute("offer", offerService.loadOfferById(id));
        return "offer";
    }

    @PostMapping("/{id}")
    public String editOffer(@PathVariable UUID id, EditJobOfferRequest request, Model model) {
        try {
            validationService.validateEditJobOfferRequest(request);
            offerService.editOffer(id, request);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "editOffer";
        }
        return "redirect:/offer/" + id;
    }

    @PostMapping("/{id}/apply")
    public String apply(@PathVariable UUID id, Authentication auth, RedirectAttributes atrr) {
        UserPerson user = (UserPerson) auth.getPrincipal();
        try {
            validationService.validateUserinfo(user);
            offerService.createApplication(user, id);
        } catch (Exception e) {
            atrr.addFlashAttribute("error", e.getMessage());
            return "redirect:/offer/" + id;
        }

        atrr.addFlashAttribute("success", "You have applied successfully!");
        return "redirect:/offer/" + id;
    }

    @PostMapping("/{id}/cancel")
    public String cancelApplication(@PathVariable UUID id, Authentication auth, RedirectAttributes atrr) {
        UserPerson user = (UserPerson) auth.getPrincipal();
        try {
            offerService.deleteApplication(user, id);
        } catch (Exception e) {
            atrr.addFlashAttribute("error", e.getMessage());
            return "redirect:/offer/" + id;
        }

        atrr.addFlashAttribute("success", "Canceled job application!");
        return "redirect:/offer/" + id;
    }

    @PostMapping("/{id}/save")
    public String saveOffer(@PathVariable UUID id, Authentication auth, RedirectAttributes attr) {
        UserPerson user = (UserPerson) auth.getPrincipal();
        try {
            offerService.saveOffer(user, id);
        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/offer/" + id;
        }

        attr.addFlashAttribute("success", "Offer saved!");
        return "redirect:/offer/" + id;
    }

    @PostMapping("/{id}/unsave")
    public String unsaveOffer(@PathVariable UUID id, Authentication auth, RedirectAttributes atrr) {
        UserPerson user = (UserPerson) auth.getPrincipal();
        try {
            offerService.unsaveOffer(user, id);
        } catch (Exception e) {
            atrr.addFlashAttribute("error", e.getMessage());
            return "redirect:/offer/" + id;
        }

        atrr.addFlashAttribute("success", "Offer removed from saved!");
        return "redirect:/offer/" + id;
    }
}
