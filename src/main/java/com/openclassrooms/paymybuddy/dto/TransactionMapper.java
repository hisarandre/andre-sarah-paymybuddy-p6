package com.openclassrooms.paymybuddy.dto;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.ConnectionTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionMapper {

    @Mapping(target="connection", source="bank.iban")
    @Mapping(target="amount", expression = "java(-(toBank.getAmount() + toBank.getAmount() * toBank.getFees()))")
    TransactionConnectionDescriptionAmountDTO debitFromBankTransaction(BankTransaction toBank);

    @Mapping(target="connection", source="bank.iban")
    @Mapping(target="amount", expression = "java(fromBank.getAmount())")
    TransactionConnectionDescriptionAmountDTO creditFromBankTransaction(BankTransaction fromBank);

    @Mapping(target="connection", ignore = true)
    @Mapping(target="amount", expression = "java(-(toUser.getAmount() + toUser.getAmount() * toUser.getFees()))")
    TransactionConnectionDescriptionAmountDTO debitFromUserTransaction(ConnectionTransaction toUser);

    @Mapping(target="connection", ignore = true)
    @Mapping(target="amount", expression = "java(fromUser.getAmount())")
    TransactionConnectionDescriptionAmountDTO creditFromUserTransaction(ConnectionTransaction fromUser);
}
