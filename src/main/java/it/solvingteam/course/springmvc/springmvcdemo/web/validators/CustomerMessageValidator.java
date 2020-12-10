package it.solvingteam.course.springmvc.springmvcdemo.web.validators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;

@Component
public class CustomerMessageValidator implements Validator {
	
	

    
    @Override
    public void validate(Object o, Errors errors) {
        CustomerDto customerDto = (CustomerDto) o;

        if (customerDto.getId()==null || customerDto.getId().equals("")) {
            errors.rejectValue("customerDto", "customerDto", "Required field");
        }
    }

	@Override
	public boolean supports(Class<?> clazz) {
		return CustomerDto.class.isAssignableFrom(clazz);
	}

}
