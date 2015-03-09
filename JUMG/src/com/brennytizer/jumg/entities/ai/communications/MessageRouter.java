package com.brennytizer.jumg.entities.ai.communications;

import java.util.ArrayList;
import java.util.LinkedList;

import com.brennytizer.jumg.entities.Entity;
import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;
import com.brennytizer.jumg.utils.engine.Time;

public class MessageRouter {
	//public static OutputStream LOG_OUTPUT;
	private static MessageRouter MESSAGE_ROUTER;
	public ArrayList<Entity> allEntities;
	public LinkedList<String> messageQueue;
	
	private MessageRouter() {
		allEntities = new ArrayList<Entity>();
		messageQueue = new LinkedList<String>();
	}
	
	public static void routeMessage(String message) {
		MessageRouter mr = MessageRouter.messageRouter();
		synchronized(mr.messageQueue) {
			mr.messageQueue.add(message);
		}
	}
	
	public void update() {
		synchronized(allEntities) {
			synchronized(messageQueue) {
				for(String message : messageQueue) {
					Message m = Message.deconstructMessage(message);
					if(m.timestamp + m.delay < Time.getCurrentTime(0)) continue;
					messageQueue.remove(message);
					MessageRouter mr = MessageRouter.messageRouter();
					Entity e = null;
					if(m.receiver.equals("world")) {} else {
						for(Entity en : mr.allEntities) {
							if(en.getUid().equals(m.receiver)) {
								e = en;
								break;
							}
						}
						if(e != null) e.aiMachine.receiveMessage(message);
						else Logging.log(LoggingSpice.HOT, "Disposing message: " + message);
					}
					MessageRouter.logMessage(message);
				}
			}
		}
	}
	
	public boolean addEntityToRouteList(Entity entity) {
		synchronized(allEntities) {
			return this.allEntities.add(entity);
		}
	}
	
	public static MessageRouter messageRouter() {
		MessageRouter.MESSAGE_ROUTER = MessageRouter.MESSAGE_ROUTER == null ? new MessageRouter() : MessageRouter.MESSAGE_ROUTER;
		return MessageRouter.MESSAGE_ROUTER;
	}
	
	public static void logMessage(String message) {
		// TODO: This will be part of the debugging section!
		// Logging.writeToLogFile(MessageRouter.LOG_OUTPUT, Words.insert("[{0}]: {1}", Logging.getLoggingTimeTrace(), message));
	}
	/*
	 * public static void setLogFile(OutputStream os) {
	 * MessageRouter.LOG_OUTPUT = os;
	 * }
	 */
}