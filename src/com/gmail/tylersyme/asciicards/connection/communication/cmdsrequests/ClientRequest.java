package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;

import java.util.HashSet;

import com.gmail.tylersyme.asciicards.connection.UserConnection;

/**
 * This represents a client's request to perform a certain action.
 */
public abstract class ClientRequest
{
	private String name;
	
	/**
	 * The client may send string packets to the server. When this occurs, the
	 * packet is parsed and sent to the corresponding {@code ClientRequest} to
	 * be evaluated. An evaluation should ensure that the request is valid 
	 * before performing any changes, permanent or otherwise.
	 * 
	 * @param client The associated client's thread
	 * @param args The arguments being passed (must be verified)
	 */
	public abstract void evaluate(UserConnection client, String... args);
	
	protected ClientRequest()
	{
		if (Commands.clientRequests == null)
		{
			Commands.clientRequests = new HashSet<ClientRequest>();
		}
		Commands.clientRequests.add(this);
	}
	
// -----------------------------------------------------------------------------
// Getters and Setters
// -----------------------------------------------------------------------------
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
