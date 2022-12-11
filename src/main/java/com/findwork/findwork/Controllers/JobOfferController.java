package com.findwork.findwork.Controllers;

import com.findwork.findwork.Entities.JobApplication;
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
@RequestMapping("/offers")
public class JobOfferController {
    private final ValidationService validationService;
    private final OfferService offerService;

    @GetMapping("/")
    public String getAllOffers(Model model,
                               @RequestParam(required = false) String search,
                               @RequestParam(required = false) String jobCategory,
                               @RequestParam(required = false) String jobLevel) {
        List<JobOffer> offers;

        if (jobCategory != null && jobCategory.equals("--Any--"))
            jobCategory = null;

        if (jobLevel != null && jobLevel.equals("--Any--"))
            jobLevel = null;

        offers = offerService.getOffers(search, jobCategory, jobLevel);

        model.addAttribute("offers", offers);
        model.addAttribute("levels", JobLevel.values());
        model.addAttribute("categories", Category.values());
        return "offers";
    }

    @GetMapping("/create")
    String getCreateOfferPage(Model model, Authentication auth) {
        UserCompany company = (UserCompany) auth.getPrincipal();
        model.addAttribute("levels", JobLevel.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("company", company);
        return "createOffer";
    }

    @PostMapping("/create")
    public String createOffer(Authentication auth, CreateJobOfferRequest request, RedirectAttributes attr) {
        UserCompany company = (UserCompany) auth.getPrincipal();
        JobOffer questionableOffer;
        try {
            validationService.validateCreateJobOfferRequest(request);
            questionableOffer = offerService.createOffer(request, company);
        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/offers/create";
        }

        return "redirect:/offers/" + questionableOffer.getId();
    }

    @PostMapping("/{id}/remove")
    public String removeOffer(@PathVariable UUID id, RedirectAttributes attr, Authentication auth) {
        UserCompany company = (UserCompany) auth.getPrincipal();
        if (!offerService.loadOfferById(id).getCompany().getId().equals(company.getId()))
            return "redirect:/offers/" + id;

        try {
            offerService.removeOffer(id);
        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/offers/" + id;
        }

        return "redirect:/company/" + company.getId();
    }

    @GetMapping("/{id}/edit")
    String getEditOfferPage(@PathVariable UUID id, Model model, Authentication auth) {
        JobOffer offer = offerService.loadOfferById(id);

        UserCompany company = (UserCompany) auth.getPrincipal();
        if (!offer.getCompany().getId().equals(company.getId()))
            return "redirect:/offers/" + id;

        model.addAttribute("offer", offer);
        model.addAttribute("levels", JobLevel.values());
        model.addAttribute("categories", Category.values());
        return "editOffer";
    }

    @GetMapping("/{id}")
    String getOfferPage(@PathVariable UUID id, Model model, Authentication auth) {
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserPerson) {
            boolean saved = offerService.checkSaved((UserPerson) auth.getPrincipal(), id);
            model.addAttribute("saved", saved);

            boolean applied = offerService.checkApplied((UserPerson) auth.getPrincipal(), id);
            model.addAttribute("applied", applied);
        }

        model.addAttribute("offer", offerService.loadOfferById(id));
        return "offer";
    }

    @GetMapping("/{id}/applications")
    public String getOfferApplications(@PathVariable UUID id, Model model, Authentication auth) {
        UserCompany company = (UserCompany) auth.getPrincipal();
        if (!offerService.loadOfferById(id).getCompany().getId().equals(company.getId()))
            return "redirect:/offers/" + id;

        List<JobApplication> applications = offerService.getOfferApplications(id);

        model.addAttribute("offer", offerService.loadOfferById(id));
        model.addAttribute("applications", applications);
        return "offerApplications";
    }

    @PostMapping("/{id}")
    public String editOffer(@PathVariable UUID id, EditJobOfferRequest request, Model model, Authentication auth) {
        UserCompany company = (UserCompany) auth.getPrincipal();
        if (!offerService.loadOfferById(id).getCompany().getId().equals(company.getId()))
            return "redirect:/offers/" + id;

        try {
            validationService.validateEditJobOfferRequest(request);
            offerService.editOffer(id, request);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "editOffer";
        }

        return "redirect:/offers/" + id;
    }

    @PostMapping("/{id}/apply")
    public String apply(@PathVariable UUID id, Authentication auth, RedirectAttributes atrr) {
        UserPerson user = (UserPerson) auth.getPrincipal();
        try {
            validationService.validateUserinfo(user);
            offerService.createApplication(user, id);
        } catch (Exception e) {
            System.out.println(user.getName());
            atrr.addFlashAttribute("error", e.getMessage());
            return "redirect:/offers/" + id;
        }

        atrr.addFlashAttribute("success", "You have applied successfully!");
        return "redirect:/offers/" + id;
    }

    @PostMapping("/{id}/cancel")
    public String cancelApplication(@PathVariable UUID id, Authentication auth, RedirectAttributes atrr) {
        UserPerson user = (UserPerson) auth.getPrincipal();
        try {
            offerService.deleteApplication(user, id);
        } catch (Exception e) {
            atrr.addFlashAttribute("error", e.getMessage());
            return "redirect:/offers/" + id;
        }

        atrr.addFlashAttribute("success", "Canceled job application!");
        return "redirect:/offers/" + id;
    }

    @PostMapping("/{id}/save")
    public String saveOffer(@PathVariable UUID id, Authentication auth, RedirectAttributes attr) {
        UserPerson user = (UserPerson) auth.getPrincipal();
        try {
            offerService.saveOffer(user, id);
        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/offers/" + id;
        }

        attr.addFlashAttribute("success", "Offer saved!");
        return "redirect:/offers/" + id;
    }

    @PostMapping("/{id}/unsave")
    public String unsaveOffer(@PathVariable UUID id, Authentication auth, RedirectAttributes atrr) {
        UserPerson user = (UserPerson) auth.getPrincipal();
        try {
            offerService.unsaveOffer(user, id);
        } catch (Exception e) {
            atrr.addFlashAttribute("error", e.getMessage());
            return "redirect:/offers/" + id;
        }

        atrr.addFlashAttribute("success", "Offer removed from saved!");
        return "redirect:/offers/" + id;
    }
}
