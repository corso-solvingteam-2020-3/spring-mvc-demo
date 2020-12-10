package it.solvingteam.course.springmvc.springmvcdemo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CustomerDto {

	@NotNull(message = "il campo non esiste")
	private String id;

	@NotEmpty(message = "Required field")
	private String name;

	@NotEmpty(message = "Required field")
	private String mobile;

	@NotEmpty(message = "Required field")
	private String address;

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

}
