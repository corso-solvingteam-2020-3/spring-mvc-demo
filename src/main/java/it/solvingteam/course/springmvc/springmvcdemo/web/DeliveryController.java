package it.solvingteam.course.springmvc.springmvcdemo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliverySearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;
import it.solvingteam.course.springmvc.springmvcdemo.service.DeliveryService;

@Controller
@RequestMapping("delivery")
public class DeliveryController {
	
	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private CustomerService customerService;
	
	
	/*
     * --------------- RICERCA CON findByExample DI DELIVERY ---------------------
     * 
     */
	
	@GetMapping
	public String list(@ModelAttribute("searchFilters") DeliverySearchFilterDto deliverySearchFilterDto, Model model) {
		
		List<CustomerDto> allCustomers = customerService.findAll();
		List<DeliveryDto> allDeliveries = deliveryService.findBySearchParameter(deliverySearchFilterDto);
		

		model.addAttribute("customers", allCustomers);
		model.addAttribute("paramSearch", deliverySearchFilterDto);
		model.addAttribute("deliveries", allDeliveries);

		return "delivery/list";
	}
	
	/*
     * ---------------------INSERIMENTO NUOVA DELIVERY ---------------------------
     * 
     */
	
	// mi serve per andare alla pagina di inserimento della delivery
    @GetMapping("insert")
    public String insert(Model model) {
        model.addAttribute("insertCustomeR", new CustomerDto());  //insertCustomer va inserito nel form della pagina insert.html in th:object
        return "customer/insert";
    }
	
}
