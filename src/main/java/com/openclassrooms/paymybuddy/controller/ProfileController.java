package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.dto.AccountBalanceBanksDTO;
import com.openclassrooms.paymybuddy.dto.BankTransferDTO;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.BankService;
import com.openclassrooms.paymybuddy.service.CustomService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for handling profile-related requests and operations.
 */
@Controller
public class ProfileController {

    @Autowired
    UserService userService;
    @Autowired
    BankService bankService;
    @Autowired
    CustomService customService;

    /**
     * Retrieves the current user's account balance and bank details and displays them on the profile page.
     *
     * @param model the data model to be used for rendering the view
     * @return the name of the view to be rendered
     */
    @RequestMapping("/home/profile")
    public String getContact(Model model) {
        User user = userService.getCurrentUser();
        AccountBalanceBanksDTO account = customService.getAccount(user);

        model.addAttribute("account", account);
        model.addAttribute("transaction", new BankTransferDTO());
        return "profile";
    }

    /**
     * Processes a bank transfer request from the user to their linked bank account.
     *
     * @param bankTransfer the DTO containing the details of the bank transfer request
     * @return a redirect to the profile page with a success or error message
     */
    @PostMapping(value = "/home/profile", params = "action=pay")
    public String transfer(@ModelAttribute("transaction") BankTransferDTO bankTransfer) {

        boolean moneySent = customService.sendMoneyToBank(bankTransfer);

        if(moneySent) {
            return "redirect:/home/profile?sent";
        } else {
            return "redirect:/home/profile?notsent";
        }
    }

    /**
     * Processes a request to add a new bank account to the user's profile.
     *
     * @param iban the IBAN of the bank account to be added
     * @param swift the SWIFT code of the bank account to be added
     * @return a redirect to the profile page with a success message
     */
    @PostMapping(value = "/home/profile", params = "action=add")
    public String transfer(@RequestParam("bankIban") String iban, @RequestParam("bankSwift") String swift) {

        bankService.addBank(iban,swift);
        return "redirect:/home/profile?added";
    }
}
