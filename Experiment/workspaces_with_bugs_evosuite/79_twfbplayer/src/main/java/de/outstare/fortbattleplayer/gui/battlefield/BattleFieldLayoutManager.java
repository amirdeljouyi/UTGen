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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.logging.Logger;

import de.outstare.fortbattleplayer.model.Battlefield;

/**
 * A BattleFieldLayoutManager sets the children into cells like a
 * {@link GridLayout}. But the cell is determined by the
 * {@link BattlefieldCell#getCellCoordinates()} (similar to
 * {@link Component#getLocation()}.
 * 
 * @author daniel
 */
class BattleFieldLayoutManager implements LayoutManager {
	private static final Logger LOG = Logger.getLogger(BattleFieldLayoutManager.class.getName());
	final static int mimimum_cell_width = 3;
	final static int mimimum_cell_height = 3;
	final static int prefered_cell_width = 15;
	final static int prefered_cell_height = 15;
	private final int cellcount_x;
	private final int cellcount_y;

	/**
	 * @param battlefield
	 */
	BattleFieldLayoutManager(final Battlefield battlefield) {
		cellcount_x = battlefield.getWidth();
		cellcount_y = battlefield.getHeight();
	}

	/**
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
	 *      java.awt.Component)
	 */
	public void addLayoutComponent(final String name, final Component comp) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	public void layoutContainer(final Container parent) {
		final Dimension cellSize = calculateCellSize(parent);

		synchronized (parent.getTreeLock()) {
			for (final Component child : parent.getComponents()) {
				if (!child.isVisible()) {
					continue;
				}
				if (child instanceof BattlefieldCell) {
					layoutCell(cellSize, (BattlefieldCell) child);
				} else if (child instanceof PlayerDrawing) {
					// players may be childs when they are moving
					// they are not layout but directly painted in MoveAnimation
				} else {
					LOG.warning("no BattlefieldCell - not layouting " + child);
				}
			}
		}
	}

	/**
	 * position and resize the given cell
	 * 
	 * @param cellSize
	 * @param cell
	 */
	private void layoutCell(final Dimension cellSize, final BattlefieldCell cell) {
		final Point cellCoords = cell.getCellCoordinates();
		final Point pixelCoords = new Point(cellCoords.x * cellSize.width, cellCoords.y * cellSize.height);
		final Rectangle cellDrawingSpace = new Rectangle(pixelCoords, cellSize);

		cell.setBounds(cellDrawingSpace);
	}

	/**
	 * @param parent
	 * @return the size in pixels of a drawn cell
	 */
	private Dimension calculateCellSize(final Container parent) {
		final int bfWidth = parent.getWidth();
		final int bfHeight = parent.getHeight();

		final int cellWidth = bfWidth / cellcount_x;
		final int cellHeight = bfHeight / cellcount_y;
		return new Dimension(cellWidth, cellHeight);
	}

	/**
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	public Dimension minimumLayoutSize(final Container parent) {
		return new Dimension(mimimum_cell_width * cellcount_x, mimimum_cell_height * cellcount_y);
	}

	/**
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	public Dimension preferredLayoutSize(final Container parent) {
		return new Dimension(prefered_cell_width * cellcount_x, prefered_cell_height * cellcount_y);
	}

	/**
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	public void removeLayoutComponent(final Component comp) {
		// no components here
	}

}
