package it.solvingteam.course.springmvc.springmvcdemo.dto.messages;

import javax.validation.constraints.NotEmpty;

public class CustomerDeletionDto {
	
	@NotEmpty
	String idToDelete;
	

	public String getIdToDelete() {
		return idToDelete;
	}

	public void setIdToDelete(String idToDelete) {
		this.idToDelete = idToDelete;
	}
	
	

}
