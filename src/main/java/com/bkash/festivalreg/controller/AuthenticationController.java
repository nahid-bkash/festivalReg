package com.bkash.festivalreg.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AuthenticationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@ResponseBody
	@RequestMapping("/test")
	String entry(){		
		return "My Spring Bood App(auth)";		
	}
	
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {

		LOGGER.info("FolkFestApp::AuthenticationController::logoutPage initiated");

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		LOGGER.info("FolkFestApp::AuthenticationController::logoutPage initiated by "+ auth.getName() );

	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/";
	    //You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}


}
