package com.openclassrooms.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for managing the home page.
 */
@Controller
public class HomeController {

    /**
     * Returns the view for the home page.
     *
     * @return the name of the view template to be rendered
     */
    @RequestMapping("/home")
    public String getHome() {
        return "home";
    }
}
