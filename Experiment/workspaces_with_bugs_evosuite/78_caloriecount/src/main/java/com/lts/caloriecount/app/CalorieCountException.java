package com.lts.caloriecount.app;


@SuppressWarnings("serial")
public class CalorieCountException extends Exception
{
	protected Reasons myReason;
	
	public enum Reasons
	{
		CouldNotCreateDocumentBuilder,
		DuplicateFrequentFood,
		FrequentFoodNotFound
	}
	
	public CalorieCountException(Reasons reason)
	{
		super(reason.toString());
		initialize(reason);
	}
	
	public CalorieCountException(Reasons reason, Throwable cause)
	{
		super(reason.toString(), cause);
		initialize(reason);
	}
	
	
	protected void initialize(Reasons reason)
	{
		myReason = reason;
	}
}
