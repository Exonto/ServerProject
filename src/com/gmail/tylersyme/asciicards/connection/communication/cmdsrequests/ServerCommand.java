package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;

import java.util.Optional;

/**
 * A {@code ServerCommand} represents a command that can be sent to the client
 * which will cause the client to react.
 */
public abstract class ServerCommand
{
	private String name;
	
	/**
	 * Returns whether the given arguments in the given order are valid for the
	 * {@code ServerCommand}.
	 */
	public abstract boolean validate(String... args);
	
	/**
	 * Converts the command and its arguments into a single {@code String} which
	 * can be output to the Server as a packet request.
	 * 
	 * @param args The arguments, in order, which the command should passed
	 * @return An {@code Optional} which may contain the packet in {@code String}
	 * 		   form or will be empty if the arguments given to it were invalid.
	 */
	public Optional<String> toPacket(String... args)
	{
		Optional<String> packet = Optional.empty();
		if (this.validate(args))
		{
			String packetStr = this.name;
			for (String arg : args)
			{
				packetStr += ":" + arg;
			}
			
			packet = Optional.of(packetStr);
		}
		
		return packet;
	}
	
// -----------------------------------------------------------------------------
// Getters and Setters
// -----------------------------------------------------------------------------
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}


















