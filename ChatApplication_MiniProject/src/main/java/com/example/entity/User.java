package com.example.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_tbl")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String username;
	
	@OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
	private List<Message> sentMessages;
	
	@OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
	private List<Message> receivedMessages;
	
	//default constructor
	public User() {
		super();
	}
	
	//parameterized constructor
	public User(String username) {
		super();
		this.username = username;
		
	}
	
	//getter and setter methods
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Message> getSentMessages() {
		return sentMessages;
	}
	public void setSentMessages(List<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}
	public List<Message> getReceivedMessages() {
		return receivedMessages;
	}
	public void setReceivedMessages(List<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}
	
	//toString method
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username +"]";
	}
	
}
