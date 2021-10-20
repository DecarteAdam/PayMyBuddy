package com.pay_my_buddy.PayMyBuddy.repository;

import com.pay_my_buddy.PayMyBuddy.model.Animal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Integer> {
}
