package com.example.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="message_tbl")
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String content;
	private LocalDateTime timestamp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="sender_id")
	private User sender;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="recipient_id")
	private User recipient;
	
	//default constructor
	public Message() {
		super();
	}
	
	//parameterized constructor
	public Message(User sender, User recipient, String content) {
		super();
		this.sender = sender;
		this.content = content;
		this.recipient = recipient;
		this.timestamp = LocalDateTime.now();
	}
	
	//getter and setter methods
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getRecipient() {
		return recipient;
	}
	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}
	
	//toString method
	@Override
	public String toString() {
		return "Message [id=" + id + ", content=" + content + ", timestamp=" + timestamp + "]";
	}
}
