package it.solvingteam.course.springmvc.springmvcdemo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DeliveryDto {

	@NotEmpty(message = "Required field")
    private String id;

    @NotEmpty(message = "Required field")
    private String description;

    @NotEmpty(message = "Required field")
    private String shippingDate;

    @NotEmpty(message = "Required field")
    private String price;
    
    @NotNull(message = "Required field")
    private CustomerDto customer;
    
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

	public CustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}

}
