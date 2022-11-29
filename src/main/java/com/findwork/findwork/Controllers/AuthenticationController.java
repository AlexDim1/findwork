package com.findwork.findwork.Controllers;

import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Entities.Users.UserPerson;
import com.findwork.findwork.Requests.RegisterCompanyRequest;
import com.findwork.findwork.Requests.RegisterPersonRequest;
import com.findwork.findwork.Services.UserService;
import com.findwork.findwork.Services.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final ValidationService validationService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String getHomepage() {
        return "homepage";
    }

    @GetMapping("/register/company")
    public String getRegistrationPageCompany() {
        return "registerCompany";
    }

    @GetMapping("/register/person")
    public String getRegistrationPagePerson() {
        return "registerPerson";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/register/person")
    public String registerPerson(RegisterPersonRequest request, Model model, HttpServletRequest sReq) {
        UserPerson registered;
        try {
            validationService.validateRegisterPersonRequest(request);
            registered = userService.registerPerson(request);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registerPerson";
        }

        authAfterRegistration(sReq, request.getEmail(), request.getPassword());
        return "redirect:/user/" + registered.getId() + "/edit";
    }

    @PostMapping("/register/company")
    public String registerCompany(RegisterCompanyRequest request, Model model, HttpServletRequest sReq) {
        UserCompany registered;
        try {
            validationService.validateRegisterCompanyRequest(request);
            registered = userService.registerCompany(request);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registerCompany";
        }

        authAfterRegistration(sReq, request.getEmail(), request.getPassword());
        return "redirect:/company/" + registered.getId();
    }


    public void authAfterRegistration(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));

        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
