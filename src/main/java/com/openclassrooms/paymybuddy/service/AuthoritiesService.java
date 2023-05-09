package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Authorities;
import com.openclassrooms.paymybuddy.repository.AuthoritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthoritiesService {
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    public Authorities save(Authorities authorities) {
        return authoritiesRepository.save(authorities);
    }
}
