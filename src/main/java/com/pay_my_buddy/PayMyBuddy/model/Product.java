package com.pay_my_buddy.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "produit")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Produi_Id")
    private int productId;
    @Column(name = "nom")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "cout")
    private int cost;
}
