/**
 * 
 */
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
 * @author nicolatassini
 *
 */
@Controller
@RequestMapping("/search/")
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexing(Model model) {
		Integer indexedDocNumber = searchService.batchIndexing();
		model.addAttribute("controllerMessage", "Indexed " + indexedDocNumber + " documents");
		return "home";
	}

	
}
