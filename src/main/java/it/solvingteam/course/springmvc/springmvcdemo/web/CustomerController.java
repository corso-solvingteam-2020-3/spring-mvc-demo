package it.solvingteam.course.springmvc.springmvcdemo.web;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomersSearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@Controller
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public String list(CustomersSearchFilterDto customersSearchFilterDto, Model model) {
		List<CustomerDto> allCustomers = customerService.findBySearchParameter(customersSearchFilterDto);

		model.addAttribute("searchFilters", customersSearchFilterDto);
		model.addAttribute("customers", allCustomers);

		return "customer/list";
	}

	@GetMapping("insert")
	public String insert(CustomerInsertDto customerInsertDto, Model model) {
		model.addAttribute("insertCustomerDto", new CustomerInsertDto());
		return "customer/insert";
	}

	@PostMapping("executeInsert")
	public String executeInsert(@Valid @ModelAttribute("customerInsertModel") CustomerInsertDto customerInsertDto,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "customer/insert";
		} else
			customerService.save(customerInsertDto);
		return "redirect:/customer/";
	}
	
	@GetMapping("show/{id}")
	public String show (@PathVariable Integer id, Model model) {
		Optional<Customer> customer = customerService.getCustomer(id);
		model.addAttribute("customer", customer);
		return "customer/show";
	}
}
