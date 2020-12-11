package it.solvingteam.course.springmvc.springmvcdemo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliveryInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliverySearchFilterDto;
import it.solvingteam.course.springmvc.springmvcdemo.mapper.CustomerMapper;
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
    private DeliveryMapper deliveryMapper;

    @Autowired
    private EntityManager entityManager;

    public List<DeliveryDto> findAll() {
        List<Delivery> all = this.deliveryRepository.findAll();
        return deliveryMapper.convertEntityToDto(all);
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
            predicates.add(cb.equal(delivery.get("shippingDate").as(String.class), deliverySearchFilterDto.getShippingDate()));
        }
        
        if (deliverySearchFilterDto.getCustomerId() != null && !deliverySearchFilterDto.getCustomerId().equals("")) {
            predicates.add(cb.equal(delivery.get("customer").get("id"), deliverySearchFilterDto.getCustomerId()));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        return deliveryMapper.convertEntityToDto(entityManager.createQuery(cq).getResultList());
    }

    public Delivery save(DeliveryDto deliveryDto) {
        Delivery delivery = deliveryMapper.convertDtoToEntity(deliveryDto);
        return this.deliveryRepository.save(delivery);
    }
    
    public Delivery save(DeliveryInsertDto deliveryInsertDto) {
        return save(deliveryMapper.convertToDeliveryDto(deliveryInsertDto));
	}

    public long count() {
        return this.deliveryRepository.count();
    }

	public DeliveryDto show(Integer id) {
		Delivery delivery = deliveryRepository.findById(id).orElse(null);
		return deliveryMapper.convertEntityToDto(delivery);
	}

	public boolean update(@Valid DeliveryDto deliveryDto) {
		Delivery delivery = deliveryRepository.findById(Integer.valueOf(deliveryDto.getId())).orElse(null);
		if (delivery == null) {
			return false;
		} else {
			Delivery updated = save(deliveryDto);
			return updated.getId().toString() == deliveryDto.getId();
		}
	}

	public boolean delete(Integer id) {
		Delivery delivery = deliveryRepository.findById(id).orElse(null);
		if (delivery == null) {
			return false;
		}
		deliveryRepository.delete(delivery);
		return true;
	}
}
