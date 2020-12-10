package it.solvingteam.course.springmvc.springmvcdemo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomersSearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.mapper.CustomerMapper;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
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

    public Customer insert(CustomerInsertDto customerinsertDto) {
    	String id= customerinsertDto.getId();
    	String name= customerinsertDto.getName();
    	String mobile= customerinsertDto.getMobile();
    	String address= customerinsertDto.getAddress();
    	
    	Customer customer = new Customer();
    	if(id!=null) {
    		customer.setId(Integer.parseInt(id));
    	}
    	customer.setName(name);
    	customer.setMobile(mobile);
    	customer.setAddress(address);
        return this.customerRepository.save(customer);
    }
    
    public void delete(CustomerInsertDto customerinsertDto) {
    	String id= customerinsertDto.getId();
    	Customer customer=customerRepository.findById(Integer.parseInt(id)).orElse(null);
        this.customerRepository.delete(customer);
    }

    public long count() {
        return this.customerRepository.count();
    }

    public CustomerInsertDto getById(Integer id) {
    	Customer c=this.customerRepository.findById(id).orElse(null);
    	CustomerInsertDto cdto=new CustomerInsertDto();
    	cdto.setId(c.getId().toString());
    	cdto.setName(c.getName());
    	cdto.setMobile(c.getMobile());
    	cdto.setAddress(c.getAddress());
    	return c==null?null:cdto;
    }
}
