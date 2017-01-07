package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;

import com.gmail.tylersyme.asciicards.connection.User;
import com.gmail.tylersyme.asciicards.connection.UserConnection;
import com.gmail.tylersyme.asciicards.sql.MySQL;

public class FriendRequestRequest extends ClientRequest
{
	FriendRequestRequest() 
	{ 
		super();
		
		this.setName("friend_request_request");
	}

	@Override
	public void evaluate(UserConnection client, String... args)
	{
		String sender = client.getUser().getUsername();
		String receiver = args[0];
		
		if (sender.equals(receiver)) 
		{
			client.sendCommand(
					Commands.FRIEND_REQUEST_CMD, 
					"false", 
					"self_friend_request");
		} else if (MySQL.usernameExists(receiver) == false)
		{
			client.sendCommand(
					Commands.FRIEND_REQUEST_CMD, 
					"false", 
					"user_not_found");
		} else if (MySQL.hasFriendRequest(sender, receiver) || 
				   MySQL.hasFriendRequest(receiver, sender)) {
			client.sendCommand(
					Commands.FRIEND_REQUEST_CMD, 
					"false", 
					"user_has_request");
		} else if (MySQL.hasFriend(sender, receiver)) { // If already friends
			client.sendCommand(
					Commands.FRIEND_REQUEST_CMD, 
					"false", 
					"user_has_friend");
		} else {
			MySQL.addFriendRequest(sender, receiver);
			
			// Confirm to the sender that the request was sent
			client.sendCommand(
					Commands.FRIEND_REQUEST_CMD, 
					"true", 
					sender, 
					receiver);
			
			// Send the receiver the friend request if they are online
			if (client.getServer().isUserOnline(receiver))
			{
				User onlineReceiver = client.getServer().getUser(receiver).get();
				onlineReceiver.getConnection().sendCommand(
						Commands.FRIEND_REQUEST_CMD, 
						"true", 
						sender, 
						receiver);
			}
		}
	}
}





