package com.salh.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDTO {
	private String code;
	private String message;
	private String description;

	public ErrorDTO(String code) {
		this.code = code;
	}
}
