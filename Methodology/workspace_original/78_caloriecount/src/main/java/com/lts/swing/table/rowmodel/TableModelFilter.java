package com.lts.swing.table.rowmodel;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.lts.swing.table.TableModelHelper;


/**
 * A class that selectively passes events onto a real set of table model listeners.
 * @author cnh
 *
 */
abstract public class TableModelFilter 
	extends TableModelListenerAdaptor
	implements TableModel
{
	abstract protected boolean filterOutEvent(TableModelListener listener);
	
	public TableModelFilter (TableModel model)
	{
		initialize(model);
	}
	
	protected void initialize(TableModel model)
	{
		myModel = model;
		myModel.addTableModelListener(this);
	}
	
	protected TableModel myModel;
	
	protected TableModelHelper myHelper;

	public void addListener(Object o)
	{
		myHelper.addListener(o);
	}

	public void fireRowAdded(int index)
	{
		myHelper.fireRowAdded(index);
	}

	public void fireRowRemoved(int index)
	{
		myHelper.fireRowRemoved(index);
	}

	public void fireRowUpdated(int index)
	{
		myHelper.fireRowUpdated(index);
	}

	public void fireTableChanged()
	{
		myHelper.fireTableChanged();
	}

	public boolean removeListener(Object o)
	{
		return myHelper.removeListener(o);
	}
	
}
