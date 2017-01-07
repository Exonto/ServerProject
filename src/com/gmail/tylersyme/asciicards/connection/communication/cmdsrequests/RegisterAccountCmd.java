package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;


public class RegisterAccountCmd extends ServerCommand
{
	RegisterAccountCmd() 
	{ 
		this.setName("register_account_cmd");
	}
	
	public boolean validate(String... args)
	{
		return args.length == 1 || args.length == 2;
	}
}
