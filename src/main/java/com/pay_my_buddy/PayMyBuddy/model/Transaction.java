package com.pay_my_buddy.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private int userId;
    @NotBlank
    private String connection;
    private String description;
    private double amount;
    private double fees;
    @Column(name="date", columnDefinition="TIMESTAMP(0)")
    @Temporal(TemporalType.DATE)
    private Date date = new Date();
}
