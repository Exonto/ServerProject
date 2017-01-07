package com.gmail.tylersyme.asciicards.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServerConnection
{
	private List<User> users = new ArrayList<>();
	
	private ServerSocket server;
	private int port = 45552;
	private boolean isClosed = false; // Prevents any new connections
	
	public ServerConnection() { }
	public ServerConnection(int port)
	{
		this.port = port;
	}
	
	/**
	 * Causes the server to start and will accept incoming connections.
	 */
	public void launch()
	{
		try
		{
			System.out.println("Starting Server on Port: " + port);
			server = new ServerSocket(port);
			
			// Handle Connection Requests
			acceptIncomingConnections();
			
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
// -----------------------------------------------------------------------------
// User Interaction
// -----------------------------------------------------------------------------
	
	/**
	 * Causes the {@code User} to be disconnected from the server.
	 * 
	 * @returns If the given {@code User} existed and was disconnected
	 */
	public synchronized boolean disconnectUser(User user)
	{
		boolean successful = true;
		
		// If the user has logged in, remove them from the list
		if (user.isLoggedIn())
		{
			successful = this.users.remove(user);
		}
		
		try
		{
			user.getConnection().clientSocket.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return successful;
	}
	
	/**
	 * Causes the {@code User} with the given {@code username} to be 
	 * disconnected from the server.
	 * 
	 * @returns If the given {@code User} existed and was disconnected
	 */
	public synchronized boolean disconnectUser(String username)
	{
		return this.disconnectUser(this.getUser(username)
				   		.orElseThrow(() -> new NullPointerException()));
	}
	
	/**
	 * Returns the {@code User} based upon their {@code username}.
	 * 
	 * @param username
	 * @return Returns an {@code Optional<User>} where the value may be null
	 * 		   if the desired {@code User} is not online.
	 */
	public synchronized Optional<User> getUser(String username)
	{
		return this.users
				   .stream()
				   .filter((v1) -> v1.getUsername().equals(username))
				   .findFirst();
	}
	
	/**
	 * Returns whether a {@code User} with the given {@code username} is 
	 * currently online.
	 * 
	 * @param username
	 * @return True if online, False if not online
	 */
	public synchronized boolean isUserOnline(String username)
	{
		
		return this.users
				   .stream()
				   .filter((user) -> user.isLoggedIn() &&
						   			 user.getUsername().equals(username))
				   .count() > 0;
	}
	
// -----------------------------------------------------------------------------
// Server Thread (Accepts Incoming Connections)
// -----------------------------------------------------------------------------

	/**
	 * When a player attempts to login, a connection request must first be 
	 * accepted. Following this, a login request should be received by the 
	 * server. If the login attempt is successful, the connection will remain.
	 * If, however, the login attempt fails, the connection will be dropped.
	 */
	private void acceptIncomingConnections()
	{
		Thread acceptConnections = new Thread(new Runnable() {
			
			@Override
			public void run()
			{
				System.out.println("Accepting Incoming Connections..");
				try
				{
					// Constantly accepts connection requests
					while (isClosed == false)
					{
						// Waits here until a connection is established
						Socket newConnection = server.accept();
						System.out.println("Connection Made To: " + 
								newConnection.getInetAddress().toString());
						
						User newUser = new User();
						
						// Begin listening for client requests on a new Thread
						UserConnection userConn = 
								new UserConnection(
										ServerConnection.this, 
										newConnection,
										newUser);
						
						newUser.setConnection(userConn);
						
						// Keep a list of all players currently connected to
						// the server
						users.add(newUser);
						
						userConn.start(); //Begin connection thread
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
		acceptConnections.start();
	}
	
}














