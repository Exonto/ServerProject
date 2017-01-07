package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;

import com.gmail.tylersyme.asciicards.connection.UserConnection;
import com.gmail.tylersyme.asciicards.sql.MySQL;

public class FriendAcceptedRequest extends ClientRequest
{
	FriendAcceptedRequest() 
	{ 
		super();
		
		this.setName("friend_accepted_request");
	}

	@Override
	public void evaluate(UserConnection client, String... args)
	{
		
		System.out.println("Friend Accept Request Received");
		// The supposed sender of the request
		// This should be verified
		String sender = args[0];
		
		// Receiver assumed to be the user accepting the friend request
		String receiver = client.getUser().getUsername();
		
		if (MySQL.hasFriendRequest(sender, receiver) == false)
		{
			client.sendCommand(
					Commands.FRIEND_ACCEPTED_CMD, 
					"false", 
					"no_friend_request");
		} else if (MySQL.hasFriend(sender, receiver)) { 
			client.sendCommand(
					Commands.FRIEND_ACCEPTED_CMD, 
					"false", 
					"has_friend");
		} else { // The friend accept request is valid
			MySQL.addFriend(sender, receiver);
			MySQL.removeFriendRequest(sender, receiver);
			
			client.sendCommand(
					Commands.FRIEND_ACCEPTED_CMD, 
					"true", 
					sender,
					receiver);
			
			// Sends the sender notice of their accepted friend request if 
			// that user is currently online
			if (client.getServer().isUserOnline(sender))
			{
				client.getServer()
					  .getUser(sender)
					  .get()
					  .getConnection()
					  .sendCommand(Commands.FRIEND_ACCEPTED_CMD, 
							  "true",
							  sender,
							  receiver);
			}
		}
	}
}





