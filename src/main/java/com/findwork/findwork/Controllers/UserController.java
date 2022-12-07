package com.findwork.findwork.Controllers;

import com.findwork.findwork.Entities.Users.UserPerson;
import com.findwork.findwork.Requests.EditPersonRequest;
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

import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ValidationService validationService;

    @GetMapping("/{id}")
    public String getPersonPage(@PathVariable UUID id, Model model) {
        model.addAttribute("user", userService.loadUserById(id));
        return "user";
    }

    @GetMapping("/{id}/edit")
    public String getEditPagePerson(@PathVariable UUID id, Model model, Authentication auth) {
        UserPerson loggedIn = (UserPerson) auth.getPrincipal();
        UserPerson user = userService.loadUserById(id);

        if (!loggedIn.getId().equals(user.getId()))
            return "redirect:/user/" + id;

        model.addAttribute("user", user);
        return "editPerson";
    }

    @PostMapping("/{id}")
    public String editPerson(@PathVariable UUID id, EditPersonRequest request, Model model, Authentication auth) {
        UserPerson loggedIn = (UserPerson) auth.getPrincipal();
        UserPerson user = userService.loadUserById(id);

        if (!loggedIn.getId().equals(user.getId()))
            return "redirect:/user/" + id;

        try {
            validationService.validateEditPersonRequest(request);
            userService.editPerson(id, request);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "editPerson";
        }

        return "redirect:/user/" + id;
    }
}
