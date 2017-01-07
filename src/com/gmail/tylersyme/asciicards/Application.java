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
		MySQL.serverLaunch("root", "89817371a");
		
		//MySQL.getCardCollection("Tyler").forEach((item) -> System.out.println(item.getTemplate().getDescription()));
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









