package com.pay_my_buddy.PayMyBuddy;

import com.pay_my_buddy.PayMyBuddy.model.Animal;
import com.pay_my_buddy.PayMyBuddy.model.Product;
import com.pay_my_buddy.PayMyBuddy.service.AnimalService;
import com.pay_my_buddy.PayMyBuddy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {

	@Autowired
	private ProductService productService;
	@Autowired
	private AnimalService animalService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/*Iterable<Product> products = productService.getproducts();
		products.forEach(product -> System.out.println(product.getName()));*/

		Iterable<Animal> animal = animalService.getAnimals();
		animal.forEach(animal1 -> System.out.println(animal1));
	}
}
