package nl.andrewlalis;

import nl.andrewlalis.message.Message;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.*;

class UdpCommunicatorTest {

	/**
	 * Tests the constructor of the UDP communicator, checking that only valid ports work.
	 */
	@Test
	void testConstructor() {
		assertDoesNotThrow(() -> {UdpCommunicator communicator = this.getCommunicatorImplementation(64324);});
		assertThrows(IllegalArgumentException.class, () -> {UdpCommunicator communicator = this.getCommunicatorImplementation(-54);});
		assertThrows(IllegalArgumentException.class, () -> {UdpCommunicator communicator = this.getCommunicatorImplementation(70000);});
	}

	/**
	 * Tests the running of a communicator.
	 */
	@Test
	void testRun() throws InterruptedException {
		UdpCommunicator communicator = this.getCommunicatorImplementation(43554);
		assertFalse(communicator.isAlive());
		communicator.start();
		assertTrue(communicator.isAlive());
		communicator.close();
		System.out.println("Sleeping for 2 seconds...");
		Thread.sleep(2000);
		System.out.println("Checking if communicator is alive...");
		assertFalse(communicator.isAlive());
	}

	private UdpCommunicator getCommunicatorImplementation(int port) {
		return new UdpCommunicator(port) {
			@Override
			public void onMessageReceived(Message message, InetAddress senderAddress, int senderPort) {
				// Do nothing.
			}
		};
	}
}
