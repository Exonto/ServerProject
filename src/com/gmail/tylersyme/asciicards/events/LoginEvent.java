package com.gmail.tylersyme.asciicards.events;

public class LoginEvent
{
	private boolean successful;
	private String username;
	
	public LoginEvent(boolean successful, String username)
	{
		this.setSuccessful(successful);
		this.username = username;
	}
	
// -----------------------------------------------------------------------------
// Getters and Setters
// -----------------------------------------------------------------------------

	/**
	 * Returns whether the login attempt was successful.
	 */
	public boolean isSuccessful()
	{
		return successful;
	}

	public void setSuccessful(boolean successful)
	{
		this.successful = successful;
	}
	
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
	
}
