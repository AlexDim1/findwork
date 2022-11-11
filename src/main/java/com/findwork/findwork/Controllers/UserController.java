package com.findwork.findwork.Controllers;

import com.findwork.findwork.Requests.EditPersonRequest;
import com.findwork.findwork.Requests.LoginRequest;
import com.findwork.findwork.Requests.RegisterCompanyRequest;
import com.findwork.findwork.Requests.RegisterPersonRequest;
import com.findwork.findwork.Services.UserService;
import com.findwork.findwork.Services.ValidationService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ValidationService validationService;
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/register/company")
    public ModelAndView getRegistrationPageCompany() {
        ModelAndView view = new ModelAndView("register");
        view.addObject("company");
        return view;
    }

    @GetMapping("/register/person")
    public ModelAndView getRegistrationPagePerson() {
        ModelAndView view = new ModelAndView("register");
        view.addObject("person");
        return view;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestBody LoginRequest request) {
        ModelAndView view = new ModelAndView();
        if(!validationService.validateLoginRequest(request)) {
            view.setViewName("login");
            view.addObject("error", "Invalid data");
            return view;
        }

        UserDetails user = userService.loadUserByUsername(request.getEmail());
        if(!encoder.matches(request.getPassword(), user.getPassword())) {
            view.setViewName("login");
            view.addObject("error", "Wrong password");
            return view;
        }

        view.setViewName("homepage");
        return view;
    }

    @PostMapping("/register/person")
    public ModelAndView registerPerson(@RequestBody RegisterPersonRequest request) {
        ModelAndView view = new ModelAndView();
        if(!validationService.validateRegisterPersonRequest(request)) {
            view.setViewName("register");
            view.addObject("error", "Invalid data");
            return view;
        }

        try {
            userService.registerPerson(request);
        } catch (Exception e) {
            view.setViewName("register");
            view.addObject("error", e.getMessage());
            return view;
        }

        view.setViewName("editInfo");
        view.addObject("success", "Bravo, pich - registrira se!");
        return view;
    }

    @PostMapping("/register/company")
    public ModelAndView registerCompany(@RequestBody RegisterCompanyRequest request) {
        ModelAndView view = new ModelAndView();
        if(!validationService.validateRegisterCompanyRequest(request)) {
            view.setViewName("register");
            view.addObject("error", "Invalid data");
            return view;
        }

        try {
            userService.registerCompany(request);
        } catch (Exception e) {
            view.setViewName("register");
            view.addObject("error", e.getMessage());
            return view;
        }

        view.setViewName("editInfo");
        view.addObject("success", "Bravo, pich - registrira se!");
        return view;
    }

    @PostMapping("/edit/person")
    public ModelAndView editPerson(@RequestBody EditPersonRequest request) {
        ModelAndView view = new ModelAndView();
        view.setViewName("editInfo");
        if (!validationService.validateEditPersonRequest(request))
        {
            view.addObject("error", "Invalid data");
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

    @PostMapping("/edit/company")                                                                              //TODO
    public ModelAndView editCompany(@RequestBody RegisterCompanyRequest request) {
        ModelAndView view = new ModelAndView();
        view.setViewName("editInfo");
        return view;
    }

    public UserController(UserService userService, ValidationService validationService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.validationService = validationService;
        this.encoder = encoder;
    }
}
