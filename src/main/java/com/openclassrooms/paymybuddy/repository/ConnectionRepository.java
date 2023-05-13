package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends CrudRepository<Connection, Integer> {

    List<Connection> findBySender(User sender);
    List<Connection> findByReceiver(User receiver);
    Connection findBySenderAndReceiver(User sender, User receiver);
    Connection findById(int id);
}
