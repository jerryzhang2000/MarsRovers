package com.nasa.rover;

/*
 * Customized Exception for the application
 */
public class ApplicationException extends Exception{

	private static final long serialVersionUID = -4328228967706963378L;

	public ApplicationException(){
		super();
	}
	
	public ApplicationException(String message){
		super(message);
	}
	
	public ApplicationException(String message, Exception e){
		super(message, e);
	}	

}
