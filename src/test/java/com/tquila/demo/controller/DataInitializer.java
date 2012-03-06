package com.tquila.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tquila.demo.model.TwitterUser;

@Component
@Scope("prototype")
public class DataInitializer {

	public static final int PERSON_COUNT = 3;

	@PersistenceContext
	private EntityManager entityManager;

	public List<String> twitterUsers = new ArrayList<String>();

	public void initData() {
		twitterUsers.clear();// clear out the previous list of people
		addTwitterUser("jin", "Jim", "Smith");
		addTwitterUser("tina", "Tina", "Marsh");
		addTwitterUser("steve", "Steve", "Blair");
		entityManager.flush();
		entityManager.clear();
	}

	public void addTwitterUser(String twitterID, String firstName, String lastName) {
		TwitterUser u = new TwitterUser();
		u.setTwitterID(twitterID);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		entityManager.persist(u);
		twitterUsers.add(u.getId());
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
