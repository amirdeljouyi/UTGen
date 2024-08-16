package com.lts.swing.table.rowmodel;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.lts.application.data.coll.ADCEvent.EventType;
import com.lts.datalist.DataList;
import com.lts.swing.table.TableModelHelper;
import com.lts.util.notifyinglist.ListEvent;
import com.lts.util.notifyinglist.NotifyingListListener;


/**
 * Translate DataList events to TableModel events.
 * 
 * <P>
 * This class translates changes in a {@link DataList} to {@link TableModelListener} events.
 * This allows things like a JTable to update themselves to stay in sync with the 
 * underlying data. 
 * </P>
 * 
 * @author cnh
 */
public class RowModelDataListBridge 
	extends RowModelTableModel 
	implements NotifyingListListener
{
	protected DataList myDataList;
	
	public RowModelDataListBridge()
	{
		initialize();
	}

	public RowModelDataListBridge(DataList dataList, RowModel rowModel)
	{
		initialize(dataList, rowModel);
	}
	
	protected void initialize(DataList dataList, RowModel rowModel)
	{
		super.initialize();
		myDataList = dataList;
		myRowModel = rowModel;
		myDataList.addListener(this);
	}

	@Override
	public int getRowCount()
	{
		return myDataList.size();
	}

	@Override
	public Object getRowData(int rowIndex)
	{
		return myDataList.get(rowIndex);
	}

	/**
	 * An entry in the data list was changed, but its position in the list did 
	 * not change.
	 * 
	 * <P>
	 * This method responds by sending an {@link TableModelEvent#UPDATE} event.
	 * </P>
	 * 
	 * @param event The event type is expected to be {@link EventType#Update}.
	 */
	private void dataListUpdate(ListEvent event)
	{
		myHelper.fireRowUpdated(event.index);
	}

	/**
	 * An entry in the data list was removed.
	 * 
	 * <P>
	 * This method responds by sending an {@link TableModelEvent#DELETE} event.
	 * </P>
	 * 
	 * @param event The event type is expected to be {@link EventType#Delete}.
	 */
	private void dataListDelete(ListEvent event)
	{
		myHelper.fireRowRemoved(event.index);
	}

	/**
	 * An entry in the data list was added.
	 * 
	 * <P>
	 * This method responds by sending an {@link TableModelEvent#INSERT} event.
	 * </P>
	 * 
	 * @param event The event type is expected to be {@link EventType#Insert}.
	 */
	private void dataListCreate(ListEvent event)
	{
		myHelper.fireRowAdded(event.index);
	}

	/**
	 * The entire data list changed.
	 * 
	 * <P>
	 * This method responds by using {@link TableModelHelper#fireTableChanged()}. 
	 * </P>
	 * 
	 * @param event The event type is expected to be {@link EventType#AllChanged}.
	 */
	private void dataListAllChanged(ListEvent event)
	{
		myHelper.fireTableChanged();
	}

	@Override
	public void listEvent(ListEvent event)
	{
		switch(event.eventType)
		{
			case AllChanged :
				dataListAllChanged(event);
				break;
				
			case Delete :
				dataListDelete(event);
				break;
				
			case Insert :
				dataListCreate(event);
				break;
				
			case Update :
				dataListUpdate(event);
				break;
				
			default :
				throw new IllegalArgumentException(event.eventType.toString());
		}
	}
}
