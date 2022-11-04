package com.findwork.findwork.Controllers;

import com.findwork.findwork.Requests.RegisterCompanyRequest;
import com.findwork.findwork.Requests.RegisterPersonRequest;
import com.findwork.findwork.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "register";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/register/person")
    public String registerPerson(@RequestBody RegisterPersonRequest request) {
        return userService.registerPerson(request);
    }

    @PostMapping("/register/company")
    public String registerCompany(@RequestBody RegisterCompanyRequest request) {
        return userService.registerCompany(request);
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
