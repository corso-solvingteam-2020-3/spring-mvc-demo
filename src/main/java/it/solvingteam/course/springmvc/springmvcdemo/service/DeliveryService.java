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

import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliveryInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliverySearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.mapper.DeliveryMapper;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
import it.solvingteam.course.springmvc.springmvcdemo.model.Delivery;
import it.solvingteam.course.springmvc.springmvcdemo.repository.DeliveryRepository;

@Service
public class DeliveryService {
	
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private DeliveryMapper deliveryMapper;

	
	@Autowired
	private EntityManager entityManager;
	
	public List<DeliveryDto> findAll() {
		List<Delivery> allDeliveries = this.deliveryRepository.findAll();
		return deliveryMapper.convertEntityToDto(allDeliveries);
	}
	
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

		if (deliverySearchFilterDto.getMinPrice() != null && !deliverySearchFilterDto.getMinPrice().equals("")) {
			predicates.add(cb.greaterThanOrEqualTo(delivery.get("price"), deliverySearchFilterDto.getMinPrice()));
		}
		
		if (deliverySearchFilterDto.getMaxPrice() != null && !deliverySearchFilterDto.getMaxPrice().equals("")) {
			predicates.add(cb.lessThanOrEqualTo(delivery.get("price"), deliverySearchFilterDto.getMaxPrice()));
		}

		if (deliverySearchFilterDto.getShippingDate() != null && !deliverySearchFilterDto.getShippingDate().equals("")) {
			predicates.add(cb.equal(delivery.get("shippingDate"), LocalDate.parse(deliverySearchFilterDto.getShippingDate()) ));
		}
		
		if (deliverySearchFilterDto.getCustomerId() != null && !deliverySearchFilterDto.getCustomerId().equals("")) {
			Optional <Customer> costumerOpt =customerService.getCustomer(Integer.parseInt(deliverySearchFilterDto.getCustomerId()));
			predicates.add(cb.equal(delivery.get("customer"), costumerOpt.get()));
		}

		cq.where(predicates.toArray(new Predicate[0]));
		return deliveryMapper.convertEntityToDto(entityManager.createQuery(cq).getResultList());
	}
	
	public Delivery save(DeliveryDto deliveryDto) {
		Delivery delivery = deliveryMapper.convertDtoToEntity(deliveryDto);
		return this.deliveryRepository.save(delivery);
	}
	
	public Delivery save(DeliveryInsertDto deliveryInsertDto) {
		String description = deliveryInsertDto.getDescription();
		String price = deliveryInsertDto.getPrice();
		String shippingDate= deliveryInsertDto.getShippingDate();
		String customerId = deliveryInsertDto.getCustomerId();
		
		Delivery delivery= new Delivery();

		delivery.setDescription(description);
		delivery.setPrice(Double.parseDouble(price));
		delivery.setShippingDate(LocalDate.parse(shippingDate));
		Optional<Customer> customerOpt = customerService.getCustomer(Integer.parseInt(customerId));
		Customer customer = customerOpt.get();
		delivery.setCustomer(customer);
		return this.deliveryRepository.save(delivery);
	}
	
	public Optional<Delivery> getDelivery(Integer id) {
		return this.deliveryRepository.findById(id);
	}
	
	public DeliveryDto getDeliveryById(Integer id) {
		Optional<Delivery> deliveryOpt = this.deliveryRepository.findById(id);
		Delivery delivery = deliveryOpt.get();
		return deliveryMapper.convertEntityToDto(delivery);
	}
	
	public void delete (Integer id) {
		this.deliveryRepository.deleteById(id);
	}

}
