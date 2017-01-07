package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;


public class LoginCmd extends ServerCommand
{
	LoginCmd() 
	{ 
		this.setName("login_cmd");
	}

	public boolean validate(String... args)
	{
		return args.length == 1 || args.length == 2;
	}
}
