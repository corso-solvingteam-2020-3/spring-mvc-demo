package it.solvingteam.course.springmvc.springmvcdemo.repository;


import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	 Optional<Customer> findByName(String name);


}
