package com.tquila.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tquila.demo.model.Tweet;
import com.tquila.demo.model.TwitterUser;

@Repository
public class TweetDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Tweet findTweet(String id) {
		return entityManager.find(Tweet.class, id);
	}
	
	public TwitterUser findTwitterAuthor(String id) {
		return entityManager.find(TwitterUser.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<TwitterUser> getTwitterUsers() {
		return entityManager.createQuery("select t from TwitterUser t").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Tweet> getTweets() {
		// return entityManager.createQuery("select t from Tweet t order by t.lastUpdate DESC").setMaxResults(5000).getResultList();
		return entityManager.createQuery("select t from Tweet t").setMaxResults(5000).getResultList();
	}

	@Transactional
	public Tweet save(Tweet tweet) {
		if(tweet.getId() == null) {
			entityManager.persist(tweet);
			return tweet;
		} else {
			return entityManager.merge(tweet);
		}		
	}
	
	@Transactional
	public TwitterUser save(TwitterUser twitterUser) {
		if(twitterUser.getId() == null) {
			entityManager.persist(twitterUser);
			return twitterUser;
		} else {
			return entityManager.merge(twitterUser);
		}		
	}
	
}
