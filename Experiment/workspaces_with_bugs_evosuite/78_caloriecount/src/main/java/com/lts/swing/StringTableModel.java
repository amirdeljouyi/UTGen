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

import java.util.Vector;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class StringTableModel 
    extends AbstractTableModel
{
    public StringTableModel ()
    {
        super();
    }
    
    public StringTableModel (Vector theStrings)
    {
        super();
        initialize(theStrings);
    }
    
    public StringTableModel (String[][] theStrings)
    {
        super();
        setTable(theStrings);
    }
    
    private Object[][] myTable;
    private String[] myColumnNames;
    
    public String[] getColumnNames ()
    {
        return myColumnNames;
    }
    
    public void setColumnNames (String[] theNames)
    {
        myColumnNames = theNames;
    }
    
    public void setColumnNames (Vector theNames)
    {
        String[] names = new String[theNames.size()];
        
        for (int i = 0; i < names.length; i++)
        {
            names[i] = (String) theNames.elementAt(i);
        }
        
        setColumnNames(names);
    }
    
    public String getColumnName (int col)
    {
        return getColumnNames()[col];
    }
    
    
    public Object[][] getTable ()
    {
        return myTable;
    }
    
    public void setTable (Object[][] theTable)
    {
        myTable = theTable;
    }
    
    public int getRowCount ()
    {
        return getTable().length;
    }
    
    public int getColumnCount ()
    {
        Object[] theRow = getTable()[0];
        return theRow.length;
    }
    
    public Object getValueAt (int theRow, int theCol)
    {
        return getTable()[theRow][theCol];
    }
    
    
    public void initialize (Vector theStrings)
    {
        Vector temp = (Vector) theStrings.firstElement();
        int theNumberOfColumns = temp.size();
        int theNumberOfRows = theStrings.size() - 1;
        
        setColumnNames(temp);
        
        
        Object[][] theTable = new Object[theNumberOfRows][theNumberOfColumns];
        for (int i = 0; i < theNumberOfRows; i++)
        {
            temp = (Vector) theStrings.elementAt(1 + i);
            
            for (int j = 0; j < theNumberOfColumns; j++)
            {
                theTable[i][j] = temp.elementAt(j);
            }
        }
        
        setTable(theTable);
    }
            
        
    
    
}
