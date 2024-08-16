package de.outstare.fortbattleplayer;

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
 * This are the default values for the tunable parameters
 * 
 * @author daniel
 */
public class Configuration {
	/**
	 * pause between two rounds in milliseconds
	 */
	public static final int ROUND_DELAY = 3000;

	/**
	 * pause between two players in a round in milliseconds
	 */
	public static final int PLAYER_DELAY = 300;

	/**
	 * pause between two actions in milliseconds
	 */
	public static final int ACTION_DELAY = 0;

	/**
	 * draw a line to the point, where the player wants to go
	 */
	public static final boolean SHOW_MOVETARGETS = false;

	/**
	 * draw a line to the enemy ath wich the player shoots
	 */
	public static final boolean SHOW_SHOOTLINE = true;
}
