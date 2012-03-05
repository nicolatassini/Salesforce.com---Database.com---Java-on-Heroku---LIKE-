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
import com.tquila.demo.dao.PersonDao;
import com.tquila.demo.model.Person;

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
	 * 1. carica dati e rivedi il modello DB
	 * 2. aggiorna relazioni orm e indicizzazione
	 * 3. API Restful per ricercare
	 * 4. Plugin JQuery per ricercare (dentro anche autocompleter)
	 * 5. Applica plugin pagina Java
	 * 2. Applica plugin VF page / controller per fare la ricerca
	 */
	
	private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
	private static final String SEARCHIFY_PRIVATE_URL = "SEARCHIFY_PRIVATE_URL";
	private static final String SEARCHIFY_INDEX_NAME = "SEARCHIFY_INDEX_NAME";
	
	@Autowired
	private PersonDao personDao;
	
	public Index initIndex() {
		IndexTankClient client = new IndexTankClient(System.getenv(SEARCHIFY_PRIVATE_URL));
		return client.getIndex(System.getenv(SEARCHIFY_INDEX_NAME));
	}
	
	public boolean batchIndexing() {
		Index index = initIndex();
		List<Document> documents = new ArrayList<Document>();
		Map<String, String> fields;
		Document document;
		
		for(Person person : personDao.getPeople()) {
			fields = new HashMap<String, String>();
			fields.put("text", person.getFirstName() + " " + person.getLastName());
			fields.put("firstname", person.getFirstName());
			fields.put("lastname", person.getLastName());
			fields.put("address", person.getAddress());
			fields.put("city", person.getCity());
			fields.put("country", person.getCountry());
			fields.put("timestamp", "" + (person.getCreatedDate().getTimeInMillis() / 1000L));
			
			document = new Document(person.getId(), fields, null, null);
			documents.add(document);
		}
		
		try {
			BatchResults results = index.addDocuments(documents);
			logger.info("SearchService.batchIndexing: results : " + results.getFailedDocuments());
			return true;
		} catch (IOException e) {
			logger.error("SearchService.batchIndexing: IO error", e);
			return false;
		} catch (IndexDoesNotExistException e) {
			logger.error("SearchService.batchIndexing: IndexDoesNotExist error", e);
			return false;
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
