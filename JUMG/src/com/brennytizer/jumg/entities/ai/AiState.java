package com.brennytizer.jumg.entities.ai;

import com.brennytizer.jumg.entities.ai.communications.Message;
import com.brennytizer.jumg.entities.ai.communications.MessageRouter;

public abstract class AiState {
	public String stateName;
	
	public AiState(String stateName) {
		this.stateName = stateName;
	}
	
	public abstract void onEnter();
	
	public abstract void onUpdate();
	
	public abstract void onExit();
	
	public abstract void reactTo(Message message);
	
	public void sendMessage(Message message) {
		MessageRouter.routeMessage(message.constructMessage());
	}
	/*
	 * Potential to lose state control before message is returned to self.
	 * Fixed by putting "state:{statename}" as part of data, where '{statename}'
	 * is the state that is to react to this message. Must be handled by each
	 * state individually, in their `reactTo(Message)` voids accordingly.
	 */
}
