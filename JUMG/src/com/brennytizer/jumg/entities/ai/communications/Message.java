package com.brennytizer.jumg.entities.ai.communications;

import com.brennytizer.jumg.utils.Words;

public class Message {
	public String messageName;
	public String sender;
	public String receiver;
	public long timestamp;
	public long delay;
	public String data;
	
	public Message(String messageName, String sender, String receiver, long timestamp, long delay, String data) {
		this.messageName = messageName;
		this.sender = sender;
		this.receiver = receiver;
		this.timestamp = timestamp;
		this.delay = delay;
		this.data = data;
	}

	public String getMessageName() {
		return messageName;
	}
	public String getSender() {
		return sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public long getDelay() {
		return delay;
	}
	public String getData() {
		return data;
	}
	
	public String constructMessage() {
		return Words.insert("messageName:[{0}], sender:[{1}], receiver:[{2}], timestamp:[{3}], delay:[{4}], data:[{5}]", getMessageName(), getSender(), getReceiver(), getTimestamp(), getDelay(), getData());
	}
	public static Message deconstructMessage(String message) {
		String m = "", s = "", r = "", d = "";
		long t = 0, de = 0;
		String[] data = message.split(", ");
		for(int i = 0; i < data.length; i++) {
			String[] split = data[i].split(":");
			if(split[0].equals("messageName")) m = split[1];
			else if(split[0].equals("sender")) s = split[1];
			else if(split[0].equals("receiver")) r = split[1];
			else if(split[0].equals("timestamp")) t = Long.parseLong(split[1]);
			else if(split[0].equals("delay")) de = Long.parseLong(split[1]);
			else if(split[0].equals("data")) d = split[1];
		}
		return new Message(m, s, r, t, de, d);
	}
	public int hashCode() {
		return constructMessage().hashCode();
	}
}