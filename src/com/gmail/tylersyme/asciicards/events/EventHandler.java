package com.gmail.tylersyme.asciicards.events;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * This is designed to be the "manager" of any events which occur throughout the
 * program. It handles lists of classes which desire to be alerted when a 
 * certain event occurs.
 * </p>
 * <p>
 * If a class wishes to be alerted whenever a certain event occurs, it must 
 * first ask this {@code EventHandler} to be added to the alert list. Whenever
 * that particular event occurs, this handler will alert every class listening 
 * for that event.
 * </p>
 * <p>
 * It is acceptable to have a class which calls an event and also listens for
 * that same event. 
 * </p>
 */
public class EventHandler
{
	private static EventHandler handler; // The static instance
	
	/**
	 * Returns a static instance of this handler.
	 */
	public static EventHandler getHandler()
	{
		if (handler == null) 
		{
			handler = new EventHandler();
		}
		
		return handler;
	}
	
// -----------------------------------------------------------------------------
	
	private Set<LoginListener> loginListeners = new HashSet<>();
	
	private EventHandler() { }
	
// -----------------------------------------------------------------------------
	
	/**
	 * Alerts all login event listeners.
	 * 
	 * @param e Basic login information
	 */
	public void callLoginEvent(LoginEvent e)
	{
		for (LoginListener l : loginListeners)
		{
			l.login(e);
		}
	}
	
	public void addLoginListener(LoginListener listener)
	{
		this.loginListeners.add(listener);
	}

}









