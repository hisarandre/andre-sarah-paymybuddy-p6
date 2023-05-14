package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller class for managing user contacts.
 */
@Controller
public class ContactController {

    @Autowired
    UserService userService;

    /**
     * Returns the view for the user contact list.
     *
     * @param model the model to be used for rendering the view
     * @return the name of the view template to be rendered
     */
    @RequestMapping("/home/contact")
    public String getContact(Model model) {
        User currentUser = userService.getCurrentUser();
        List<User> contactList = currentUser.getContactList();
        model.addAttribute("contacts", contactList);
        return "contact";
    }
}
