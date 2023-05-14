package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Authorities;
import com.openclassrooms.paymybuddy.repository.AuthoritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AuthoritiesService is a service class that provides methods for accessing and manipulating the authorities data.
 */
@Service
public class AuthoritiesService {
    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    /**
     * Saves the given authorities object to the database.
     *
     * @param authorities Authorities object to be saved.
     * @return Authorities object after being saved to the database.
     */
    public Authorities save(Authorities authorities) {
        return authoritiesRepository.save(authorities);
    }
}
