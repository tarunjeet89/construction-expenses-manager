package com.salh.config.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
	private static final String REQUEST_BODY_MISSING = "request-body.missing";
	private static final String CHECK_ERROR_FIELD_MESSAGE = "check-validation.errors";

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString(), new Date(),
				ex.getMessage(), request.getDescription(false));

		log.error("Exception", ex);
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<ErrorDTO> globalHDInternalServerExceptionHandler(Exception ex, WebRequest request) {

		InternalServerException e = (InternalServerException) ex;
		String message = getErrorMessage(e.getCode());
		ErrorDTO hdError = new ErrorDTO(e.getCode(), message, "");

		log.error("HDInternalServerException -> code: {}, message: {}", hdError.getCode(), hdError.getMessage(), e);
		return new ResponseEntity<>(hdError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorDTO> globalHDBadRequestExceptionHandler(Exception ex, WebRequest request) {

		BadRequestException e = (BadRequestException) ex;
		String message = getErrorMessageWithArguments(e.getCode(), e.getParams());
		ErrorDTO hdError = new ErrorDTO(e.getCode(), message, "");

		log.error("HDBadRequestException -> code: {}, message: {}", hdError.getCode(), hdError.getMessage(), e);
		return new ResponseEntity<>(hdError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorDTO> hdCustomExceptionHandler(Exception ex, WebRequest request) {

		CustomException e = (CustomException) ex;

		ErrorDTO hdError = new ErrorDTO(e.getCode(), e.getDescription(), "");

		log.error("HDCustomException", ex);
		return new ResponseEntity<>(hdError, e.getStatusCode());
	}
	
	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
															   HttpHeaders headers, HttpStatus status,
															   WebRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("Exception In Binding Result:: ", ex);
		}

		ErrorMessage payloadBindingValidationError = new ErrorMessage(
			UNPROCESSABLE_ENTITY.toString(), new Date(),
			getErrorMessage(REQUEST_BODY_MISSING), request.getDescription(false)
		);
		return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(payloadBindingValidationError);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
															   HttpStatus status, WebRequest request) {
		return handleValidationFailedException(request, ex);
	}

	@Override
	public ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
													  HttpStatus status, WebRequest request) {
		return handleValidationFailedException(request, ex);
	}

	private ResponseEntity<Object> handleValidationFailedException(WebRequest request, BindException ex) {
		ErrorMessage errorMessage = new ErrorMessage(
			BAD_REQUEST.toString(), new Date(),
			getErrorMessage(CHECK_ERROR_FIELD_MESSAGE), request.getDescription(false)
		);

		BindingResult bindingResult = ex.getBindingResult();
		bindingResult.getFieldErrors().forEach(errorMessage::addValidationError);
		bindingResult.getGlobalErrors().forEach(errorMessage::addValidationError);

		if (log.isDebugEnabled()) {
			log.debug("BindException :: ", ex);
		}
		return ResponseEntity.badRequest().body(errorMessage);
	}

	private String getErrorMessage(String errorCode) {
		try {
			return messageSource.getMessage(errorCode, null, Locale.GERMAN);
		} catch (NoSuchMessageException nsme) {
			return errorCode;
		}
	}
	
	private String getErrorMessageWithArguments(String errorCode, Object[] params) {
		try {
			return messageSource.getMessage(errorCode, params, Locale.GERMAN);
		} catch (Throwable nsme) {
			return errorCode;
		}
	}
}