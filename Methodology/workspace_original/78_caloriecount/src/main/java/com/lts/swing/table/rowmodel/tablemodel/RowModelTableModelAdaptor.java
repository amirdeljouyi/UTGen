package com.lts.swing.table.rowmodel.tablemodel;


import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.lts.swing.table.TableModelHelper;
import com.lts.swing.table.rowmodel.RowModel;
import com.lts.util.collection.CollectionEvent;
import com.lts.util.notifyinglist.NotifyingCollection;
import com.lts.util.notifyinglist.NotifyingList;
import com.lts.util.notifyinglist.NotifyingListListenerAdaptor;


/**
 * A bridge between the world of JTables and the world of NotifyingCollections.
 * 
 * <P>
 * In essence, this class makes a {@link NotifyingCollection} look like a JTableModel.  
 * The class listens for {@link CollectionEvent}s and translates them into 
 * {@link TableModelEvent}s. 
 * </P>
 * 
 * <H3>QuickStart</H3>
 * <UL>
 * <LI>Get an instance of NotifyingCollection that contains the data</LI>
 * <LI>Get an instance of {@link RowModel} to view the data with.</LI>
 * <LI>Create an instance of this class.</LI>
 * <LI>Use the new instance as you would an instance of {@link TableModel}</LI>
 * </UL>
 * 
 * @author cnh
 */
public class RowModelTableModelAdaptor implements RowModelTableModel
{
	private class LocalListener extends NotifyingListListenerAdaptor
	{
		@Override
		public void allRowsChanged()
		{
			reload();
		}

		@Override
		public void rowDeleted(int index, Object element)
		{
			myTableModelHelper.fireRowRemoved(index);
		}

		@Override
		public void rowInserted(int index, Object element)
		{
			myTableModelHelper.fireRowAdded(index);
		}

		@Override
		public void rowUpdated(int index, Object element)
		{
			myTableModelHelper.fireRowUpdated(index);
		}
		
	}
	
	private TableModelHelper myTableModelHelper;
	private RowModelTableModelListenerHelper myRowModelHelper;
	private RowModel myRowModel;
	private NotifyingList myCollection;

	
	public RowModelTableModelAdaptor (RowModel rowModel, NotifyingList collection)
	{
		initialize(rowModel, collection);
	}
	
	protected RowModelTableModelAdaptor()
	{}
	
	protected void initialize(RowModel rowModel, NotifyingList collection)
	{
		myTableModelHelper = new TableModelHelper(this);
		myRowModelHelper = new RowModelTableModelListenerHelper();
		myCollection = collection;
		LocalListener listener = new LocalListener();
		myCollection.addListener(listener);
		myRowModel = rowModel;
		reload();
	}
	
	
	public Object getRowData(int index)
	{
		return myCollection.get(index);
	}
	
	public RowModel getRowModel()
	{
		return myRowModel;
	}
	
	public void setRowModel(RowModel model)
	{
		myRowModel = model;
	}

	public int getColumnCount()
	{
		return myRowModel.getColumnCount();
	}

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Object data = getRowData(rowIndex);
		return myRowModel.getValueAt(columnIndex, data);
	}

	public String getColumnName(int column)
	{
		return myRowModel.getColumnName(column);
	}

	public Class getColumnClass(int column)
	{
		return myRowModel.getColumnClass(column);
	}

	public boolean isCellEditable(int row, int col)
	{
		return myRowModel.isColumnEditable(col);
	}

	public void setValueAt(Object value, int row, int col)
	{
		Object o = getRowData(row);
		myRowModel.setValueAt(row, o, col, value);
		myTableModelHelper.fireRowUpdated(row);
	}
	
	public void addTableModelListener(TableModelListener l)
	{
		myTableModelHelper.addListener(l);
	}

	public void removeTableModelListener(TableModelListener l)
	{
		myTableModelHelper.removeListener(l);
	}

	public Object getRow(int index)
	{
		return getRowData(index);
	}

	public int getRowCount()
	{
		return myCollection.size();
	}

	public void addRowModelTableListener(RowModelTableModelListener listener)
	{
		myRowModelHelper.addListener(listener);
	}

	/*
	public void collectionEvent(CollectionEvent event)
	{
		switch (event.myEvent)
		{
			case add :
			{
				int index = myList.size() - 1;
				myList.add(index, event.myElement);
				myElementToRowMap.put(event.myElement, index);
				myTableModelHelper.fireRowAdded(index);
				break;
			}
			
			case allChanged :
				reload();
				break;
				
			case remove :
			{
				int index = myElementToRowMap.get(event.myElement);
				myElementToRowMap.remove(event.myElement);
				myList.remove(index);
				myTableModelHelper.fireRowRemoved(index);
				break;
			}
			
			case update :
			{
				int index = myElementToRowMap.get(event.myElement);
				myTableModelHelper.fireRowUpdated(index);
				break;
			}
		}
	}
	*/
	
	public void reload()
	{
		myTableModelHelper.fireTableChanged();
	}
}
