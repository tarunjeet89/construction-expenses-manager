package com.salh.config.exception;

import lombok.Data;

@Data
public class InternalServerException extends RuntimeException {

	private static final long serialVersionUID = 5660887970984827081L;
	
	String code;
	
	public InternalServerException(String code) {
        this.code = code;
    }
}