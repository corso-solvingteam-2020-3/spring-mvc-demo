package it.solvingteam.course.springmvc.springmvcdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.AddCustomerMessageDTO;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomersSearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeleteCustomerMessageDto;
import it.solvingteam.course.springmvc.springmvcdemo.exceptions.RoleNotFoundException;
import it.solvingteam.course.springmvc.springmvcdemo.mapper.CustomerMapper;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
import it.solvingteam.course.springmvc.springmvcdemo.model.Delivery;
import it.solvingteam.course.springmvc.springmvcdemo.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private EntityManager entityManager;

	public List<CustomerDto> findAll() {
		List<Customer> allCustomers = this.customerRepository.findAll();
		return customerMapper.convertEntityToDto(allCustomers);
	}

	public Optional<Customer> findById(Integer id) {
		return this.customerRepository.findById(id);
	}

	public List<CustomerDto> findBySearchParameter(CustomersSearchFilterDto customersSearchFilterDto) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

		Root<Customer> customer = cq.from(Customer.class);
		List<Predicate> predicates = new ArrayList<>();

		if (customersSearchFilterDto.getId() != null && !customersSearchFilterDto.getId().equals("")) {
			predicates.add(cb.like(customer.get("id").as(String.class), "%" + customersSearchFilterDto.getId() + "%"));
		}

		if (customersSearchFilterDto.getName() != null && !customersSearchFilterDto.getName().equals("")) {
			predicates.add(cb.like(customer.get("name"), "%" + customersSearchFilterDto.getName() + "%"));
		}

		if (customersSearchFilterDto.getAddress() != null && !customersSearchFilterDto.getAddress().equals("")) {
			predicates.add(cb.like(customer.get("address"), "%" + customersSearchFilterDto.getAddress() + "%"));
		}

		if (customersSearchFilterDto.getMobile() != null && !customersSearchFilterDto.getMobile().equals("")) {
			predicates.add(cb.like(customer.get("mobile"), "%" + customersSearchFilterDto.getMobile() + "%"));
		}

		cq.where(predicates.toArray(new Predicate[0]));
		return customerMapper.convertEntityToDto(entityManager.createQuery(cq).getResultList());
	}

	public Customer save(CustomerDto customerDto) {
		Customer customer = customerMapper.convertDtoToEntity(customerDto);
		return this.customerRepository.save(customer);
	}

	public long count() {
		return this.customerRepository.count();
	}

	public void insertCustomer(AddCustomerMessageDTO addCustomerMessageDto) throws RoleNotFoundException {
		this.customerRepository.save(customerMapper.convertDtoToEntity(addCustomerMessageDto));
	}

	public Optional<Customer> findCustomerByName(String name) {
		return customerRepository.findByName(name);
	}

	public void updateCustomer(CustomerDto customerDto) {
		Customer customer = customerMapper.convertDtoToEntity(customerDto);
		this.customerRepository.save(customer);

	}

	public CustomerDto convertCustomer(Integer id) {
		Customer customer = this.findById(id).orElse(null);
		return customerMapper.convertEntityToDto(customer);
	}

	public Customer convertCustomerInEntity(CustomerDto customerDto) {
	  return customerMapper.convertDtoToEntity(customerDto);
	}
	
	public Customer getCustomerFromDeleteDto(DeleteCustomerMessageDto deleteCustomerMessageDto) {
		Customer customer = this.findById(Integer.parseInt(deleteCustomerMessageDto.getIdDelete())).orElse(null);
		return customer;
	}
	
	public void delete(Integer id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		this.customerRepository.delete(customer);
	}
	
}
