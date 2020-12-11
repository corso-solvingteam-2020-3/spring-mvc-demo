package it.solvingteam.course.springmvc.springmvcdemo.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerDeletionDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomersSearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.UserSignupMessageDto;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;
import it.solvingteam.course.springmvc.springmvcdemo.web.validators.CustomerDeletionDtoValidator;

@Controller
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private CustomerDeletionDtoValidator customerDeletionDtoValidator;

    @GetMapping
    public String list(@ModelAttribute("searchFilters") CustomersSearchFilterDto customersSearchFilterDto, Model model) {
        List<CustomerDto> allCustomers = customerService.findBySearchParameter(customersSearchFilterDto);

//        model.addAttribute("searchFilters", customersSearchFilterDto);
        model.addAttribute("customers", allCustomers);
        model.addAttribute("customerDeletionModel", new CustomerDeletionDto());

        return "customer/list";
    }
    
    @GetMapping("insert")
    public String goInsert(Model model) {
        model.addAttribute("customerInsertModel", new CustomerInsertDto());
        return "customer/insert";
    }

    @PostMapping("insert")
    public String insert(@Valid @ModelAttribute("customerInsertModel") CustomerInsertDto customerInsertDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "customer/insert";
        } else {
            customerService.save(customerInsertDto);
            return "redirect:/customer/";
        }
    }
    
    @GetMapping("{id}/show")
    public String show(@PathVariable Integer id, Model model) {
    	CustomerDto dto = customerService.show(id);
        model.addAttribute("customerModel", dto);
        return "customer/show";
    }
    
    @GetMapping("{id}/update")
    public String goUpdate(@PathVariable Integer id, Model model) {
    	CustomerDto dto = customerService.show(id);
        model.addAttribute("customerModel", dto);
        return "customer/update";
    }

    @PostMapping("{id}/update")
    public String update(@Valid @ModelAttribute("customerModel") CustomerDto customerDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "customer/update";
        } else {
        	customerService.update(customerDto);
            return "redirect:/customer/";
        }
    }
    
    @GetMapping("{idToDelete}/delete")
    public String goDelete(@ModelAttribute("searchFilters") CustomersSearchFilterDto customersSearchFilterDto, @Valid @ModelAttribute("customerDeletionModel") CustomerDeletionDto customerDeletionDto, Model model, BindingResult bindingResult) {
    	
    	customerDeletionDtoValidator.validate(customerDeletionDto, bindingResult);
    	
    	if (bindingResult.hasErrors()) {
    		List<CustomerDto> allCustomers = customerService.findBySearchParameter(customersSearchFilterDto);
    		model.addAttribute("customers", allCustomers);
            return "customer/list";
        } else {
        	CustomerDto dto = customerService.show(Integer.valueOf(customerDeletionDto.getIdToDelete()));
        	model.addAttribute("customerModel", dto);
    		return "customer/delete";
        }   
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Integer id, Model model) {

    	if (customerService.delete(id)) {
    		return "redirect:/customer/";
        } else {
        	return "customer/{id}/delete";
        }
    }

}
