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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.Battlefield;
import de.outstare.fortbattleplayer.model.Sector;

/**
 * A simple {@link Battlefield} which just is a set of {@link Area}s with
 * x/y-coordinates
 * 
 * @author daniel
 */
public class SimpleBattleField implements Battlefield {
	private final Map<Point, Area> cells = new HashMap<Point, Area>();
	private final Set<Sector> sectors = new HashSet<Sector>();
	private final int width;
	private final int height;

	/**
	 * @param width
	 * @param height
	 * @param cells
	 * @param sectors
	 */
	public SimpleBattleField(final int width, final int height, final Collection<Area> cells,
	        final Collection<Sector> sectors) {
		this.width = width;
		this.height = height;
		for (final Area cell : cells) {
			this.cells.put(cell.getLocation(), cell);
		}
		this.sectors.addAll(sectors);
	}

	/**
	 * generates Areas for the whole battlefield with as a single sector
	 * 
	 * @param width
	 * @param height
	 */
	public SimpleBattleField(final int width, final int height) {
		this(width, height, Collections.<Area> emptyList(), Collections.<Sector> emptyList());
		final Sector theSector = new SimpleSector(0, false, false, 0, 0, false, 0, null);
		sectors.add(theSector);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				final Area cell = new SimpleArea(x, y, theSector);
				cells.put(cell.getLocation(), cell);
			}
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Battlefield#getHeight()
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Battlefield#getWidth()
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Battlefield#_getArea(int, int)
	 */
	public Area _getArea(final int x, final int y) throws IllegalArgumentException {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			throw new IllegalArgumentException("the given coordinates does not exist on this battlefield!");
		}
		return cells.get(new Point(x, y));
	}
}
