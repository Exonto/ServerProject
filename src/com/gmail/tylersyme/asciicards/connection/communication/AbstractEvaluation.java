package com.gmail.tylersyme.asciicards.connection.communication;

import java.util.ArrayList;
import java.util.List;

public class AbstractEvaluation<T>
{
	private List<AbstractCase<T>> cases = new ArrayList<>();
	public AbstractEvaluation()
	{
		
	}
	
	public AbstractCase<T> addCase(int totalArgs)
	{
		AbstractCase<T> newCase = new AbstractCase<T>();
		this.cases.add(newCase);
		
		return newCase;
	}

	
	private class AbstractCase<T>
	{
		
	}
}
