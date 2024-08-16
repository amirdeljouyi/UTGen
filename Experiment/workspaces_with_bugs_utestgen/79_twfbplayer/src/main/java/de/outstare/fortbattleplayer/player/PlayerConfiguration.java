package de.outstare.fortbattleplayer.player;

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

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.outstare.fortbattleplayer.Configuration;

/**
 * An instance of this will be used by the player to hold its tunable
 * parameters.
 * 
 * @author daniel
 */
public class PlayerConfiguration implements Cloneable {
	/**
	 * a configuration which skips animation (used for purposes where the user
	 * should not see whats going on)
	 */
	public static final PlayerConfiguration NO_DELAY = new PlayerConfiguration();
	static {
		NO_DELAY.ACTION_DELAY = 0;
		NO_DELAY.PLAYER_DELAY = 0;
		NO_DELAY.ROUND_DELAY = 0;
		NO_DELAY.setShowMoveTargets(false);
		NO_DELAY.setShowShootline(false);
	}

	/**
	 * this is used to control who currently does something with the player
	 */
	private final Lock playLock = new ReentrantLock();

	private final Set<PlayerConfigurationListener> listeners = new HashSet<PlayerConfigurationListener>();

	/**
	 * current state of the player
	 */
	private PlayerState state = PlayerState.STOP;

	/**
	 * pause between two rounds in milliseconds
	 */
	public volatile int ROUND_DELAY = Configuration.ROUND_DELAY;

	/**
	 * pause between two players in a round in milliseconds
	 */
	public volatile int PLAYER_DELAY = Configuration.PLAYER_DELAY;

	/**
	 * pause between two actions in milliseconds
	 */
	public volatile int ACTION_DELAY = Configuration.ACTION_DELAY;

	/**
	 * draw a line to the point, where the player wants to go
	 */
	private volatile boolean SHOW_MOVETARGETS = Configuration.SHOW_MOVETARGETS;

	/**
	 * draw a line to the enemy ath wich the player shoots
	 */
	private volatile boolean SHOW_SHOOTLINE = Configuration.SHOW_SHOOTLINE;

	/**
	 * @param other
	 */
	public void setTo(final PlayerConfiguration other) {
		ROUND_DELAY = other.ROUND_DELAY;
		PLAYER_DELAY = other.PLAYER_DELAY;
		ACTION_DELAY = other.ACTION_DELAY;
	}

	/**
	 * @return a copy of this object
	 * @throws CloneNotSupportedException
	 */
	public PlayerConfiguration copy() throws CloneNotSupportedException {
		return (PlayerConfiguration) clone();
	}

	/**
	 * should only be called by {@link Battleplayer}!
	 * 
	 * @param newState
	 */
	synchronized void setState(final PlayerState newState) {
		state = newState;
	}

	/**
	 * @return true if the player should be playing
	 */
	public synchronized boolean isPlaying() {
		return state == PlayerState.PLAY;
	}

	/**
	 * @return true if the player is hold
	 */
	public synchronized boolean isPaused() {
		return state == PlayerState.PAUSE;
	}

	/**
	 * @return true if the player is stopped
	 */
	public synchronized boolean isStopped() {
		return state == PlayerState.STOP;
	}

	/**
	 * exclusive use of the player
	 */
	void lock() {
		playLock.lock();
	}

	/**
	 * release the player
	 */
	void unlock() {
		playLock.unlock();
	}

	/**
	 * @return <code>true</code> if lines to movement targets should be
	 *         displayed
	 */
	public boolean showMoveTargets() {
		return SHOW_MOVETARGETS;
	}

	/**
	 * @param showMovetargets
	 */
	public void setShowMoveTargets(final boolean showMovetargets) {
		SHOW_MOVETARGETS = showMovetargets;
		for (final PlayerConfigurationListener listener : listeners) {
			listener.changedShowMoveTarget(SHOW_MOVETARGETS);
		}
	}

	/**
	 * @return <code>true</code> if shooting lines should be displayed
	 */
	public boolean showShootline() {
		return SHOW_SHOOTLINE;
	}

	/**
	 * @param showShootline
	 */
	public void setShowShootline(final boolean showShootline) {
		SHOW_SHOOTLINE = showShootline;
		for (final PlayerConfigurationListener listener : listeners) {
			listener.changedShowShootingLine(SHOW_SHOOTLINE);
		}
	}

	/**
	 * Adds a listener which will be notified about changes.
	 * 
	 * @param listener
	 */
	public void addListener(final PlayerConfigurationListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes the listener from the registered ones.
	 * 
	 * @param listener
	 */
	public void removeListener(final PlayerConfigurationListener listener) {
		listeners.remove(listener);
	}
}
