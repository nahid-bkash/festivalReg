package com.bkash.festivalreg.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {

	private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String exception(final Throwable throwable, final Model model) {

		String errorMessage = "Internal server error, Please contact with Administrator";
		if (throwable.getCause().getClass().equals(ConstraintViolationException.class)) {
			errorMessage = "Possible Duplicate Entry, Please check your record";
		}	

		logger.error("Exception during execution of SpringSecurity application", throwable);

		model.addAttribute("errorMessage", errorMessage);
		throwable.printStackTrace();
		return "error";
	}

}