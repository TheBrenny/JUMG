package com.brennytizer.jumg.entities.ai;

import com.brennytizer.jumg.entities.Entity;
import com.brennytizer.jumg.entities.ai.communications.Message;
import com.brennytizer.jumg.entities.ai.communications.Poller;
import com.brennytizer.jumg.entities.ai.communications.PollingMachine;

public abstract class AiStateMachine implements Poller {
	public AiState[] states;
	public AiState activeState;
	public int nextState = 0;
	
	public AiStateMachine(AiState ... aiStates) {
		this.states = aiStates;
		this.activeState = states[0];
	}
	
	public void updateState() {
		this.activeState.onUpdate();
	}
	
	public void receiveMessage(String message) {
		this.activeState.reactTo(Message.deconstructMessage(message));
	}
	
	public void changeState() {
		this.activeState.onExit();
		this.activeState = getState(nextState);
		this.activeState.onEnter();
	}
	
	public void setNextState(int state) {
		this.nextState = state;
	}
	
	public AiState getActiveState() {
		return activeState;
	}
	
	public AiState getState(int index) {
		return this.states[index];
	}
	
	public String listToPoll() {
		return "NULL-LIST";
	}
	
	public void updatePoll() {
		for(Entity e : PollingMachine.getPollingMachine().getPollList(listToPoll())) {
			pollEntity(e);
		}
	}
}