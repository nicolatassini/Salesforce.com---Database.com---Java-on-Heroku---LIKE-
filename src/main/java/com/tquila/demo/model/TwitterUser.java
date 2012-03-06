/**
 * 
 */
package com.tquila.demo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.force.sdk.jpa.annotation.CustomField;

/**
 * @author nicolatassini
 *
 */
@Entity
public class TwitterUser implements Serializable {

	private static final long serialVersionUID = -8830674343092997696L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column
	@CustomField(label = "First Name")
	private String firstName;

	@Column
	@CustomField(label = "Last Name")
	private String lastName;
	
	@Column(nullable = false, unique = true)
	@CustomField(externalId = true, label = "Twitter ID")
	private String twitterID;
	
	@Column
	@CustomField(label = "Description")
	private String description;
	
	@OneToMany(mappedBy = "author")
	private List<Tweet> tweets;

	public TwitterUser() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTwitterID() {
		return twitterID;
	}

	public void setTwitterID(String twitterID) {
		this.twitterID = twitterID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	
}
