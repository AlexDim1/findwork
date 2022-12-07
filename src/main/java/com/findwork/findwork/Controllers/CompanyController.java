package com.findwork.findwork.Controllers;

import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Requests.EditCompanyRequest;
import com.findwork.findwork.Services.UserService;
import com.findwork.findwork.Services.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final UserService userService;
    private final ValidationService validationService;

    @GetMapping("/{id}")
    public String getCompanyPage(@PathVariable UUID id, Model model) {
        model.addAttribute("company", userService.loadUserCompanyById(id));
        return "company";
    }

    @GetMapping("/{id}/edit")
    public String getEditPageCompany(@PathVariable UUID id, Model model, Authentication auth) {
        if (!id.equals(((UserCompany) auth.getPrincipal()).getId()))
            return "redirect:/company/" + id;

        model.addAttribute("company", userService.loadUserCompanyById(id));
        return "editCompany";
    }

    @PostMapping("/{id}")
    public String editCompany(@PathVariable UUID id, EditCompanyRequest request, RedirectAttributes atrr, Authentication auth) {
        if (!id.equals(((UserCompany) auth.getPrincipal()).getId()))
            return "redirect:/company/" + id;

        try {
            validationService.validateEditCompanyRequest(request);
            userService.editCompany(id, request);
        } catch (Exception e) {
            atrr.addFlashAttribute("error", e.getMessage());
            return "redirect:/company/" + id + "/edit";
        }

        return "redirect:/company/" + id;
    }

}
