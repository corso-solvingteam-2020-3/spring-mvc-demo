package it.solvingteam.course.springmvc.springmvcdemo.dto.messages;

import javax.validation.constraints.NotEmpty;

public class DeliveryInsertDto {
	
    private String id;
    
    @NotEmpty(message = "Required field")
    private String description;
    
    @NotEmpty(message = "Required field")
    private String shippingDate;
    
    @NotEmpty(message = "Required field")
    private String price;
    
    @NotEmpty(message = "Required field")
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
    
    
}
