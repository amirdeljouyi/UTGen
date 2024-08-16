package com.lts.swing.table.dragndrop.test;

import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

public class ImportDataCalled extends RecordingEvent
{
	protected TransferHandler.TransferSupport myTransferSupport;
	
	public ImportDataCalled(TransferHandler.TransferSupport transferSupport)
	{
		initialize(transferSupport);
	}
	
	
	private void initialize(TransferSupport transferSupport)
	{
		myTransferSupport = transferSupport;
	}


	
	public void appendSubclassToString(StringBuffer sb)
	{
		if (!myTransferSupport.isDrop())
		{
			sb.append("not a drop");
		}
		else
		{
			String action = actionToString(myTransferSupport.getSourceDropActions());
			sb.append("source drop action = ");
			sb.append(action);
			
			sb.append(", drop action = ");
			action = actionToString(myTransferSupport.getDropAction());
			sb.append(action);
			
			sb.append(", user drop action = ");
			action = actionToString(myTransferSupport.getUserDropAction());
			sb.append(action);
		}
	}
}
