package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.ConnectionTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConnectionTransactionRepository extends CrudRepository<ConnectionTransaction, Integer> {
   ConnectionTransaction findById(int id);
}
