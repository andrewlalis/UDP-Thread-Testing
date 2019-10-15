package nl.andrewlalis.message;

import java.util.Arrays;

/**
 * Represents a single message that can be sent between a client and a server.
 */
public class Message {
	/**
	 * The maximum length of content for a message.
	 */
	public static final int CONTENT_LENGTH = 128;

	/**
	 * The type of message.
	 */
	private MessageType type;

	/**
	 * The message content.
	 */
	private String content;

	public Message() {
		this.type = null;
		this.content = null;
	}

	/**
	 * Constructs a new message.
	 * @param type The type of message.
	 * @param content The content for the message.
	 */
	public Message(MessageType type, String content) {
		this.type = type;
		this.content = content;
	}

	/**
	 * @return The type of message.
	 */
	public MessageType getType() {
		return this.type;
	}

	/**
	 * @return The content of the message.
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * @return An ultra-minimalistic byte-representation of this message.
	 */
	public byte[] toByteArray() {
		byte[] array = new byte[this.content.length() + 2]; // Allocate just enough space for metadata and content.
		array[0] = (byte) this.type.getId(); // The first byte is always dedicated to the message type.
		byte[] contentBytes = this.content.getBytes(); // All remaining bytes are the message content.
		array[1] = (byte) contentBytes.length;
		System.arraycopy(contentBytes, 0, array, 2, contentBytes.length); // Copy content into the array.
		return array; // And finally return the result.
	}

	/**
	 * Populates this message's properties from a given byte array.
	 *
	 * @param bytes An array of bytes representing a message.
	 */
	public void fromByteArray(byte[] bytes) {
		this.type = MessageType.getType(bytes[0]);
		int contentLength = bytes[1];
		this.content = new String(Arrays.copyOfRange(bytes, 2, contentLength + 2));
	}

	@Override
	public String toString() {
		return this.type.toString() + " - " + this.content;
	}
}
