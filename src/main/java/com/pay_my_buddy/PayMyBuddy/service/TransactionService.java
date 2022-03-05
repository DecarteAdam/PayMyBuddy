package com.pay_my_buddy.PayMyBuddy.service;

import com.pay_my_buddy.PayMyBuddy.data.TransactionRepository;
import com.pay_my_buddy.PayMyBuddy.model.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    /*public List<Transaction> getTransactions(int userId){
        return transactionRepository.findByUserIdOrderByDateDesc(userId);
    }*/

    public Page<Transaction> getTransactions(int pageNumber, int userId){
        Pageable pageable = PageRequest.of(pageNumber -1,3);
        return transactionRepository.findByUserIdOrderByDateDesc(pageable, userId);
    }

    public void save(Transaction transaction){
        transactionRepository.save(transaction);
    }
}
