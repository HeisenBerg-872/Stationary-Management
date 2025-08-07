package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
//@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userservice) {
    	this.userService = userservice;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("userDto") @Valid UserRegistrationDto dto,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        userService.registerUser(dto);
        model.addAttribute("successMessage", "Registration successful!");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/home")
    public String redirectToDashboard(@AuthenticationPrincipal com.example.demo.service.impl.CustomUserDetails userDetails) {
        String role = userDetails.getUser().getRole().name();

        if ("ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/student/dashboard";
        }
    }

    @GetMapping("/redirect")
    public String redirectBasedOnRole(@AuthenticationPrincipal com.example.demo.service.impl.CustomUserDetails userDetails) {
        String role = userDetails.getUser().getRole().name();
        if (role.equals("ADMIN")) {
            return "redirect:/admin/orders";
        } else {
            return "redirect:/student/upload-order";
        }
    }


}
