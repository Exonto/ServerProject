package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;

import com.gmail.tylersyme.asciicards.connection.UserConnection;
import com.gmail.tylersyme.asciicards.sql.MySQL;

public class LoginRequest extends ClientRequest
{
	LoginRequest() 
	{ 
		super();
		
		this.setName("login_request");
	}

	@Override
	public void evaluate(UserConnection client, String... args)
	{
		String username = null;
		String password = null;
		
		try 
		{
			username = args[0];
			password = args[1];
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		
		// Determine if username and password are valid
		if (MySQL.isValidLogin(username, password))
		{
			// Checks if the user has already logged into an account
			if (client.getServer().isUserOnline(username) == false)
			{
				client.getUser().login(username);
				
				client.sendCommand(Commands.LOGIN_CMD, "true", username);
			} else {
				client.sendCommand(Commands.LOGIN_CMD, "false", "already_online");
			}
		} else {
			client.sendCommand(Commands.LOGIN_CMD, "false", "invalid_credentials");
		}
	}
}
