package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.dto.UserTransferDTO;
import com.openclassrooms.paymybuddy.dto.ContactsAndTransactionsListDTO;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.CustomService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TransferController {

    @Autowired
    CustomService customService;

    @Autowired
    UserService userService;

    @RequestMapping("/home/transfer")
    public String getUser(Model model) {

        ContactsAndTransactionsListDTO transferDetails = customService.getTransferDetails();

        model.addAttribute("transfer", transferDetails);
        model.addAttribute("transaction", new UserTransferDTO());
        return "transfer";
    }

    @PostMapping("/home/transfer")
    public String transfer(@ModelAttribute("transaction") UserTransferDTO transaction){

        boolean moneySent = customService.sendMoneyToUser(transaction);

        if(moneySent) {
            return "redirect:/home/transfer?success";
        } else {
            return "redirect:/home/transfer?error";
        }

    }
}
