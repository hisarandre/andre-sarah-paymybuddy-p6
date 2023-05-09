package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.ConnectionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConnectionController {

    @Autowired
    ConnectionService connectionService;

    @Autowired
    UserService userService;

    @RequestMapping("/home/connection")
    public String getConnection(Model model) {
        return "connection";
    }

    @PostMapping("/home/connection")
    public String addConnection(@RequestParam("email") String email){

        User existingUser = userService.findByEmail(email);
        if(existingUser == null) return "redirect:/home/connection?error";

        connectionService.addConnection(existingUser);

        return "redirect:/home/connection?success";
    }
}
