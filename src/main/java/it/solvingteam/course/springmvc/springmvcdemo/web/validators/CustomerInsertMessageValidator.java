//package it.solvingteam.course.springmvc.springmvcdemo.web.validators;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.InsertCustomerMessageDto;
//import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;
//
//@Component
//public class CustomerInsertMessageValidator implements Validator {
//	
//	@Autowired
//	private CustomerService customerService;
//	
//	@Override
//	public boolean supports(Class<?> clazz) {
//		return InsertCustomerMessageDto.class.isAssignableFrom(clazz);
//	}
//
//	@Override
//	public void validate(Object target, Errors errors) {
//		InsertCustomerMessageDto insertCustomerMessageDto = (InsertCustomerMessageDto) target;
//
//        if (!insertCustomerMessageDto.getName().isEmpty()) {
//            errors.rejectValue("name", "nameIsEmpty", "Name is required");
//        }
//        
//        if (!insertCustomerMessageDto.getMobile().isEmpty()) {
//            errors.rejectValue("mobile", "mobileIsEmpty", "Mobile is required");
//        }
//        
//        if (!insertCustomerMessageDto.getAddress().isEmpty()) {
//            errors.rejectValue("address", "addressIsEmpty", "Address is required");
//        }
//		
//	}
//
//}
