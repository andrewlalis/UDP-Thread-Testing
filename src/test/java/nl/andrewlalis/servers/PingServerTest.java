package nl.andrewlalis.servers;

import nl.andrewlalis.message.Message;
import nl.andrewlalis.message.MessageType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the functionality of the ping server, and its ability to respond by sending an exact message back to whoever
 * sent the message.
 */
class PingServerTest {
	/**
	 * Tests the ability of the ping server to respond with the exact message that was sent.
	 */
	@Test
	void testPingServer() throws IOException {
		PingServer server = new PingServer(44441);
		server.start();
		assertTrue(server.isAlive());

		// For testing, and being able to check responses, we forgo the use of a client, and do things manually.
		DatagramSocket socket = new DatagramSocket(44440);
		Message msg = new Message(MessageType.GENERAL, "Hello world!");
		byte[] bytes = msg.toByteArray();
		DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getLocalHost(), server.getPort());
		socket.send(packet);

		// Immediately after sending a packet prepare to receive the response from the ping server.
		byte[] receiveBuffer = new byte[256];
		DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
		socket.receive(receivePacket);
		Message receivedMessage = new Message();
		receivedMessage.fromByteArray(receiveBuffer);

		// Assert some basic properties of the received packet to ensure that it's all the same.
		assertEquals(44441, receivePacket.getPort());
		assertEquals("Hello world!", receivedMessage.getContent());
		assertEquals(MessageType.GENERAL, receivedMessage.getType());
	}
}
