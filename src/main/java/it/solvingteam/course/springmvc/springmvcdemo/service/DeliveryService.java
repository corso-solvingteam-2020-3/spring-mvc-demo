package it.solvingteam.course.springmvc.springmvcdemo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solvingteam.course.springmvc.springmvcdemo.dto.CustomerDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.AddDeliveryMessageDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliverySearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.exceptions.RoleNotFoundException;
import it.solvingteam.course.springmvc.springmvcdemo.mapper.DeliveryMapper;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
import it.solvingteam.course.springmvc.springmvcdemo.model.Delivery;
import it.solvingteam.course.springmvc.springmvcdemo.repository.DeliveryRepository;
@Service
public class DeliveryService {

	@Autowired
	private DeliveryRepository deliveryRepository;

	@Autowired
	private DeliveryMapper deliveryMapper;

	@Autowired
	private EntityManager entityManager;
	
	
	public List<DeliveryDto> findAll() {
		List<Delivery> allDelivery = this.deliveryRepository.findAll();
		return deliveryMapper.convertEntityToDto(allDelivery);
	}
	public Optional<Delivery> findById(Integer id) {
		return this.deliveryRepository.findById(id);
	}
	
	public List<DeliveryDto> findBySearchParameter(DeliverySearchFilterDto deliverySearchFilterDto) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Delivery> cq = cb.createQuery(Delivery.class);

		Root<Delivery> delivery = cq.from(Delivery.class);
		List<Predicate> predicates = new ArrayList<>();
		
		
		if (deliverySearchFilterDto.getDescription() != null && !deliverySearchFilterDto.getDescription().equals("")) {
			predicates.add(cb.like(delivery.get("description"), "%" + deliverySearchFilterDto.getDescription() + "%"));
		}

		if (deliverySearchFilterDto.getShippingDate() != null && !deliverySearchFilterDto.getShippingDate().equals("")) {
			predicates.add(cb.equal(delivery.get("shippingDate"),LocalDate.parse(deliverySearchFilterDto.getShippingDate())));
		}

		if (deliverySearchFilterDto.getPrice() != null && !deliverySearchFilterDto.getPrice().equals("")) {
			predicates.add(cb.lessThanOrEqualTo(delivery.get("price"), deliverySearchFilterDto.getPrice()));
		}
		if (deliverySearchFilterDto.getPrice() != null && !deliverySearchFilterDto.getPrice().equals("")) {
			predicates.add(cb.greaterThanOrEqualTo(delivery.get("price"), deliverySearchFilterDto.getPrice()));
		}
		if(deliverySearchFilterDto.getCustomer() != null && !deliverySearchFilterDto.getCustomer().equals("") ) {
			Customer customer = new Customer();
			customer.setId(Integer.parseInt(deliverySearchFilterDto.getCustomer()));
			  predicates.add(cb.equal(delivery.get("customer"),customer));
		}

		cq.where(predicates.toArray(new Predicate[0]));
		return deliveryMapper.convertEntityToDto(entityManager.createQuery(cq).getResultList());
	}
	
	public DeliveryDto convertDelivery(Integer id) {
		Delivery delivery = this.findById(id).orElse(null);
		return deliveryMapper.convertEntityToDto(delivery);
	}

	public void deleteDelivery(Integer id) {
		Delivery delivery = deliveryRepository.findById(id).orElse(null);
		this.deliveryRepository.delete(delivery);
	}
	
	public void updateDelivery(DeliveryDto deliveryDto) {
		Delivery delivery= deliveryMapper.convertDtoToEntity(deliveryDto);
		this.deliveryRepository.save(delivery);

	}
	
	public void insertDelivery(AddDeliveryMessageDto addDeliveryMessageDto) throws RoleNotFoundException {
		this.deliveryRepository.save(deliveryMapper.convertDtoToEntity(addDeliveryMessageDto));
	}

	
}
