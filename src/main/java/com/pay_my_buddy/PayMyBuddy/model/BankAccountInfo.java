package com.pay_my_buddy.PayMyBuddy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountInfo {
    private Long id;
    private String fullName;
    private double balance;
}
