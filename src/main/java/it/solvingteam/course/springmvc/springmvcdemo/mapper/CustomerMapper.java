package it.solvingteam.course.springmvc.springmvcdemo.mapper;

import org.springframework.stereotype.Component;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;

@Component
public class CustomerMapper extends AbstractMapper<Customer, CustomerDto> {
    @Override
    public CustomerDto convertEntityToDto(Customer entity) {
        if (entity == null) {
            return null;
        }

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(String.valueOf(entity.getId()));
        customerDto.setName(entity.getName());
        customerDto.setAddress(entity.getAddress());
        customerDto.setMobile(entity.getMobile());
        customerDto.setDeliveriesCount(String.valueOf(entity.getDeliveries().size()));
        return customerDto;
    }

    @Override
    public Customer convertDtoToEntity(CustomerDto dto) {
        if (dto == null) {
            return null;
        }

        Customer customer = new Customer();

        if (dto.getId() != null) {
            customer.setId(Integer.valueOf(dto.getId()));
        }

        customer.setName(dto.getName());
        customer.setAddress(dto.getAddress());
        customer.setMobile(dto.getMobile());

        return customer;
    }
    
    public CustomerDto convertToCustomerDto(CustomerInsertDto customerInsertDto) {
    	CustomerDto customerDto = new CustomerDto();
		customerDto.setName(customerInsertDto.getName());
		customerDto.setAddress(customerInsertDto.getAddress());
		customerDto.setMobile(customerInsertDto.getMobile());
		return customerDto;
    }
}
