package com.lts.caloriecount.ui.dnd;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

@SuppressWarnings("serial")
public class LTSTransferHandler extends TransferHandler
{

	@Override
	public boolean canImport(TransferSupport support)
	{
		// TODO Auto-generated method stub
		return super.canImport(support);
	}

	@Override
	protected Transferable createTransferable(JComponent c)
	{
		// TODO Auto-generated method stub
		return super.createTransferable(c);
	}

	@Override
	public void exportAsDrag(JComponent comp, InputEvent e, int action)
	{
		// TODO Auto-generated method stub
		super.exportAsDrag(comp, e, action);
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action)
	{
		// TODO Auto-generated method stub
		super.exportDone(source, data, action);
	}

	@Override
	public void exportToClipboard(JComponent comp, Clipboard clip, int action)
			throws IllegalStateException
	{
		// TODO Auto-generated method stub
		super.exportToClipboard(comp, clip, action);
	}

	@Override
	public int getSourceActions(JComponent c)
	{
		// TODO Auto-generated method stub
		return super.getSourceActions(c);
	}

	@Override
	public boolean importData(TransferSupport support)
	{
		// TODO Auto-generated method stub
		return super.importData(support);
	}
	
}
