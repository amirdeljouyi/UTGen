package com.lts.swing.keyboard;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Convenience enum to handle key codes and the like.
 * 
 * @author cnh
 */
public enum InputKey
{
	Enter
	{
		public KeyStroke getKeyStroke()
		{
			KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
			return key;
		}
	},
	UpArrow
	{
		public KeyStroke getKeyStroke()
		{
			return KeyStroke.getKeyStroke(KeyEvent.VK_UP,0);
		}
	},
	DownArrow
	{
		public KeyStroke getKeyStroke()
		{
			return KeyStroke.getKeyStroke(KeyEvent.VK_UP,0);
		}
	},
	LeftArrow
	{
		public KeyStroke getKeyStroke()
		{
			return KeyStroke.getKeyStroke(KeyEvent.VK_UP,0);
		}
	},

	RightArrow
	{
		public KeyStroke getKeyStroke()
		{
			return KeyStroke.getKeyStroke(KeyEvent.VK_UP,0);
		}
	},

	Insert
	{
		public KeyStroke getKeyStroke()
		{
			return KeyStroke.getKeyStroke(KeyEvent.VK_UP,0);
		}
	},

	Delete
	{
		public KeyStroke getKeyStroke()
		{
			return KeyStroke.getKeyStroke(KeyEvent.VK_UP,0);
		}
	}
	;
	
	abstract public KeyStroke getKeyStroke();
}