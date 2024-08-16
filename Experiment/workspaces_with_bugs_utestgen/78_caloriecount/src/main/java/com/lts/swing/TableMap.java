//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the util library.
//
//  The util library is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published by
//  the Free Software Foundation; either version 2.1 of the License, or (at
//  your option) any later version.
//
//  The util library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//  or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the util library; if not, write to the Free Software Foundation,
//  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.swing;


import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/** 
 * In a chain of data manipulators some behaviour is common. TableMap
 * provides most of this behavour and can be subclassed by filters
 * that only need to override a handful of specific methods. TableMap 
 * implements TableModel by routing all requests to its model, and
 * TableModelListener by routing all events to its listeners. Inserting 
 * a TableMap which has not been subclassed into a chain of table filters 
 * should have no effect.
 *
 * @version 1.4 12/17/97
 * @author Philip Milne 
 */

@SuppressWarnings("serial")
public class TableMap 
	extends AbstractTableModel 
    implements TableModelListener 
{
    protected TableModel model; 

    public TableModel getModel() 
    {
        return model;
    }

    public void setModel(TableModel model) 
    {
        this.model = model; 
        model.addTableModelListener(this); 
    }

    // By default, implement TableModel by forwarding all messages 
    // to the model. 

    public Object getValueAt(int aRow, int aColumn) 
    {
        return model.getValueAt(aRow, aColumn); 
    }
        
    public void setValueAt(Object aValue, int aRow, int aColumn) 
    {
        model.setValueAt(aValue, aRow, aColumn); 
    }

    public int getRowCount() 
    {
        return (model == null) ? 0 : model.getRowCount(); 
    }

    public int getColumnCount() 
    {
        return (model == null) ? 0 : model.getColumnCount(); 
    }
        
    public String getColumnName(int aColumn) 
    {
        return model.getColumnName(aColumn); 
    }

    public Class getColumnClass(int aColumn) 
    {
        return model.getColumnClass(aColumn); 
    }
        
    public boolean isCellEditable(int row, int column) 
    { 
         return model.isCellEditable(row, column); 
    }
//
// Implementation of the TableModelListener interface, 
//
    // By default forward all events to all the listeners. 
    public void tableChanged(TableModelEvent e) 
    {
        fireTableChanged(e);
    }
}
