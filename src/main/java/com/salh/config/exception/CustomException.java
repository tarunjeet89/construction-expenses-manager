package com.salh.config.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class CustomException extends RuntimeException{
	
	private static final long serialVersionUID = 5660887970984827081L;
	
	String code;
	String description;
	HttpStatus statusCode;
	
	public CustomException(String code,String description,HttpStatus statusCode) {
        this.code = code;
        this.description = description;
        this.statusCode = statusCode;
    }

}
