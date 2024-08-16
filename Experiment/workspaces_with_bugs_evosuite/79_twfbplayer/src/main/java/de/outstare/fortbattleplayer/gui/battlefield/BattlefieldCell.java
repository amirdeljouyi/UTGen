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
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantSide;
import de.outstare.fortbattleplayer.model.SectorObserver;
import de.outstare.fortbattleplayer.statistics.AreaStatistic;

/**
 * A BattlefieldCell is an element which will be shown on a cell of a
 * {@link BattlefieldDrawing}.
 * 
 * TODO make this an observer of the {@link Area}
 * 
 * @author daniel
 */
class BattlefieldCell extends JComponent implements SectorObserver {
	/**
	 * determines if and which borders of this cell are sector borders
	 * 
	 * @author daniel
	 */
	private static class SectorBorders {
		private boolean top = false;
		private boolean left = false;
		private boolean right = false;
		private boolean bottom = false;
		boolean hasBorders = false;

		/**
		 * for visibility (default would be private)
		 */
		SectorBorders() {
			super();
		}

		/**
		 * @param top
		 *            the top to set
		 */
		public void setTop() {
			top = true;
			hasBorders = true;
		}

		/**
		 * @return the top
		 */
		public boolean hasTop() {
			return top;
		}

		/**
		 * @param left
		 *            the left to set
		 */
		public void setLeft() {
			left = true;
			hasBorders = true;
		}

		/**
		 * @return the left
		 */
		public boolean hasLeft() {
			return left;
		}

		/**
		 * @param right
		 *            the right to set
		 */
		public void setRight() {
			right = true;
			hasBorders = true;
		}

		/**
		 * @return the right
		 */
		public boolean hasRight() {
			return right;
		}

		/**
		 * @param bottom
		 *            the bottom to set
		 */
		public void setBottom() {
			bottom = true;
			hasBorders = true;
		}

		/**
		 * @return the bottom
		 */
		public boolean hasBottom() {
			return bottom;
		}
	}

	private static final long serialVersionUID = -3510791359953259669L;
	/**
	 * the color of the border if the sector is neutral
	 */
	public static final Color gridColor = Color.BLACK.brighter();
	private final Area area;
	private final AreaStatistic statistics;
	private final SectorBorders sectorBorders = new SectorBorders();

	/**
	 * @param area
	 *            which is drawn by this cell
	 * @param statistics
	 */
	BattlefieldCell(final Area area, final AreaStatistic statistics) {
		super();
		setOpaque(false);
		setLayout(new BattlefieldCellLayoutManager());
		this.area = area;
		this.statistics = statistics;
		area.getSector().addObserver(this);
	}

	/**
	 * A BattlefieldCellLayoutManger shows mainly the current {@link Combatant}
	 * and others only small.
	 * 
	 * @author daniel
	 */
	private static class BattlefieldCellLayoutManager implements LayoutManager {

		/**
		 * default constructor for setting visibility
		 */
		BattlefieldCellLayoutManager() {
			super();
		}

		/**
		 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
		 *      java.awt.Component)
		 */
		public void addLayoutComponent(final String name, final Component comp) {
			// not used
		}

		/**
		 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
		 */
		public void removeLayoutComponent(final Component comp) {
			// not used
		}

		/**
		 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
		 */
		public Dimension preferredLayoutSize(final Container parent) {
			final int playerCount = getPlayerCount(parent);
			final int containerWidth = BattleFieldLayoutManager.prefered_cell_width * playerCount;
			final int containerHeight = BattleFieldLayoutManager.prefered_cell_height * playerCount;
			return new Dimension(containerWidth, containerHeight);
		}

		/**
		 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
		 */
		public Dimension minimumLayoutSize(final Container parent) {
			final int playerCount = getPlayerCount(parent);
			final int containerWidth = BattleFieldLayoutManager.mimimum_cell_width * playerCount;
			final int containerHeight = BattleFieldLayoutManager.mimimum_cell_height * playerCount;
			return new Dimension(containerWidth, containerHeight);
		}

		/**
		 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
		 */
		public void layoutContainer(final Container parent) {
			final int width = parent.getWidth();
			final int height = parent.getHeight();

			// check what has to be painted
			final int playerCount = getPlayerCount(parent);

			// calculate drawing area for each component
			// TODO if one living and multiple dead are on the field use more
			// space for the living and only a small area for the dead
			final double playerCols = Math.ceil(Math.sqrt(playerCount));
			final double playerRows = Math.ceil(playerCount / playerCols);
			final double playerWidth = width / playerCols;
			final double playerHeight = height / playerRows;
			int playerIndex = 0;
			for (final Component child : parent.getComponents()) {
				if (!child.isVisible()) {
					continue;
				}
				if (child instanceof PlayerDrawing) {
					final int x = (int) ((playerIndex % playerCols) * playerWidth);
					final int y = (int) (Math.floor(playerIndex / playerCols) * playerHeight);
					child.setBounds(x, y, (int) playerWidth, (int) playerHeight);
					playerIndex++;
				} else {
					child.setBounds(0, 0, 0, 0);
				}
			}
		}

		/**
		 * @param parent
		 * @return
		 */
		int getPlayerCount(final Container parent) {
			int playerCount = 0;
			for (final Component child : parent.getComponents()) {
				if (child.isVisible()) {
					if (child instanceof PlayerDrawing) {
						playerCount++;
					} else if (child instanceof MoveTarget) {
						// currently not painted
					}
				}
			}
			return playerCount;
		}
	}

	/**
	 * @param aArea
	 * @return
	 */
	private Color getSectorColor(final Area aArea) {
		// TODO this call chain smells a bit
		final CombatantSide occupier = aArea.getSector().getOccupier();
		final Color color;
		if (occupier == null) {
			color = gridColor;
		} else {
			color = occupier.color();
		}
		return color;
	}

	/**
	 * @param side
	 *            1 = top, 2 = right, 3 = bottom, 4 = left
	 */
	void setSectorBorder(final int side) {
		switch (side) {
		case 1:
			sectorBorders.setTop();
			break;
		case 2:
			sectorBorders.setRight();
			break;
		case 3:
			sectorBorders.setBottom();
			break;
		case 4:
			sectorBorders.setLeft();
			break;
		default:
			// TODO maybe log?
			break;
		}
		updateBorder();
	}

	private void updateBorder() {
		Border cellBorder = null;
		if (sectorBorders.hasBorders) {
			final int borderThickness = 1;
			int top = 0, left = 0, bottom = 0, right = 0;
			if (sectorBorders.hasBottom()) {
				bottom = borderThickness;
			}
			if (sectorBorders.hasLeft()) {
				left = borderThickness;
			}
			if (sectorBorders.hasRight()) {
				right = borderThickness;
			}
			if (sectorBorders.hasTop()) {
				top = borderThickness;
			}
			final Color borderColor = getSectorColor(area);
			cellBorder = BorderFactory.createMatteBorder(top, left, bottom, right, borderColor);
		}
		setBorder(cellBorder);
	}

	/**
	 * @return the cell on the battlefield (top-left is 0,0)
	 */
	Point getCellCoordinates() {
		return area.getLocation();
	}

	/**
	 * @see javax.swing.JComponent#getToolTipText()
	 */
	@Override
	public String getToolTipText(final MouseEvent event) {
		final StringBuilder text = new StringBuilder(20);
		text.append("<html>");
		final String ownText = super.getToolTipText(event);
		if (ownText != null && ownText.length() > 0) {
			text.append(ownText);
			text.append("<br>");
		}
		// TODO format and show only if requested
		if (statistics != null) {
			text.append(statistics.toString());
			text.append("<br>");
		}
		boolean first = true;
		for (final Component child : getComponents()) {
			if (child instanceof CellContent) {
				if (first) {
					first = false;
				} else {
					text.append("<br>");
				}
				text.append(((CellContent) child).getDescription());
			}
		}
		text.append("</html>");
		return text.toString();
	}

	/**
	 * @param content
	 * @see java.awt.Container#add(java.awt.Component)
	 */
	public void addContent(final CellContent content) {
		super.add(content);
		// enable tooltips
		if (getToolTipText() == null) {
			setToolTipText(""); // not null
		}
	}

	/**
	 * @see java.awt.Container#remove(java.awt.Component)
	 */
	@Override
	public void remove(final Component comp) {
		super.remove(comp);
		// disable tooltip if cell is empty
		if (getComponentCount() == 0) {
			setToolTipText(null);
		}
	}

	/**
	 * @param left
	 * @return
	 */
	boolean sameSector(final BattlefieldCell left) {
		if (left == null) {
			return false;
		}
		return area.getSector().equals(left.area.getSector());
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.SectorObserver#occupierChanged()
	 */
	public void occupierChanged() {
		updateBorder();
	}
}
