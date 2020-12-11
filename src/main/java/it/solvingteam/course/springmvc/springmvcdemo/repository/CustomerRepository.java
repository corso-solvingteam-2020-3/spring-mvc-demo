package it.solvingteam.course.springmvc.springmvcdemo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("from Customer c left join fetch c.deliveries")
	List<Customer> findAllFetchDeliveries();

	@Query("from Customer c left join fetch c.deliveries where c.id = ?1")
	Optional<Customer> findByIdFetchDeliveries(Integer id);
}
