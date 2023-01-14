package com.rafaelsantos.helpdesk.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rafaelsantos.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.rafaelsantos.helpdesk.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError error = new StandardError(System.currentTimeMillis(), status.value(), "Object not found", ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		StandardError error = new StandardError(System.currentTimeMillis(), status.value(), "Violação de dados", ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		ValidationError errors = new ValidationError(System.currentTimeMillis(), status.value(), 
				"Validation error", "Erro na validação dos campos", request.getRequestURI());
		
		for(FieldError x : ex.getBindingResult().getFieldErrors()) {
			errors.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(errors);
	}
}
