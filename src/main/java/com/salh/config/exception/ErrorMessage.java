package com.salh.config.exception;

import lombok.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class ErrorMessage {
    private final String code;
    private final Date timestamp;
    private final String message;
    private final String description;

    private List<ValidationObjectError> errors;

    @Getter
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    public static class ValidationFieldError extends ValidationObjectError {
        private final Object rejectedValue;
        private final String field;

        public ValidationFieldError(String field, String message, Object rejectedValue) {
            super(message);
            this.field = field;
            this.rejectedValue = rejectedValue;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @Getter
    public static class ValidationObjectError {
        private String message;
    }

    public void addValidationError(FieldError fieldError) {
        if (Objects.isNull(errors)) errors = new ArrayList<>();

        errors.add(fieldErrorToValidationError(fieldError));
    }

    public void addValidationError(ObjectError objectError) {
        if (Objects.isNull(errors)) errors = new ArrayList<>();
        errors.add(objectErrorToValidationError(objectError));
    }

    private ValidationObjectError objectErrorToValidationError(ObjectError objectError) {
        return new ValidationObjectError(objectError.getDefaultMessage());
    }

    private ValidationObjectError fieldErrorToValidationError(FieldError fieldError) {
        return new ValidationFieldError(
                fieldError.getField(),
                fieldError.getDefaultMessage(),
                fieldError.getRejectedValue()
        );
    }

	public String getStatusCode() {
		return code;
	}

	public String getCode() {
		return code;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

	public List<ValidationObjectError> getErrors() {
		return errors;
	}
	
	
	
}