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
/*
 * Copyright (c) 2004 by Long Term Software, LLC.  All rights reserved.
 */
package com.lts.swing.table;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.lts.swing.SimpleMouseListener;

/**
 * A class that provides utility methods for use with JTables.
 * 
 * <P>Utilities this class contains:</P>
 * 
 * <P>
 * <UL>
 * <LI>Cell drop-downs: {@link #setupComboBox(int, JTable, List)}</LI>
 * <LI>Cells with centered data: {@link #centerColumn(int, JTable)}</LI>
 * <LI>Scrolling to a cell: {@link #scrollToCell(JTable, int, int)}</LI>
 * <LI>Double-click support: {@link #setupDoubleClick(JTable, ActionListener)}</LI>
 * <LI>Setting column widths: {@link #setPreferredColumnPercents(JTable, double[])}</LI>
 * </UL>
 * </P>
 */
public class JTableUtils
{
	public static JComboBox setupComboBox (
		int columnIndex, 
		JTable table, 
		List options
	)
	{
		TableColumn tcol = table.getColumnModel().getColumn(columnIndex);
		Vector v = new Vector(options);
		JComboBox comboBox = new JComboBox(v);
		tcol.setCellEditor(new DefaultCellEditor(comboBox));
		return comboBox;
	}
	
	public static JComboBox setupComboBox (
		int columnIndex, 
		JTable table, 
		String[] options
	)
	{
		TableColumn tcol = table.getColumnModel().getColumn(columnIndex);
		JComboBox comboBox = new JComboBox(options);
		TableCellEditor editor = new DefaultCellEditor(comboBox);
		tcol.setCellEditor(editor);
		
		return comboBox;
	}
	
	
	public static void centerColumn (int column, JTable table)
	{
		TableColumn tcol = table.getColumnModel().getColumn(column);

		TableCellRenderer tcr = tcol.getCellRenderer();
		if (null == tcr)
		{
			tcr = new DefaultTableCellRenderer();
			tcol.setCellRenderer(tcr);
		}
		
		if (!(tcr instanceof DefaultTableCellRenderer))
			throw new IllegalArgumentException();

		DefaultTableCellRenderer dtcr = (DefaultTableCellRenderer) tcr;
		dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
	}
	
			
	/**
	 * Scroll a JScrollPane or whatever in such a way as to make a particular cell 
	 * in a JTable visible.
	 * 
	 * <H2>Description</H2>
	 * I found this code at http://www.codeguru.com/java/articles/161.shtml  The page 
	 * said that it had been posted by Zafir Anjum.
	 * 
	 * @param table The table that contains the cell.
	 * @param row Row of interest.
	 * @param col Column of interest.
	 */
	public static void scrollToCell( JTable table, int row, int col )
	{
		Container p = table.getParent();
		if (p instanceof JViewport)
		{
			Container gp = p.getParent();
			if (gp instanceof JScrollPane)
			{
				JScrollPane scrollPane = (JScrollPane) gp;
				// Make sure the table is the main viewport
				JViewport viewport = scrollPane.getViewport();
				if (viewport == null || viewport.getView() != table)
				{
					return;
				}

				Rectangle cellrect = table.getCellRect(row, col, true);
				Rectangle viewrect = viewport.getViewRect();
				if (viewrect.contains(cellrect))
					return;
				Rectangle union = viewrect.union(cellrect);
				int x = (int) (union.getX() + union.getWidth() - viewrect.getWidth());
				int y = (int) (union.getY() + union.getHeight() - viewrect.getHeight());
				viewport.setViewPosition(new Point(x, y));
			}
		}
	}
	
	
	public static void scrollToRow (JTable table, int row)
	{
		Rectangle rect = table.getCellRect(row, 0, true);
		table.scrollRectToVisible(rect);
	}
	
	
	public static class DoubleClickListener extends SimpleMouseListener
	{
		private ActionListener myListener;
		
		public DoubleClickListener(ActionListener listener)
		{
			myListener = listener;
		}
		
		
		@Override
		public void doubleClick()
		{
			ActionEvent event = new ActionEvent(this,-1, null);
			myListener.actionPerformed(event);
		}

		@Override
		public void showPopup(Component comp, int x, int y)
		{
		}
	}
	
	public static void setupDoubleClick (JTable table, ActionListener listener)
	{
		DoubleClickListener dcl = new DoubleClickListener(listener);
		table.addMouseListener(dcl);
	}
	
	
	public static void setSelectedRow (JTable table, int row)
	{
		setSelectedRows(table, row, row);
	}
	
	
	public static void setSelectedRows (JTable table, int start, int end)
	{
		ListSelectionModel model = table.getSelectionModel();
		model.setSelectionInterval(start, end);
	}
	
	
	public static List getSelectedColumns (JTable table)
	{
		int rowIndex = table.getSelectedRow();
		List list = null;
		if (-1 == rowIndex)
		{
			TableModel model = table.getModel();
			list = new ArrayList();
			int colCount = table.getColumnCount();
			for (int i = 0; i < colCount; i++)
			{
				list.add(model.getValueAt(rowIndex, i));
			}
		}
		
		return list;
	}
	
	
	public static void setPreferredColumnPercents2 (JTable table, double[] percentages)
	{
		Dimension tableDim = table.getPreferredSize();

		double total = 0;
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++)
			total += percentages[i];

		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++)
		{
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth((int) (tableDim.width * (percentages[i] / total)));
		} 
	}

	/**
	 * Resize the columns of a JTable to percentages of the table width.
	 * 
	 * <H3>Note</H3>
	 * <UL>
	 * <LI>
	 * In order to actually cause the JTable to display differently, 
	 * {@link JTable#doLayout()} must be called after using these methods.
	 * </LI>
	 * 
	 * <LI>
	 * The percentabes provided are "normalized" by the following formula:
	 * <BR/>
	 * 
	 * <CODE>
	 * <PRE>
	 * normal = 1.0/(sum(values));
	 * </PRE>
	 * </CODE>
	 * 
	 * When dividing up space, the "normal" is used to calculate how much a 
	 * particular column gets.  See below for details.
	 * </LI>
	 * 
	 * </UL>
	 * 
	 * <H3>Description</H3>
	 * Example: the table is 100 pixels wide.  Given the following percentages:
	 * <CODE>
	 * <PRE>
	 * { 0.1, 0.4, 0.3, 0.2 }
	 * </PRE>
	 * </CODE>
	 * 
	 * Column 0 gets 10 pixels (10%), column 1 gets 40 pixels (50%), 2 gets 
	 * 30 pixels and 3 gets 20 pixels.
	 * </P>
	 * 
	 * <P>
	 * Specifying a value less than 0 means to use the remainder of the space 
	 * evenly.  For example:
	 * </P>
	 * 
	 * <CODE>
	 * <PRE>
	 * { 0.2, 0.4, -1.0, -1.0 }
	 * </PRE>
	 * </CODE>
	 * 
	 * <P>
	 * Means give 20 pixels (20%) to column 0, 40 to column 1 (40%).  The remaining 
	 * 50 pixels are given equally to columns 2 and 3 (25 pixels each).
	 * </P>
	 * 
	 * <P>
	 * Not providing a value for a column has the same effect as using a negative 
	 * value.  Thus the array:
	 * </P>
	 * 
	 * <CODE>
	 * <PRE>
	 * { 0.2, 0.4 }
	 * </PRE>
	 * </CODE>
	 * 
	 * <P>
	 * Does the same thing as the previous example: column 0 gets 20 pixels, 1 gets 40, 
	 * 2 and 3 get 25 pixels each.
	 * </P>
	 * 
	 * <H3>Normalization</H3>
	 * When calculating the space that a column gets, negative or unspecified values 
	 * receive the following value:
	 *  
	 * <CODE>
	 * <PRE>
	 * { 0.2, 0.4 }
	 * </PRE>
	 * </CODE>
	 * 
	 * When calculate space allocation, all the non-negative values are summed up and 
	 * the result is normalizied.  
	 * 
	 * @param table The table to change.
	 * @param percentages How to divide up the space as percentages.  See description 
	 * for additional notes.
	 */
	public static void setPreferredColumnPercents (JTable table, double[] percentages)
	{
		//
		// Figure out the total amount that the user provided, excluding any unspecified
		// columns
		//
		int count = 0;
		int tableColumns = table.getColumnModel().getColumnCount();
		
		double total = 0;
		for (int i = 0; i < percentages.length; i++)
		{
			if (percentages[i] > 0)
			{
				total += percentages[i];
				count++;
			}
		}
		
		//
		// any unspecified or leftover columns?
		//
		double standardAmount = 1.0/((double) tableColumns);
		if (count < tableColumns)
		{
			total += ((double) (tableColumns - count)) * standardAmount;
		}
		
		//
		// the normalization factor is that amount such that
		//     tableWidth = total * normalizationFactor
		//
		Dimension tableDim = table.getPreferredSize();
		int tableWidth = tableDim.width;
		double factor = ((double) tableWidth) / ((double) total);
		
		//
		// come up with the percents with all the columns, including missing or 
		// negative values, factored in.
		//
		int[] allColumnPercentages = new int[tableColumns];
		for (int i = 0; i < allColumnPercentages.length; i++)
		{
			if (i < percentages.length && percentages[i] > 0)
				allColumnPercentages[i] = (int) (percentages[i] * factor);
			else
				allColumnPercentages[i] = (int) (standardAmount * factor);
		}
		
		//
		// now calculate the amount of space for each column
		//
		for (int i = 0; i < tableColumns; i++)
		{
			TableColumn col = table.getColumnModel().getColumn(i);
			
			int minSize = col.getMinWidth();
			if (allColumnPercentages[i] < minSize)
				allColumnPercentages[i] = minSize;
			
			col.setPreferredWidth(allColumnPercentages[i]);
		}
	}
	

	static public void setColumnWidth (JTable table, int colIndex, int width)
	{
		TableColumnModel model = table.getColumnModel();
		TableColumn column = model.getColumn(colIndex);
		column.setPreferredWidth(width);
		column.setMaxWidth(width);
	}
}
