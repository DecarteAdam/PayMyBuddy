package com.pay_my_buddy.PayMyBuddy.service;

import com.pay_my_buddy.PayMyBuddy.data.TransactionRepository;
import com.pay_my_buddy.PayMyBuddy.model.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getTransactions(int userId){
        return transactionRepository.findByUserIdOrderByDateDesc(userId);
    }

    public void save(Transaction transaction){
        transactionRepository.save(transaction);
    }
}
