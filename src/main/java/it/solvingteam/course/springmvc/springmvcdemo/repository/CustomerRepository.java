package it.solvingteam.course.springmvc.springmvcdemo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {



}
