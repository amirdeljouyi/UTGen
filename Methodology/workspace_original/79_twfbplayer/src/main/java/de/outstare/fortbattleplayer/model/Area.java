package de.outstare.fortbattleplayer.model;

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

/**
 * A small area of the Battlefield, which can hold exactly one {@link Combatant}
 * . Technically a cell on the map and has a x/y location.
 * 
 * @author daniel
 */
public interface Area {
	/**
	 * @return the position of this {@link Area} on the {@link Battlefield}
	 */
	Point getLocation();

	/**
	 * @return true if a {@link Combatant} is in this area
	 */
	boolean isOccupied();

	/**
	 * @return the {@link Combatant} who holds this {@link Area} or
	 *         <code>null</code> if <code>!{@link #isOccupied()}</code>
	 */
	Combatant getOccupier();

	/**
	 * The given {@link Combatant} completly fills this area. If another
	 * Combatant occupies this area he swappes places and goes to
	 * <code>oldPosition</code>
	 * 
	 * @param occupier
	 * @param oldPosition
	 *            must not be occupied (<code>!{@link #isOccupied()}</code>)
	 * @return the previous occupier, if the current occupier was moved to
	 *         <code>oldPosition</code> else <code>null</code>.
	 */
	Combatant occupy(Combatant occupier, Area oldPosition);

	/**
	 * no combatant occupies this area
	 */
	void free();

	/**
	 * @return the height of this area on the battlefield
	 */
	int getHeight();

	/**
	 * @return the sector this area belongs to (never null)
	 */
	Sector getSector();

	/**
	 * @param combatant
	 *            for whome the bonus should be calculated
	 * @return the amount of bonus for the given combatant in this area
	 */
	SectorBonus getSectorBonus(Combatant combatant);
}
