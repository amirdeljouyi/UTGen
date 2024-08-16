package com.lts.swing.table;

import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * A JTable that has several convenience methods.
 * 
 * <H2>Description</H2>
 * 
 * @author cnh
 *
 */
@SuppressWarnings("serial")
public class LTSTable extends JTable
{
	public LTSTable(TableModel tableModel)
	{
		super(tableModel);
	}

	public ModifiableTableModel getModifiableModel()
	{
		return (ModifiableTableModel) getModel();
	}
	
	public void insert (int index, Object data)
	{
		getModifiableModel().insert(index, data);
	}
	
	public void append (Object data)
	{
		int index = getRowCount();
		insert(index, data);
	}
	
	public void replace (int index, Object data)
	{
		getModifiableModel().replace(index, data);
	}
	
	public void delete (int index)
	{
		getModifiableModel().remove(index);
	}
}
