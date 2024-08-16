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
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import de.outstare.fortbattleplayer.gui.battlefield.BattlefieldDrawing.BattlefieldLine;
import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.CharacterClass;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantObserver;
import de.outstare.fortbattleplayer.model.CombatantSide;
import de.outstare.fortbattleplayer.model.Weapon;

/**
 * draws a combatant. This class must be subclassed to identify the different
 * {@link CombatantSide}s.
 * 
 * @author daniel
 */
public class PlayerDrawing extends CellContent implements CombatantObserver {
	private static final long serialVersionUID = 1928594043582424210L;
	private static final Color selectedColor = Color.YELLOW;

	/**
	 * default visibility for access of inner class and subclasses
	 */
	boolean isSelected = false;

	private final BattlefieldDrawing battlefield;

	private final Combatant player;
	private boolean isAlive = true;
	private final CellContent currentTarget;

	/**
	 * setups a new drawing for the given player
	 * 
	 * @param player
	 * @param battlefield
	 */
	public PlayerDrawing(final Combatant player, final BattlefieldDrawing battlefield) {
		super();
		assert player != null && battlefield != null : "null parameter!";
		this.player = player;
		this.battlefield = battlefield;
		currentTarget = new MoveTarget(player);
		setPlayerPos(player._getLocation());
		player.addObserver(this);

		// transparent
		setOpaque(false);
		final LineContainer highlightTargetLine = new LineContainer();
		addMouseListener(new MouseAdapter() {
			/**
			 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseClicked(final MouseEvent e) {
				isSelected = !isSelected;
				repaint();
			}

			/**
			 * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseEntered(final MouseEvent e) {
				// show target
				final BattlefieldLine line = battlefield.drawTargetAt(PlayerDrawing.this, player._state().getTarget(),
				        getCombatantColor().darker(), false);
				highlightTargetLine.line = line;
			}

			/**
			 * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseExited(final MouseEvent e) {
				if (highlightTargetLine.line != null) {
					battlefield.removeLine(highlightTargetLine.line);
					highlightTargetLine.line = null;
				}
			}
		});
		// setToolTipText(player.getName());
		setVisible(true);
	}

	/**
	 * highlight this drawing
	 */
	public void mark() {
		isSelected = true;
		repaint();
	}

	/**
	 * remove highlighted status
	 */
	public void unmark() {
		isSelected = false;
		repaint();
	}

	private void setPlayerPos(final Area newPos) {
		final BattlefieldCell newCell = battlefield.getCell(newPos.getLocation());
		moveToCell(newCell);
		revalidate();
	}

	/**
	 * @return the color for this type of combatant
	 */
	protected Color getCombatantColor() {
		return player.getSide().color();
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (isAlive) {
			paintAlive(g);
			paintHealthBar(g);
			paintBayonet(g);
			paintOnline(g);
		} else {
			paintDeath(g);
		}
	}

	/**
	 * @param g
	 */
	private void paintAlive(final Graphics g) {
		final Color c = (isSelected) ? selectedColor : getCombatantColor();
		g.setColor(c);
		final int margin = 1;
		final int playerGraphicWidth = getWidth() - 2 * margin;
		final int playerGraphicHeight = getHeight() - 2 * margin;
		g.fillOval(margin, margin, playerGraphicWidth, playerGraphicHeight);

		drawClass(g, new DrawingArea(margin, margin, playerGraphicWidth, playerGraphicHeight));
	}

	/**
	 * @param g
	 * @param playerGraphicHeight
	 * @param playerGraphicWidth
	 */
	private void drawClass(final Graphics g, final DrawingArea drawingArea) {
		// TODO maybe use a subclass??
		final CharacterClass characterClass = player.getCharacterClass();
		if (characterClass != null) {
			g.setColor(Color.BLACK);
			switch (characterClass) {
			case GREENHORN:
				drawGreenhorn(g, drawingArea);
				break;
			case ADVENTURER:
				drawAdventurer(g, drawingArea);
				break;
			case DUELANT:
				drawDuelant(g, drawingArea);
				break;
			case SOLDIER:
				drawSoldier(g, drawingArea);
				break;
			case WORKER:
				drawWorker(g, drawingArea);
				break;

			default:
				drawUnknownClass(g, drawingArea);
				break;
			}
		}

	}

	/**
	 * @param ratio
	 *            percentage of the given drawing area to return
	 * @param drawingArea
	 *            the original area
	 * @return the centered part of the given area with the size percentage of
	 *         drawingArea
	 */
	private DrawingArea getCenteredArea(final double ratio, final DrawingArea drawingArea) {
		final int drawingWidth = (int) (drawingArea.width * ratio);
		final int drawingHeight = (int) (drawingArea.height * ratio);
		final int marginX = (drawingArea.width - drawingWidth) / 2;
		final int marginY = (drawingArea.height - drawingHeight) / 2;
		final int x = drawingArea.posX + marginX;
		final int y = drawingArea.posY + marginY;
		return new DrawingArea(x, y, drawingWidth, drawingHeight);
	}

	/**
	 * a very ugly painting to highlight an 'error'
	 * 
	 * @param g
	 * @param playerGraphicHeight
	 * @param playerGraphicWidth
	 */
	private void drawUnknownClass(final Graphics g, final DrawingArea drawingArea) {
		final DrawingArea classDrawing = getCenteredArea(0.5, drawingArea);
		g.setColor(Color.PINK);
		g.fillOval(classDrawing.posX, classDrawing.posY, classDrawing.width, classDrawing.height);
	}

	/**
	 * @param g
	 * @param playerGraphicHeight
	 * @param playerGraphicWidth
	 */
	private void drawGreenhorn(final Graphics g, final DrawingArea drawingArea) {
		final DrawingArea classDrawing = getCenteredArea(0.5, drawingArea);
		g.drawLine(classDrawing.posX, classDrawing.posY + classDrawing.height / 2, classDrawing.posX
		        + classDrawing.width, classDrawing.posY + classDrawing.height / 2);
		g.drawLine(classDrawing.posX + classDrawing.width / 2, classDrawing.posY, classDrawing.posX
		        + classDrawing.width / 2, classDrawing.posY + classDrawing.height);
	}

	/**
	 * @param g
	 * @param playerGraphicHeight
	 * @param playerGraphicWidth
	 */
	private void drawAdventurer(final Graphics g, final DrawingArea drawingArea) {
		final DrawingArea classDrawing = getCenteredArea(0.4, drawingArea);
		g.drawOval(classDrawing.posX, classDrawing.posY, classDrawing.width, classDrawing.height);
	}

	/**
	 * @param g
	 * @param playerGraphicHeight
	 * @param playerGraphicWidth
	 */
	private void drawDuelant(final Graphics g, final DrawingArea drawingArea) {
		final DrawingArea classDrawing = getCenteredArea(0.5, drawingArea);
		g.drawLine(classDrawing.posX, classDrawing.posY + classDrawing.height, classDrawing.posX + classDrawing.width,
		        classDrawing.posY);
	}

	/**
	 * @param g
	 * @param playerGraphicHeight
	 * @param playerGraphicWidth
	 */
	private void drawSoldier(final Graphics g, final DrawingArea drawingArea) {
		final DrawingArea classDrawing = getCenteredArea(0.5, drawingArea);
		g.drawLine(classDrawing.posX, classDrawing.posY + classDrawing.height, classDrawing.posX + classDrawing.width,
		        classDrawing.posY);
		g.drawLine(classDrawing.posX, classDrawing.posY, classDrawing.posX + classDrawing.width, classDrawing.posY
		        + classDrawing.height);
	}

	/**
	 * @param g
	 * @param playerGraphicHeight
	 * @param playerGraphicWidth
	 */
	private void drawWorker(final Graphics g, final DrawingArea drawingArea) {
		final DrawingArea classDrawing = getCenteredArea(0.5, drawingArea);
		g.drawRect(classDrawing.posX, classDrawing.posY, classDrawing.width, classDrawing.height);
	}

	private void paintDeath(final Graphics g) {
		final Color skullColor = Color.WHITE.darker();
		final Color lineColor = getCombatantColor();
		g.setColor(skullColor);
		g.fillOval(1, 1, getWidth() - 2, getHeight() - 2);
		g.setColor(lineColor);
		g.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
		// eyes
		final int fifth = getWidth() / 5;
		final int tenth = getWidth() / 10;
		final int h = getHeight() / 4;
		g.drawOval(fifth, h, fifth, tenth);
		g.drawOval(3 * fifth, h, fifth, tenth);
		// bones
		g.drawLine(1, getHeight() / 2, getWidth() - 1, getHeight() - 1);
		g.drawLine(1, getHeight() - 1, getWidth() - 1, getHeight() / 2);
	}

	/**
	 * @param g
	 */
	private void paintHealthBar(final Graphics g) {
		final int barHeight = (int) (getHeight() / 3.0);
		final int y = getHeight() - barHeight;
		// background
		g.setColor(Color.BLACK);
		g.fillRect(0, y, getWidth(), barHeight);
		// foreground
		final int barWidth = (int) (getHealthRatio() * (getWidth() - 2));
		g.setColor(getCombatantColor());
		g.fillRect(1, y + 1, barWidth, barHeight - 2);
	}

	/**
	 * @param g
	 */
	private void paintOnline(final Graphics g) {
		if (player.isOnline()) {
			final int diameter = (int) (getHeight() / 3.0);
			final int x = getWidth() - diameter;
			// background
			g.setColor(Color.BLACK);
			g.fillOval(x, 0, diameter, diameter);
			// foreground
			g.setColor(Color.GREEN);
			g.fillOval(x + 1, 1, diameter - 2, diameter - 2);
		}
	}

	/**
	 * @param g
	 */
	private void paintBayonet(final Graphics g) {
		if (player.usesBayonet()) {
			final int height = (int) (getHeight() * 0.7);
			final Polygon bayonet = bayonetDrawing(height);
			final Graphics bayonetArea = g.create((int) (getWidth() * 0.1), 0, height, height);
			bayonetArea.setColor(Color.LIGHT_GRAY);
			bayonetArea.fillPolygon(bayonet);
			bayonetArea.setColor(Color.BLACK);
			bayonetArea.drawPolygon(bayonet);
		}
	}

	/**
	 * we assume a quadrat as drawing area for the designed aspect ratio
	 * 
	 * @param drawingHeight
	 * @return
	 */
	private Polygon bayonetDrawing(final int drawingHeight) {
		final int bayonetWidth = (int) (drawingHeight * 0.25);
		final int shaftWidth = (int) (drawingHeight * 0.3);
		final int shaftHeight = (int) (drawingHeight * 0.8);
		final int drawingWidth = shaftWidth;
		final int middle = drawingWidth / 2;

		final Polygon bayonet = new Polygon();

		int currentX, currentY;
		/**
		 * should look like
		 * 
		 * <pre>
		 * |\
		 * ||
		 * ||
		 * ||
		 * =_=
		 * </pre>
		 */
		// start at the top
		currentX = middle - (bayonetWidth / 2);
		currentY = 0;
		bayonet.addPoint(currentX, currentY);
		// go around clockwise
		currentX = middle + (bayonetWidth / 2);
		currentY = 2 * bayonetWidth;
		bayonet.addPoint(currentX, currentY);

		currentY = shaftHeight;
		bayonet.addPoint(currentX, currentY);

		// shaft
		currentX = drawingWidth;
		bayonet.addPoint(currentX, currentY);

		currentY = drawingHeight;
		bayonet.addPoint(currentX, currentY);

		currentX = 0;
		bayonet.addPoint(currentX, currentY);

		currentY = shaftHeight;
		bayonet.addPoint(currentX, currentY);

		currentX = middle - (bayonetWidth / 2);
		bayonet.addPoint(currentX, currentY);

		// end at the top
		currentY = 0;
		bayonet.addPoint(currentX, currentY);

		return bayonet;
	}

	/**
	 * @return relative amount of health between 1 and 0
	 */
	private double getHealthRatio() {
		return player._health() / (double) player._maxHealth();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#aimsAt(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void aimsAt(final Combatant combatant, final Combatant target) {
		battlefield.drawShootingAt(this, target._getLocation(), getCombatantColor().darker());
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#hasMoved(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Area)
	 */
	public void hasMoved(final Combatant combatant, final Area newPos) {
		battlefield.drawMovingTo(this, newPos);
		setPlayerPos(newPos);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isDead(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void isDead(final Combatant combatant) {
		isAlive = false;
		// move to back
		final Container parent = getParent();
		if (parent != null) {
			parent.setComponentZOrder(this, parent.getComponentCount() - 1);
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isAlive(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void isAlive(final Combatant combatant) {
		isAlive = true;
		final Container parent = getParent();
		if (parent != null) {
			parent.setComponentZOrder(this, 0);
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isHit(de.outstare.fortbattleplayer.model.Combatant,
	 *      int, int)
	 */
	public void isHit(final Combatant combatant, final int damage, final int oldHealthAmount) {
		// refresh health bar
		repaint();
		// try {
		// EventQueue.invokeAndWait(new Runnable() {
		// public void run() {
		// drawText(String.valueOf(damage), Color.LIGHT_GRAY);
		// }
		// });
		// } catch (final InterruptedException e) {
		// e.printStackTrace();
		// } catch (final InvocationTargetException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#newDestination(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Area)
	 */
	public void newDestination(final Combatant combatant, final Area destination) {
		final BattlefieldCell destCell = battlefield.getCell(destination.getLocation());
		currentTarget.moveToCell(destCell);

		battlefield.drawTargetAt(this, destination, getCombatantColor().brighter().brighter());
	}

	/**
	 * the text will be horizontally left aligned and vertically centered
	 * 
	 * @param text
	 * @param pos
	 * @param color
	 */
	void drawText(final String text, final Color color) {
		final Graphics g = getGraphics();
		if (g != null) {
			g.setColor(color);
			g.setFont(g.getFont().deriveFont((float) getWidth() / text.length() * 1.5f));
			final int fontHeight = g.getFont().getSize();
			g.drawString(text, 0, getHeight() - (getHeight() - fontHeight) / 2);
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isOnline(de.outstare.fortbattleplayer.model.Combatant,
	 *      boolean)
	 */
	public void isOnline(final Combatant combatant, final boolean changed) {
		// ignore event and use state of combatant for painting
		if (changed) {
			repaint();
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.gui.battlefield.CellContent#getDescription()
	 */
	@Override
	public String getDescription() {
		final StringBuilder description = new StringBuilder(player.getName().length() + 10);
		if (!isAlive) {
			description.append("&#x2020; ");
		}
		description.append(player.getName());
		description.append(" (");
		description.append(player._health());
		description.append("/");
		description.append(player._maxHealth());
		description.append(" HP) - ");
		description.append(player.getCity());

		return description.toString();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#hasSwappedPosition(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void hasSwappedPosition(final Combatant combatant, final Combatant swappedWith) {
		// ignore
	}

	/**
	 * @return the name of the drawn {@link Combatant}
	 */
	public String getCombatantName() {
		return player.getName();
	}

	/**
	 * @return the gun of the player which is drawn
	 */
	public Weapon getGun() {
		return player.getWeapon();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#criticalShot(de.outstare.fortbattleplayer.model.Combatant,
	 *      Combatant, int)
	 */
	public void criticalShot(final Combatant combatant, final Combatant victim, final int damage) {
		// ignore
	}

	private static class DrawingArea {
		final int posX;
		final int posY;
		final int width;
		final int height;

		/**
		 * @param posX
		 * @param posY
		 * @param width
		 * @param height
		 */
		public DrawingArea(final int posX, final int posY, final int width, final int height) {
			this.posX = posX;
			this.posY = posY;
			this.width = width;
			this.height = height;
		}
	}

	/**
	 * @return
	 */
	boolean combatantIsDead() {
		return !isAlive;
	}

	private static class LineContainer {
		BattlefieldLine line = null;

		/**
		 * Constructor for visibility
		 */
		LineContainer() {
			super();
		}
	}
}
