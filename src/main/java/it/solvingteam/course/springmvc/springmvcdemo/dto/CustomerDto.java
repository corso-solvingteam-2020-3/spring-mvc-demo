package it.solvingteam.course.springmvc.springmvcdemo.dto;

public class CustomerDto {

    private String id;

    private String name;

    private String mobile;

    private String address;
    
    private String size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
