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
import com.tquila.demo.dao.PersonDao;
import com.tquila.demo.model.Person;

/**
 * @author nicolatassini
 *
 */
public class SearchService {
	
	/*
	 * next steps:
	 * 1. carica tanti dati
	 * 2. VF page / controller per fare la ricerca
	 *  
	 */
	
	private static final Logger logger = LoggerFactory.getLogger(SearchService.class); 
	
	@Autowired
	private PersonDao personDao;
	
	public Index initIndex() {
		IndexTankClient client = new IndexTankClient("http://:GwWoDRbSkWaKIj@dhyja.api.searchify.com");
		return client.getIndex("sfdc_index");
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
	
	public boolean batchIndexingOld() {
		Index index = initIndex();
		List<Document> documents = new ArrayList<Document>();

		// Document is built with:
		// - String docId
		// - Map<String, String> fields
		// - Map<Integer, Float> variables 
		// - Map<String, String> facetingCategories
		
		Map<String, String> fields1 = new HashMap<String, String>();
		fields1.put("text", "document 1 text disema test nik");
		Document document1 = new Document("1", fields1, null, null);
		documents.add(document1); 

		Map<String, String> fields2 = new HashMap<String, String>();
		fields2.put("text", "document 2 text");
		Map<Integer, Float> variables2 = new HashMap<Integer, Float>();
		variables2.put(1, 0.4f);
		Document document2 = new Document("2", fields2, variables2, null);
		documents.add(document2); 

		try {
			BatchResults results = index.addDocuments(documents);
			logger.error("SearchService.batchIndexing: results : " + results.getFailedDocuments());
		} catch (IOException e) {
			logger.error("SearchService.batchIndexing: IO error", e);
		} catch (IndexDoesNotExistException e) {
			logger.error("SearchService.batchIndexing: IndexDoesNotExist error", e);
		}
		
		return true;
	}
	
}
