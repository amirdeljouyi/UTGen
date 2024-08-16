package com.lts.swing.table.rowmodel;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class TableModelListenerAdaptor implements TableModelListener
{
	@SuppressWarnings("unused")
	private TableModelEvent myEvent;
	
	public void tableChanged(TableModelEvent e)
	{
		myEvent = e;
		
		switch(e.getType())
		{
			case TableModelEvent.DELETE :
				delete(e.getFirstRow(), e.getLastRow());
				break;
				
			case TableModelEvent.INSERT :
				insert(e.getFirstRow(), e.getLastRow());
				break;
				
			case TableModelEvent.UPDATE :
			{
				//
				// A complete change is represented by an update with a first row
				// of Integer.MIN_VALUE and a last row of Integer.MAX_VALUE
				//
				if (e.getFirstRow() == Integer.MIN_VALUE
						&& e.getLastRow() == Integer.MAX_VALUE)
				{
					allChanged();
				}
				else
				{
					update(e.getFirstRow(), e.getLastRow());
				}
				break;
			}
			
			default :
				break;
		}
	}

	protected void update(int firstRow, int lastRow)
	{}

	protected void delete(int firstRow, int lastRow)
	{}

	protected void insert(int firstRow, int lastRow)
	{}
	
	protected void allChanged()
	{}

}
