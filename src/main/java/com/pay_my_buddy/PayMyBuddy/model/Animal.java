package com.pay_my_buddy.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "animal")
@Data
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "espece")
    private String espece;
    @Column(name = "sexe")
    private String sexe;
    @Column(name = "date_naissance")
    private Date date_naissance;
    @Column(name = "nom")
    private String name;
    @Column(name = "commentaires")
    private String comment;
}
