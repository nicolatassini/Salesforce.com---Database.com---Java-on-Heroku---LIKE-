package com.tquila.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tquila.demo.service.SearchService;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private SearchService searchService;

	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("Welcome home");
		model.addAttribute("controllerMessage",
				"This is the message from the controller!");
		return "home";
	}
	
	@RequestMapping(value = "/indexing", method = RequestMethod.GET)
	public String indexing(Model model) {
		searchService.batchIndexing();
		model.addAttribute("controllerMessage",
				"Indexed!!!");
		return "home";
	}

}
