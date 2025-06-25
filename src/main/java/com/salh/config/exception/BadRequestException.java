package com.salh.config.exception;

import lombok.Data;

@Data
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 5660887970984827081L;
	
	String code;
	Object[] params;
	
	public BadRequestException(String code) {
        this.code = code;
    }
	
	public BadRequestException(String code, Object... params) {
        this.code = code;
        this.params = params;
    }
}

