package com.gmail.tylersyme.asciicards.events;

public interface LoginListener
{
	/**
	 * Should occur when the server gives the client a login specific message.<br>
	 * <b>Warning:</b> This will occur even if the login was not successful.
	 * 
	 * @param e Contains basic event information
	 */
	public void login(LoginEvent e);

}
