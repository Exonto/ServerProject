package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;


public class FriendAcceptedCmd extends ServerCommand
{
	FriendAcceptedCmd() 
	{ 
		this.setName("friend_accepted_cmd");
	}

	public boolean validate(String... args)
	{
		return true;
	}
}
