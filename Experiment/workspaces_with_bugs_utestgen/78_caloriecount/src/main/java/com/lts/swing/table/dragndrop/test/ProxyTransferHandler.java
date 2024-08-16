package com.lts.swing.table.dragndrop.test;

@SuppressWarnings("serial")
public class ProxyTransferHandler
    extends javax.swing.TransferHandler

{
    public boolean canImport(
        javax.swing.JComponent arg0,
        java.awt.datatransfer.DataFlavor[] arg1
    )
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.canImport(arg0, arg1);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public boolean canImport(javax.swing.TransferHandler.TransferSupport arg0)
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.canImport(arg0);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    protected java.awt.datatransfer.Transferable createTransferable(javax.swing.JComponent arg0)
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.createTransferable(arg0);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public void exportAsDrag(
        javax.swing.JComponent arg0,
        java.awt.event.InputEvent arg1,
        int arg2
    )
    {
        try
        {
            EventLog.getInstance().enterMethod();
            super.exportAsDrag(arg0, arg1, arg2);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    protected void exportDone(
        javax.swing.JComponent arg0,
        java.awt.datatransfer.Transferable arg1,
        int arg2
    )
    {
        try
        {
            EventLog.getInstance().enterMethod();
            super.exportDone(arg0, arg1, arg2);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public void exportToClipboard(
        javax.swing.JComponent arg0,
        java.awt.datatransfer.Clipboard arg1,
        int arg2
    )
    {
        try
        {
            EventLog.getInstance().enterMethod();
            super.exportToClipboard(arg0, arg1, arg2);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public int getSourceActions(javax.swing.JComponent arg0)
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.getSourceActions(arg0);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public javax.swing.Icon getVisualRepresentation(java.awt.datatransfer.Transferable arg0)
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.getVisualRepresentation(arg0);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public boolean importData(
        javax.swing.JComponent arg0,
        java.awt.datatransfer.Transferable arg1
    )
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.importData(arg0, arg1);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public boolean importData(javax.swing.TransferHandler.TransferSupport arg0)
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.importData(arg0);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public boolean equals(java.lang.Object arg0)
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.equals(arg0);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public java.lang.String toString()
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.toString();
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
}
