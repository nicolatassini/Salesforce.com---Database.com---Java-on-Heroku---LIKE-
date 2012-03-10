package com.tquila.demo.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.tquila.demo.controller.DataInitializer;
import com.tquila.demo.model.TwitterUser;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TweetDaoTest {

	@Autowired
	private TweetDao tweetDao;

	@Autowired
	private DataInitializer dataInitializer;

	@Before
	public void prepareData() {
		dataInitializer.initData();
	}

	@Test
	public void shouldSaveATwitterUser() {
		TwitterUser u = new TwitterUser();
		u.setTwitterID("andy");
		u.setFirstName("Andy");
		u.setLastName("Gibson");
		tweetDao.save(u);
		String id = u.getId();
		Assert.assertNotNull(id);
	}

	@Test
	public void shouldLoadAPerson() {
		String id = dataInitializer.twitterUsers.get(0);
		TwitterUser u = tweetDao.findTwitterAuthor(id);

		Assert.assertNotNull("Twitter user not found!", u);
		Assert.assertEquals(id, u.getId());
	}

	public void shouldListPeople() {
		List<TwitterUser> users = tweetDao.getTwitterUsers();
		Assert.assertEquals(DataInitializer.PERSON_COUNT, users.size());
	}

}
