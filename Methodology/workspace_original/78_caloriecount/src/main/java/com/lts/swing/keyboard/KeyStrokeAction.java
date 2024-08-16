/**
 * 
 */
package com.lts.swing.keyboard;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;

import com.lts.exception.ExceptionHandler;

/**
 * An AbstractAction that is specific to keyboard events.
 * 
 * <H2>Abstract Class</H2>
 * Subclasses must define:
 * 
 * <UL>
 * <LI>{@link #keyAction(InputKey)</LI>
 * </UL>
 * 
 * <H2>Note</H2>
 * The class uses the {@link ExceptionHandler} class to process any exceptions
 * that occur while processing key strokes.  To use a different approach, subclass
 * AbstractAction instead of this class. 
 * 
 * <H2>Description</H2>
 * This class works by "remembering" the {@link InputKey} that the handler is defined
 * to process.  The problem with {@link AbstractAction} is that a) it is not apparent
 * whether that class "remembers" the key event that triggered the action and b) 
 * if it does, it is not apparent where it is remembered.
 * 
 * <P>
 * This class solves the problem by requiring an InputKey to be provided when the 
 * instance is defined and then providing this info in the {@link #keyAction(InputKey)}
 * call.
 * </P>
 * 
 * @author cnh
 *
 */
@SuppressWarnings("serial")
abstract public class KeyStrokeAction extends AbstractAction
{
	/**
	 * Process a key stroke.
	 * 
	 * <P>
	 * This is the method that subclasses use when defining an instance of this 
	 * class.  The method (and the class) provide a convenience mechanism for obtaining
	 * the keyboard event that triggered the event in the first place, in the form 
	 * of the key parameter.
	 * </P>
	 * 
	 * @param key The keyboard key that was pressed.
	 * @throws Exception If a problem is encountered while processing the keystroke.
	 */
	abstract public void keyAction(InputKey key) throws Exception;
	
	public InputKey myKey;
	public ActionEvent myEvent;
	
	public KeyStrokeAction(InputKey key)
	{
		myKey = key;
	}
	
	public KeyStrokeAction()
	{}
	
	/**
	 * Cause the underlying subclass to process the key stroke; process any exceptions
	 * via the ExceptionHandler class.
	 * 
	 * <P>
	 * The method uses {@link #keyAction(InputKey)} to tell the subclass about the 
	 * key stroke event and which key was used.  If an exception occurs during that
	 * method, {@link ExceptionHandler#processException(Exception)} is used to 
	 * handle the exception.
	 * </P>
	 * 
	 * <P>
	 * If a subclass wants to look at the {@link ActionEvent} that was passed to this
	 * method, it can be accessed iv the {@link #myEvent} field.
	 * </P>
	 * 
	 */
	public void actionPerformed(ActionEvent event)
	{
		try
		{
			myEvent = event;
			keyAction(myKey);
		}
		catch (Exception e)
		{
			ExceptionHandler.processException(e);
		}
	}
	
	/**
	 * Define an action to be taken when a key is hit for a particular component.
	 * 
	 * <P>
	 * This method takes care of the mapping and key binding process for a particular
	 * component.  
	 * </P>
	 * 
	 * @param key The key to react to.
	 * @param action The action to take when the key is pressed.
	 * @param comp The component to perform the mapping for.
	 */
	public static void mapInputKey (InputKey key, KeyStrokeAction action, JComponent comp)
	{
		InputMap imap = comp.getInputMap();
		imap.put(key.getKeyStroke(), key);
		ActionMap amap = comp.getActionMap();
		amap.put(key, action);
	}
	
	/**
	 * Define an action to be taken when a key is pressed in a particular component's
	 * window.
	 * 
	 * @param key The key to watch for.
	 * @param action The action to take.
	 * @param comp The component whose window we want to define a default action.
	 */
	public static void mapDefaultKey (InputKey key, KeyStrokeAction action, JComponent comp)
	{
		InputMap imap = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imap.put(key.getKeyStroke(), action);
		ActionMap amap = comp.getActionMap();
		amap.put(key, action);
	}
}