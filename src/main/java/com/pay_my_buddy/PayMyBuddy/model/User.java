package com.pay_my_buddy.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String role;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private BankAccount account;

    @ManyToMany //FetchType.LAZY by default
    @JoinTable(
            name = "user_connections",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "connection_id"))
    private Set<User> connections;

    @OneToMany
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Set<Transaction> transaction;
}
