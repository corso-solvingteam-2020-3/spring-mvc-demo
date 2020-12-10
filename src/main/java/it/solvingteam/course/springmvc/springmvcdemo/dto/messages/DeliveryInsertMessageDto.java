package it.solvingteam.course.springmvc.springmvcdemo.dto.messages;

import javax.validation.constraints.NotEmpty;

public class DeliveryInsertMessageDto {

	@NotEmpty(message = "The description is required")
	private String description;

	@NotEmpty(message = "The Delivery Date is required")
    private String deliveryDate;

	@NotEmpty(message = "The price is required")
    private String price;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
