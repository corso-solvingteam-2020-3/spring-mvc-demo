package it.solvingteam.course.springmvc.springmvcdemo.service;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.mapper.CustomerMapper;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
import it.solvingteam.course.springmvc.springmvcdemo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public List<CustomerDto> findAll(){
        List<Customer> allCustomers = this.customerRepository.findAll();
        return customerMapper.convertEntityToDto(allCustomers);
    }

    public Customer save(CustomerDto customerDto){
        Customer customer = customerMapper.convertDtoToEntity(customerDto);
        return this.customerRepository.save(customer);
    }

    public long count(){
        return this.customerRepository.count();
    }

}
