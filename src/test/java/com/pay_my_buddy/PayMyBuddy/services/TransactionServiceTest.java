package com.pay_my_buddy.PayMyBuddy.services;

import com.pay_my_buddy.PayMyBuddy.data.BankAccountDAO;
import com.pay_my_buddy.PayMyBuddy.data.BankAccountRepository;
import com.pay_my_buddy.PayMyBuddy.data.UserRepository;
import com.pay_my_buddy.PayMyBuddy.exception.BankTransactionException;
import com.pay_my_buddy.PayMyBuddy.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransactionServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    BankAccountDAO bankAccountDAO;
    @Test
    public void sendMoney() throws BankTransactionException {
        Optional<User> user1 = userRepository.findById(1);
        Optional<User> user2 = userRepository.findById(2);
        this.bankAccountDAO.sendMoney(user1.get(), user2.get(), "", 300);
        Optional<User> userAssert = this.userRepository.findById(2);
        assertEquals(1300, userAssert.get().getAccount().getBalance());
    }
}
