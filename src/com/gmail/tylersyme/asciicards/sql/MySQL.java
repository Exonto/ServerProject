package com.gmail.tylersyme.asciicards.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gmail.tylersyme.asciicards.connection.game.cards.Card.CardFactory;
import com.gmail.tylersyme.asciicards.connection.game.cards.CardType;
import com.gmail.tylersyme.asciicards.connection.game.cards.CreatureCard;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


public class MySQL
{
	private static MysqlDataSource dataSource;
	
// -----------------------------------------------------------------------------
	
	public static boolean isValidLogin(String username, String password)
	{
		boolean userExists = false;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = connect("ascii_cards");
			stmt = conn.prepareStatement(
					  "SELECT * FROM users " +
					  "WHERE users.username = '" + username + "' " +
					  "AND users.password = '" + password + "'");
			
			userExists = stmt.executeQuery().first();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
		
		return userExists;
	}
	
	public static void createNewUser(String username, String password)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = connect("ascii_cards");
			stmt = conn.prepareStatement(
					"INSERT INTO users (username,password) VALUES('" +
					username + "','" + password + "')"
					);
			
			stmt.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
	}
	
	public static boolean usernameExists(String username)
	{
		boolean usernameExists = false;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = connect("ascii_cards");
			stmt = conn.prepareStatement(
					  "SELECT * FROM users " +
					  "WHERE users.username = ?");
			stmt.setString(1, username);
			
			usernameExists = stmt.executeQuery().first();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
		
		return usernameExists;
	}
	
	public static void addFriendRequest(String sender, String receiver)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			
			
			conn = connect("ascii_cards");
			stmt = conn.prepareStatement(
					"INSERT INTO user_friend_requests (sender, receiver)" + 
					"	VALUES(" + 
					"		(SELECT user_id FROM users WHERE username = ?)," + 
					"       (SELECT user_id FROM users WHERE username = ?))"
					);
			stmt.setString(1, sender);
			stmt.setString(2, receiver);
			
			stmt.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
	}
	
	public static void removeFriendRequest(String sender, String receiver)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			
			
			conn = connect("ascii_cards");
			stmt = conn.prepareStatement(
					"DELETE FROM user_friend_requests WHERE sender = "
					+ "(SELECT user_id FROM users WHERE username = ?)"
					+ " AND receiver = "
					+ "(SELECT user_id FROM users WHERE username = ?)"
					);
			stmt.setString(1, sender);
			stmt.setString(2, receiver);
			
			stmt.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
	}
	
	/**
	 * Determines if the given sender's username sent a friend request to the
	 * given receiver's username.
	 * 
	 * @param sender The username of the sender
	 * @param receiver The username of the receiver
	 * @return If the sender sent a friend request to the receiver
	 */
	public static boolean hasFriendRequest(String sender, String receiver)
	{
		boolean hasFriendRequest = true;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = connect("ascii_cards");
			stmt = conn.prepareStatement(
					
					"SELECT * FROM user_friend_requests " + 
					"WHERE 		" + 
					"			user_friend_requests.sender = " + 
					"	(SELECT user_id FROM users WHERE users.username = ?) " + 
					"		AND user_friend_requests.receiver = " + 
					"   (SELECT user_id FROM users WHERE users.username = ?)"
					
					);
			stmt.setString(1, sender);
			stmt.setString(2, receiver);
			
			hasFriendRequest = stmt.executeQuery().first();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
		
		return hasFriendRequest;
	}
	
	/**
	 * Will remove a friend request containing the specified sender's username
	 * and receiver's username.
	 * 
	 * @param sender The friend request sender's username
	 * @param receiver The friend request receiver's username
	 */
	public static void deleteFriendRequest(String sender, String receiver)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			
			
			conn = connect("ascii_cards");
			stmt = conn.prepareStatement(
					"DELETE FROM user_friend_requests WHERE sender = " + 
					"		(SELECT user_id FROM users WHERE username = ?) " + 
					"		AND receiver = " + 
					"		(SELECT user_id FROM users WHERE username = ?);"
					);
			stmt.setString(1, sender);
			stmt.setString(2, receiver);
			
			stmt.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
	}
	
	/**
	 * <p>
	 * Adds a new friend row with the specified sender's username and receiver's
	 * username. They will be considered friends by both the server and the 
	 * client.
	 * </p>
	 * <p>
	 * <b>Warning:</b> This does <b>not</b> remove any corresponding friend 
	 * request. That action should be done in conjunction with this 
	 * {@code addFriend} method by using the 
	 * {@link MySQL#deleteFriendRequest(String, String)} method.
	 * </p>
	 * 
	 * @param sender
	 * @param receiver
	 */
	public static void addFriend(String sender, String receiver)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			
			
			conn = connect("ascii_cards");
			stmt = conn.prepareStatement(
					"INSERT INTO user_friends (sender, receiver)" + 
					"	VALUES(" + 
					"		(SELECT user_id FROM users WHERE username = ?)," + 
					"       (SELECT user_id FROM users WHERE username = ?))"
					);
			stmt.setString(1, sender);
			stmt.setString(2, receiver);
			
			stmt.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
	}

	/**
	 * Returns whether the given supposed sender has sent a friend request to
	 * the supposed receiver.
	 * 
	 * @param sender The supposed sender's username
	 * @param receiver The supposed receiver's username
	 * @return Whether the sender has sent the receiver a friend request
	 */
	public static boolean hasFriend(String sender, String receiver)
	{
		boolean hasFriendRequest = true;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = connect("ascii_cards");
			stmt = conn.prepareStatement(
					
					"SELECT * FROM user_friends " + 
					"WHERE  user_friends.sender = " + 
					"(SELECT user_id FROM users WHERE users.username = ?)" + 
					"	AND user_friends.receiver = " + 
					"(SELECT user_id FROM users WHERE users.username = ?)"
					
					);
			stmt.setString(1, sender);
			stmt.setString(2, receiver);
			
			hasFriendRequest = stmt.executeQuery().first();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
		
		return hasFriendRequest;
	}
	
	public static CreatureCard getCreatureCard(String qualifiedName)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String title = null;
		String description = null;
		String quote = null;
		String cardType = null;
		String manaType = null;
		Integer manaCost = null;
		Integer health = null;
		Integer cooldown = null;
		Integer damage = null;
		boolean isEnabled = false;
		
		try
		{
			conn = connect("ascii_cards");
			
			/*
			 * Gets the usernames of all players who received a friend request
			 * from the given sender.
			 */
			stmt = conn.prepareStatement(
					"SELECT * FROM cards WHERE cards.name = ?");
			stmt.setString(1, qualifiedName);
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			
			isEnabled = Boolean.parseBoolean(rs.getString("enabled"));
			if (isEnabled)
			{
				title = rs.getString("title");
				description = rs.getString("description");
				quote = rs.getString("quote");
				cardType = rs.getString("card_type");
				manaType = rs.getString("mana_type");
				manaCost = rs.getInt("mana_cost");
				health = rs.getInt("health");
				cooldown = rs.getInt("cooldown");
				damage = rs.getInt("damage");
				
				System.out.println("pls " + quote);
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
		
		return CardFactory.createNewCreatureCard(
				qualifiedName, 
				title,
				description,
				quote,
				cardType,
				manaType,
				manaCost,
				health,
				cooldown,
				damage,
				isEnabled);
	}

	public static void addUserCard(String username, String qualifiedName)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			
			
			conn = connect("ascii_cards");
			stmt = conn.prepareStatement(
					"INSERT INTO user_cards (user_id, card_id) " + 
					"VALUES" + 
					"(" + 
					"	(SELECT user_id FROM users WHERE username = ?)," + 
					"   (SELECT card_id FROM cards WHERE name = ?)" + 
					");"
					);
			stmt.setString(1, username);
			stmt.setString(2, qualifiedName);
			
			stmt.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
	}
// -----------------------------------------------------------------------------
// Get User Information
// -----------------------------------------------------------------------------
	
	public static List<String> getSentFriendRequests(String username)
	{
		List<String> friendRequests = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = connect("ascii_cards");
			
			/*
			 * Gets the usernames of all players who received a friend request
			 * from the given sender.
			 */
			stmt = conn.prepareStatement(
					
				"SELECT username FROM ascii_cards.users " + 
				"INNER JOIN ascii_cards.user_friend_requests " + 
				"ON ascii_cards.users.user_id = ascii_cards.user_friend_requests.receiver " + 
				"WHERE ascii_cards.user_friend_requests.sender = " +
				"    (SELECT user_id FROM users WHERE username = ?)"
					
				);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next())
			{
				friendRequests.add(rs.getString(1)); // Gets the username
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
		
		return friendRequests;
	}
	
	public static List<String> getReceivedFriendRequests(String username)
	{
		List<String> friendRequests = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = connect("ascii_cards");
			
			/*
			 * Gets the usernames of all players who received a friend request
			 * from the given sender.
			 */
			stmt = conn.prepareStatement(
					
				"SELECT username FROM ascii_cards.users " + 
				"INNER JOIN ascii_cards.user_friend_requests " + 
				"ON ascii_cards.users.user_id = ascii_cards.user_friend_requests.sender " + 
				"WHERE ascii_cards.user_friend_requests.receiver = " +
				"    (SELECT user_id FROM users WHERE username = ?)"
					
				);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next())
			{
				friendRequests.add(rs.getString(1)); // Gets the username
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
		
		return friendRequests;
	}

	public static List<String> getFriends(String username)
	{
		List<String> friends = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try
		{
			conn = connect("ascii_cards");
			
			/*
			 * Gets the usernames of all players who are friends with the given
			 * user's username
			 */
			stmt = conn.prepareStatement(
					
				"SELECT username FROM users " + 
				"		INNER JOIN user_friends " + 
				"		ON users.user_id = user_friends.sender " + 
				"		WHERE user_friends.receiver = " + 
				"			(SELECT user_id FROM users WHERE username = ?)"	
					
				);
			stmt.setString(1, username);
			
			stmt2 = conn.prepareStatement(
					
					"SELECT username FROM users " + 
					"		INNER JOIN user_friends " + 
					"		ON users.user_id = user_friends.receiver " + 
					"		WHERE user_friends.sender = " + 
					"			(SELECT user_id FROM users WHERE username = ?)"
					
					);
			stmt2.setString(1, username);
			
			ResultSet rs1 = stmt.executeQuery();
			ResultSet rs2 = stmt2.executeQuery();
			
			while (rs1.next())
			{
				friends.add(rs1.getString(1)); // Gets the username
			}
			
			while (rs2.next())
			{
				friends.add(rs2.getString(1)); // Gets the username
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
			closeStatement(stmt2);
		}
		
		return friends;
	}

	public static List<CardType> getCardCollection(String username)
	{
		List<CardType> cards = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = connect("ascii_cards");
			
			/*
			 * Gets the card name of every card the user owns
			 */
			stmt = conn.prepareStatement(
					
				"SELECT cards.name FROM cards " + 
				"INNER JOIN user_cards ON cards.card_id = user_cards.card_id " + 
				"WHERE user_cards.user_id = " +
				"    (SELECT user_id FROM users WHERE username = ?)"	
					
			);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			
			// Gets the CardType associated with the card name
			cards.add(CardType.getTypeByName(rs.getString("name")));
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
		
		return cards;
	}
	
// -----------------------------------------------------------------------------

	/**
	 * This should be called when this (the master server) is turned on in order
	 * to initialize important database properties and create required databases
	 * and tables if needed.
	 */
	public static void serverLaunch(String user, String pass)
	{
		// Establishes a basic data source (does not specify a database)
		establishDataSource(user, pass);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS ascii_cards");
			
			stmt.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
		
		executeStatement("ascii_cards", 
				
				"CREATE TABLE IF NOT EXISTS users("
				+ "user_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "username VARCHAR(16) NOT NULL,"
				+ "password VARCHAR(20) NOT NULL,"
				+ "UNIQUE (username)"
				+ ")"
				
				);
		
		executeStatement("ascii_cards", 
				
				"CREATE TABLE IF NOT EXISTS user_friends("
				+ "sender INT UNSIGNED NOT NULL,"
				+ "receiver INT UNSIGNED NOT NULL,"
				+ "FOREIGN KEY(sender) REFERENCES ascii_cards.users(user_id) "
				+ "ON UPDATE CASCADE ON DELETE CASCADE,"
				+ "FOREIGN KEY(receiver) REFERENCES ascii_cards.users(user_id) "
				+ "ON UPDATE CASCADE ON DELETE CASCADE)"
				
				);
		
		executeStatement("ascii_cards", 
				
				"CREATE TABLE IF NOT EXISTS user_friend_requests("
				+ "sender INT UNSIGNED NOT NULL,"
				+ "receiver INT UNSIGNED NOT NULL,"
				+ "FOREIGN KEY(sender) REFERENCES ascii_cards.users(user_id) "
				+ "ON UPDATE CASCADE ON DELETE CASCADE,"
				+ "FOREIGN KEY(receiver) REFERENCES ascii_cards.users(user_id) "
				+ "ON UPDATE CASCADE ON DELETE CASCADE)"
				
				);
		
		executeStatement("ascii_cards", 
				
				"CREATE TABLE IF NOT EXISTS cards("
				+ "card_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "name VARCHAR(20) NOT NULL,"
				+ "title VARCHAR(20) NOT NULL,"
				+ "description VARCHAR(100) NOT NULL,"
				+ "quote VARCHAR(50) NULL,"
				+ "card_type ENUM('Creature', "
				+ "			 'Structure',"
				+ "			 'Spell',"
				+ "			 'Enchantment') "
				+ "			 NOT NULL,"
				+ "mana_type ENUM('Order', 'Growth', 'Decay') NOT NULL,"
				+ "mana_cost INTEGER NULL,"
				+ "health INTEGER NULL,"
				+ "cooldown INTEGER NULL,"
				+ "damage INTEGER NULL,"
				+ "UNIQUE (name)"
				+ ")"
				
				);
		
		executeStatement("ascii_cards", 
				
				"CREATE TABLE IF NOT EXISTS user_cards" + 
				"(" + 
				"	user_id INT UNSIGNED NOT NULL," + 
				"    card_id INT UNSIGNED NOT NULL," + 
				"    FOREIGN KEY(user_id) REFERENCES users(user_id)" + 
				"    ON UPDATE CASCADE ON DELETE CASCADE," + 
				"    FOREIGN KEY(card_id) REFERENCES cards(card_id)" + 
				"    ON UPDATE CASCADE ON DELETE CASCADE," + 
				"    UNIQUE(user_id, card_id)" + 
				");"
				
				);
		
		
	}
	
	public static void establishDataSource(String user, String pass)
	{
		dataSource = new MysqlDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(pass);
	}
	
	/**
	 * Returns a connection to the given database.
	 */
	public static Connection connect(String dbName)
	{
		try
		{
			dataSource.setDatabaseName(dbName);
			
			return dataSource.getConnection();
		} catch (SQLException e)
		{
			e.printStackTrace();
			
			return null;
		}
	}
	
	private static void executeStatement(String dbName, String sqlCode)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = connect(dbName);
			stmt = conn.prepareStatement(sqlCode);
			
			stmt.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally { // Close all connections
			closeConnection(conn);
			closeStatement(stmt);
		}
	}
	
// -----------------------------------------------------------------------------
// Close Resources Assistance
// -----------------------------------------------------------------------------
	
	private static void closeConnection(Connection conn)
	{
		try
		{
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void closeStatement(PreparedStatement stmt)
	{
		try
		{
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void closeResultSet(ResultSet resultSet)
	{
		try
		{
			resultSet.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}












