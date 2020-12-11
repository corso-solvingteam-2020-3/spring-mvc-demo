package it.solvingteam.course.springmvc.springmvcdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerDeletionDto;
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
        List<Customer> allCustomers = this.customerRepository.findAllFetchDeliveries();
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

    public Customer save(CustomerDto customerDto) {
        Customer customer = customerMapper.convertDtoToEntity(customerDto);
        return this.customerRepository.save(customer);
    }
    
    public Customer save(CustomerInsertDto customerInsertDto) {
        return save(customerMapper.convertToCustomerDto(customerInsertDto));	
	}

    public long count() {
        return this.customerRepository.count();
    }

	public CustomerDto show(Integer id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		return customerMapper.convertEntityToDto(customer);
	}

	public boolean update(@Valid CustomerDto customerDto) {
		Customer customer = customerRepository.findById(Integer.valueOf(customerDto.getId())).orElse(null);
		if (customer == null) {
			return false;
		} else {
			Customer updated = save(customerDto);
			return updated.getId().toString() == customerDto.getId();
		}
	}

	public boolean delete(Integer id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		if (customer == null) {
			return false;
		}
		customerRepository.delete(customer);
		return true;
	}

	public boolean hasDeliveries(CustomerDeletionDto dto) {
		Integer customerId = Integer.valueOf(dto.getIdToDelete());
        Optional<Customer> customer = customerRepository.findByIdFetchDeliveries(customerId);
        if (customer.isPresent()) {
        	return customer.get().getDeliveries().size() > 0;
        }
		return false;
	}

}
