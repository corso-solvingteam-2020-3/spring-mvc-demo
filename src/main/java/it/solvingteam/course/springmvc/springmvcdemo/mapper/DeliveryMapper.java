package it.solvingteam.course.springmvc.springmvcdemo.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import it.solvingteam.course.springmvc.springmvcdemo.dto.messages.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.model.Delivery;

@Component
public class DeliveryMapper extends AbstractMapper <Delivery, DeliveryDto>{

	@Override
    public DeliveryDto convertEntityToDto(Delivery entity) {
        if (entity == null) {
            return null;
        }

        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setId(String.valueOf(entity.getId()));
        deliveryDto.setDescription(entity.getDescription());
        deliveryDto.setDeliveryDate(String.valueOf(entity.getDeliveryDate()));
        deliveryDto.setPrice(String.valueOf(entity.getPrice()));

        return deliveryDto;
    }

    @Override
    public Delivery convertDtoToEntity(DeliveryDto dto) {
        if (dto == null) {
            return null;
        }

        Delivery delivery = new Delivery();

        if (dto.getId() != null) {
            delivery.setId(Integer.valueOf(dto.getId()));
        }

        delivery.setDescription(dto.getDescription());
        delivery.setDeliveryDate(LocalDate.parse(dto.getDeliveryDate()));
        delivery.setPrice(Double.parseDouble(dto.getPrice()));

        return delivery;
    }
	
	
}
