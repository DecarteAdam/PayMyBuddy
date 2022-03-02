package com.pay_my_buddy.PayMyBuddy.model;

import com.pay_my_buddy.PayMyBuddy.DTO.UserDTO;
import lombok.Data;

@Data
public class SendMoneyForm {

    private Long fromAccountId;
    private UserDTO userFrom;
    private Long toAccountId;
    private UserDTO userTo;
    private Double amount;
}
