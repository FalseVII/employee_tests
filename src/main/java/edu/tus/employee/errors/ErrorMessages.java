package edu.tus.employee.errors;

public enum ErrorMessages {
	EMPTY_FIELDS("One of more empty field"),
	ALREADY_EXISTS("Email address already exists"),
	INVALID_AGE("Employees must be over 18 and under 66"),
	NOT_FOUND("No employee found");
	
	private String errorMessage;
	
	ErrorMessages(String errMsg){
		this.errorMessage=errMsg;
	}
	
	public String getMsg(){
		return errorMessage;
	}
}
