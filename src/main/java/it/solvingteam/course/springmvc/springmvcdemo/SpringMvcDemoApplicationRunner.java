package it.solvingteam.course.springmvc.springmvcdemo;

import it.solvingteam.course.springmvc.springmvcdemo.model.Role;
import it.solvingteam.course.springmvc.springmvcdemo.service.CustomerService;
import it.solvingteam.course.springmvc.springmvcdemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SpringMvcDemoApplicationRunner implements ApplicationRunner {


    @Autowired
    private RoleService roleService;

    @Autowired
    private CustomerService customerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (roleService.count() == 0) {
            Role role = new Role();
            role.setName("GUEST");

            roleService.save(role);
        }


    }

}
