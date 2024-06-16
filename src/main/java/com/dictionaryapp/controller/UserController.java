package com.dictionaryapp.controller;

import com.dictionaryapp.model.dto.RegisterUserDTO;
import com.dictionaryapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerData")
    public RegisterUserDTO registerUserDTO() {
        return new RegisterUserDTO();
    }

    @GetMapping("/register")
    public String viewRegister(Model model) {

        return "register";
    }


    @PostMapping("/register")
    public String doRegister(@Valid RegisterUserDTO registerUserDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors() || !userService.register(registerUserDTO)) {
            redirectAttributes.addFlashAttribute("registerData", registerUserDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);

            return "redirect:/register";
        }

        return "redirect:/login";
    }
}
