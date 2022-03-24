package com.pay_my_buddy.PayMyBuddy.services;

import com.pay_my_buddy.PayMyBuddy.data.BankAccountDAO;
import com.pay_my_buddy.PayMyBuddy.data.BankAccountRepository;
import com.pay_my_buddy.PayMyBuddy.data.TransactionRepository;
import com.pay_my_buddy.PayMyBuddy.data.UserRepository;
import com.pay_my_buddy.PayMyBuddy.exception.BankTransactionException;
import com.pay_my_buddy.PayMyBuddy.model.Transaction;
import com.pay_my_buddy.PayMyBuddy.model.User;
import com.pay_my_buddy.PayMyBuddy.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransactionServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    BankAccountDAO bankAccountDAO;
    @Autowired
    TransactionService transactionService;

    @Test
    @DisplayName("Transaction should pass without problems")
    public void sendMoney() throws BankTransactionException {
        Optional<User> user1 = userRepository.findById(1);
        Optional<User> user2 = userRepository.findById(2);

        this.bankAccountDAO.sendMoney(user1.get(), user2.get(), "", 300);


        Optional<User> userFrom = this.userRepository.findById(1);
        Optional<User> userTo = this.userRepository.findById(2);

        assertEquals(1300, userTo.get().getAccount().getBalance());
        assertEquals(698.5, userFrom.get().getAccount().getBalance());
    }


    @Test
    @DisplayName("Transaction should throw an exception because not enough money")
    public void sendMoneyException() {

        Optional<User> user1 = userRepository.findById(1);
        Optional<User> user2 = userRepository.findById(2);

        assertThrows(BankTransactionException.class, () -> {
            this.bankAccountDAO.sendMoney(user1.get(), user2.get(), "", 2000);
        });
    }

    @Test
    @DisplayName("Transaction should throw an exception and balances should stay untouched")
    public void sendMoneyExceptionRollback() {

        Optional<User> user1 = userRepository.findById(1);
        Optional<User> user2 = userRepository.findById(2);

        assertThrows(BankTransactionException.class, () -> {
            this.bankAccountDAO.sendMoney(user1.get(), user2.get(), "", 2000);
        });

        Optional<User> userFrom = this.userRepository.findById(1);
        Optional<User> userTo = this.userRepository.findById(2);

        assertEquals(1000, userTo.get().getAccount().getBalance());
        assertEquals(1000, userFrom.get().getAccount().getBalance());
    }

    @Test
    @DisplayName("Get realized transaction")
    public void getTransaction() throws BankTransactionException {

        Optional<User> user1 = userRepository.findById(1);
        Optional<User> user2 = userRepository.findById(2);

        Transaction transactionEx = new Transaction();
        transactionEx.setAmount(100);

        this.bankAccountDAO.sendMoney(user1.get(), user2.get(), "", 100);

        Optional<User> userAssertFrom = this.userRepository.findById(1);

        Page<Transaction> transactionActual = this.transactionService.getTransactions(1, userAssertFrom.get().getId());
        List<Transaction> transaction = transactionActual.getContent();

        assertEquals(transactionEx.getAmount(), transaction.get(0).getAmount());
    }

    @Test
    @DisplayName("Get transactions fees amount")
    public void getTransactionsAmount() throws BankTransactionException {

        Transaction transaction1 = new Transaction();
        transaction1.setFees(0.5);
        Transaction transaction2 = new Transaction();
        transaction2.setFees(0.5);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);


        double amount = this.transactionService.transactionAmount(transactions);

        assertEquals(1, amount);
    }

}
