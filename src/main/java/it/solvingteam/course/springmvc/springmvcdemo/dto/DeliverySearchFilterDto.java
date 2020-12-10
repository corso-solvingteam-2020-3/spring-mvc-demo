package it.solvingteam.course.springmvc.springmvcdemo.dto;

public class DeliverySearchFilterDto {
	
	private String id;
	private String description;
	private String shippingDate;
	private String minimunPrice;
	private String maximunPrice;
	private String customerId;
	
	
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
	public String getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}
	public String getMinimunPrice() {
		return minimunPrice;
	}
	public void setMinimunPrice(String minimunPrice) {
		this.minimunPrice = minimunPrice;
	}
	public String getMaximunPrice() {
		return maximunPrice;
	}
	public void setMaximunPrice(String maximunPrice) {
		this.maximunPrice = maximunPrice;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	
	
	

}
