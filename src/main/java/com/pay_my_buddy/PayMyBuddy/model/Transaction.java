package com.pay_my_buddy.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;
    private String connection;
    private String description;
    private double amount;
    private Date date = new Date();
}
