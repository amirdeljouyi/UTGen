package com.lts.caloriecount.ui.propertywindow;

import java.awt.Container;
import java.util.Arrays;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.lts.LTSException;
import com.lts.caloriecount.swing.CalorieCountPanel;
import com.lts.event.Callback;
import com.lts.event.CallbackListenerHelper;
import com.lts.event.SimpleAction;
import com.lts.swing.LTSPanel;
import com.lts.util.notifyinglist.NotifyingListAdaptor;
import com.lts.util.notifyinglist.TableBridge;
import com.lts.util.notifyinglist.sorted.SortedListProxy;

@SuppressWarnings("serial")
public class PropertyWindow extends CalorieCountPanel
{
	private JTable myTable;
	private JTextField myNameField;
	private JTextField myValueField;
	private Properties myProperties;
	private NotifyingListAdaptor<KeyValue> myList;
	private CallbackListenerHelper myHelper = new CallbackListenerHelper();
	private TableBridge<KeyValue> myBridge;
	
	@Override
	protected String getViewName()
	{
		return "properties";
	}
	
	
	public PropertyWindow(Container container) throws LTSException
	{
		initialize(container);
	}
	
	
	public void initialize(Container container) throws LTSException
	{
		setBottomPanelMode(BOTTOM_PANEL_OK_CANCEL);
		super.initialize(container);
	}
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		buildTable();
		JScrollPane jsp = new JScrollPane(myTable);
		panel.addFill(jsp);
		
		panel.nextRow();
		
		panel.addHorizontal(createControlPanel());
		
		return panel;
	}


	public JPanel createControlPanel ()
	{
		LTSPanel panel = new LTSPanel();
		
		JButton button = new JButton("Create");
		SimpleAction action = new SimpleAction() {
			public void action() {
				createProperty();
			}
		};
		
		button.addActionListener(action);
		
		panel.addButton(button,5);
		
		button = new JButton("Delete");
		action = new SimpleAction() {
			public void action() {
				deleteProperty();
			}
		};
		
		button.addActionListener(action);
		panel.addButton(button,5);
		
		return panel;
	}


	protected void createProperty()
	{
		String name = JOptionPane.showInputDialog("Please enter a property name");
		String value = myProperties.getProperty(name);
		if (null != value)
		{
			JOptionPane.showMessageDialog(this, "property already exists");
			return;
		}
		
		myProperties.setProperty(name, "");
		KeyValue kv = new KeyValue(name, "");
		myList.add(kv);
	}
	
	
	protected void deleteProperty()
	{
		int index = myTable.getSelectedRow();
		if (-1 == index)
			return;
		
		KeyValue kv = (KeyValue) myBridge.getRow(index);
		if (null == kv)
			return;
		
		String message =
			"Delete property?";
		
		int result = JOptionPane.showConfirmDialog(this, message);
		if (JOptionPane.OK_OPTION == result)
		{
			myBridge.remove(index);
		}
	}


	public void setProperties(Properties props)
	{
		myProperties = props;
		myList.clear();
		
		for (Object o : props.keySet())
		{
			String name = (String) o;
			String value = props.getProperty(name);
			KeyValue keyValue = new KeyValue(name, value);
			myList.add(keyValue);
		}
	}
	
	
	protected void updateProperty()
	{
		String[] names = (String[]) myProperties.keySet().toArray();
		Arrays.sort(names);
		String name = myNameField.getText();
		String value = myValueField.getText();
		myProperties.setProperty(name, value);
	}


	private void buildTable()
	{
		myList = new NotifyingListAdaptor<KeyValue>();
		SortedListProxy<KeyValue> sorted;
		sorted = new SortedListProxy<KeyValue>(KeyValue.CASELESS, myList);
		PropertyRowModel rowModel = new PropertyRowModel();
		myBridge = new TableBridge<KeyValue>(sorted, rowModel);
		myTable = new JTable(myBridge);
	}
	
	
	public void okButtonPressed()
	{
		for (KeyValue keyValue : myList)
		{
			myProperties.setProperty(keyValue.key, keyValue.value);
		}
		
		myHelper.fire(this);
		closeWindow();
	}


	public Properties getProperties()
	{
		Properties p = new Properties();
		for (KeyValue kv : myList)
		{
			p.setProperty(kv.key, kv.value);
		}
		
		return p;
	}


	public static PropertyWindow createWindow() throws LTSException
	{
		JFrame frame = new JFrame();
		PropertyWindow win = new PropertyWindow(frame);
		return win;
	}


	public void addCallback(Callback listener)
	{
		myHelper.addListener(listener);
	}
	
}
