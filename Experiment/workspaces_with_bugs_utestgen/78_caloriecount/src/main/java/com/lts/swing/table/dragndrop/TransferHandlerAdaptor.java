package com.lts.swing.table.dragndrop;

import java.awt.Component;

import javax.swing.JComponent;

public class TransferHandlerAdaptor implements SimplifiedTransferHandler
{

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
	{
	}

	@Override
	public boolean supportsCopy()
	{
		return false;
	}

	@Override
	public boolean supportsCut()
	{
		return false;
	}

	@Override
	public boolean supportsMove()
	{
		return false;
	}

	@Override
	public boolean supportsPaste()
	{
		return false;
	}
}
