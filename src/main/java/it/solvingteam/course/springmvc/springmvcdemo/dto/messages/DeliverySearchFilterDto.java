package it.solvingteam.course.springmvc.springmvcdemo.dto.messages;

public class DeliverySearchFilterDto {

	private String id;
 	private String description;
 	private String deliveryDate;
 	private String price;
 	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
