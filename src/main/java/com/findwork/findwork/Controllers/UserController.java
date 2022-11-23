package com.findwork.findwork.Controllers;

import com.findwork.findwork.Requests.EditPersonRequest;
import com.findwork.findwork.Services.UserService;
import com.findwork.findwork.Services.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ValidationService validationService;

    @GetMapping("/{id}/edit")
    public String getEditPagePerson() {return "editPerson";}

    @PutMapping("/{id}/edit")
    public String editPerson(@PathVariable UUID id, EditPersonRequest request, Model model)
    {
        try
        {
            validationService.validateEditPersonRequest(request);
            userService.editPerson(id, request);
        }
        catch (Exception e)
        {
            model.addAttribute("error", e.getMessage());
            return "editPerson";
        }
      return "user" /* + "/" + id*/;
    }
}
