package com.pay_my_buddy.PayMyBuddy.data;

import com.pay_my_buddy.PayMyBuddy.exception.BankTransactionException;
import com.pay_my_buddy.PayMyBuddy.model.BankAccount;
import com.pay_my_buddy.PayMyBuddy.model.Transaction;
import com.pay_my_buddy.PayMyBuddy.model.User;
import com.pay_my_buddy.PayMyBuddy.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Repository
public class BankAccountDAO {
    private static final Logger logger = LoggerFactory.getLogger(BankAccountDAO.class);

    @Value("${appBankAccount}")
    String applicationAccount;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private BankAccountRepository bankAccountRepository;


    /**
     * Transactional method to add amount and manage commits and rollbacks
     * @param accountNumber of the account to send money
     * @param amount of money
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void addAmount(String accountNumber, double amount) throws BankTransactionException {
        logger.info("Add amount: {} {}", accountNumber, amount);

        BankAccount account = this.bankAccountRepository.findBankAccountByBankAccountNumber(accountNumber);
        if (account == null) {
            throw new BankTransactionException("Account not found " + accountNumber);
        }
        double newBalance = account.getBalance() + amount;
        if (account.getBalance() + amount < 0) {
            throw new BankTransactionException(
                    "The money in the account '" + accountNumber + "' is not enough (" + account.getBalance() + ")");
        }
        account.setBalance(newBalance);
    }

    /**
     * Transactional method to process transaction
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
    public void sendMoney(@NotNull User user,
                          @NotNull User connection,
                          String description,
                          @NotNull double amount) throws BankTransactionException {
        logger.info("Send fromAccount: {}", user.getAccount().getBankAccountNumber());
        logger.info("Send toAccount: {}", connection.getAccount().getBankAccountNumber());

        double fees = (amount * 1.005) - amount;

        addAmount(connection.getAccount().getBankAccountNumber(), amount);
        addAmount(user.getAccount().getBankAccountNumber(), (-amount -fees));

        // Add fees to the Application bank account
        populateAppAccount(fees);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setFees(fees);
        transaction.setUserId(user.getId());
        transaction.setDescription(description);
        transaction.setConnection(connection.getFirstname().concat(" " + connection.getLastname()));
        transactionService.save(transaction);
    }

    /**
     * Add transaction fees to the app bank account
     * @param fees to apply
     */
    public void populateAppAccount(double fees){
        BankAccount appAccount = this.bankAccountRepository.findBankAccountByBankAccountNumber(applicationAccount);
        appAccount.setBalance(appAccount.getBalance() + fees);

    }

}
