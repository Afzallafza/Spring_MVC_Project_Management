package org.babor.proj.controller;


import jakarta.validation.Valid;
import org.babor.proj.dto.LoginDto;
import org.babor.proj.entity.User;
import org.babor.proj.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthenticationController {
    private final UserService userService;
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String login() {
        return "Login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("user") LoginDto user) {

        return "Login";
    }
    @GetMapping("/register")
    public String register() {
        return "Registration";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result,Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errors", result);
            return "Registration";
        }
        try{
            userService.save(user);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
