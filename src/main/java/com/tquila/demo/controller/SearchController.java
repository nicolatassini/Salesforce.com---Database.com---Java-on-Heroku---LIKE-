/**
 * 
 */
package com.tquila.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping(method=RequestMethod.GET,value="/query")
	public String search() {		
		return "search";
	}

	@RequestMapping(method=RequestMethod.POST, value="/query")
	public ModelAndView query(@RequestParam(value="q", required=true) String query,
			@RequestParam(value="o", required=true) Integer offset,
			@RequestParam(value="n", required=true) Integer numberOfResults) {		
		ModelAndView mav = new ModelAndView();
 		mav.setViewName("search");
 		
 		if(query == null || query.length() == 0) {
 			query = "*";
 		}
 		
 		mav.addObject("q", query);
 		mav.addObject("o", offset);
 		mav.addObject("n", numberOfResults);
		
 		SearchResults results = searchService.query(query, offset, numberOfResults);
 		mav.addObject("totalResults", results.matches);
 		
 		List<TweetResult> resultList = new ArrayList<TweetResult>();
 		for(Map<String, Object> document : results.results) {
 			TweetResult tweetResult = new TweetResult();
 			tweetResult.sfid = "" + document.get("docid");
 			tweetResult.tweet = "" + document.get("tweet");
 			resultList.add(tweetResult);
 		}
 		mav.addObject("results", resultList);
 		
 		mav.addObject("hasPrevious", offset > 0);
 		mav.addObject("hasNext", (offset+1)*numberOfResults < results.matches);
 		
 		return mav;
	}
	
	public class TweetResult {
		public String sfid;
		public String tweet;
		public String getSfid() {
			return sfid;
		}
		public void setSfid(String sfid) {
			this.sfid = sfid;
		}
		public String getTweet() {
			return tweet;
		}
		public void setTweet(String tweet) {
			this.tweet = tweet;
		}
	}
	
}
