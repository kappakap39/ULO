package com.ava.dynamic.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExceptionController {
	
//	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
//	public void conflict() {
//		// Nothing to do
//	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String viewGeneralError(Model model, Exception e) {
		model.addAttribute("e", e);

		model.addAttribute("title", "Unexpected error. Please contact admin!");
		model.addAttribute("detail", e.getLocalizedMessage());
		model.addAttribute("cause", e.getCause());
		model.addAttribute("message", e.getMessage());
		model.addAttribute("trace", ExceptionUtils.getStackFrames(e));
		return "error/generic_error";
	}

	
	@RequestMapping(value = "/error/404", method = RequestMethod.GET)
	public String e404(Model model, Exception e) {
		model.addAttribute("e", e);

		model.addAttribute("title", "Error 404 No such directory you requested!");
		model.addAttribute("detail", "The 404 or Not Found error message is an HTTP standard response code "
				+ "indicating that the client was able to communicate with a given server, " + "but the server could not find what was requested. "
				+ "The web site hosting server will typically generate a \"404 Not Found\" web page " + "when a user attempts to follow a broken or dead link; "
				+ "hence the 404 error is one of the most recognizable errors users can find on the web.");
		model.addAttribute("cause", e.getCause());
		model.addAttribute("message", e.getMessage());
		model.addAttribute("trace", ExceptionUtils.getStackFrames(e));
		return "error/generic_error";
	}

	@RequestMapping(value = "/error/500", method = RequestMethod.GET)
	public String e500(Model model, HttpServletRequest request) {
		Throwable e = (Throwable) request.getAttribute("javax.servlet.error.exception");
		model.addAttribute("e", e);

		model.addAttribute("title", "Error 500 Programmatic error. Please contact admin!");
		model.addAttribute("detail", ExceptionUtils.getMessage(e));
		model.addAttribute("cause", ExceptionUtils.getRootCauseMessage(e));
		model.addAttribute("message", e.getMessage());
		model.addAttribute("trace", ExceptionUtils.getStackFrames(e));
		return "error/generic_error";
	}
}
