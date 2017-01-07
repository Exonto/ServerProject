package com.gmail.tylersyme.asciicards.connection.communication.cmdsrequests;

import java.util.Arrays;
import java.util.List;

/**
 * This class is used to help format and parse more complicated arguments sent
 * by commands by providing several static methods.
 */
public class ArgumentHelper
{
	/**
	 * Takes a list of strings and converts it into a single string argument
	 * which can be passed with a command and then parsed on the other side.<br>
	 * An empty list is acceptable and will be converted.
	 * 
	 * @param list The list to be converted
	 * @return The resulting argument string
	 */
	public static String convertList(List<String> list)
	{
		StringBuilder builder = new StringBuilder(
				"[" +
				 list
				.stream()
				.map((requestUsername) -> requestUsername + ",")
				.reduce((accumulation, nextUsername) -> accumulation + nextUsername)
				.orElse("")
				+ "]"
		);
		
		return builder.toString();
	}
	
	/**
	 * Converts a string argument back into a list of string arguments.
	 * 
	 * @param listArg The argument to be converted
	 * @return The resulting {@code List<String>}
	 */
	public static List<String> convertToList(String listArg)
	{
		StringBuilder builder = new StringBuilder(listArg);
		
		// Remove '[]' brackets
		builder.deleteCharAt(0).deleteCharAt(builder.length() - 1);
		
		// Split arguments and convert that array to a list
		List<String> result = Arrays.asList(builder.toString().split(","));
		
		return result;
	}
}
