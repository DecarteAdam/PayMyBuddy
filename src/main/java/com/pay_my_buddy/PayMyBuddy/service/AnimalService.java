package com.pay_my_buddy.PayMyBuddy.service;

import com.pay_my_buddy.PayMyBuddy.model.Animal;
import com.pay_my_buddy.PayMyBuddy.repository.AnimalRepository;
import com.pay_my_buddy.PayMyBuddy.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;

    public Iterable<Animal> getAnimals(){
        return this.animalRepository.findAll();
    }
}
