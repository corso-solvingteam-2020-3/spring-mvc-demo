package it.solvingteam.course.springmvc.springmvcdemo.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.model.Delivery;

@Component
public class DeliveryMapper extends AbstractMapper<Delivery, DeliveryDto> {
	
	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public DeliveryDto convertEntityToDto(Delivery entity) {
		 if (entity == null) {
	            return null;
	     }
		 
		 DeliveryDto deliveryDto = new DeliveryDto();
		 deliveryDto.setId(String.valueOf(entity.getId()));
		 deliveryDto.setDescription(entity.getDescription());
		 deliveryDto.setShippingDate(entity.getShippingDate().toString());
		 deliveryDto.setPrice(String.valueOf(entity.getPrice()));
		 deliveryDto.setCustomer(customerMapper.convertEntityToDto(entity.getCustomer()));
		 
		 return deliveryDto;
	}

	@Override
	public Delivery convertDtoToEntity(DeliveryDto dto) {
		if (dto == null) {
            return null;
        }
		
		Delivery delivery = new Delivery();
		
		if(dto.getId() != null) {
			delivery.setId(Integer.parseInt(dto.getId()));
		}
		
		delivery.setDescription(dto.getDescription());
		
		LocalDate date = LocalDate.parse(dto.getShippingDate());
		delivery.setShippingDate(date);
		
		delivery.setPrice(Double.parseDouble(dto.getPrice()));
		
		delivery.setCustomer(customerMapper.convertDtoToEntity(dto.getCustomer()));
		
		
		return delivery;
	}

}
