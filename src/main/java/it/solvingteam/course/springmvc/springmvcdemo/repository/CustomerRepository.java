package it.solvingteam.course.springmvc.springmvcdemo.repository;

import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Optional<Customer> findById(int id);
	
	@Query("from Customer c left join fetch c.deliveries d where c.id = ?1")
	Optional<Customer> findByIDWithDeliveries(Integer id);

}
