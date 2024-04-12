package com.ava.dynamic.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.ava.dynamic.exception.DashboardException;

@ControllerAdvice
public class DashboardExceptionController {

	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
	public void conflict() {
		// Nothing to do
	}

	@ExceptionHandler(DashboardException.class)
	public ModelAndView viewProgrammaticHandlingError(HttpServletRequest req, DashboardException e) {
		System.out.println("Handling dashboard error /error/dashboard");
		ModelAndView mav = new ModelAndView();
		mav.addObject("e", e);
		mav.addObject("url", req.getRequestURL());

		mav.addObject("title", e.getErrCode() + " : Unexpected error. Please contact admin!");
		mav.addObject("detail", e.getErrMsg());
		mav.addObject("cause", e.getCause());
		mav.addObject("message", e.getMessage());
		mav.addObject("trace", ExceptionUtils.getStackFrames(e));

		// Set view name
		mav.setViewName("error/generic_error");
		return mav;
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView viewProgrammaticHandlingError(HttpServletRequest req, NoHandlerFoundException e) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("e", e);
		mav.addObject("url", req.getRequestURL());

		mav.addObject("title", "Error 500 : Unexpected error. Please contact admin!");
		mav.addObject("detail", e.getLocalizedMessage());
		mav.addObject("cause", e.getCause());
		mav.addObject("message", e.getMessage());
		mav.addObject("trace", ExceptionUtils.getStackFrames(e));

		// Set view name
		mav.setViewName("error/generic_error");
		return mav;
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView viewProgrammaticHandlingError(HttpServletRequest req, Exception e) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("e", e);
		mav.addObject("url", req.getRequestURL());
		
		mav.addObject("title", "Error 500 : Unexpected error. Please contact admin!");
		mav.addObject("detail", e.getLocalizedMessage());
		mav.addObject("cause", e.getCause());
		mav.addObject("message", e.getMessage());
		mav.addObject("trace", ExceptionUtils.getStackFrames(e));
		
		// Set view name
		mav.setViewName("error/generic_error");
		return mav;
	}

}
