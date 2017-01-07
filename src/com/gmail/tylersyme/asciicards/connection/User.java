package com.gmail.tylersyme.asciicards.connection;


/**
 * This class is primarily used for caching important player information.
 */
public class User
{
	public static int nextOpenID = 0;
	
// -----------------------------------------------------------------------------
	
	private int id = nextOpenID++;
	
	private UserConnection conn;
	private String username;
	private boolean isLoggedIn = false;
	
	public User() { }
	public User(UserConnection conn)
	{
		this.conn = conn; // Connected but not logged in
	}
	
	/**
	 * Disconnects the {@code User} from the server by calling 
	 * {@link UserConnection#disconnect()}.
	 */
	public void disconnect()
	{
		this.conn.disconnect();
	}
	
	/**
	 * <p>
	 * Logs the {@code User} as being logged in with the given {@code username}.<br>
	 * Throws an exception if the {@code User} has already logged in.
	 * </p>
	 * <p>
	 * <b>Warning:</b> There is no login logic being performed. This is designed
	 * purely to cache the User's {@code username} and other information. The 
	 * {@code User}'s login information should be validated prior to calling 
	 * this method.
	 * </p>
	 * 
	 * @param username The {@code User}'s username to be cached
	 * 
	 * @throws IllegalStateException If the {@code User} has already logged in
	 */
	public void login(String username)
	{
		System.out.println("Loggin in as " + username);
		if (this.isLoggedIn() == false)
		{
			this.username = username;
			this.isLoggedIn = true;
		} else {
			throw new IllegalStateException("This user has already logged in as \"" + this.getUsername() + "\"");
		}
	}
	
// -----------------------------------------------------------------------------
// Override Equals and Hashcode
// -----------------------------------------------------------------------------
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
// -----------------------------------------------------------------------------
// Getters and Setters
// -----------------------------------------------------------------------------

	public UserConnection getConnection()
	{
		return conn;
	}
	
	public void setConnection(UserConnection conn)
	{
		this.conn = conn;
	}

	public String getUsername()
	{
		return this.username;
	}
	
	/**
	 * Returns whether this {@code User} has been assigned a "Logged In" status.
	 */
	public boolean isLoggedIn()
	{
		return this.isLoggedIn;
	}
	
	/**
	 * Returns a unique identifier given to every {@code User}.
	 */
	public int getID()
	{
		return id;
	}
}















