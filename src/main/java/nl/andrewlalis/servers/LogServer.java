package nl.andrewlalis.servers;

import nl.andrewlalis.UdpCommunicator;
import nl.andrewlalis.message.Message;

import java.net.InetAddress;
import java.util.logging.Logger;

public class LogServer extends UdpCommunicator {
	private static final Logger logger = Logger.getLogger(LogServer.class.getName());

	/**
	 * Constructs a new server, and initializes the socket.
	 *
	 * @param port The port on which to start this server.
	 */
	public LogServer(int port) {
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
		logger.info("Received message from " + senderAddress + ":" + senderPort + " -> \"" + message.toString() + "\"");
	}
}
