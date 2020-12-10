package it.solvingteam.course.springmvc.springmvcdemo.web;

import java.util.List;
import java.util.Optional;

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


import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliveryInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliverySearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
import it.solvingteam.course.springmvc.springmvcdemo.model.Delivery;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;
import it.solvingteam.course.springmvc.springmvcdemo.service.DeliveryService;

@Controller
@RequestMapping("delivery")
public class DeliveryController {
	
	@Autowired
	private DeliveryService deliveryService;
	
	@Autowired
	private CustomerService customerService;


	@GetMapping
	public String list(DeliverySearchFilterDto deliverySearchFilterDto, Model model) {
		List<DeliveryDto> allDeliveries = deliveryService.findBySearchParameter(deliverySearchFilterDto);

		model.addAttribute("searchFilters", deliverySearchFilterDto);
		model.addAttribute("deliveries", allDeliveries);
		model.addAttribute("customers", customerService.findAll());

		return "delivery/list";
	}
	
	@GetMapping("insert")
	public String insert( Model model) {
		model.addAttribute("deliveryInsertDto", new DeliveryInsertDto());
		model.addAttribute("customers", customerService.findAll());
		return "delivery/insert";
	}

	@PostMapping("executeInsert")
	public String executeInsert(@Valid @ModelAttribute("deliveryInsertDto") DeliveryInsertDto deliveryInsertDto,
			BindingResult bindingResult,  Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("customers", customerService.findAll());
			return "delivery/insert";
		} else
			deliveryService.save(deliveryInsertDto);
		return "redirect:/delivery/";
	}
	
	@GetMapping("show/{id}")
	public String show (@PathVariable Integer id, Model model) {
		Optional<Delivery> delivery = deliveryService.getDelivery(id);
		model.addAttribute("delivery", delivery.get());
		return "delivery/show";
	}


}
