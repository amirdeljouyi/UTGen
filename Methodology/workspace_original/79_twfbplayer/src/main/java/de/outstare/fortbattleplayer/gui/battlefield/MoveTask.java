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

import java.awt.Point;
import java.util.TimerTask;

import javax.swing.JComponent;

/**
 * A MoveTask moves a given {@link JComponent} a given distance. Multiple calls
 * move the component along a straight line.
 * 
 * @author daniel
 */
class MoveTask extends TimerTask {

	private final JComponent comp;
	/**
	 * using double for precise calculations of current location
	 */
	private final double diffX;
	private final double diffY;
	private double shouldBeX;
	private double shouldBeY;

	/**
	 * @param comp
	 * @param x
	 *            distance to move in one step
	 * @param y
	 *            distance to move in one step
	 */
	MoveTask(final JComponent comp, final double x, final double y) {
		this.comp = comp;
		this.diffX = x;
		this.diffY = y;
		shouldBeX = comp.getLocation().x;
		shouldBeY = comp.getLocation().y;
	}

	/**
	 * move the component by the given distance
	 * 
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		shouldBeX += diffX;
		shouldBeY += diffY;
		final Point newPos = new Point((int) shouldBeX, (int) shouldBeY);
		comp.setLocation(newPos);
	}

}
