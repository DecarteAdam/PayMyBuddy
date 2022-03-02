package com.pay_my_buddy.PayMyBuddy.data;

import com.pay_my_buddy.PayMyBuddy.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByUserIdOrderByDateDesc(int userId);
}
