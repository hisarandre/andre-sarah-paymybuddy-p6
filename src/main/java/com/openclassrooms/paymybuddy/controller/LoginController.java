package com.openclassrooms.paymybuddy.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for managing user login and logout.
 */
@Controller
public class LoginController {

    /**
     * Returns the view for the login page.
     *
     * @return the name of the view template to be rendered
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Handles the user logout action.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return a redirect to the login page with a logout parameter
     */
    @RequestMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}