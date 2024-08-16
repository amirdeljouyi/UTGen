package com.lts.swing.list;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.lts.event.ActionListenerHelper;
import com.lts.event.LTSMouseAdapter;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleMouseAdapter;
import com.lts.swing.keyboard.InputKey;
import com.lts.swing.keyboard.KeyStrokeAction;

/**
 * A panel that displays a JList along some buttons that are commonly used with
 * a list like create, delete, move up, etc.
 * 
 * <P>
 * The full list of buttons that the list can have, along with their default behaviors
 * are:
 * </P>
 * 
 * <UL>
 * <LI>create - uses {@link JOptionPane#showInputDialog(Object)} to get a string 
 * from the user, which is then added to the list at the currently selected location.</LI>
 * <LI>update - uses JOptionPane to allow the user to edit the currently selected
 * element.  Then the element is updated with the new value.</LI>
 * <LI>delete - deletes the current element.</LI>
 * <LI>up - Move the currently selected element up one.</LI>
 * <LI>down - Move the currently selected element down one.</LI>
 * </UL>
 * 
 * @author cnh
 */
@SuppressWarnings("serial")
public class SimpleCrudListPanel extends LTSPanel
{
	abstract public static class SclpThreadedAction extends SimpleThreadedAction
	{
		public SimpleCrudListPanel panel;
	}
	
	
	public enum PanelButton 
	{
		Create
		{
			public JButton createButton(SimpleCrudListPanel panel)
			{
				SclpThreadedAction action =  new SclpThreadedAction() {
					public void action() {
						panel.processCreate();
					}
				};
				
				action.panel = panel;
				JButton button = new JButton("Create");
				button.addActionListener(action);
				return button;
			}
		},
		
		Update
		{
			public JButton createButton(SimpleCrudListPanel panel)
			{
				SclpThreadedAction action =  new SclpThreadedAction() {
					public void action() {
						panel.processUpdate();
					}
				};
				
				action.panel = panel;
				JButton button = new JButton("Update");
				button.addActionListener(action);
				return button;
			}
		},

		Delete
		{
			public JButton createButton(SimpleCrudListPanel panel)
			{
				SclpThreadedAction action =  new SclpThreadedAction() {
					public void action() {
						panel.processDelete();
					}
				};
				
				action.panel = panel;
				JButton button = new JButton("Delete");
				button.addActionListener(action);
				return button;
			}
		},

		MoveUp
		{
			public JButton createButton(SimpleCrudListPanel panel)
			{
				SclpThreadedAction action =  new SclpThreadedAction() {
					public void action() {
						panel.processMoveUp();
					}
				};
				
				action.panel = panel;
				JButton button = new JButton("Up");
				button.addActionListener(action);
				return button;
			}
		},

		MoveDown
		{
			public JButton createButton(SimpleCrudListPanel panel)
			{
				SclpThreadedAction action =  new SclpThreadedAction() {
					public void action() {
						panel.processMoveDown();
					}
				};
				
				action.panel = panel;
				JButton button = new JButton("Down");
				button.addActionListener(action);
				return button;
			}
		};
		
		abstract public JButton createButton(SimpleCrudListPanel panel);
	}
	
	
	public enum PanelMode
	{
		AllButtons
		{
			public PanelButton[] getButtons()
			{
				return new PanelButton[] {
						PanelButton.Create,
						PanelButton.Update,
						PanelButton.Delete,
						PanelButton.MoveDown,
						PanelButton.MoveUp,
				};
			}
		},
		
		EditButtons
		{
			public PanelButton[] getButtons()
			{
				return new PanelButton[] {
						PanelButton.Create,
						PanelButton.Update,
						PanelButton.Delete,
				};
			}
		},

		MovementButtons
		{
			public PanelButton[] getButtons()
			{
				return new PanelButton[] {
						PanelButton.MoveDown,
						PanelButton.MoveUp,
				};
			}
		},
		
		MinusAdd
		{
			public PanelButton[] getButtons()
			{
				return new PanelButton[] {
						PanelButton.Update,
						PanelButton.Delete,
						PanelButton.MoveUp,
						PanelButton.MoveDown
				};
			}
		},
		
		JustDelete
		{
			public PanelButton[] getButtons()
			{
				return new PanelButton[] {
						PanelButton.Delete
				};
			}
		},
		
		None
		{
			public PanelButton[] getButtons()
			{
				PanelButton[] buttons = new PanelButton[0];
				return buttons;
			}
		}
		;
		
		
		abstract public PanelButton[] getButtons();
	}
	
	
	protected boolean myConfirmDeletes;
	protected DefaultListModel myModel;
	protected JList myList;
	protected ActionListenerHelper myHelper;
	
	public SimpleCrudListPanel(PanelMode mode)
	{
		initialize(mode);
	}
	
	/**
	 * Create the panel using the provided list model and with the specified 
	 * buttons.
	 * 
	 * @param model The list model to use.
	 * @param buttons The buttons to display.
	 */
	protected void initialize(DefaultListModel model, PanelButton[] buttons)
	{
		myHelper = new ActionListenerHelper();
		myModel = model;
		initializePanel(buttons);
	}
	
	
	protected void initialize(DefaultListModel model, PanelMode mode)
	{
		initialize(model, mode.getButtons());
	}
	
	protected void initialize(PanelMode mode)
	{
		DefaultListModel model = new DefaultListModel();
		initialize(model, mode);
	}
	
	protected void initialize(PanelButton[] buttons)
	{
		DefaultListModel model = new DefaultListModel();
		initialize(model, buttons);
	}
	
	
	protected void initializePanel(PanelButton[] buttons)
	{
		
		addFill(createListPanel(myModel));
		nextRow();
		addHorizontal(createButtonPanel(buttons));
		
		mapKeys();
		initializeMouseListener();
	}
	
	
	protected void mapMouse()
	{
		SimpleMouseAdapter listener = new SimpleMouseAdapter()
		{
			public void doubleClick(MouseEvent event)
			{
				int selected = myList.getSelectedIndex();
				if (-1 != selected)
				{
					processDoubleClick(selected);
				}
			}
		};
		
		myList.addMouseListener(listener);
	}
	
	
	protected void processDoubleClick(int index)
	{}


	abstract protected class ListKeyStrokeAction extends KeyStrokeAction
	{
		public ListKeyStrokeAction(InputKey key)
		{
			super(key);
		}
	}
	
	
	public static InputKey[] DEFAULT_MAPPED_KEYS = {
		InputKey.Insert,
		InputKey.Delete
	};
	
	/**
	 * Define keyboard mappings for commonly used keys.
	 * 
	 * <P>
	 * The keys and their mappings:
	 * </P>
	 * 
	 * <UL>
	 * <LI>{@link InputKey#Delete} - {@link #processDelete()}</LI>
	 * <LI>{@link InputKey#Insert} - {@link #processCreate()}</LI>
	 * </UL>
	 */
	protected void mapKeys(InputKey[] keysToMap)
	{
		KeyStrokeAction action;
		
		action = new KeyStrokeAction() {
			public void keyAction(InputKey key) {
				processKey(key);
			}			
		};
		
		for (int i = 0; i < keysToMap.length; i++)
		{
			KeyStrokeAction.mapInputKey(keysToMap[i], action, myList);
		}
	}
	
	
	protected void mapKeys()
	{
		InputKey[] keysToMap = getDefaultKeysToMap();
		mapKeys(keysToMap);
	}
	
	protected InputKey[] getDefaultKeysToMap()
	{
		return DEFAULT_MAPPED_KEYS;
	}

	/**
	 * React to a special key being pressed while the JList has focus.
	 * 
	 * <P>
	 * See the class description for a list of which key strokes are mapped by
	 * default.  Subclasses that want to process additional keys should override 
	 * this method, check for the additional keys, and then call this version of
	 * the method to get the default processing.
	 * </P>
	 * 
	 * @param key The key to process.
	 */
	protected void processKey(InputKey key)
	{
		switch (key)
		{
			case Delete :
				processDelete();
				break;
				
			case Insert :
				processCreate();
				break;
				
			default :
				String message = "Unrecognized key code: " + key;
				throw new IllegalArgumentException(message);
		}
	}

	protected JPanel createListPanel(DefaultListModel model)
	{
		LTSPanel panel = new LTSPanel();
		
		myList = new JList(model);
		JScrollPane jsp = new JScrollPane(myList);
		panel.addFill(jsp);
		mapKeys();
		
		return panel;
	}

	
	
	protected void processUpdate()
	{
		int index = myList.getSelectedIndex();
		if (-1 == index)
			return;
		
		String value = (String) myModel.get(index);
		String message = "Edit value";
		value = JOptionPane.showInputDialog(message, value);
		if (null != value)
			myModel.set(index, value);
	}
	
	protected void processMoveUp()
	{
		int[] selections = myList.getSelectedIndices();
		if (null == selections || selections.length < 1)
		{
			return;
		}
		
		//
		// cannot move anying above the first element
		//
		if (selections[0] < 1)
			return;
		
		//
		// shift all the selected elements up one.
		//
		Object first = selections[0];
		for (int i = 0; i < selections.length; i++)
		{
			int index = selections[i];
			myModel.set(index - 1, myModel.get(index));
		}
		
		int index = selections[selections.length - 1];
		myModel.set(index, first);
	}
	
	protected void processMoveDown()
	{
		int[] selections = myList.getSelectedIndices();
		if (null == selections || selections.length < 1)
		{
			return;
		}

		int start = selections[0];
		int stop = selections[selections.length - 1];
		int maxIndex = myModel.size() - 1;
		
		if (stop >= maxIndex)
			return;
		
		Object lastObject = myModel.get(stop + 1);
		for (int index = stop + 1; index >= start; index--)
		{
			Object above = myModel.get(index - 1);
			myModel.set(index, above);
		}
		myModel.set(start, lastObject);
	}
	
	protected void processDelete()
	{
		if (myConfirmDeletes)
		{
			String message = "Delete Element?";
			int result = JOptionPane.showConfirmDialog(this, message);
			if (result != JOptionPane.OK_OPTION)
				return;
		}
		
		int index = myList.getSelectedIndex();
		if (-1 == index)
			return;
		
		myModel.remove(index);
	}
	
	protected void processCreate()
	{
		String message = "Enter value for new element";
		String value = JOptionPane.showInputDialog(message);
		int index = myList.getSelectedIndex();
		if (-1 == index)
			myModel.addElement(value);
		else
			myModel.add(index, value);
	}
	
	protected JPanel createButtonPanel(PanelButton[] buttons)
	{
		LTSPanel panel = new LTSPanel();
		JButton button;
		
		for (int i = 0; i < buttons.length; i++)
		{
			button = buttons[i].createButton(this);
			panel.addButton(button,5);
		}
		
		return panel;
	}
	
	/**
	 * Insert an element before the currently selected element, or add it to the 
	 * end of list if there is no selected element.
	 * 
	 * @param element The element to add.
	 * @return TODO
	 */
	public int insertOrAdd (Object element)
	{
		int index = myList.getSelectedIndex();
		if (-1 == index)
		{
			index = myModel.getSize() - 1;
			myModel.addElement(element);
		}
		else
		{
			myModel.add(index, element);
		}
		
		return index;
	}
	
	
	public void update (int index, Object newValue)
	{
		myModel.set(index, newValue);
	}
	
	
	public void setElements(List list)
	{
		myModel.removeAllElements();
		for (Object o : list)
		{
			myModel.addElement(o);
		}
	}
	
	public void addElement(Object element)
	{
		myModel.addElement(element);
	}
	
	public void addIfAbsent(Object element)
	{
		if (!myModel.contains(element))
		{
			myModel.addElement(element);
		}
	}
	
	public List getElements()
	{
		List list = new ArrayList();
		
		int count = myModel.getSize();
		for (int i = 0; i < count; i++)
		{
			list.add(myModel.get(i));
		}
		
		return list;
	}

	public void initializeMouseListener()
	{
		LTSMouseAdapter mouseListener = new LTSMouseAdapter()
		{
			public void doubleClick(Object source)
			{
				myHelper.fireAction(this);
			}
		};
		
		myList.addMouseListener(mouseListener);
	}
	
	
	public void addDoubleClickListener (ActionListener listener)
	{
		myHelper.addListener(listener);
	}

	public Object getSelectedItem()
	{
		return myList.getSelectedValue();
	}
	
	public int getSelectedIndex()
	{
		return myList.getSelectedIndex();
	}
	
	public void removeAt(int index)
	{
		myList.remove(index);
	}

	public boolean containsElement(Object o)
	{
		return myModel.contains(o);
	}

	public void replaceWith(List list)
	{
		myModel.clear();
		for (Object o : list)
		{
			myModel.addElement(o);
		}
	}
	
}
