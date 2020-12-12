package it.solvingteam.course.springmvc.springmvcdemo.web.validators;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerDeletionDto;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;

@Component
public class CustomerDeletionValidator implements Validator {

	@Autowired
	private CustomerService customerService;

	@Override
	public boolean supports(Class<?> clazz) {
		return CustomerDeletionDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CustomerDeletionDto customerDeletionDto = (CustomerDeletionDto) target;
		Optional<Customer> customer = customerService
				.getCustomer(Integer.parseInt(customerDeletionDto.getIdToDelete()));
		if (customer.get().getDelivery().size() > 0) {
			errors.rejectValue("idToDelete", "CustomerDeleteError", "This customer has pending deliveries");
		}
	}
}
