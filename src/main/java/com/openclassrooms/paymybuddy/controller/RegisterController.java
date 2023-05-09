package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.Authorities;
import com.openclassrooms.paymybuddy.model.Bank;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.AuthoritiesService;
import com.openclassrooms.paymybuddy.service.BankService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;
    @Autowired
    BankService bankService;
    @Autowired
    AuthoritiesService authoritiesService;

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("user") User user){

        User existingUser = userService.findByEmail(user.getEmail());
        if(existingUser != null) return "redirect:/register?error";

        userService.save(user);

        Bank bank = new Bank();
        bank.setIban("FR76 4567 8790 8798 789056782");
        bank.setSwift("AXAB BE 22 XXX");
        bank.setUser(user);
        bankService.save(bank);

        Authorities authorities = new Authorities();
        authorities.setEmail(user.getEmail());
        authoritiesService.save(authorities);

        return "redirect:/register?success";
    }
}
