package it.solvingteam.course.springmvc.springmvcdemo.dto.messages;

import javax.validation.constraints.NotNull;

public class DeleteCustomerMessageDto {
	@NotNull(message = "il campo non esiste")
	  private String idDelete;

	public String getIdDelete() {
		return idDelete;
	}

	public void setIdDelete(String idDelete) {
		this.idDelete = idDelete;
	}
	
}
