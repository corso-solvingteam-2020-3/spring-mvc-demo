package it.solvingteam.course.springmvc.springmvcdemo.dto.messages;

import javax.validation.constraints.NotEmpty;

public class CustomerInsertDto {


    @NotEmpty(message = "Required field")
    private String name;

    @NotEmpty(message = "Required field")
    private String mobile;

    @NotEmpty(message = "Required field")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
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
