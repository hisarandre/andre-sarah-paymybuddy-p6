package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Connection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends CrudRepository<Connection, Integer> {

    List<Connection> findByIdSender(int id);
    List<Connection> findByIdReceiver(int id);
    Connection findByIdSenderAndIdReceiver(int idSender, int idReceiver);
    Connection findById(int id);
}
