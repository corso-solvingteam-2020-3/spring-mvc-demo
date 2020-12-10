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

import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.AddDeliveryMessageDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliverySearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.exceptions.RoleNotFoundException;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;
import it.solvingteam.course.springmvc.springmvcdemo.service.DeliveryService;

@Controller
@RequestMapping("delivery")
public class DeliveryController {
	@Autowired
	private DeliveryService deliveryService;
	@Autowired CustomerService customerService;
	
	@GetMapping
	public String list(DeliverySearchFilterDto deliverySearchFilterDto, Model model) {
		List<DeliveryDto> allDelivery = deliveryService.findBySearchParameter(deliverySearchFilterDto);

		model.addAttribute("searchFilters", deliverySearchFilterDto);
		model.addAttribute("delivery", allDelivery);
        model.addAttribute("customers", customerService.findAll());
		return "delivery/listDelivery";
	}

	@GetMapping("/showDelivery")
	public String showDelivery(@RequestParam("id") Integer id , Model model) {
		if (id != null) {
			model.addAttribute("delivery", deliveryService.convertDelivery(id));
			return "delivery/showDelivery";
		} else {
			return "redirect:/delivery";
		}
         
	}
	
	@GetMapping("/prepareDeleteDelivery")
	public String deleteDelivery(@RequestParam("id") String id,  Model model) {
		if(id != null ) {
	     model.addAttribute("id", id);
			return "delivery/deleteDelivery";
		}
		else {
			return "redirect:/delivery";
		}

	}
	
	@GetMapping("/executeDeleteDelivery")
	public String executeDeleteDelivery(@RequestParam("id") String id) {
					
			deliveryService.deleteDelivery(Integer.parseInt(id));
			return "redirect:/delivery";
	}
	
	@GetMapping("/updateDelivery")
	public String updateDelivery(@RequestParam("id") Integer id, Model model) {
		if(id != null ) {
			model.addAttribute("deliveryUpdateModel", deliveryService.convertDelivery(id));
			model.addAttribute("customers", customerService.findAll());
			return "delivery/updateDelivery";
		}
		else {
			return "redirect:/delivery";
		}

	}
	@PostMapping("/updateDelivery")
	public String updateDelivery(@Valid @ModelAttribute("deliveryUpdateModel") DeliveryDto deliveryDto,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("customers", customerService.findAll());
			return "delivery/updateDelivery";
		} else {
			deliveryService.updateDelivery(deliveryDto);
			return "redirect:/delivery";
		}
	}
	
	@GetMapping("addDelivery")
	public String addDelivery(Model model) {
		model.addAttribute("deliveryAddModel", new AddDeliveryMessageDto());
		model.addAttribute("customers", customerService.findAll());
		return "delivery/addDelivery";
	}

	@PostMapping("addDelivery")
	public String addDelivery(@Valid @ModelAttribute("deliveryAddModel") AddDeliveryMessageDto addDeliveryMessageDto,
			BindingResult bindingResult,Model model) throws RoleNotFoundException {
		if (bindingResult.hasErrors()) {
			model.addAttribute("customers", customerService.findAll());
			return "delivery/addDelivery";

		} else {
			deliveryService.insertDelivery(addDeliveryMessageDto);
			return "redirect:/delivery";
		}
	}
}
