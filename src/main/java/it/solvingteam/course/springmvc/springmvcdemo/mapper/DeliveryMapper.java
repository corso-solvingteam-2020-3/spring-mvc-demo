package it.solvingteam.course.springmvc.springmvcdemo.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import it.solvingteam.course.springmvc.springmvcdemo.dto.DeliveryDto;
import it.solvingteam.course.springmvc.springmvcdemo.model.Delivery;

@Component
public class DeliveryMapper extends AbstractMapper<Delivery, DeliveryDto>{
	
	@Override
    public DeliveryDto convertEntityToDto(Delivery entity) {
        if (entity == null) {
            return null;
        }

        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setId(entity.getId().toString());
        deliveryDto.setDescription(entity.getDescription());
        deliveryDto.setShippingDate(entity.getShippingDate().toString());
        deliveryDto.setPrice(entity.getPrice().toString());

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
        delivery.setShippingDate(LocalDate.parse(dto.getShippingDate()));
        delivery.setPrice(Double.parseDouble(dto.getPrice()));

        return delivery;
    }

}
