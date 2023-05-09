package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Authorities;
import com.openclassrooms.paymybuddy.model.Bank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends CrudRepository<Authorities, Integer> {
}
