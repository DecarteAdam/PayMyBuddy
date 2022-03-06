package com.pay_my_buddy.PayMyBuddy.model;

import lombok.Data;

@Data
public class SendMoneyForm {

    private Long fromAccountId;
    private Long toAccountId;
    private Double amount;
}
