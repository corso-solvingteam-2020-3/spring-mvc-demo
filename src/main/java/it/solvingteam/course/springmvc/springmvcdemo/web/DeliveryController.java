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
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliveryInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliverySearchFilterDto;
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
        List<DeliveryDto> deliveries = deliveryService.findBySearchParameter(deliverySearchFilterDto);

        model.addAttribute("searchFilters", deliverySearchFilterDto);
        model.addAttribute("deliveries", deliveries);
        model.addAttribute("customers", customerService.findAll());

        return "delivery/list";
    }
    
    @GetMapping("insert")
    public String goInsert(Model model) {
        model.addAttribute("deliveryInsertModel", new DeliveryInsertDto());
        model.addAttribute("customers", customerService.findAll());
        return "delivery/insert";
    }

    @PostMapping("insert")
    public String insert(@Valid @ModelAttribute("deliveryInsertModel") DeliveryInsertDto deliveryInsertDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
        	model.addAttribute("customers", customerService.findAll());
            return "delivery/insert";
        } else {
            deliveryService.save(deliveryInsertDto);
            return "redirect:/delivery/";
        }
    }
    
    @GetMapping("{id}/show")
    public String show(@PathVariable Integer id, Model model) {
    	DeliveryDto dto = deliveryService.show(id);
        model.addAttribute("deliveryModel", dto);
        return "delivery/show";
    }
    
    @GetMapping("{id}/update")
    public String goUpdate(@PathVariable Integer id, Model model) {
    	DeliveryDto dto = deliveryService.show(id);
        model.addAttribute("deliveryModel", dto);
        model.addAttribute("customers", customerService.findAll());
        return "delivery/update";
    }

    @PostMapping("{id}/update")
    public String update(@Valid @ModelAttribute("deliveryModel") DeliveryDto deliveryDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
        	model.addAttribute("customers", customerService.findAll());
            return "delivery/update";
        } else {
        	deliveryService.update(deliveryDto);
            return "redirect:/delivery/";
        }
    }
    
    @GetMapping("{id}/delete")
    public String goDelete(@PathVariable Integer id, Model model) {
    	DeliveryDto dto = deliveryService.show(id);
        model.addAttribute("deliveryModel", dto);
        return "delivery/delete";
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Integer id, Model model) {

    	if (deliveryService.delete(id)) {
    		return "redirect:/delivery/";
        } else {
        	return "delivery/{id}/delete";
        }
    }

}
