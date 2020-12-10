package it.solvingteam.course.springmvc.springmvcdemo.web;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomersSearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.UserSigninMessageDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.UserSignupMessageDto;
import it.solvingteam.course.springmvc.springmvcdemo.exceptions.RoleNotFoundException;
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

import javax.validation.Valid;

@Controller
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /*
     * --------------- RICERCA CON findByExample DI CUSTOMER ---------------------
     * 
     */
    
    @GetMapping
    public String list(@ModelAttribute ("searchFilters") CustomersSearchFilterDto customersSearchFilterDto, Model model) {
        List<CustomerDto> allCustomers = customerService.findBySearchParameter(customersSearchFilterDto);

        // model.addAttribute("searchFilters", customersSearchFilterDto);
        model.addAttribute("paramSearch", customersSearchFilterDto);
        model.addAttribute("customers", allCustomers);

        return "customer/list";
    }
    
    /*
     * ---------------------INSERIMENTO NUOVO CUSTOMER ---------------------------
     * 
     */
    
    // mi serve per andare alla pagina di inserimento del customer
    @GetMapping("insert")
    public String insert(Model model) {
        model.addAttribute("insertCustomeR", new CustomerDto());  //insertCustomer va inserito nel form della pagina insert.html in th:object
        return "customer/insert";
    }
    
    @PostMapping("insertCustomer")
    public String insertCustomer(@Valid @ModelAttribute("insertCustomeR") CustomerInsertDto customerInsertDto, BindingResult bindingResult) throws RoleNotFoundException {

        if (bindingResult.hasErrors()) {
            return "customer/insert";
        } else {
        	CustomerDto customerDaInserire = new CustomerDto();
        	customerDaInserire.setName(customerInsertDto.getName());
        	customerDaInserire.setMobile(customerInsertDto.getMobile());
        	customerDaInserire.setAddress(customerInsertDto.getAddress());
            customerService.save(customerDaInserire);
            return "redirect:/customer/";
        }
    }
    
    /*
     * --------------------- VISUALIZZA CUSTOMER -----------------------
     * 
     */
    
    @GetMapping("show/{idToShow}")
    public String show(@PathVariable Integer idToShow, @ModelAttribute ("searchFilters") CustomersSearchFilterDto customersSearchFilterDto, Model model) {
    	
    	System.out.println(customersSearchFilterDto);
    	
    	CustomerDto customerDaVisualizzare = customerService.findById(idToShow);
    	
    	model.addAttribute("customerToShow", customerDaVisualizzare);
    	
		return "/customer/show";
    	
    }
    
    /*
     * ---------------- MODIFICA DI UN CUSTOMER ---------------------
     * 
     */
    
    @GetMapping("prepareEdit/{id}")
    public String prepareEdit(@PathVariable Integer id, Model model) {
    	
    	CustomerDto customerDaAggiornare = customerService.findById(id);
    	
    	model.addAttribute("customerToEdit", customerDaAggiornare);
    	
		return "customer/edit";
    	
    }
    
    @PostMapping("editCustomer")
    public String editCustomer(@Valid @ModelAttribute("customerToEdit") CustomerDto customerDto, BindingResult bindingResult) throws RoleNotFoundException{
		
    	if (bindingResult.hasErrors()) {
            return "customer/edit";
        } else {
        	customerService.update(customerDto);
        	return "redirect:/customer/";
        }
    	
    }
    
    /*
     * -------------------- ELIMINAZIONE DI UN CUSTOMER --------------------------
     * 
     */
    
    @GetMapping("prepareDelete/{id}")
    public String prepareDelete(@PathVariable Integer id, Model model) {
    	
		CustomerDto customerDaEliminare = new CustomerDto();
		
		customerDaEliminare.setId(Integer.toString(id));

		model.addAttribute("customerToDelete", customerDaEliminare);

		return "customer/delete";
    	
    }
    
    @GetMapping("delete/{id}")
    public String DeleteCustomer(@PathVariable Integer id, Model model) {
    	
		customerService.delete(id);

		return "redirect:/customer/";
    	
    }

}
