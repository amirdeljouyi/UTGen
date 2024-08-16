package com.lts.swing.table.dragndrop.test;

import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class TestTransferHandler extends ProxyTransferHandler
{

	@Override
	public boolean canImport(TransferSupport arg0)
	{
		super.canImport(arg0);
		return true;
	}

	@Override
	public int getSourceActions(JComponent arg0)
	{
		super.getSourceActions(arg0);
		return COPY_OR_MOVE;
	}

	@Override
	protected Transferable createTransferable(JComponent arg0)
	{
		super.createTransferable(arg0);
		return new DummyTransferable();
	}
	
	
	
}
