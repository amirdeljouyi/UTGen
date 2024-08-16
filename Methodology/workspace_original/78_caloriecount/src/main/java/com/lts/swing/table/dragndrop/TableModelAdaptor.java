package com.lts.swing.table.dragndrop;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.lts.swing.table.TableModelHelper;

/**
 * A class to simplify the implementation of simple TableModels
 * 
 * <H2>Quickstart</H2>
 * <UL>
 * <LI>Create a subclass of TableModelAdaptor</LI>
 * <LI>Define initialize</LI>
 *     <UL>
 *     <LI>Call super.initialize()</LI>
 *     <LI>initialize myColumnNames</LI>
 *     <LI>(optional) initialize myColumnClasses</LI>
 *     <LI>(optional) initialize myColumnEditable</LI>
 *     </UL>
 * <LI>Define getRowCount</LI>
 * <LI>Define getValueAt</LI>
 * <LI>Define setValueAt</LI>
 * </UL>
 * 
 * <H3>Define initialize</H3>
 * <H4>Call super.initialize()</H4>
 * This is needed to initialize myHelper.  myHelper is used to handle add and 
 * removeListener calls.
 * 
 * <H4>Initialize myColumnNames</H4>
 * myColumnNames is needed for the call to {@link #getColumnName(int)} but also
 * for {@link #getColumnCount()}.
 * 
 * <H4>(Optional) Initialize myColumnClasses</H4>
 * If this field is not initialize, then the value returned by {@link #getColumnClass(int)}
 * defaults to String.
 * 
 * <H4>(Optional) Initialize myColumnEditable</H4>
 * If this field is not initialize, then the value returned by {@link #isCellEditable(int, int)}
 * defaults to true.
 * 
 * <H2>Description</H2>
 * This class simplifies implementations of TableModel by only requiring subclasses to 
 * define the methods outlined in the Quickstart section.  In particular, the 
 * client must define how the data is accessed i.e., how getValueAt, setValueAt and
 * getRowCount work.
 * 
 * <P>
 * The preferred mechanism for setting myColumnClasses, myColumnNames, and myColumnEditable
 * is to simply access the field, but subclasses can choose to use accessors instead.
 * </P>
 * 
 * <P>
 * Note that the {@link TableModelHelper} class significantly reduces complexity associated
 * with notifying listeners via methods like {@link TableModelHelper#fireRowAdded(int)}. 
 * </P>
 * 
 * @author cnh
 *
 */
abstract public class TableModelAdaptor implements TableModel
{
	abstract public int getRowCount();
	abstract public Object getValueAt(int row, int col);
	abstract public void setValueAt(Object value, int row, int col);
	abstract public String[] getColumnNames();
	
	protected TableModelHelper myHelper;
	protected Class[] myColumnClasses;
	protected String[] myColumnNames;
	protected boolean[] myColumnEditable;
	
	@Override
	public String getColumnName(int columnIndex)
	{
		return myColumnNames[columnIndex];
	}


	protected void setColumnClasses(Class[] classes)
	{
		myColumnClasses = classes;
	}
	
	protected void setColumnNames(String[] names)
	{
		myColumnNames = names;
	}
	
	protected void setColumnEditable(boolean[] columnsEditable)
	{
		myColumnEditable = columnsEditable;
	}
	
	
	protected void initialize()
	{
		myHelper = new TableModelHelper(this);
		myColumnNames = getColumnNames();
	}
	
	
	@Override
	public void addTableModelListener(TableModelListener l)
	{
		myHelper.addListener(l);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		if (null == myColumnClasses)
			return String.class;
		else
			return myColumnClasses[columnIndex];
	}

	@Override
	public int getColumnCount()
	{
		return myColumnNames.length;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		if (null == myColumnEditable)
			return true;
		else
			return myColumnEditable[columnIndex];
	}

	@Override
	public void removeTableModelListener(TableModelListener l)
	{
		myHelper.removeListener(l);
	}
}
