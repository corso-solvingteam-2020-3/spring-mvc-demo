package it.solvingteam.course.springmvc.springmvcdemo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliveriesSearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliveryInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.mapper.DeliveryMapper;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
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
	
    public List<DeliveryDto> findAll() {
        List<Delivery> allDeliveries = this.deliveryRepository.findAll();
        return deliveryMapper.convertEntityToDto(allDeliveries);
    }
    
    public List<DeliveryDto> findBySearchParameter(DeliveriesSearchFilterDto deliveriesSearchFilterDto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Delivery> cq = cb.createQuery(Delivery.class);

        Root<Delivery> delivery = cq.from(Delivery.class);
        List<Predicate> predicates = new ArrayList<>();

        if (deliveriesSearchFilterDto.getId() != null && !deliveriesSearchFilterDto.getId().equals("")) {
            predicates.add(cb.like(delivery.get("id").as(String.class), "%" + deliveriesSearchFilterDto.getId() + "%"));
        }

        if (deliveriesSearchFilterDto.getDescription() != null && !deliveriesSearchFilterDto.getDescription().equals("")) {
            predicates.add(cb.like(delivery.get("description"), "%" + deliveriesSearchFilterDto.getDescription() + "%"));
        }

        if (deliveriesSearchFilterDto.getShippingDate() != null && !deliveriesSearchFilterDto.getShippingDate().equals("")) {
            predicates.add(cb.equal(delivery.get("shippingDate"), LocalDate.parse(deliveriesSearchFilterDto.getShippingDate()) ));
        }

        if (deliveriesSearchFilterDto.getMinPrice() != null && !deliveriesSearchFilterDto.getMinPrice().equals("")) {
            predicates.add(cb.greaterThanOrEqualTo(delivery.get("price"), deliveriesSearchFilterDto.getMinPrice() ));
        }
        
        if (deliveriesSearchFilterDto.getMaxPrice() != null && !deliveriesSearchFilterDto.getMaxPrice().equals("")) {
            predicates.add(cb.lessThanOrEqualTo(delivery.get("price"), deliveriesSearchFilterDto.getMaxPrice() ));
        }
        
        if (deliveriesSearchFilterDto.getCustomerId() != null && !deliveriesSearchFilterDto.getCustomerId().equals("")) {
            predicates.add(cb.equal(delivery.get("customer").get("id"), deliveriesSearchFilterDto.getCustomerId() ));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        return deliveryMapper.convertEntityToDto(entityManager.createQuery(cq).getResultList());
    }
    
    public Delivery insert(DeliveryInsertDto deliveryinsertDto) {
    	String id= deliveryinsertDto.getId();
    	String description= deliveryinsertDto.getDescription();
    	String shippingDate= deliveryinsertDto.getShippingDate();
    	String price= deliveryinsertDto.getPrice();
    	String customerId= deliveryinsertDto.getCustomerId();
    	Customer c= customerRepository.findById(Integer.parseInt(customerId)).orElse(null);
    	
    	Delivery delivery = new Delivery();
    	if(id!=null) {
    		delivery.setId(Integer.parseInt(id));
    	}
    	delivery.setDescription(description);
    	delivery.setShippingDate(LocalDate.parse(shippingDate));
    	delivery.setPrice(Double.parseDouble(price));
    	delivery.setCustomer(c);
        return this.deliveryRepository.save(delivery);
    }
    
    public void delete(DeliveryInsertDto deliveryinsertDto) {
    	String id= deliveryinsertDto.getId();
    	Delivery delivery=deliveryRepository.findById(Integer.parseInt(id)).orElse(null);
        this.deliveryRepository.delete(delivery);
    }
    
    public long count() {
        return this.deliveryRepository.count();
    }

    public DeliveryInsertDto getById(Integer id) {
    	Delivery d=this.deliveryRepository.findById(id).orElse(null);
    	DeliveryInsertDto ddto=new DeliveryInsertDto();
    	ddto.setId(d.getId().toString());
    	ddto.setDescription(d.getDescription());
    	ddto.setShippingDate(d.getShippingDate().toString());
    	ddto.setPrice(d.getPrice().toString());
    	ddto.setCustomerId(d.getCustomer().getId().toString());
    	return d==null?null:ddto;
    }
}
