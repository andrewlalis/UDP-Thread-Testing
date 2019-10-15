package nl.andrewlalis.message;

/**
 * Represents some different types of messages that could be sent between clients and servers.
 */
public enum MessageType {
	CONNECT(0),
	GENERAL(1),
	DISCONNECT(2),
	SHUTDOWN(3);

	/**
	 * A simple integer identifier for each message type.
	 */
	private int id;

	MessageType(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static MessageType getType(int id) {
		switch (id) {
			case 0:
				return CONNECT;
			case 1:
				return GENERAL;
			case 2:
				return DISCONNECT;
			case 3:
				return SHUTDOWN;
			default:
				return null;
		}
	}
}
