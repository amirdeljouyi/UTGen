package com.lts.swing.table;

import javax.swing.table.TableModel;

/**
 * A table model that can do adds, deletes, etc.
 * 
 * <H2>Description</H2>
 * This interface extends the notion of TableModels to include adding and removing
 * rows.
 * 
 * <P>
 * This class is designed to work with LTSTable, though it can be used with regular 
 * JTables.
 * </P>
 * 
 * @author cnh
 *
 */
public interface ModifiableTableModel extends TableModel
{
	public void insert (int index, Object data);
	public void remove (int index);
	public void replace(int index, Object data);
	public Object getRowData(int index);
}
