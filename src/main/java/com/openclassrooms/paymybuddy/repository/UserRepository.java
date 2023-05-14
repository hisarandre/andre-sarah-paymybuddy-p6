package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    User findByEmail(String email);
    User findById(int idUser);
}
