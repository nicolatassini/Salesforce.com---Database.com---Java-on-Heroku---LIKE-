package com.tquila.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tquila.demo.model.Tweet;

@Repository
public class TweetDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Tweet find(String template) {
		return entityManager.find(Tweet.class, template);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tweet> getPeople() {
		return entityManager.createQuery("select t from Tweet t").getResultList();
	}
	
	@Transactional
	public Tweet save(Tweet person) {
		if (person.getId() == null) {
			entityManager.persist(person);
			return person;
		} else {
			return entityManager.merge(person);
		}		
	}	
	
}
