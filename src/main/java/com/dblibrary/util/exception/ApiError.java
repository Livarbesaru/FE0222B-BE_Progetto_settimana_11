package com.dblibrary.util.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {
	public ApiError(String message2, HttpStatus internalServerError) {
	}
	private String message;
	private HttpStatus status;
}
