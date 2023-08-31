package com.example.pricecompareredis.vo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;

@Data
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No Keys in Redis")
public class NotFoundException extends RuntimeException {

	private String errorMessage;
	private HttpStatus httpStatus;

	public NotFoundException(String errorMessage, HttpStatus httpStatus) {
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}
}
