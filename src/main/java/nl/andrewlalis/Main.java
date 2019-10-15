package nl.andrewlalis;

import nl.andrewlalis.clients.Client;
import nl.andrewlalis.message.Message;
import nl.andrewlalis.message.MessageType;
import nl.andrewlalis.servers.LogServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * The main class where the application starts.
 */
public class Main {
	/**
	 * The main method where the application starts.
	 *
	 * @param args The arguments provided to the application on the command-line.
	 */
	public static void main(String[] args) throws UnknownHostException {
		LogServer server = new LogServer(156); // A simple server that just logs anything it receives.
		server.start();

		Client client = new Client(4446); // The client that we'll use for sending stuff to the server.
		client.start();
		Scanner scanner = new Scanner(System.in); // The scanner will be used to get user input.

		while (client.isAlive()) {
			System.out.println("Enter a message to send:");
			String input = scanner.nextLine();

			if (input == null || input.length() > Message.CONTENT_LENGTH) {
				System.err.println("A message must not be empty, and must not be longer than " + Message.CONTENT_LENGTH + " bytes.");
				continue;
			}

			if (input.equalsIgnoreCase("stop")) {
				client.close();
				continue;
			}

			client.sendMessage(new Message(MessageType.GENERAL, input), InetAddress.getLocalHost(), server.getPort());
		}
		System.out.println("All clients disconnected.");
		server.close();
	}
}
