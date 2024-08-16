package com.lts.swing.table.dragndrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;

abstract public class TransferableFactory
{
	abstract public Transferable createTransferable(JComponent component);
	abstract public boolean canImport (JComponent component, DataFlavor[] flavors);
}
