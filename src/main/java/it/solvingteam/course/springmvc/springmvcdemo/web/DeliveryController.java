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
import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDtoForInsert;
import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliverySearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomersSearchFilterDto;
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
    public String insertCustomer(@Valid @ModelAttribute("newDelivery") DeliveryDtoForInsert deliveryDtoForInsert, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
        	 model.addAttribute("allCustomers", customerService.findAll());
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
    
    /*
     * --------------------- VISUALIZZA DELIVERY -----------------------
     * 
     */
    
    @GetMapping("show/{idToShow}")
    public String show(@PathVariable Integer idToShow, Model model) {
    	
    	DeliveryDto deliveryDaVisualizzare = deliveryService.findById(idToShow);
    	
    	model.addAttribute("deliveryToShow", deliveryDaVisualizzare);
    	
		return "/delivery/show";
    	
    }
    
    
    /*
     * ---------------- MODIFICA DI UNA DELIVERY ---------------------
     * 
     */
    
    @GetMapping("prepareEdit/{id}")
    public String prepareEdit(@PathVariable Integer id, Model model) {
    	
    	DeliveryDto deliveryDaAggiornare = deliveryService.findById(id);
    	
    	List<CustomerDto> allCustomers = customerService.findAll();
    	
    	model.addAttribute("deliveryToEdit", deliveryDaAggiornare);
    	
    	model.addAttribute("allCustomers", allCustomers);
    	
		return "delivery/editDelivery";
    	
    }
    
    @PostMapping("editDelivery")
    public String editDelivery(@Valid @ModelAttribute("deliveryToEdit") DeliveryDto deliveryDto, BindingResult bindingResult, Model model){
		
    	if (bindingResult.hasErrors()) {
    		
    		List<CustomerDto> allCustomers = customerService.findAll();
    		model.addAttribute("allCustomers", allCustomers);
    		
            return "delivery/editDelivery";
        } else {
        	deliveryService.update(deliveryDto);
        	return "redirect:/delivery/";
        }
    	
    }
	
}
