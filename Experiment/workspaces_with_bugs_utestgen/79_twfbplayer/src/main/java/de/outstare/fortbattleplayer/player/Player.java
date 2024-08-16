package de.outstare.fortbattleplayer.player;

import java.util.List;

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
 * A Player shows a recorded fortbattle.
 * 
 * @author daniel
 */
public interface Player {
	/**
	 * continuosly play one round after the other
	 */
	void play();

	/**
	 * stop the fortbattle
	 */
	void stop();

	/**
	 * changes the position of the player to the given round (not playing)
	 * 
	 * @param roundNo
	 */
	void gotoRound(int roundNo);

	/**
	 * @return the list of round numbers. These are not necessarily complete in
	 *         their order (for example it may be 2,3,11,20)
	 */
	List<Integer> getRoundNumbers();
}
