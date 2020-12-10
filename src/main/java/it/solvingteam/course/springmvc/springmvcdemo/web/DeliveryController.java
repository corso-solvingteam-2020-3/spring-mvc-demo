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

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliveryInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliverySearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.model.Delivery;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;
import it.solvingteam.course.springmvc.springmvcdemo.service.DeliveryService;
import it.solvingteam.course.springmvc.springmvcdemo.web.validators.CustomerMessageValidator;
import it.solvingteam.course.springmvc.springmvcdemo.web.validators.UserSignupMessageValidator;

@Controller
@RequestMapping("delivery")
public class DeliveryController {

	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerMessageValidator customerMessageValidator;

	@GetMapping
	public String list(DeliverySearchFilterDto deliverySearchFilterDto, Model model) {
		List<DeliveryDto> allDeliveries = deliveryService.findBySearchParameter(deliverySearchFilterDto);

		model.addAttribute("searchFilters", deliverySearchFilterDto);
		model.addAttribute("deliveries", allDeliveries);
		model.addAttribute("customers", customerService.findAll());

		return "delivery/list";
	}

	@GetMapping("insert")
	public String insert(Model model) {
		model.addAttribute("deliveryInsertDto", new DeliveryInsertDto());
		model.addAttribute("customers", customerService.findAll());
		return "delivery/insert";
	}

	@PostMapping("executeInsert")
	public String executeInsert(@Valid @ModelAttribute("deliveryInsertDto") DeliveryInsertDto deliveryInsertDto,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("customers", customerService.findAll());
			return "delivery/insert";
		} else
			deliveryService.save(deliveryInsertDto);
		return "redirect:/delivery/";
	}

	@GetMapping("update/{id}")
	public String update(@PathVariable Integer id, Model model) {
		DeliveryDto deliveryDto = deliveryService.getDeliveryById(id);
		model.addAttribute("deliveryDto", deliveryDto);
		model.addAttribute("customers", customerService.findAll());
		return "delivery/update";
	}

	@PostMapping("executeUpdate/{id}")
	public String executeUpdate(@Valid @ModelAttribute("deliveryDto") DeliveryDto deliveryDto,
			BindingResult bindingResult, Model model) {

		customerMessageValidator.validate(deliveryDto.getCustomerDto(), bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("customers", customerService.findAll());
			return "delivery/update";
		} else
			deliveryService.save(deliveryDto);
		return "redirect:/delivery/";
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable Integer id, Model model) {
		DeliveryDto deliveryDto = deliveryService.getDeliveryById(id);
		model.addAttribute("deliveryDto", deliveryDto);
		return "delivery/delete";
	}

	@GetMapping("executeDelete/{id}")
	public String executedDelete(@PathVariable Integer id, Model model) {
		deliveryService.delete(id);
		return "redirect:/delivery/";
	}

	@GetMapping("show/{id}")
	public String show(@PathVariable Integer id, Model model) {
		Optional<Delivery> delivery = deliveryService.getDelivery(id);
		model.addAttribute("delivery", delivery.get());
		return "delivery/show";
	}

}
