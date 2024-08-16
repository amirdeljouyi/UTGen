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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import de.outstare.fortbattleplayer.gui.battlefield.BattlefieldDrawing.BattlefieldLine.LineType;
import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.Battlefield;
import de.outstare.fortbattleplayer.player.Battleplayer.RoundListener;
import de.outstare.fortbattleplayer.player.PlayerConfiguration;
import de.outstare.fortbattleplayer.player.PlayerConfigurationListener;
import de.outstare.fortbattleplayer.statistics.AreaStatistic;

/**
 * The battlefield is a rectangle divided into {@link BattlefieldCell}s. On each
 * cell a Player may be drawn.
 * 
 * @author daniel
 */
public class BattlefieldDrawing extends JComponent implements RoundListener, PlayerConfigurationListener {
	private static final Logger LOG = Logger.getLogger(BattlefieldDrawing.class.getName());
	private static final long serialVersionUID = 6701540385277809886L;
	private static final String imageFilePath = "/images/fortbattleBackground.png";
	/**
	 * original background image which will be scaled to the current window size
	 */
	private Image backgroundImage;
	private final Battlefield battlefield;
	private final PlayerConfiguration config;
	private final Map<Point, BattlefieldCell> cells = new HashMap<Point, BattlefieldCell>();
	private final Set<BattlefieldLine> lines = Collections
	        .newSetFromMap(new ConcurrentHashMap<BattlefieldLine, Boolean>());

	/**
	 * @param battlefield
	 *            the battlefield to draw
	 * @param fortImage
	 * @param config
	 *            used for animating combatant movement
	 * @param fieldStats
	 */
	public BattlefieldDrawing(final Battlefield battlefield, final Image fortImage, final PlayerConfiguration config,
	        final Map<Area, AreaStatistic> fieldStats) {
		super();
		setLayout(new BattleFieldLayoutManager(battlefield));
		createCells(battlefield, fieldStats);
		this.config = config;
		// TODO remove listener after GUI-Player is closed
		config.addListener(this);
		this.battlefield = battlefield;
		setBackground(Color.WHITE);
		try {
			final InputStream backgroundFile = BattlefieldDrawing.class.getResourceAsStream(imageFilePath);
			if (backgroundFile == null) {
				throw new IOException("file not found by ClassLoader: " + imageFilePath);
			}
			backgroundImage = ImageIO.read(backgroundFile);
		} catch (final IOException e) {
			LOG.severe(e.toString());
			backgroundImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			final Graphics graphics = backgroundImage.getGraphics();
			graphics.setColor(Color.GREEN);
			graphics.fillRect(0, 0, 10, 10);
		}
		// draw fort on background
		final int backgroundHeight = backgroundImage.getHeight(null);
		final int backgroundWidth = backgroundImage.getWidth(null);
		final Graphics g = backgroundImage.getGraphics();
		g.drawImage(fortImage, 0, 0, backgroundWidth, backgroundHeight, null);
		g.dispose();
		updateBackgroundBuffer();
		// the backround has to be rescaled
		addComponentListener(new ComponentAdapter() {
			/**
			 * @see java.awt.event.ComponentAdapter#componentResized(java.awt.event.ComponentEvent)
			 */
			@Override
			public void componentResized(final ComponentEvent e) {
				updateBackgroundBuffer();
			}
		});
	}

	private Image backgroundBuffer = null;

	/**
	 * redraws the background image only if necessary
	 */
	void updateBackgroundBuffer() {
		final Dimension paintSize = battlefieldPaintArea();
		backgroundBuffer = createImage(paintSize.width, paintSize.height);
		if (backgroundBuffer != null) {
			final Graphics g = backgroundBuffer.getGraphics();
			paintBackgroundImage(g, paintSize);
			// the grid must be painted separately to be one pixel thin
			paintCellGrid(paintSize, g);
		}
	}

	/**
	 * @param paintSize
	 * @param g
	 */
	void paintCellGrid(final Dimension paintSize, final Graphics g) {
		final int backgroundHeight = paintSize.height;
		final int backgroundWidth = paintSize.width;
		// draw grid/cells
		g.setColor(BattlefieldCell.gridColor);
		final int cellHeigth = backgroundHeight / battlefield.getHeight();
		for (int cellRow = 0; cellRow < battlefield.getHeight(); cellRow += 2) {
			g.drawRect(0, cellRow * cellHeigth, backgroundWidth, cellHeigth);
		}
		final int cellWidth = backgroundWidth / battlefield.getWidth();
		for (int cellColumn = 0; cellColumn < battlefield.getWidth(); cellColumn += 2) {
			g.drawRect(cellColumn * cellWidth, 0, cellWidth, backgroundHeight);
		}
	}

	/**
	 * @see javax.swing.JComponent#addNotify()
	 */
	@Override
	public void addNotify() {
		super.addNotify();
		// optimize image for hardware acceleration (for GraphicsConfiguration)
		// Cannot be done until added, because it needs a GraphicsConfiguration
		// which is inherited from the parent
		final Image acceleratedImage = createImage(backgroundImage.getWidth(null), backgroundImage.getHeight(null));
		final Graphics g = acceleratedImage.getGraphics();
		g.drawImage(backgroundImage, 0, 0, null);
		g.dispose();
		backgroundImage = acceleratedImage;
	}

	/**
	 * @param battlefield2
	 * @param fieldStats
	 */
	private void createCells(final Battlefield battlefield2, final Map<Area, AreaStatistic> fieldStats) {
		BattlefieldCell left;
		BattlefieldCell top;
		for (int y = 0; y < battlefield2.getHeight(); y++) {
			left = null;
			for (int x = 0; x < battlefield2.getWidth(); x++) {
				final Area area = battlefield2._getArea(x, y);
				final AreaStatistic stats = fieldStats.get(area);
				final BattlefieldCell cell = new BattlefieldCell(area, stats);
				cells.put(cell.getCellCoordinates(), cell);
				add(cell);

				// calculate sector borders
				if (!cell.sameSector(left)) {
					cell.setSectorBorder(4);
					if (left != null) {
						left.setSectorBorder(2);
					}
				}
				if (y == 0) {
					cell.setSectorBorder(1);
				} else {
					top = cells.get(new Point(x, y - 1));
					if (!cell.sameSector(top)) {
						cell.setSectorBorder(1);
						top.setSectorBorder(3);
					}
				}
				if (y == battlefield2.getHeight()) {
					cell.setSectorBorder(3);
				}

				left = cell;
			}
			// last cell has a right border
			if (left != null) {
				left.setSectorBorder(2);
			}
		}
		LOG.finer("Battlefield has " + getComponentCount() + " cells");
	}

	/**
	 * @param cellContent
	 *            not null
	 * @param target
	 *            not null
	 * @param color
	 *            not null
	 */
	void drawShootingAt(final CellContent cellContent, final Area target, final Color color) {
		// quickfix for shooting at dead people (location == null) if rewinding
		if (target == null) {
			return;
		}
		if (EventQueue.isDispatchThread()) {
			connectCells(cellContent.getCurrentCell(), target, color, LineType.SHOOT);
		} else {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					connectCells(cellContent.getCurrentCell(), target, color, LineType.SHOOT);
				}
			});
		}
	}

	/**
	 * draws the target symbol at the given destination
	 * 
	 * @param source
	 * @param destination
	 * @param color
	 */
	void drawTargetAt(final CellContent source, final Area destination, final Color color) {
		drawTargetAt(source, destination, color, true);
	}

	/**
	 * draws the target symbol at the given destination
	 * 
	 * @param source
	 * @param destination
	 * @param color
	 * @param useConfig
	 *            if <code>false</code> the line is always painted till the end
	 *            of the round. The result may be used to manually remove it.
	 * @return the line drawn or <code>null</code>
	 */
	BattlefieldLine drawTargetAt(final CellContent source, final Area destination, final Color color,
	        final boolean useConfig) {
		final Graphics g = getGraphics();
		if (g == null) {
			return null;
		}
		LineType type;
		if (useConfig) {
			type = LineType.MOVETARGET;
		} else {
			type = LineType.FORCEDTARGET;
		}
		final BattlefieldLine line = connectCells(source.getCurrentCell(), destination, color, type);
		return line;
	}

	/**
	 * @param destPoint
	 *            in pixels on this drawing
	 * @param color
	 * @param g
	 */
	void drawTargetPoint(final Point destPoint, final Color color, final Graphics g) {
		final int minCellSize = Math.min(cellWidth(), cellHeight());
		final int radius = (int) (minCellSize * 0.3);
		final int centerRadius = (int) (minCellSize * 0.15);
		drawCenteredFilledCircle(Color.GRAY, destPoint, radius, g);
		drawCenteredFilledCircle(color, destPoint, centerRadius, g);
	}

	private void drawCenteredFilledCircle(final Color color, final Point center, final int radius, final Graphics g) {
		g.setColor(color);
		g.fillOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
	}

	/**
	 * @param cellContent
	 * @param target
	 */
	void drawMovingTo(final CellContent cellContent, final Area target) {
		if (getWidth() > 0) {
			final Container originalContainer = cellContent.getParent();
			// move content to this battlefield to be drawn in our coordinate
			// system
			add(cellContent);
			cellContent.setLocation(cellPos(cellContent.getCurrentCell().getCellCoordinates()));
			final Point destination = cellPos(target.getLocation());
			new MoveAnimation(cellContent, destination, config.PLAYER_DELAY);
			// place back in original container
			originalContainer.add(cellContent);
		}
	}

	/**
	 * @param coordinates
	 * @return the drawing component for the given cell coordinates
	 */
	BattlefieldCell getCell(final Point coordinates) {
		return cells.get(coordinates);
	}

	/**
	 * draws a line between the given cells (from center to center)
	 * 
	 * @param cell
	 * @param destination
	 * @param color
	 * @param type
	 *            of the line
	 * @return
	 */
	BattlefieldLine connectCells(final BattlefieldCell cell, final Area destination, final Color color,
	        final LineType type) {
		final Point start = cell.getCellCoordinates();
		final Point end = destination.getLocation();
		final BattlefieldLine bfLine = new BattlefieldLine(start, end, color, type);
		lines.add(bfLine);

		final Graphics g = getGraphics();
		drawLine(bfLine, g);
		return bfLine;
	}

	/**
	 * @param bfLine
	 * @param g
	 */
	void drawLine(final BattlefieldLine bfLine, final Graphics g) {
		if (isPainted(bfLine)) {
			final Point startUpperLeftPixel = cellPos(bfLine.start);
			final Point endUpperLeftPixel = cellPos(bfLine.end);
			final int centerOffsetX = cellWidth() / 2;
			final int centerOffsetY = cellHeight() / 2;
			final Point startCenterPixel = new Point(startUpperLeftPixel.x + centerOffsetX, startUpperLeftPixel.y
			        + centerOffsetY);
			final Point endCenterPixel = new Point(endUpperLeftPixel.x + centerOffsetX, endUpperLeftPixel.y
			        + centerOffsetY);

			drawLine(startCenterPixel, endCenterPixel, bfLine.color, g);
			if (bfLine.type == LineType.MOVETARGET || bfLine.type == LineType.FORCEDTARGET) {
				drawTargetPoint(endCenterPixel, bfLine.color, g);
			}
		}
	}

	/**
	 * @param line
	 * @return true if the config allows showing the given line
	 */
	boolean isPainted(final BattlefieldLine line) {
		switch (line.type) {
		case SHOOT:
			return config.showShootline();
		case MOVETARGET:
			return config.showMoveTargets();
		case FORCEDTARGET:
		default:
			return true;
		}
	}

	/**
	 * @param startPixel
	 * @param endPixel
	 * @param color
	 */
	private void drawLine(final Point startPixel, final Point endPixel, final Color color, final Graphics g) {
		if (g != null) {
			g.setColor(color);
			g.drawLine(startPixel.x, startPixel.y, endPixel.x, endPixel.y);
		}
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundBuffer, 0, 0, null);
		paintLines(g);
	}

	/**
	 * @param g
	 */
	private void paintLines(final Graphics g) {
		if (g != null && g instanceof Graphics2D) {
			for (final BattlefieldLine line : lines) {
				drawLine(line, g);
			}
		}
	}

	/**
	 * @return the size of the area which will be painted (a multiple of
	 *         {@link #cellWidth()}/{@link #cellHeight()})
	 */
	private Dimension battlefieldPaintArea() {
		final int bfWidth = cellcount_x() * cellWidth();
		final int bfHeight = cellcount_y() * cellHeight();
		return new Dimension(bfWidth, bfHeight);
	}

	private void paintBackgroundImage(final Graphics g2, final Dimension bfSize) {
		final Insets insets = getInsets();
		g2.drawImage(backgroundImage, insets.left, insets.top, bfSize.width, bfSize.height, 0, 0,
		        backgroundImage.getWidth(null), backgroundImage.getHeight(null), null);
	}

	private int cellcount_x() {
		return battlefield.getWidth();
	}

	private int cellcount_y() {
		return battlefield.getHeight();
	}

	/**
	 * converts cell coordinates to pixel coordinates for drawing
	 * 
	 * @param cellCoordinates
	 * @return the upper left pixel of the cell
	 */
	private Point cellPos(final Point cellCoordinates) {
		return new Point(cellPosX(cellCoordinates.x), cellPosY(cellCoordinates.y));
	}

	/**
	 * @param y
	 *            the number of the cell on the y-axis
	 * @return the position in pixels
	 */
	private int cellPosY(final int y) {
		return y * cellHeight();
	}

	/**
	 * @param x
	 *            the number of the cell on the x-axis
	 * @return the position in pixels
	 */
	private int cellPosX(final int x) {
		return x * cellWidth();
	}

	/**
	 * @return
	 */
	private int cellHeight() {
		return getHeight() / cellcount_y();
	}

	/**
	 * @return
	 */
	private int cellWidth() {
		return getWidth() / cellcount_x();
	}

	/**
	 * repaint complete screen at round start
	 * 
	 * @see de.outstare.fortbattleplayer.player.Battleplayer.RoundListener#nextRound(int)
	 */
	public void nextRound(final int roundNo) {
		drawBigString(String.valueOf(roundNo));
		try {
			Thread.sleep(2000);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		lines.clear();
		repaint();
	}

	/**
	 * draw a String with the default font in a huge size in fancy colors
	 * 
	 * @param string
	 * @param fontSize
	 */
	private void drawBigString(final String string) {
		final float fontSize = getHeight() / 1.5f;
		final float borderScale = 1.1f;
		final Graphics2D g = (Graphics2D) getGraphics();
		if (g == null) {
			return;
		}
		final Font origFont = g.getFont();

		final Font bigFont = origFont.deriveFont(fontSize);
		final Font borderFont = bigFont.deriveFont(borderScale * fontSize);

		drawBigString(string, borderFont, Color.YELLOW, g);
		drawBigString(string, bigFont, Color.RED, g);
		// reset
		g.setFont(origFont);
	}

	/**
	 * @param string
	 * @param bigFont
	 * @param g
	 */
	private void drawBigString(final String string, final Font bigFont, final Color color, final Graphics2D g) {
		final Rectangle2D textSize = bigFont.getStringBounds(string, g.getFontRenderContext());
		// center text on this component
		final float x = (float) ((getWidth() - textSize.getWidth()) / 2.0);
		final float y = (float) (getHeight() / 2.0 - textSize.getCenterY());
		g.setFont(bigFont);
		g.setColor(color);
		g.drawString(string, x, y);
	}

	/**
	 * A BattlefieldLine connects two cells (battlefield coordinates).
	 * 
	 * @author daniel
	 */
	static class BattlefieldLine {
		/**
		 * Type of line which is displayed
		 * 
		 * @author daniel
		 */
		enum LineType {
			/**
			 * points to the place where a player wants to go.
			 */
			MOVETARGET,
			/**
			 * points to a place where a player shot at.
			 */
			SHOOT,
			/**
			 * a move target line that is always displayed
			 */
			FORCEDTARGET
		}

		/**
		 * the start of the line
		 */
		final Point start;
		/**
		 * the end of the line
		 */
		final Point end;
		/**
		 * line color when
		 */
		final Color color;
		/**
		 * The type decides if the lines has to be painted
		 */
		final LineType type;

		/**
		 * Constructor
		 * 
		 * @param start
		 * @param end
		 * @param color
		 * @param type
		 */
		BattlefieldLine(final Point start, final Point end, final Color color, final LineType type) {
			this.start = start;
			this.end = end;
			this.color = color;
			this.type = type;
		}
	}

	/**
	 * @param line
	 */
	void removeLine(final BattlefieldLine line) {
		lines.remove(line);
		repaint();
	}

	/**
	 * @see de.outstare.fortbattleplayer.player.PlayerConfigurationListener#changedShowMoveTarget(boolean)
	 */
	public void changedShowMoveTarget(final boolean newValue) {
		repaint();
	}

	/**
	 * @see de.outstare.fortbattleplayer.player.PlayerConfigurationListener#changedShowShootingLine(boolean)
	 */
	public void changedShowShootingLine(final boolean newValue) {
		repaint();
	}
}
