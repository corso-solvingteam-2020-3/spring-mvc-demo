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

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDtoForInsert;
import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliverySearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.exceptions.RoleNotFoundException;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
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
        model.addAttribute("newDelivery", new DeliveryDtoForInsert());
        model.addAttribute("allCustomers", customerService.findAll());
        return "delivery/insertDelivery";
    }
    
    @PostMapping("insertDelivery")
    public String insertCustomer(@Valid @ModelAttribute("newDelivery") DeliveryDtoForInsert deliveryDtoForInsert, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "delivery/insertDelivery";
        } else {
        	DeliveryDto deliveryDaInserire = new DeliveryDto();
        	deliveryDaInserire.setDescription(deliveryDtoForInsert.getDescription());
        	deliveryDaInserire.setShippingDate(deliveryDtoForInsert.getShippingDate());
        	deliveryDaInserire.setPrice(deliveryDtoForInsert.getPrice());
        	
        	Integer customerId = Integer.parseInt(deliveryDtoForInsert.getCustomerId());
        	
        	CustomerDto customerDto = customerService.findById(customerId);
        	
        	deliveryDaInserire.setCustomer(customerDto);
        	
        	deliveryService.insert(deliveryDaInserire);
            return "redirect:/delivery/";
        }
    }
	
}
