package com.lts.caloriecount.ui.frequent;

import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.table.TableModel;

import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.entry.EntryList;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.data.frequent.FrequentFood;
import com.lts.caloriecount.data.frequent.FrequentFoodList;
import com.lts.caloriecount.ui.gatherwin.old.OldGatherWindow;
import com.lts.datalist.DataList;
import com.lts.swing.table.ControlPanel;
import com.lts.swing.table.dragndrop.test.EventLog;
import com.lts.swing.table.rowmodel.RowModel;
import com.lts.swing.table.rowmodel.RowModelDataListBridge;

@SuppressWarnings("serial")
public class FrequentControlPanel extends ControlPanel
{
	protected FrequentFood mySelected;
	protected FrequentFoodList myList;
	protected TransferHandler myTransferHandler;
	
	public TransferHandler getRecordingTransferHandler()
	{
		if (null == myTransferHandler)
			return null;
		
		return myTransferHandler;
	}
	
	
	public FrequentControlPanel()
	{
		initialize();
	}
	
	protected void initialize()
	{
		myList = CalorieCount.getData().getFrequentFoods();
		super.initialize(PanelModes.Browse);
	}
	
	
	@Override
	protected void create(int index)
	{
		Food food = OldGatherWindow.selectFood();
		if (null != food)
		{
			myList.createFrequentFood(food);
		}
	}

	@Override
	protected TableModel createTableModel()
	{
		DataList list = CalorieCount.getData().getFrequentFoods();
		RowModel rowModel = new FrequentRowModel();
		return new RowModelDataListBridge(list, rowModel);
	}
	
	protected RowModelDataListBridge getRMDLB()
	{
		return (RowModelDataListBridge) myModel;
	}
	

	
	
//	@Override
//	protected LTSPanel createTablePanel(PanelModes mode)
//	{
//		myModel = createTableModel();
//		myTable = new LTSTable(myModel);
////		myTransferHandler = new TestTransferHandler();
////		
////		myTable.setTransferHandler(myTransferHandler);
//		// myRth = new RecordingTransferHandler();
//		// myTable.setTransferHandler(myRth);
//		
//		myTable.setDragEnabled(true);
//		myMode = mode;
//		
//		SimpleMouseListener sml = new SimpleMouseListener() {
//			public void doubleClick(MouseEvent event) {
//				launchDoubleClick();
//			}
//		};
//		
//		myTable.addMouseListener(sml);
//		
//		JScrollPane jsp = new JScrollPane(myTable);
//		LTSPanel panel = new LTSPanel();
//		panel.addFill(jsp);
//		
//		// FrequentTransferHandler handler = new FrequentTransferHandler();
//		// myTable.setTransferHandler(handler);
//		myTable.setDragEnabled(true);
//		
//		return panel;		
//	}



//	protected class  LocalDragAndDrop extends JTableDragAndDrop
//	{
//		@Override
//		protected Object copyRow(int row)
//		{
//			return rowCopy(row);
//		}
//
//		@Override
//		protected void deleteRow(int row)
//		{
//			rowDelete(row);
//		}
//
//		@Override
//		protected void pasteRow(int destination, Object rowData)
//		{
//			rowPaste(destination, rowData);
//		}
//	}
	
	protected JTable createJTable(TableModel tableModel)
	{
		LTSTable table = new LTSTable(tableModel);
		FrequentTableDragAndDrop dnd = new FrequentTableDragAndDrop();
		table.setDragAndDropHandler(dnd);
		table.setDragEnabled(true);

		return table;
	}


	protected void frequentFoodSelected(FrequentFood frequentFood)
	{
		if (getMode() == PanelModes.SelectOnly)
		{
			EntryList list = CalorieCount.getEntries();
			list.createMeal(frequentFood.getFood());
			closeWindow();
		}
	}
	
	
	@Override
	protected void delete(int index)
	{
		
		myList.remove(index);
	}

	@Override
	protected void edit(int index)
	{
		TransferHandler temp = getRecordingTransferHandler();
		String transcript = null;
		
		if (null != temp)
			transcript = EventLog.getInstance().getTranscript();
		
		transcript = EventLog.transcript();
		
		System.out.println(transcript);
		
		if (index > 0)
			;
		else 
			;
	}


	@Override
	protected Object[][] buildButtonSpec()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected void select(int select)
	{
		// TODO Auto-generated method stub
		
	}
}
