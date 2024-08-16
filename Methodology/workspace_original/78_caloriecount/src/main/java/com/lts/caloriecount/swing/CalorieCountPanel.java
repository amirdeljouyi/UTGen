package com.lts.caloriecount.swing;

import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.caloriecount.app.CalorieCount;
import com.lts.caloriecount.data.CalorieCountData;
import com.lts.caloriecount.data.CalorieCountSerdser;
import com.lts.caloriecount.ui.budget.BudgetWin;
import com.lts.caloriecount.ui.dayview.DayView;
import com.lts.caloriecount.ui.foodwin.FoodListWindow;
import com.lts.caloriecount.ui.frequent.FrequentFoodPanel;
import com.lts.caloriecount.ui.mainframe.HelpAboutPanel;
import com.lts.caloriecount.ui.propertywindow.PropertyWindow;
import com.lts.event.Callback;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.layout.WindowUtils;
import com.lts.swing.table.dragndrop.example.ExampleWindow;
import com.lts.xml.XMLUtils;
import com.lts.xml.simple.SimpleElement;
import com.lts.xml.simple.SimpleElementConverter;

abstract public class CalorieCountPanel extends ApplicationContentPanel
{
	public CalorieCountPanel()
	{}
	
	public CalorieCountPanel(Container container) throws LTSException
	{
		super(container);
	}
	
	
	protected SimpleThreadedAction winFoodList = new SimpleThreadedAction() {
			public void action() { showFoodList(); }
		};
		
	protected SimpleThreadedAction winDayList = new SimpleThreadedAction() {
			public void action() { showDayList(); } 
		};
		
	protected SimpleThreadedAction winFrequentList = new SimpleThreadedAction() {
		public void action() { showFrequentList(); }
	};
	
	protected SimpleThreadedAction winExample = new SimpleThreadedAction() {
		public void action() { showExample(); }
	};
	
	protected SimpleThreadedAction otherMainFrame = new SimpleThreadedAction() {
		public void action() { showOtherMainFrame(); }
	};
	
	protected SimpleThreadedAction winBudget = new SimpleThreadedAction() {
		public void action() { showBudget(); }
	};
	
	protected SimpleThreadedAction fileExport = new SimpleThreadedAction() {
		public void action() { exportFiles(); }
	};
	
	protected SimpleThreadedAction fileImport = new SimpleThreadedAction() {
		public void action() { importFiles(); }
	};

	protected SimpleThreadedAction propertiesEdit = new SimpleThreadedAction() {
		public void action() throws Exception { editProperties(); }
	};
	
	protected SimpleThreadedAction systemPropertiesDisplay = new SimpleThreadedAction() {
		public void action() throws Exception { displaySystemProperties(); }
	};
	
	protected Object[][] SPEC_WINDOW_MENU = {
				{ "Window" },
					{ "Foods",					winFoodList },
					{ "Daylist",				winDayList },
					{ "Frequently Selected",	winFrequentList },
					{ "Position/Size",			winSize },
					{ "Other Main Frame",		otherMainFrame },
					{ "Budget",					winBudget },
					{ "Properties",				propertiesEdit },
					{ "System Properties",		systemPropertiesDisplay },
		};
	
	protected Object[][] SPEC_FILE_ACTION_MENU = {
			{ "File" },
				{ "New",			fileNew },
				{ "Open...",		fileOpen },
				{},
				{ "Save", 			fileSave },
				{ "Save As...", 	fileSaveAs },
				{ },
				{ "Export...",		fileExport },
				{ "Import...",		fileImport },
				{ "Exit", 			fileExit },
		};

	protected SimpleThreadedAction helpAbout = new SimpleThreadedAction() {
		public void action() { displayAbout(); }
	};
	
	protected Object[][] SPEC_HELP_MENU = {
			{ "Help" },
				{ "About",			helpAbout },
		};
	

	protected Object[][][] SPEC_MENU_BAR = {
			SPEC_FILE_ACTION_MENU,
			SPEC_WINDOW_MENU,
			SPEC_HELP_MENU
		};

	protected void importFiles() 
	{
		try
		{
			File file = CalorieCount.getApp().browseOpenFile();
			if (null != file)
			{
				CalorieCountSerdser ccs = new CalorieCountSerdser();
				CalorieCountData data = ccs.deserializeCalorieCount(file);
				CalorieCount.setData(data);
			}
		}
		catch (Exception e)
		{
			showException(e);
		}
	}
	
	protected void displaySystemProperties() throws LTSException
	{
		Properties p = System.getProperties();
		PropertyWindow win = PropertyWindow.createWindow();
		win.setProperties(p);
		win.showWindow();
	}

	protected void editProperties() throws LTSException
	{
		Callback listener = new Callback() {
			public void callback(Object data) {
				processNewProperties(data);
			}
		};
		
		PropertyWindow win = PropertyWindow.createWindow();
		Properties props = Application.getInstance().getProperties();
		Properties copy = new Properties();
		
		for (Object o : props.keySet())
		{
			String name = (String) o;
			String value = props.getProperty(name);
			copy.setProperty(name, value);
		}
		
		win.setProperties(copy);
		win.addCallback(listener);
		win.showWindow();
		
	}

	protected void processNewProperties(Object data)
	{
		PropertyWindow win = (PropertyWindow) data;
		Properties props = win.getProperties();
		Application.getInstance().setProperties(props);
	}

	protected void processNewProperties()
	{
		
	}

	protected void performWhatever()
	{
	}

	@SuppressWarnings("serial")
	protected void displayAbout()
	{
		HelpAboutPanel.showAboutWindow();
	}

	protected void showOtherMainFrame()
	{
		notImplemented();
	}


	protected void showExample()
	{
		try
		{
			@SuppressWarnings("unused")
			ExampleWindow win = ExampleWindow.showFrame();
		}
		catch (LTSException e)
		{
			Application.showException(e);
		}
	}


	protected void showFrequentList()
	{
		try
		{
			FrequentFoodPanel.showFrame();
		}
		catch (LTSException e)
		{
			Application.showException(e);
		}
	}


	protected void exportFiles()
	{
		try
		{
			File file = CalorieCount.getApp().browseSaveFile();
			if (null != file)
			{
				CalorieCountSerdser ccs = new CalorieCountSerdser();
				CalorieCountData data = CalorieCount.getData();
				SimpleElement root = ccs.serializeCalorieCount(data);
				SimpleElementConverter sec = new SimpleElementConverter();
				Document doc = XMLUtils.createDocument();
				Element xroot = sec.toElement(root, doc);
				doc.appendChild(xroot);
				XMLUtils.writeDocument(file, doc);
			}
		}
		catch (Exception e)
		{
			showException(e);
		}
	}
		
	protected void showDayView()
	{
		DayView.displayWindow();
	}

	protected void showDayList()
	{
		DayView.displayWindow();
	}

	protected void showFoodList()
	{
		FoodListWindow.displayFoodList();
	}
	
	protected void showBudget()
	{
		BudgetWin.displayWindow();
	}
	
	public void initialize(Container container) throws LTSException
	{
		setupShowSize(container);
		super.initialize(container);
		
		if (definesWindowProperties())
			listenForWindowComponentEvents();
	}

	public Dimension getWindowSize()
	{
		Dimension dim = getWindowSizeFromProperties();
		if (null == dim)
			dim = getDefaultWindowSize();
		
		return dim;
	}
	
	protected Dimension getDefaultWindowSize()
	{
		return WindowUtils.getFractionOfScreenSize(3);
	}
	
	public Dimension getWindowSizeFromProperties()
	{
		if (!definesWindowProperties())
		{
			return null;
		}
		
		String name = buildPropertyNameForWindow(PROPERTY_WINDOW_SIZE);
		String sizeProp = Application.getAppProperty(name);
		if (null == sizeProp)
			return null;
		
		String[] fields = sizeProp.split(",");
		if (fields.length < 2)
			return null;
		
		Dimension dim = new Dimension();
		dim.height = Integer.parseInt(fields[0]);
		dim.width = Integer.parseInt(fields[1]);
			
		return dim;
	}


	
}
