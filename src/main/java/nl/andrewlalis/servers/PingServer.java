package nl.andrewlalis.servers;

import nl.andrewlalis.UdpCommunicator;
import nl.andrewlalis.message.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * A server that replies to any received messages with the message itself.
 */
public class PingServer extends UdpCommunicator {
	/**
	 * Constructs a new server, and initializes the socket.
	 *
	 * @param port The port for the server.
	 */
	public PingServer(int port) {
		super(port);
	}

	/**
	 * Sends the received message back to the sender.
	 *
	 * @param message The message that was received.
	 * @param senderAddress The address from which the message was sent.
	 * @param senderPort The port from which the message was sent.
	 */
	@Override
	public void onMessageReceived(Message message, InetAddress senderAddress, int senderPort) {
		byte[] bytes = message.toByteArray();
		DatagramPacket packet = new DatagramPacket(bytes, bytes.length, senderAddress, senderPort);
		try {
			this.socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
