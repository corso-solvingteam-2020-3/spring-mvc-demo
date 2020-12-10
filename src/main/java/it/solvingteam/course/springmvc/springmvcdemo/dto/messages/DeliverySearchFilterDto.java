package it.solvingteam.course.springmvc.springmvcdemo.dto.messages;


public class DeliverySearchFilterDto {
	
 	private String id;
	
    private String description;
	
    private String shippingDate;
    
    private String MinPrice;
    
    private String MaxPrice;
    
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


	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMinPrice() {
		return MinPrice;
	}

	public void setMinPrice(String minPrice) {
		MinPrice = minPrice;
	}

	public String getMaxPrice() {
		return MaxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		MaxPrice = maxPrice;
	}
    

}
