package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.ConnectionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for managing user connections.
 */
@Controller
public class ConnectionController {

    @Autowired
    ConnectionService connectionService;

    @Autowired
    UserService userService;

    /**
     * Returns the view for the user connection page.
     *
     * @return the name of the view template to be rendered
     */
    @RequestMapping("/home/connection")
    public String getConnection() {
        return "connection";
    }

    /**
     * Adds a connection to the current user's list of connections.
     *
     * @param email the email address of the user to connect with
     * @return the URL to redirect to after the connection is added
     */
    @PostMapping("/home/connection")
    public String addConnection(@RequestParam("email") String email){

        //search if user exists
        User existingUser = userService.findByEmail(email);
        if(existingUser == null) return "redirect:/home/connection?error";

        //if user exist, add the connection
        connectionService.addConnection(existingUser);
        return "redirect:/home/connection?success";
    }
}
