package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;

import com.gmail.tylersyme.asciicards.connection.UserConnection;
import com.gmail.tylersyme.asciicards.sql.MySQL;

public class RegisterAccountRequest extends ClientRequest
{
	RegisterAccountRequest() 
	{ 
		super();
		
		this.setName("register_account_request");
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
			// Send error message
		}
		
		if (username.length() > 16 || password.length() > 20)
		{
			client.sendCommand(Commands.REGISTER_ACCOUNT_CMD, "false", "invalid_field");
		} else if (MySQL.usernameExists(username) == false)
		{
			MySQL.createNewUser(args[0], args[1]);
			
			client.sendCommand(Commands.REGISTER_ACCOUNT_CMD, "true", username);
		} else {
			client.sendCommand(Commands.REGISTER_ACCOUNT_CMD, "false", "user_exists");
		}
	}
}
