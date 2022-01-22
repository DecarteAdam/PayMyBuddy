package com.pay_my_buddy.PayMyBuddy.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@AllArgsConstructor
@Table(name = "connection")
@Data
public class Connection extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int user_id;
}
