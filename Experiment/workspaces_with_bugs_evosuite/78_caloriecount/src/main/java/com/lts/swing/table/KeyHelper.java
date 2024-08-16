package com.lts.swing.table;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class KeyHelper
{
	public enum Mapping
	{
		Insert (KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,0)),
		Delete (KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0)),
		Enter  (KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0));
		
		public final KeyStroke keyStroke;
		Mapping (KeyStroke stroke)
		{
			keyStroke = stroke;
		}
	};
	
	public static void mapKey (Mapping mapping, JComponent comp, Action action)
	{
		comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(mapping.keyStroke, mapping.toString());
		comp.getActionMap().put(mapping.toString(), action);
	}
}
