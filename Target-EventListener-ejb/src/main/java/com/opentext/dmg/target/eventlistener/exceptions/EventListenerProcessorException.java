package com.opentext.dmg.target.eventlistener.exceptions;

public class EventListenerProcessorException extends Exception {

	private static final long serialVersionUID = 5059668220684533766L;
	
	public EventListenerProcessorException(String message) {
        super(message);
    }
	
    public EventListenerProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}

