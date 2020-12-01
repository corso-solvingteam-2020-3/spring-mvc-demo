package it.solvingteam.course.springmvc.springmvcdemo.web;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String list(Model model) {
        List<CustomerDto> allCustomers = customerService.findAll();
        model.addAttribute("customers", allCustomers);

        return "customer/list";
    }

}
