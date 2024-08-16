package com.lts.swing.table.dragndrop;

import java.awt.Component;

import javax.swing.JComponent;

public class SimplifiedTransferHandlerAdaptor implements SimplifiedTransferHandler
{
	protected boolean mySupportsCopy;
	protected boolean mySupportsCut;
	protected boolean mySupportsMove;
	protected boolean mySupportsPaste;
	
	protected void initialize()
	{
		mySupportsCopy = false;
		mySupportsCut = false;
		mySupportsMove = false;
		mySupportsPaste = false;
	}
	
	
	@Override
	public Object copy(JComponent comp)
	{
		return null;
	}

	@Override
	public boolean paste(Component comp, Object data)
	{
		return false;
	}

	@Override
	public void remove(JComponent comp, Object data)
	{}

	@Override
	public boolean supportsCopy()
	{
		return mySupportsCopy;
	}

	@Override
	public boolean supportsCut()
	{
		return mySupportsCut;
	}

	@Override
	public boolean supportsMove()
	{
		return mySupportsMove;
	}

	@Override
	public boolean supportsPaste()
	{
		return mySupportsPaste;
	}

}
