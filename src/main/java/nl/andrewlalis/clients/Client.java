package nl.andrewlalis.clients;

import nl.andrewlalis.UdpCommunicator;
import nl.andrewlalis.message.Message;

import java.net.InetAddress;

/**
 * Represents a client that sends messages to a server.
 */
public class Client extends UdpCommunicator {

	public Client(int port) {
		super(port);
	}

	/**
	 * What to do when a message is received.
	 *
	 * @param message       The message that was received.
	 * @param senderAddress The address from which the message was sent.
	 * @param senderPort    The port from which the message was sent.
	 */
	@Override
	public void onMessageReceived(Message message, InetAddress senderAddress, int senderPort) {
		// Do nothing. This client only sends messages.
	}
}
