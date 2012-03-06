/**
 * 
 */
package com.tquila.demo.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.force.sdk.jpa.annotation.CustomField;
import com.sforce.soap.metadata.FieldType;

/**
 * @author nicolatassini
 *
 */
@Entity
public class Tweet implements Serializable {
	
	private static final long serialVersionUID = -7690989691789008517L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@Column
	@CustomField(label = "Tweet", length = 140, type = FieldType.Text)
	private String tweet;

	@Column
	@CustomField(label = "Posted On", type = FieldType.DateTime)
	private Calendar postedOn;
	
	@ManyToOne
	@CustomField(label = "Author", type = FieldType.MasterDetail)
	private TwitterUser author;
	

	public Tweet() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public Calendar getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Calendar postedOn) {
		this.postedOn = postedOn;
	}

	public TwitterUser getAuthor() {
		return author;
	}

	public void setAuthor(TwitterUser author) {
		this.author = author;
	}
	
}
