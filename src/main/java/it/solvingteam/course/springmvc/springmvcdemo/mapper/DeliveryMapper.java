package it.solvingteam.course.springmvc.springmvcdemo.mapper;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliveryInsertDto;
import it.solvingteam.course.springmvc.springmvcdemo.model.Customer;
import it.solvingteam.course.springmvc.springmvcdemo.model.Delivery;
import it.solvingteam.course.springmvc.springmvcdemo.repository.CustomerRepository;

@Component
public class DeliveryMapper extends AbstractMapper<Delivery, DeliveryDto> {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Override
	public DeliveryDto convertEntityToDto(Delivery entity) {
		 if (entity == null) {
	            return null;
	        }
		 DeliveryDto dto = new DeliveryDto();
		 dto.setId(String.valueOf(entity.getId()));
		 dto.setDescription(entity.getDescription());
		 dto.setPrice(String.valueOf(entity.getPrice()));
		 dto.setShippingDate(String.valueOf(entity.getShippingDate()));	
		 dto.setCustomer(customerMapper.convertEntityToDto(entity.getCustomer()));
		 return dto;
	}

	@Override
	public Delivery convertDtoToEntity(DeliveryDto dto) {
		if (dto == null) {
            return null;
        }
		Delivery entity = new Delivery();
		
		if (dto.getId() != null) {
			entity.setId(Integer.valueOf(dto.getId()));
		}
		entity.setDescription(dto.getDescription());
		entity.setPrice(Double.valueOf(dto.getPrice()));
		entity.setShippingDate(LocalDate.parse(dto.getShippingDate()));	
		entity.setCustomer(customerMapper.convertDtoToEntity(dto.getCustomer()));
		return entity;
	}
	
	public DeliveryDto convertToDeliveryDto(DeliveryInsertDto deliveryInsertDto) {
		DeliveryDto deliveryDto = new DeliveryDto();
		deliveryDto.setDescription(deliveryInsertDto.getDescription());
		deliveryDto.setPrice(deliveryInsertDto.getPrice());
		deliveryDto.setShippingDate(deliveryInsertDto.getShippingDate());
		Integer customerId = Integer.valueOf(deliveryInsertDto.getCustomerId());
		Customer customerEntity = customerRepository.findById(customerId).orElse(null);
		deliveryDto.setCustomer(customerMapper.convertEntityToDto(customerEntity));
		return deliveryDto;
	}
}
