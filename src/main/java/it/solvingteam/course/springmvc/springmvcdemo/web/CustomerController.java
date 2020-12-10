package it.solvingteam.course.springmvc.springmvcdemo.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.AddCustomerMessageDTO;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomersSearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.exceptions.RoleNotFoundException;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;
import it.solvingteam.course.springmvc.springmvcdemo.web.validators.AddCustomerMessageValidator;

@Controller
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private AddCustomerMessageValidator addCustomerMessageValidator;

	@GetMapping
	public String list(CustomersSearchFilterDto customersSearchFilterDto, Model model) {
		List<CustomerDto> allCustomers = customerService.findBySearchParameter(customersSearchFilterDto);

		model.addAttribute("searchFilters", customersSearchFilterDto);
		model.addAttribute("customers", allCustomers);

		return "customer/list";
	}

	@GetMapping("addCustomer")
	public String addCustomer(Model model) {
		model.addAttribute("customerAddModel", new AddCustomerMessageDTO());
		return "customer/addCustomer";
	}

	@PostMapping("addCustomer")
	public String addCustomer(@Valid @ModelAttribute("customerAddModel") AddCustomerMessageDTO addCustomerMessageDto,
			BindingResult bindingResult) throws RoleNotFoundException {
		addCustomerMessageValidator.validate(addCustomerMessageDto, bindingResult);
		if (bindingResult.hasErrors()) {
			return "customer/addCustomer";

		} else {
			customerService.insertCustomer(addCustomerMessageDto);
			return "redirect:/customer";
		}
	}

	@GetMapping("/showCustomer")
	public String showCostumer(@RequestParam("id") Integer id , Model model) {
		if (id != null) {
			model.addAttribute("customer", customerService.convertCustomer(id));
			return "customer/showCustomer";
		} else {
			return "redirect:/customer";
		}
         
	}

	@GetMapping("/updateCustomer")
	public String updateCustomer(@RequestParam("id") Integer id, Model model) {
		if(id != null ) {
			model.addAttribute("customerUpdateModel", customerService.convertCustomer(id));
			return "customer/updateCustomer";
		}
		else {
			return "redirect:/customer";
		}

	}

	@PostMapping("/updateCustomer")
	public String updateCustomer(@Valid @ModelAttribute("customerUpdateModel") CustomerDto customerDto,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "customer/updateCustomer";
		} else {
			customerService.updateCustomer(customerDto);
			return "redirect:/customer";
		}
	}
	
	@GetMapping("/prepareDeleteCustomer")
	public String deleteCustomer(@RequestParam("id") String id,  Model model) {
		if(id != null ) {
	     model.addAttribute("id", id);
			return "customer/deleteCustomer";
		}
		else {
			return "redirect:/customer";
		}

	}

	@GetMapping("/executeDeleteCustomer")
	public String executeDeleteCustomer(@RequestParam("id") String id) {
					
			customerService.deleteCustomer(Integer.parseInt(id));
			return "redirect:/customer";
	}

}
