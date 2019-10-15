package nl.andrewlalis.clients;

import nl.andrewlalis.message.Message;
import nl.andrewlalis.message.MessageType;

import java.net.InetAddress;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMessageSender extends Client {

	private Thread sendingThread;

	public RandomMessageSender(int port, InetAddress serverAddress, int serverPort) {
		super(port);
		this.sendingThread = new Thread(() -> {
			while (true) {
				ThreadLocalRandom rand = ThreadLocalRandom.current();
				int sleepTime = rand.nextInt(1000, 5000);
				System.out.println("Sleeping for " + sleepTime + " milliseconds.");
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sendMessage(new Message(MessageType.GENERAL, getRandomMessage()), serverAddress, serverPort);
			}
		});
	}

	@Override
	public synchronized void start() {
		super.start();
		this.sendingThread.start();
	}

	private String getRandomMessage() {
		StringBuilder sb = new StringBuilder();
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		for (int i = 0; i < 16; i++) {
			sb.append((char) rand.nextInt(65, 91));
		}
		return sb.toString();
	}

	@Override
	public void close() {
		super.close();
		this.sendingThread.interrupt();
	}
}
