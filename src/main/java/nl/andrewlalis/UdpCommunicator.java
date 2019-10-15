package nl.andrewlalis;

import nl.andrewlalis.message.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Represents any object that sends and receives messages via UDP.
 */
public abstract class UdpCommunicator extends Thread {
	/**
	 * Whether or not the server is running.
	 */
	private volatile boolean running = false;

	/**
	 * The socket that's used for receiving and sending connections.
	 */
	protected DatagramSocket socket;

	/**
	 * Constructs a new server, and initializes the socket.
	 */
	public UdpCommunicator(int port) {
		try {
			this.socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return The port that this server is listening for messages on.
	 */
	public int getPort() {
		return this.socket.getLocalPort();
	}

	/**
	 * What to do when a message is received.
	 *
	 * @param message The message that was received.
	 * @param senderAddress The address from which the message was sent.
	 * @param senderPort The port from which the message was sent.
	 */
	public abstract void onMessageReceived(Message message, InetAddress senderAddress, int senderPort);

	/**
	 * Sends a message to the given address and port.
	 *
	 * @param message The message to send.
	 * @param toAddress The address to send the message to.
	 * @param toPort The port to send the message to.
	 */
	public void sendMessage(Message message, InetAddress toAddress, int toPort) {
		byte[] bytes = message.toByteArray();
		DatagramPacket packet = new DatagramPacket(bytes, bytes.length, toAddress, toPort);
		try {
			this.socket.send(packet);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Runs the server, listening for and responding to user input.
	 */
	@Override
	public void run() {
		this.running = true;
		while (this.running) {
			System.out.println("Running is true.");
			try {
				byte[] buffer = new byte[256];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				this.socket.receive(packet); // Block until a packet is received.

				Message message = new Message();
				message.fromByteArray(buffer);
				this.onMessageReceived(message, packet.getAddress(), packet.getPort()); // Delegate event handling to its own method.
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		System.out.println("closed.");
		this.socket.close();
	}

	/**
	 * Closes the server, and quits accepting new requests.
	 */
	public synchronized void close() {
		this.running = false;
		this.stop(); // I know this is deprecated, but just to show an example.
	}
}
