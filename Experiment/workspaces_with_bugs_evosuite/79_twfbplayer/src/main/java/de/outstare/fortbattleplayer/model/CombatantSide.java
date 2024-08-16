package de.outstare.fortbattleplayer.model;

import java.awt.Color;

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
 * A party of the battle whose {@link Combatant}s fight together against the
 * others.
 * 
 * @author daniel
 */
public enum CombatantSide {
	/**
	 * the attackers try to take control over the fort.
	 */
	ATTACKER(new Color(0xff1111)),
	/**
	 * the owner of the fort and their allies will defend the fort.
	 */
	DEFENDER(new Color(0x1111ff));

	private final Color color;

	private CombatantSide(final Color color) {
		this.color = color;
	}

	/**
	 * @return the color of this side for graphical representation
	 */
	public Color color() {
		return color;
	}

	/**
	 * @return a variant of the default color
	 */
	public Color darkColor() {
		return halfBrightness(color);
	}

	private static Color halfBrightness(final Color c) {
		final float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
		hsb[2] = hsb[2] / 2;
		return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
	}
}
