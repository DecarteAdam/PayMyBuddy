package com.pay_my_buddy.PayMyBuddy.data;

import com.pay_my_buddy.PayMyBuddy.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    BankAccount findBankAccountByBankAccountNumber(@NotBlank String accountNumber);
}
