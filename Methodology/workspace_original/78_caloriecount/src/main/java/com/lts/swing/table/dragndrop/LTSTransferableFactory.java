package com.lts.swing.table.dragndrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;

public class LTSTransferableFactory extends TransferableFactory
{
	@Override
	public boolean canImport(JComponent component, DataFlavor[] flavors)
	{
		return LTSTransferable.canImport(component, flavors);
	}

	@Override
	public Transferable createTransferable(JComponent component)
	{
		return new LTSTransferable(component);
	}
}
