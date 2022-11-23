package com.findwork.findwork.Controllers;

import com.findwork.findwork.Requests.*;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class UserController {
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
        try {
            validationService.validateRegisterPersonRequest(request);
            userService.registerPerson(request);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registerPerson";
        }

        authAfterRegistration(sReq, request.getEmail(), request.getPassword());
        return "redirect:/";
    }

    @PostMapping("/register/company")
    public String registerCompany(RegisterCompanyRequest request, Model model, HttpServletRequest sReq) {
        try {
            validationService.validateRegisterCompanyRequest(request);
            userService.registerCompany(request);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registerCompany";
        }

        authAfterRegistration(sReq, request.getEmail(), request.getPassword());
        return "redirect:/";
    }

    @PostMapping("/edit/person")
    public ModelAndView editPerson(@RequestBody EditPersonRequest request) {
        ModelAndView view = new ModelAndView();
        view.setViewName("editInfo");
        try {validationService.validateEditPersonRequest(request);}
        catch (Exception e)
        {
            view.addObject("error", e.getMessage());
            return view;
        }
        try
        {
            userService.editPerson(request);
        }
        catch (Exception e)
        {
            view.addObject("error", e.getMessage());
            return view;
        }
        view.addObject("success", "Profile changed successfully!");
        return view;
        }

    @PostMapping("/edit/company")
    public ModelAndView editCompany(@RequestBody EditCompanyRequest request) throws Exception {
        ModelAndView view = new ModelAndView();
        view.setViewName("editInfo");
        try {validationService.validateEditCompanyRequest(request);}
        catch (Exception e)
        {
            view.addObject("error", e.getMessage());
            return view;
        }
        try
        {
            userService.editCompany(request);
        }
        catch (Exception e)
        {
            view.addObject("error", e.getMessage());
            return view;
        }
        view.addObject("success", "Profile changed successfully!");
        return view;
    }

    public void authAfterRegistration(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));

        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
