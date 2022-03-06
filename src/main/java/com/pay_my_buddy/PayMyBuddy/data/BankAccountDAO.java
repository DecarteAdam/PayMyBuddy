package com.pay_my_buddy.PayMyBuddy.data;

import com.pay_my_buddy.PayMyBuddy.exception.BankTransactionException;
import com.pay_my_buddy.PayMyBuddy.model.BankAccount;
import com.pay_my_buddy.PayMyBuddy.model.Transaction;
import com.pay_my_buddy.PayMyBuddy.model.User;
import com.pay_my_buddy.PayMyBuddy.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

@Repository
public class BankAccountDAO {
    private static final Logger logger = LoggerFactory.getLogger(BankAccountDAO.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionService transactionService;


    public BankAccountDAO() {
    }

    public BankAccount findById(int id) {
        return this.entityManager.find(BankAccount.class, id);
    }

    /**
     * Transactional method to add amount and manage commits and rollbacks
     * @param id of the account to send money
     * @param amount of money
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void addAmount(int id, double amount) throws BankTransactionException {
        logger.info("Add amount: {} {}", id, amount);
        BankAccount account = this.findById(id);
        if (account == null) {
            throw new BankTransactionException("Account not found " + id);
        }
        double newBalance = account.getBalance() + amount;
        if (account.getBalance() + amount < 0) {
            throw new BankTransactionException(
                    "The money in the account '" + id + "' is not enough (" + account.getBalance() + ")");
        }
        account.setBalance(newBalance);
    }

    /**
     * Transactional method to process transaction
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
    public void sendMoney(@NotNull int fromAccountId,
                          @NotNull int toAccountId,
                          String description,
                          @NotNull double amount,
                          @NotNull User user) throws BankTransactionException {
        logger.info("Send fromAccountId: {}", fromAccountId);
        logger.info("Send toAccountId: {}", toAccountId);
        logger.info("Send user: {}", user);

        addAmount(toAccountId, amount);
        addAmount(fromAccountId, -amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setUserId(fromAccountId);
        transaction.setDescription(description);
        transaction.setConnection(user.getFirstname().concat(" " + user.getLastname()));
        transactionService.save(transaction);
    }

}
