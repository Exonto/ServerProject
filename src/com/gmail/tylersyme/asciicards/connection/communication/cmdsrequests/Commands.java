package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Set;

import com.gmail.tylersyme.asciicards.connection.UserConnection;

public class Commands
{
	public static final LoginRequest LOGIN_REQUEST = new LoginRequest();
	public static final LoginCmd LOGIN_CMD = new LoginCmd();
	
	public static final RegisterAccountRequest REGISTER_ACCOUNT_REQUEST = new RegisterAccountRequest();
	public static final RegisterAccountCmd REGISTER_ACCOUNT_CMD = new RegisterAccountCmd();
	
	public static final FriendRequestRequest FRIEND_REQUEST_REQUEST = new FriendRequestRequest();
	public static final FriendRequestCmd FRIEND_REQUEST_CMD = new FriendRequestCmd();
	
	public static final FriendAcceptedRequest FRIEND_ACCEPTED_REQUEST = new FriendAcceptedRequest();
	public static final FriendAcceptedCmd FRIEND_ACCEPTED_CMD = new FriendAcceptedCmd();
	
	public static final PlayerProfileRequest PLAYER_PROFILE_REQUEST = new PlayerProfileRequest();
	public static final PlayerProfileCmd PLAYER_PROFILE_CMD = new PlayerProfileCmd();
	
// -----------------------------------------------------------------------------
	
	private Commands() { } // Cannot Instantiate
	
// -----------------------------------------------------------------------------
	
	/**
	 * Maintains a list of distinct {@code ServerCommands}.
	 */
	static Set<ClientRequest> clientRequests; 
	
	/**
	 * Takes an unparsed packet request sent by the client and both unparses and 
	 * evaluates the {@code ClientRequest} associated with the request name 
	 * contained within the packet.
	 * 
	 * @param client The connection to the server
	 * @param unparsed The unparsed packet in {@code String} form
	 * 
	 * @see ServerCommand
	 */
	public static void evaluateUnparsed(UserConnection client, String unparsed)
	{
		String[] parsed = unparsed.split(":");
		
		String requestName = parsed[0];
		String[] args = Arrays.copyOfRange(parsed, 1, parsed.length);
		
		// Get the server command from the server commands list based upon
		// the cmdName
		try
		{
			ClientRequest clientRequest = 
					clientRequests
					.stream()
					.filter((request) -> request.getName().equals(requestName))
					.findFirst()
					.orElseThrow(() -> new NoSuchElementException(
						"The client request \"" + requestName + "\" does not exist."));
			
			clientRequest.evaluate(client, args); // Executes the command
		} catch (NoSuchElementException ex) {
			ex.printStackTrace();
		}
	}
	
}












