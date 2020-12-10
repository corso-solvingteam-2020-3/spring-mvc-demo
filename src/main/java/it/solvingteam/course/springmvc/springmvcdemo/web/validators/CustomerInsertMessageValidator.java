package it.solvingteam.course.springmvc.springmvcdemo.web.validators;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.UserSignupMessageDto;
import it.solvingteam.course.springmvc.springmvcdemo.model.User;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;

public class CustomerInsertMessageValidator implements Validator {
	
    @Autowired
    private CustomerService customerService;

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerInsertMessageValidator.class.isAssignableFrom(aClass);
    }

	@Override
	public void validate(Object o, Errors errors) {
		CustomerInsertMessageValidator customerInsertDto = (CustomerInsertMessageValidator) o;
		/*
        if (!userSignupMessageDto.getPassword().equals(userSignupMessageDto.getRepeatePassword())) {
            errors.rejectValue("repeatePassword", "passwordsDoesntMatch", "Password doesn't match");
        }

        Optional<User> user = userService.findUserByUSername(userSignupMessageDto.getUsername());
        if (user.isPresent()) {
            errors.rejectValue("username", "usernameAlreadyExists", "Username already exists");
        }*/
	}

}
