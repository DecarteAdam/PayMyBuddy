package com.pay_my_buddy.PayMyBuddy.services;

import com.pay_my_buddy.PayMyBuddy.data.BankAccountDAO;
import com.pay_my_buddy.PayMyBuddy.data.UserRepository;
import com.pay_my_buddy.PayMyBuddy.exception.BankTransactionException;
import com.pay_my_buddy.PayMyBuddy.model.BankAccount;
import com.pay_my_buddy.PayMyBuddy.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = TransactionServiceTest.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
public class TransactionServiceTest {


    @Autowired
    UserRepository userRepository;

    @Autowired
    BankAccountDAO bankAccountDAO;

    @Test
    public void sendMoney() throws BankTransactionException {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountNumber("FR86000000");

        BankAccount bankAccountTo = new BankAccount();
        bankAccountTo.setBankAccountNumber("FR93111111");

        User user = new User();
        user.setId(1);
        user.setFirstname("Adam");
        user.setLastname("Decarte");
        user.setAccount(bankAccount);

        User connection = new User();
        connection.setId(2);
        connection.setFirstname("John");
        connection.setLastname("Doe");
        connection.setAccount(bankAccountTo);


        this.bankAccountDAO.sendMoney(user, connection, "", 300);

        Optional<User> user1 = this.userRepository.findById(2);

        assertEquals(connection, user1.get());


    }

}
