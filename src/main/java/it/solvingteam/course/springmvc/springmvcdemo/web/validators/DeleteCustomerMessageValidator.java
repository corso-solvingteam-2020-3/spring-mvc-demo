package it.solvingteam.course.springmvc.springmvcdemo.web.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.AddCustomerMessageDTO;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeleteCustomerMessageDto;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;

@Component
public class DeleteCustomerMessageValidator implements Validator{
	@Autowired 
	CustomerService customerService;

	@Override
	public boolean supports(Class<?> clazz) {
		return DeleteCustomerMessageDto.class.isAssignableFrom(clazz);
	
	}

	@Override
	public void validate(Object target, Errors errors) {
		DeleteCustomerMessageDto deleteCustomerMessageDto = (DeleteCustomerMessageDto)target;
		Customer customer = customerService.getCustomerFromDeleteDto(deleteCustomerMessageDto);
		if(customer.getDeliveries().size()>0) {
			errors.rejectValue("idDelete", "deleteModelHasDeliveries","this Customer has deliveries!!!!!");
		}
		
	}
	
	

}
