package de.outstare.fortbattleplayer.gui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * a window that comes to the front and is closable
 * 
 * @author daniel
 */
public class PopupWindow extends JFrame {
	private static final long serialVersionUID = 2412693869136473187L;

	/**
	 * @param title
	 * @param relativeTo
	 */
	public PopupWindow(final String title, final Component relativeTo) {
		super(title);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(600, 450);
		setLocationRelativeTo(relativeTo);
		setVisible(true);
		toFront();
	}
}
