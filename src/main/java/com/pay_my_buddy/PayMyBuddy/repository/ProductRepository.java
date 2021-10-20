package com.pay_my_buddy.PayMyBuddy.repository;

import com.pay_my_buddy.PayMyBuddy.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
