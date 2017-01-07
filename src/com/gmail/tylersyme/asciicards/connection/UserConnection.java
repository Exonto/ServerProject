package com.gmail.tylersyme.asciicards.connection;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests.Commands;
import com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests.ServerCommand;

/**
 * Each new connected client is given its own {@code Thread} which will handle 
 * all of the requests sent by the client.
 */
public class UserConnection extends Thread
{
	protected ServerConnection server;
	protected User user;
	protected Socket clientSocket;
	protected ObjectOutputStream output = null;
	protected ObjectInputStream input = null;
	public UserConnection(ServerConnection server, Socket clientSocket, User user)
	{
		this.server = server;
		this.clientSocket = clientSocket;
		this.user = user;
	}
	
	@Override
	public void run()
	{
		// Establish output and input streams for communication
		try
		{
			System.out.println("Establish Communication Streams..");
			output = new ObjectOutputStream(this.clientSocket.getOutputStream());
			output.flush();
			
			input = new ObjectInputStream(this.clientSocket.getInputStream());
			System.out.println("Communication Streams Successfully Deployed..");
		} catch (SocketException connEnded) {
			System.out.println(
					"Connection to " 
					+ clientSocket.getInetAddress() 
					+ " was Dropped");
			
			// Handle User Removal Here
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		// Connection loop begins
		
		boolean isConnected = true;
		
		// Begin listening for Client requests
		String request;
		while (isConnected)
		{
			try
			{
				request = (String) input.readObject();
				
				// Process client requests
				this.process(request);
			} catch (SocketException | EOFException streamEnded) {
				// If the socket connection fails the connection is dropped
				// If the stream ends the connection is dropped
				
				System.out.println(
						"Connection to " 
						+ clientSocket.getInetAddress() 
						+ " was Dropped");
				
				// Handle forced user disconnection
				this.disconnect();
				
				isConnected = false; // Closes this thread
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
// -----------------------------------------------------------------------------
// IO Communication Between Server and Client
// -----------------------------------------------------------------------------
	
	/**
	 * Sends a plain {@code String} packet to the client.
	 * 
	 * @param packet
	 */
	public void send(String packet)
	{
		try
		{
			this.output.writeObject(packet);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void sendCommand(ServerCommand cmd, String... args)
	{
		try
		{
			this.output.writeObject(cmd.toPacket(args).orElseThrow(
					() -> new IllegalArgumentException("The arguments supplied to this request were invalid.")));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void process(String clientRequest)
	{
		Commands.evaluateUnparsed(this, clientRequest);
	}
	
	/**
	 * Disconnects this user connection from the server.
	 */
	public void disconnect()
	{
		this.server.disconnectUser(this.user);
	}
	
// -----------------------------------------------------------------------------
// Getters and Setters
// -----------------------------------------------------------------------------
	
	public ServerConnection getServer()
	{
		return this.server;
	}
	
	public User getUser()
	{
		return this.user;
	}
}













