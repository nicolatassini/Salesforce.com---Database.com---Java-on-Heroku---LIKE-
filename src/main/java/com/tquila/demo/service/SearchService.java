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

import com.flaptor.indextank.apiclient.Index;
import com.flaptor.indextank.apiclient.IndexDoesNotExistException;
import com.flaptor.indextank.apiclient.IndexTankClient;
import com.flaptor.indextank.apiclient.IndexTankClient.BatchResults;
import com.flaptor.indextank.apiclient.IndexTankClient.Document;

/**
 * @author nicolatassini
 *
 */
public class SearchService {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchService.class); 

	public Index initIndex() {
		IndexTankClient client = new IndexTankClient("http://:GwWoDRbSkWaKIj@dhyja.api.searchify.com");
		return client.getIndex("idx");
	}
	
	public boolean batchIndexing() {
		Index index = initIndex();
		List<Document> documents = new ArrayList<Document>();

		Map<String, String> fields1 = new HashMap<String, String>();
		fields1.put("text", "document 1 text disema test nik");

		// Document is built with:
		// - String docId
		// - Map<String, String> fields
		// - Map<Integer, Float> variables 
		// - Map<String, String> facetingCategories
		Document document1 = new Document("1", fields1, null, null);
		documents.add(document1); 

		Map<String, String> fields2 = new HashMap<String, String>();
		fields1.put("text", "document 2 text");
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
