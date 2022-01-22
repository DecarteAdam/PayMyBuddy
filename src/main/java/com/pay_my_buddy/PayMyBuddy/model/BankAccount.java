package com.pay_my_buddy.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "bank_account")
public class BankAccount {


    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "Full_Name", length = 128, nullable = false)
    private String fullName;

    @Column(name = "Balance", nullable = false)
    private double balance;


}
