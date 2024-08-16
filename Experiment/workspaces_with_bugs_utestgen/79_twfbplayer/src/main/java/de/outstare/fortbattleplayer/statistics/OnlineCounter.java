package de.outstare.fortbattleplayer.statistics;

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
 * This is only correct if online and offline events are triggered
 * 
 * @author daniel
 */
class OnlineCounter {
	private final static int NOT_YET_ONLINE = -1;

	private int onlineEvents = 0;
	private int offlineEvents = 0;
	private int firstOnline = NOT_YET_ONLINE;

	/**
	 * add one online event to the counter
	 */
	void addOnline() {
		if (firstOnline == NOT_YET_ONLINE) {
			firstOnline = offlineEvents;
		}
		onlineEvents++;
	}

	/**
	 * add one offline event to the counter
	 */
	void addOffline() {
		offlineEvents++;
	}

	/**
	 * @return the amount of online events
	 */
	int onlineEvents() {
		return onlineEvents;
	}

	/**
	 * @return <code>true</code> if this counter has not counted any online
	 *         event
	 */
	boolean wasOffline() {
		return firstOnline == NOT_YET_ONLINE;
	}

	/**
	 * @return the number of events, after that the first online event was
	 *         counted
	 */
	int firstOnline() {
		return firstOnline;
	}

	/**
	 * @return the sum of alle caunted online and offline events
	 */
	int sumOfEvents() {
		return onlineEvents + offlineEvents;
	}
}