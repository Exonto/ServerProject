package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;


public class PlayerProfileCmd extends ServerCommand
{
	PlayerProfileCmd() 
	{ 
		this.setName("player_profile_cmd");
	}

	public boolean validate(String... args)
	{
		return true;
	}
}
