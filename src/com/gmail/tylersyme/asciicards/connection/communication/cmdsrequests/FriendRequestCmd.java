package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;


public class FriendRequestCmd extends ServerCommand
{
	FriendRequestCmd() 
	{ 
		this.setName("friend_request_cmd");
	}

	public boolean validate(String... args)
	{
		return true;
	}
}
