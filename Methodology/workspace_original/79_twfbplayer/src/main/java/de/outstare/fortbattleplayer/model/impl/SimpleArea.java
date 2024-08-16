package de.outstare.fortbattleplayer.model.impl;

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
import java.util.logging.Logger;

import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.Sector;
import de.outstare.fortbattleplayer.model.SectorBonus;

/**
 * A simple area which can hold one {@link Combatant}
 * 
 * @author daniel
 */
public class SimpleArea implements Area {
	private static final transient Logger LOG = Logger.getLogger(SimpleArea.class.getName());
	private Combatant occupier;
	private final Point location;
	private final Sector sector;

	/**
	 * Creates a new Area identified by the given coordinates
	 * 
	 * @param x
	 * @param y
	 * @param sector
	 *            not <code>null</code>
	 */
	public SimpleArea(final int x, final int y, final Sector sector) {
		this(new Point(x, y), sector);
	}

	/**
	 * Creates a new Area identified by the given location
	 * 
	 * @param location
	 * @param sector
	 *            not <code>null</code>
	 */
	public SimpleArea(final Point location, final Sector sector) {
		this.location = location;
		this.sector = sector;
		sector._addArea(this);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Area " + location.x + "/" + location.y + " [occupied by " + occupier + "]";
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Area#free()
	 */
	public void free() {
		occupier = null;
		sector.free();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Area#isOccupied()
	 */
	public boolean isOccupied() {
		return occupier != null;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Area#getOccupier()
	 */
	public Combatant getOccupier() {
		return occupier;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Area#occupy(de.outstare.fortbattleplayer.model.Combatant,
	 *      Area)
	 */
	public Combatant occupy(final Combatant newOccupier, final Area oldPosition) {
		Combatant oldOccupier = null;
		// System.out.println(toString() + " will be occupied by " + newOccupier
		// + " from " + oldPosition);
		if (isOccupied() && !occupier.equals(newOccupier)) {
			if (oldPosition == null) {
				// IGNORE, because in the initial setup, multiple combatants may
				// stay in the same area
				// throw new IllegalStateException(
				// "This Area is already occupied and no Area for the current occupier was given!");
			} else if (oldPosition.isOccupied()) {
				// IGNORE, for old battles with wrong start positions
				// throw new IllegalArgumentException(toString() +
				// " cannot be occupied by " + newOccupier.getName()
				// + " because " + oldPosition.toString()
				// +
				// " (the source Area of the occupier) is not free for the current occupier of this Area!");
			} else {
				// swap places
				// (TODO this is like a hack, but it is what The West does :-/)
				oldOccupier = occupier;
				LOG.fine(occupier.getName() + " and " + newOccupier + " swapping positions");
				occupier.move(oldPosition);
			}
		}
		occupier = newOccupier;
		sector.gainControl(occupier.getSide());
		return oldOccupier;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Area#getLocation()
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Area#getHeight()
	 */
	public int getHeight() {
		return sector.getHeight();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Area#getSector()
	 */
	public Sector getSector() {
		assert sector != null : "every area needs sector!";
		return sector;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Area#getSectorBonus(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public SectorBonus getSectorBonus(final Combatant combatant) {
		return sector.getBonus(combatant.getCharacterClass());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SimpleArea)) {
			return false;
		}
		final SimpleArea other = (SimpleArea) obj;
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		return true;
	}

}
