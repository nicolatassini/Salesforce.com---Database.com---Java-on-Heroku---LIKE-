/**
 * 
 */
package com.tquila.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.flaptor.indextank.apiclient.Index;
import com.flaptor.indextank.apiclient.IndexDoesNotExistException;
import com.flaptor.indextank.apiclient.IndexTankClient;
import com.flaptor.indextank.apiclient.IndexTankClient.BatchResults;
import com.flaptor.indextank.apiclient.IndexTankClient.Document;
import com.flaptor.indextank.apiclient.IndexTankClient.Query;
import com.flaptor.indextank.apiclient.IndexTankClient.SearchResults;
import com.flaptor.indextank.apiclient.InvalidSyntaxException;
import com.tquila.demo.dao.TweetDao;
import com.tquila.demo.model.Tweet;
import com.tquila.demo.model.TwitterUser;

/**
 * @author nicolatassini
 * 
 * Heroku init script:
 * heroku config:add SEARCHIFY_PRIVATE_URL= SEARCHIFY_INDEX_NAME=
 * 
 */
public class SearchService {
	
	/*
	 * next steps:
	 * D0. usate le var di sistema
	 * D01. rivedi il modello DB
	 * D1. carica dati
	 * D2. aggiorna relazioni orm e indicizzazione
	 * 3. API Restful per ricercare
	 * 4. Plugin JQuery per ricercare (dentro anche autocompleter)
	 * 5. Applica plugin pagina Java
	 * 2. Applica plugin VF page / controller per fare la ricerca
	 */
	
	private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
	private static final String SEARCHIFY_PRIVATE_URL = "SEARCHIFY_PRIVATE_URL";
	private static final String SEARCHIFY_INDEX_NAME = "SEARCHIFY_INDEX_NAME";
	
	@Autowired
	private TweetDao tweetDao;
	
	public Index initIndex() {
		IndexTankClient client = new IndexTankClient(System.getenv(SEARCHIFY_PRIVATE_URL));
		return client.getIndex(System.getenv(SEARCHIFY_INDEX_NAME));
	}
	
	public Integer batchIndexing() {
		Index index = initIndex();
		List<Document> documents = new ArrayList<Document>();
		Map<String, String> fields;
		Document document;
		
		List<Tweet> tweets = tweetDao.getTweets();
		for(Tweet tweet : tweets) {
			fields = new HashMap<String, String>();
			TwitterUser author = tweet.getAuthor();
			
			fields.put("text", tweet.getTweet() + " " + author.getTwitterID());
			fields.put("firstname", author.getFirstName());
			fields.put("lastname", author.getLastName());
			fields.put("twitterID", author.getTwitterID());
			fields.put("tweet", tweet.getTweet());
			fields.put("timestamp", "" + (tweet.getPostedOn().getTimeInMillis() / 1000L));
			
			document = new Document(tweet.getId(), fields, null, null);
			documents.add(document);
		}
		
		try {
			BatchResults results = index.addDocuments(documents);
			logger.info("SearchService.batchIndexing: results : " + results.getFailedDocuments());
			return documents.size();
		} catch (IOException e) {
			logger.error("SearchService.batchIndexing: IO error", e);
			return 0;
		} catch (IndexDoesNotExistException e) {
			logger.error("SearchService.batchIndexing: IndexDoesNotExist error", e);
			return 0;
		}
	}
	
	public SearchResults query(String query) {
		Index index = initIndex();
		try {
			return index.search(Query.forString(query));
		} catch (IOException e) {
			logger.error("SearchService.query: IO error", e);
		} catch (InvalidSyntaxException e) {
			logger.error("SearchService.query: InvalidSyntaxException error", e);
		}
//		    System.out.println("doc id: " + document.get("docid"));
		return null;
	}
}
