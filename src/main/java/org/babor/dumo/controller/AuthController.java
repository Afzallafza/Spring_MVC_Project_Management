package org.babor.dumo.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babor.dumo.Constants;
import org.babor.dumo.dto.LoginDto;
import org.babor.dumo.dto.UserRegistrationDto;
import org.babor.dumo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/auth")
@SessionAttributes({Constants.USERNAME, Constants.ROLE})
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                        RedirectAttributes redirectAttributes, Model model) {
        try {
            if (userService.validateLogin(loginDto)) {
                model.addAttribute("username", loginDto.getUsername());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password.");
                return "redirect:/auth/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password.");
            return "redirect:/auth/login";
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegistrationDto userRegistrationDto) {
        userService.save(userRegistrationDto);
        return "redirect:/auth/login";
    }
}
