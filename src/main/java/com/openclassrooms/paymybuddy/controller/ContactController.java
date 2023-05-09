package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.ConnectionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ContactController {

    @Autowired
    UserService userService;

    @Autowired
    ConnectionService connectionService;


    @RequestMapping("/home/contact")
    public String getContact(Model model) {
        User currentUser = userService.getCurrentUser();
        List<User> contactList = currentUser.getContactList();
        model.addAttribute("contacts", contactList);
        return "contact";
    }

    @PostMapping("/home/contact")
    public String deleteConnection(@RequestParam("id") int id){
        User currentUser = userService.getCurrentUser();
        Connection connection = connectionService.findByIdSenderAndIdReceiver(currentUser.getIdUser(), id);
        connectionService.deleteConnection(connection);

        return "redirect:/home/contact?success";
    }
}
