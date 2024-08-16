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

/**
 * The battlefield is the place where the battle takes place. It is divided into
 * a lot of {@link Area}s which are grouped into {@link Sector}s.
 * 
 * @author daniel
 */
public interface Battlefield {

	/**
	 * @return the amount of {@link Area}s horizontally in parallel
	 */
	int getWidth();

	/**
	 * @return the amount of {@link Area}s vertically in parallel
	 */
	int getHeight();

	/**
	 * internal method for getting the Area for the given coordinates
	 * 
	 * @param x
	 * @param y
	 * @return the area for the given coordinates
	 * @throws IllegalArgumentException
	 *             if not 0 <= x < {@link #getWidth()} and 0 <= y <
	 *             {@link #getHeight()}
	 */
	Area _getArea(int x, int y) throws IllegalArgumentException;
}
