package it.solvingteam.course.springmvc.springmvcdemo.dto.messages;



import javax.validation.constraints.NotEmpty;

public class AddDeliveryMessageDto {
	 @NotEmpty(message = "Required field")
	 private String description;
	 
	 @NotEmpty(message = "Required field")
	 private String shippingDate;
	 
	 @NotEmpty(message = "Required field")
	 private String price;
	 @NotEmpty(message = "Required field")
	 
	 private String customer;
	    
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

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	 
}
