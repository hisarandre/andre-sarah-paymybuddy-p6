package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankTransactionRepository extends CrudRepository<BankTransaction, Integer> {

    Optional<BankTransaction> findById(int id);
}
