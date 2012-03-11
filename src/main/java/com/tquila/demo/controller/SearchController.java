/**
 * 
 */
package com.tquila.demo.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.flaptor.indextank.apiclient.IndexTankClient.SearchResults;
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

	@RequestMapping(method=RequestMethod.GET, value="/query")
	public String query(@RequestParam(value="q",required=true) String query,
			@RequestParam(value="o",required=true) Integer offset,
			@RequestParam(value="n",required=true) Integer numberOfResults) {		
 		logger.info("Query: " + query);
 		
 		SearchResults results = searchService.query(query, offset, numberOfResults);
 		for(Map<String, Object> document : results.results) {
 			logger.info("doc id: " + document.get("docid") + " " + document.get("tweet"));
 		}
 		
		return "home";
	}
	
}
