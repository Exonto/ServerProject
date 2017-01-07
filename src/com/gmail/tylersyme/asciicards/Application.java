package com.gmail.tylersyme.asciicards;

import com.gmail.tylersyme.asciicards.connection.ServerConnection;
import com.gmail.tylersyme.asciicards.sql.MySQL;

/*
 * Todo List:
 * 
 * Make sure user's cannot use ":" in their username. This is used to separate
 * packet information.
 */

public class Application
{
	private static Application app;
	
	public static void main(String[] args)
	{
		getApp().launch();
	}
	
	public static Application getApp()
	{
		if (app == null)
		{
			app = new Application();
		}
		
		return app;
	}

// -----------------------------------------------------------------------------
	
	private Application()
	{
		// Replace the 'x' with database password
		MySQL.serverLaunch("root", "xxxxxxxxxxx");
	}
	
	/**
	 * The launching point of the application.
	 */
	private void launch()
	{
		ServerConnection conn = new ServerConnection();
		conn.launch();
	}
	
}









