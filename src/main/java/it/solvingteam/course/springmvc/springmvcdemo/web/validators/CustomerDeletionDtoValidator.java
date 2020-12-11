package it.solvingteam.course.springmvc.springmvcdemo.web.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerDeletionDto;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;

@Component
public class CustomerDeletionDtoValidator implements Validator {
	
	@Autowired
    private CustomerService customerService;

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerDeletionDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CustomerDeletionDto dto = (CustomerDeletionDto) o;

        if (customerService.hasDeliveries(dto)) {
            errors.rejectValue("idToDelete", "devileriesPending", "Customer has deliveries pending!");
        }
    }
}
