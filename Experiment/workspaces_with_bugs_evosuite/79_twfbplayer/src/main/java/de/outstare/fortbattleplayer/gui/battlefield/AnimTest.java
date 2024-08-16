package de.outstare.fortbattleplayer.gui.battlefield;

/*
 Copyright (c) 2010 Daniel Raap

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


/**
 * 
 * @author daniel
 */
public class AnimTest extends JComponent {
	private static final long serialVersionUID = -7866898949482352930L;
	private final JComponent comp = new JPanel();

	/**
	 * create a new AnimTest with a blue rectangle
	 */
	public AnimTest() {
		setSize(400, 300);
		comp.setBackground(Color.BLUE);
		comp.setSize(30, 30);
		comp.setLocation(190, 140);
		add(comp);
		setVisible(true);

		new MoveAnimation(comp, new Point(0, 290), 500);
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final JFrame window = new JFrame();
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setSize(500, 400);
		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					window.setVisible(true);
				}
			});
		} catch (final InterruptedException e1) {
			e1.printStackTrace();
			return;
		} catch (final InvocationTargetException e1) {
			e1.printStackTrace();
			return;
		}
		window.add(new AnimTest());
	}
}
