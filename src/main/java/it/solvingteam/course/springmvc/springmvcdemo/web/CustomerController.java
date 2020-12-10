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
import org.springframework.web.bind.annotation.RequestParam;

import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerInsertMessageDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomersSearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;

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
	public String insert(Model model) {
		model.addAttribute("customerInsertModel", new CustomerInsertMessageDto());
		return "customer/insert";
	}

	@PostMapping("executeInsert")
	public String executeInsert(
			@Valid @ModelAttribute("customerInsertModel") CustomerInsertMessageDto insertCustomerMessageDto,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "customer/insert";
		} else {
			customerService.insert(insertCustomerMessageDto);
			return "redirect:/customer";
		}
	}

	@GetMapping("show/{id}")
	public String show(@PathVariable Integer id, Model model) {
		if (id != null) {
			CustomerDto customerDtoShow = customerService.customerEntityToCustomerDto(id);
			model.addAttribute("customerShow", customerDtoShow);
		} else {
			return "redirect:/customer";
		}
		return "customer/show";
	}

	@GetMapping("update/{id}")
	public String update(@PathVariable Integer id,
			@Valid @ModelAttribute("customerUpdateModel") CustomerDto customerDto, BindingResult bindingResult,
			Model model) {

		if (id != null || !bindingResult.hasErrors()) {
			CustomerDto customerDtoUpdate = customerService.customerEntityToCustomerDto(id);
			model.addAttribute("customerUpdateModel", customerDtoUpdate);
			return "customer/update";

		} else {
			return "redirect:/customer";
		}
	}

	@PostMapping("executeUpdate")
	public String executeUpdate(@Valid @ModelAttribute("customerUpdateModel") CustomerDto customerDto,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "customer/update";
		} else {
			customerService.update(customerDto);
			return "redirect:/customer";
		}
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable Integer id, Model model) {

		if (id != null) {
			model.addAttribute("idCustomerDelete", id);
			return "customer/delete";

		} else {
			return "redirect:/customer";
		}
	}

	@GetMapping("executeDelete")
	public String executeDelete(@RequestParam("idCustomerDelete") Integer id) {
		customerService.delete(id);
		return "redirect:/customer";
	}
}
