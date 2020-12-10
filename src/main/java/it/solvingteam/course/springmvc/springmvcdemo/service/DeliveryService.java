package it.solvingteam.course.springmvc.springmvcdemo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliverySearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.CustomersSearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.mapper.CustomerMapper;
import it.solvingteam.course.springmvc.springmvcdemo.mapper.DeliveryMapper;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
import it.solvingteam.course.springmvc.springmvcdemo.model.Delivery;
import it.solvingteam.course.springmvc.springmvcdemo.model.Delivery;
import it.solvingteam.course.springmvc.springmvcdemo.repository.CustomerRepository;
import it.solvingteam.course.springmvc.springmvcdemo.repository.DeliveryRepository;

@Service
public class DeliveryService {
	
	@Autowired
    private DeliveryRepository deliveryRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private DeliveryMapper deliveryMapper;
    
    @Autowired
    private EntityManager entityManager;
    
    
    //insert
    public Delivery insert(DeliveryDto deliveryDto) {
        Delivery delivery = deliveryMapper.convertDtoToEntity(deliveryDto);
        return this.deliveryRepository.save(delivery);
    }
    
    //findById
    public DeliveryDto findById(int id) {
    	DeliveryDto deliveryDto = deliveryMapper.convertEntityToDto(deliveryRepository.findById(id).get());
		return deliveryDto;
    }
    
    // update
    public void update(DeliveryDto deliveryDto) {
    	Delivery delivery = deliveryMapper.convertDtoToEntity(deliveryDto);
    	deliveryRepository.save(delivery);
    }
    
    //delete
    public void delete(int id) {
    	Delivery delivery = deliveryRepository.findById(id).get();
    	deliveryRepository.delete(delivery);
    }
    
    //findByExample
    public List<DeliveryDto> findBySearchParameter(DeliverySearchFilterDto deliverySearchFilterDto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Delivery> cq = cb.createQuery(Delivery.class);

        Root<Delivery> delivery = cq.from(Delivery.class);
        List<Predicate> predicates = new ArrayList<>();

        if (deliverySearchFilterDto.getId() != null && !deliverySearchFilterDto.getId().equals("")) {
            predicates.add(cb.like(delivery.get("id").as(String.class), "%" + deliverySearchFilterDto.getId() + "%"));
        }

        if (deliverySearchFilterDto.getDescription() != null && !deliverySearchFilterDto.getDescription().equals("")) {
            predicates.add(cb.like(delivery.get("description"), "%" + deliverySearchFilterDto.getDescription() + "%"));
        }

        if (deliverySearchFilterDto.getShippingDate() != null && !deliverySearchFilterDto.getShippingDate().equals("")) {
            predicates.add(cb.like(delivery.get("shipping_date"), "%" + deliverySearchFilterDto.getShippingDate() + "%"));
        }

        if (deliverySearchFilterDto.getPrice() != null && !deliverySearchFilterDto.getPrice().equals("")) {
            predicates.add(cb.like(delivery.get("price"), "%" + deliverySearchFilterDto.getPrice() + "%"));
        }
        
        if (deliverySearchFilterDto.getCustomerId() != null && !deliverySearchFilterDto.getCustomerId().equals("")) {
            predicates.add(cb.equal(delivery.get("customer").get("id"), deliverySearchFilterDto.getCustomerId()));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        return deliveryMapper.convertEntityToDto(entityManager.createQuery(cq).getResultList());
    }
    
    

}

