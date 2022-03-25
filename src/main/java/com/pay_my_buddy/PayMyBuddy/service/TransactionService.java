package com.pay_my_buddy.PayMyBuddy.service;

import com.pay_my_buddy.PayMyBuddy.data.TransactionRepository;
import com.pay_my_buddy.PayMyBuddy.data.UserRepository;
import com.pay_my_buddy.PayMyBuddy.model.BillDTO;
import com.pay_my_buddy.PayMyBuddy.model.Transaction;
import com.pay_my_buddy.PayMyBuddy.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    /**
     * Get list of transaction for user
     */
    public Page<Transaction> getTransactions(int pageNumber, int userId){
        Pageable pageable = PageRequest.of(pageNumber -1  ,3);
        return transactionRepository.findByUserIdOrderByDateDesc(pageable, userId);
    }

    /**
     * Save transaction into the BDD
     * @param transaction to save
     */
    public void save(Transaction transaction){
        transactionRepository.save(transaction);
    }

    /**
     * Retrieve transaction to bill for
     * @param userId of user
     * @param date to bill for
     * @return BillDTO object for billing
     */
    public BillDTO getTransactionByUserIdAndDate(int userId, Date date){
        List<Transaction> transactions = this.transactionRepository.findAllByUserIdAndDate(userId, date);
        User user = this.userRepository.getById(userId);

        BillDTO billDTO = new BillDTO();
        billDTO.setUsername(user.getFirstname() + " " + user.getLastname());
        billDTO.setTransaction(transactions);
        billDTO.setSommeFees(transactionAmount(transactions));
         return billDTO;
    }

    /**
     * Compute transaction fees sum
     * @param transactions list of transactions
     * @return sum
     */
    public double transactionAmount(List<Transaction> transactions){
        return transactions.stream().map(Transaction::getFees)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
