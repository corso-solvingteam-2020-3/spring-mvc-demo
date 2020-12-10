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
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomerInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomersSearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliveriesSearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliveryInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;
import it.solvingteam.course.springmvc.springmvcdemo.service.DeliveryService;

@Controller
@RequestMapping("delivery")
public class DeliveryController {
	
	@Autowired
    private DeliveryService deliveryService;
	
	@Autowired
    private CustomerService customerService;
	
	@PostMapping("insert")
    public String insert(@Valid @ModelAttribute("deliveryInsertModel") DeliveryInsertDto deliveryinsertDto, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
        	List<CustomerDto> allcustomers = customerService.findBySearchParameter(new CustomersSearchFilterDto());
        	model.addAttribute("customers",allcustomers);
            return "delivery/insert";
        } else {
        	deliveryService.insert(deliveryinsertDto);
            return "redirect:/delivery/";
        }
    }
    
    @GetMapping("insert")
    public String insert(Model model) {
    	List<CustomerDto> allcustomers = customerService.findBySearchParameter(new CustomersSearchFilterDto());
    	model.addAttribute("customers",allcustomers);
        model.addAttribute("deliveryInsertModel", new DeliveryInsertDto());
        return "delivery/insert";
    }
    
    @GetMapping("show/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
    	DeliveryInsertDto delivery = deliveryService.getById(id);
    	CustomerInsertDto c=customerService.getById(Integer.parseInt(delivery.getCustomerId()));
    	model.addAttribute("customer_name",c.getName());
        model.addAttribute("delivery",delivery);
        return "delivery/show";
    }
    
    @GetMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
    	DeliveryInsertDto delivery = deliveryService.getById(id);
    	List<CustomerDto> allcustomers = customerService.findBySearchParameter(new CustomersSearchFilterDto());
    	model.addAttribute("customers",allcustomers);
        model.addAttribute("deliveryUpdateModel",delivery);
        return "delivery/update";
    }
    
    @PostMapping("update/{id}")
    public String update(@Valid @ModelAttribute("deliveryUpdateModel") DeliveryInsertDto deliveryupdateDto, BindingResult bindingResult, @PathVariable("id") Integer id,
    		Model model){
        if (bindingResult.hasErrors()) {
        	List<CustomerDto> allcustomers = customerService.findBySearchParameter(new CustomersSearchFilterDto());
        	model.addAttribute("customers",allcustomers);
        	model.addAttribute("deliveryUpdateModel",deliveryupdateDto);
            return "delivery/update";
        } else {
        	deliveryupdateDto.setId(id.toString());
        	deliveryService.insert(deliveryupdateDto);
            return "redirect:/delivery/";
        }
    }
    
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
    	DeliveryInsertDto delivery = deliveryService.getById(id);
        model.addAttribute("deliveryDeleteModel",delivery);
        return "delivery/delete";
    }
    
    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id){
    	DeliveryInsertDto delivery = deliveryService.getById(id);
    	deliveryService.delete(delivery);
        return "redirect:/delivery/";
    }

    @GetMapping
    public String list(DeliveriesSearchFilterDto deliverysSearchFilterDto, Model model) {
        List<DeliveryDto> allDeliveries = deliveryService.findBySearchParameter(deliverysSearchFilterDto);

        List<CustomerDto> allcustomers = customerService.findBySearchParameter(new CustomersSearchFilterDto());
    	model.addAttribute("customers",allcustomers);
        model.addAttribute("searchFilters", deliverysSearchFilterDto);
        model.addAttribute("deliveries", allDeliveries);
        return "delivery/list";
    }

}
