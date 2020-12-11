package it.solvingteam.course.springmvc.springmvcdemo.web;

import java.time.format.DateTimeParseException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

//    private final static Logger logger = LoggerFactory.getLogger(ControllerAdviceExceptionHandler.class);
    
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String exceptionHandler0(DataIntegrityViolationException e) {
        return "redirect:/";
    }
    
    @ExceptionHandler(DateTimeParseException.class)
    public String exceptionHandler1(DateTimeParseException e) {
    	return "redirect:/";
    }
      
    @ExceptionHandler(NumberFormatException.class)
    public String exceptionHandler2(NumberFormatException e) {
    	return "redirect:/";
    }

}
