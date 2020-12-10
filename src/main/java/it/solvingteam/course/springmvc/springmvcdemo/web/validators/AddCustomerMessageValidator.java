package it.solvingteam.course.springmvc.springmvcdemo.web.validators;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.AddCustomerMessageDTO;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;

@Component
public class AddCustomerMessageValidator  implements Validator {
	
	 @Autowired
	 private CustomerService customerService;
	 
	@Override
	public boolean supports(Class<?> clazz) {
		return AddCustomerMessageDTO.class.isAssignableFrom(clazz);
	
	}

	@Override
	public void validate(Object target, Errors errors) {
		AddCustomerMessageDTO addCustomerMessageDto = (AddCustomerMessageDTO) target;
		  Optional<Customer> customer = customerService.findCustomerByName(addCustomerMessageDto.getName());
	 if(customer.isPresent()) {
		  errors.rejectValue("name", "nameAlreadyExists", "name already exists");
	      }
		  
	}
	  
	

}
