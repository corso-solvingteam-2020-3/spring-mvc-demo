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
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomersSearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;

@Controller
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    
    @PostMapping("insert")
    public String insert(@Valid @ModelAttribute("customerInsertModel") CustomerInsertDto customerinsertDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "customer/insert";
        } else {
        	customerService.insert(customerinsertDto);
            return "redirect:/customer/";
        }
    }
    
    @GetMapping("insert")
    public String insert(Model model) {
        model.addAttribute("customerInsertModel", new CustomerInsertDto());
        return "customer/insert";
    }
    
    @GetMapping("show/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
    	CustomerInsertDto customer = customerService.getById(id);
        model.addAttribute("customer",customer);
        return "customer/show";
    }
    
    @GetMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
    	CustomerInsertDto customer = customerService.getById(id);
        model.addAttribute("customerUpdateModel",customer);
        return "customer/update";
    }
    
    @PostMapping("update/{id}")
    public String update(@Valid @ModelAttribute("customerUpdateModel") CustomerInsertDto customerupdateDto, BindingResult bindingResult, @PathVariable("id") Integer id,
    		Model model){
        if (bindingResult.hasErrors()) {
        	model.addAttribute("customerUpdateModel",customerupdateDto);
            return "customer/update";
        } else {
        	customerupdateDto.setId(id.toString());
        	customerService.insert(customerupdateDto);
            return "redirect:/customer/";
        }
    }
    
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
    	CustomerInsertDto customer = customerService.getById(id);
        model.addAttribute("customerDeleteModel",customer);
        return "customer/delete";
    }
    
    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id){
    	CustomerInsertDto customer = customerService.getById(id);
    	customerService.delete(customer);
        return "redirect:/customer/";
    }

    @GetMapping
    public String list(CustomersSearchFilterDto customersSearchFilterDto, Model model) {
        List<CustomerDto> allCustomers = customerService.findBySearchParameter(customersSearchFilterDto);

        model.addAttribute("searchFilters", customersSearchFilterDto);
        model.addAttribute("customers", allCustomers);
        return "customer/list";
    }
    

}
