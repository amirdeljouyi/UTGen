package com.lts.util.notifyinglist;

import javax.swing.table.TableModel;

import com.lts.swing.table.rowmodel.RowModel;
import com.lts.swing.table.rowmodel.RowModelTableAdaptor;

/**
 * A RowModelTableAdaptor converts events from a NotifyingList into those of a TableModel.
 * <P>
 * This class makes an instance of {@link NotifyingList} look like an instance of
 * {@link TableModel} by using an instance of {@link RowModel} to translate the cells and
 * the NotifyingList to keep track of changes.
 * </P>
 * <P>
 * The class delegates getValueAt and setValueAt to the RowModel. It delegates whole row
 * operations onto the NotifyingList. It does not directly change anything itself but
 * instead waits for corresponding {@link ListEvent}s to occur and then updates itself
 * accordingly.
 * </P>
 * 
 * @author cnh
 * @param <E>
 */
public class TableBridge<E> 
	extends RowModelTableAdaptor
	implements NotifyingListListener
{
	protected NotifyingList<E> myList;
	protected ListEvent myEvent;
	
	public TableBridge(NotifyingList<E> list, RowModel rowModel)
	{
		initialize(rowModel, list);
	}
	
	protected void initialize(RowModel rowModel, NotifyingList<E> list)
	{
		super.initialize(rowModel);
		myList = list;
		myList.addListener(this);
	}
	
	
	@Override
	public void listEvent(ListEvent event)
	{
		switch (event.eventType)
		{
			case AllChanged :
				listEventAllChanged();
				break;
				
			case Delete :
				listEventDelete(event.index, event.element);
				break;
				
			case Insert :
				listEventInsert(event.index, event.element);
				break;
				
			case Update :
				listEventUpdate(event.index, event.element);
				break;
				
			default :
				throw new RuntimeException ("Unknown list event: " + event.eventType);
		}
	}

	protected void listEventUpdate(int index, Object element)
	{
		myHelper.fireRowUpdated(index);
	}

	protected void listEventInsert(int index, Object element)
	{
		myHelper.fireRowAdded(index);
	}

	protected void listEventDelete(int index, Object element)
	{
		myHelper.fireRowRemoved(index);
	}

	protected void listEventAllChanged()
	{
		myHelper.fireTableChanged();
	}

	@Override
	public Object getRow(int index)
	{
		return myList.get(index);
	}

	@Override
	public int getRowCount()
	{
		return myList.size();
	}

	@Override
	public void insert(int index, Object data)
	{
		myList.add(index, (E) data);
	}

	@Override
	public void remove(int index)
	{
		myList.remove(index);
	}

	@Override
	public void update(int index, Object data)
	{
		myList.set(index, (E) data);
	}

}
