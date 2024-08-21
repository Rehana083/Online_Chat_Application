package com.example.test;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.example.entity.Message;
import com.example.entity.User;

public class JPATest {

	static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu");

	public static void insertUser(String username) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			User user = new User(username);
			entityManager.persist(user);
			entityManager.getTransaction().commit();
			System.out.println("User Inserted Successfully.");
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
	}

	public static void sendMessage(int senderId, int recipientId, String content) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			User sender = entityManager.find(User.class, senderId);
			User recipient = entityManager.find(User.class, recipientId);

			if (sender != null && recipient != null) {
				Message message = new Message(sender, recipient, content);
				entityManager.persist(message);
				System.out.println("Message Sent Successfully.");
			} else {
				System.out.println("Error: Sender or Recipient not found!");
			}
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			entityManager.close();
		}

	}

	public static void viewMessagesById(int userId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			User user = entityManager.find(User.class, userId);

			if (user != null) {
				List<Message> sentMessages = entityManager
						.createQuery("select m from Message m where m.sender.id = :userId", Message.class)
						.setParameter("userId", userId).getResultList();
				List<Message> receivedMessages = entityManager
						.createQuery("select m from Message m where m.recipient.id = :userId", Message.class)
						.setParameter("userId", userId).getResultList();

				System.out.println("Sent Messages: ");
				if (sentMessages.isEmpty()) {
					System.out.println("No Sent Messages.");
				} else {
					for (Message message : sentMessages) {
						System.out.println(message);
					}
				}

				System.out.println("\nReceived Messages: ");
				if (receivedMessages.isEmpty()) {
					System.out.println("No Received Messages.");
				} else {
					for (Message message : receivedMessages) {
						System.out.println(message);
					}
				}
			} else {
				System.out.println("Error: User not found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
	}

	public static void deleteMessage(int messageId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			Message message = entityManager.find(Message.class, messageId);

			if (message != null) {
				entityManager.remove(message);
				System.out.println("Message Deleted Successfully.");
			} else {
				System.out.println("Error: Message not found!");
			}
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			entityManager.close();
		}

	}

	public static void close() {
		if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
			System.out.println("EntityManagerFactory closed succesfully.");
		} else {
			System.out.println("EntityManagerFactory is already closed or not initialized.");
		}

	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean runLoop = true;

		do {
			System.out.println("Available Options\nType");
			System.out.println("1. Insert User\n" + "2. Send Message\n" + "3. View Messages By User Id\n"+ "4. Delete Message\n" + "5. Close Entities\n"
					+ "6. Exit Program");
			System.out.println("Enter the choice: ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Enter Username: ");
				sc.nextLine(); // consumes newline
				String username = sc.nextLine();
				insertUser(username);
				break;

			case 2:
				System.out.println("Enter Sender ID: ");
				int senderId = sc.nextInt();
				System.out.println("Enter Recipient ID: ");
				int recipientId = sc.nextInt();
				System.out.println("Enter Message Content: ");
				sc.nextLine(); // consumes newLine
				String content = sc.nextLine();
				sendMessage(senderId, recipientId, content);
				break;
				
			case 3:
				System.out.println("Enter User Id to view messages: ");
				int userId = sc.nextInt();
				viewMessagesById(userId);
				break;

			case 4:
				System.out.println("Enter Message Id to Delete: ");
				int messageId = sc.nextInt();
				deleteMessage(messageId);
				break;

			case 5:
				close();
				break;

			case 6:
				runLoop = false;
				close();
				System.out.println("Exiting the program.....");
				break;

			default:
				System.out.println("Invalid choice. Please choose a valid option.");
			}

		} while (runLoop);

		sc.close();

	}

}
