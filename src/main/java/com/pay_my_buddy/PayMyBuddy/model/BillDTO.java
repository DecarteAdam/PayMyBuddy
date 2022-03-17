package com.pay_my_buddy.PayMyBuddy.model;

import lombok.Data;

import java.util.List;
@Data
public class BillDTO {
    private String username;
    private List<Transaction> transaction;
    private Double  sommeFees;

}
