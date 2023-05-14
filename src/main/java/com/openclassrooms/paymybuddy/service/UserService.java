package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for user management, such as retrieving user information, saving users,
 * and getting the currently authenticated user.
 */
@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves all users from the user repository.
     *
     * @return an iterable containing all users
     */
    public Iterable<User> getUsers() {
        return userRepository.findAll();}

    /**
     * Retrieves a user with the specified ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID, or null if no such user exists
     */
    public User findById(int id) {
        return userRepository.findById(id);
    }

    /**
     * Retrieves a user with the specified email address.
     *
     * @param email the email address of the user to retrieve
     * @return the user with the specified email address, or null if no such user exists
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Saves a new user or updates an existing user in the user repository.
     * The user's password is encrypted using the BCryptPasswordEncoder before saving.
     *
     * @param user the user to save or update
     * @return the saved user
     */
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Retrieves the currently authenticated user from the security context.
     *
     * @return the currently authenticated user, or null if no user is authenticated
     */
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return findByEmail(email);
    }
}
