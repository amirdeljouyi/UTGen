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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import de.outstare.fortbattleplayer.model.Combatant;

/**
 * A {@link Battleplayer} controls the battle. It is a puppet player controlling
 * the behavior of the {@link Combatant}s.
 * 
 * @author daniel
 */
public class Battleplayer implements Player {
	private static final transient Logger LOG = Logger.getLogger(Battleplayer.class.getName());
	private final Battleplan plan;
	private final Set<RoundListener> listeners = new HashSet<RoundListener>();
	private boolean isPaused = false;
	/**
	 * round numbers may not be sequential (this variable is also used for
	 * locking during a round)
	 */
	private int currentRoundNo;
	private volatile boolean isPlaying = false;
	private volatile boolean shouldStop = false;
	/**
	 * controls some aspects of the behavior of the player
	 */
	public final PlayerConfiguration config;

	/**
	 * @param plan
	 * @param config
	 */
	public Battleplayer(final Battleplan plan, final PlayerConfiguration config) {
		this.plan = plan;
		this.config = config;

		if (!plan.hasMoreRounds(0)) {
			throw new IllegalStateException("Cannot play a battle without rounds!");
		}
		currentRoundNo = plan.getNextRound(0);
	}

	/**
	 * @param plan
	 */
	public Battleplayer(final Battleplan plan) {
		this(plan, new PlayerConfiguration());
	}

	/**
	 * let the battle begin! The whole battle is done by executing the
	 * {@link Battleplan}.
	 */
	public void play() {
		isPlaying = true;
		LOG.info("now playing");
		while (!shouldStop) {
			playCurrentRound(currentRoundNo);
			if (plan.hasMoreRounds(currentRoundNo)) {
				currentRoundNo = plan.getNextRound(currentRoundNo);
			} else {
				break;
			}
		}
		shouldStop = false;
		isPlaying = false;
		LOG.info("stopped playing");
	}

	/**
	 * blocks until current round has ended
	 * 
	 * @see de.outstare.fortbattleplayer.player.Player#gotoRound(de.outstare.fortbattleplayer.player.Round)
	 */
	public void gotoRound(final int i) {
		assert plan.hasRoundNo(i) : "round with number " + i + " must exist!";
		stop();
		while (isPlaying) {
			try {
				LOG.fine("waiting for round " + currentRoundNo + " to end to go to round " + i);
				Thread.sleep(500);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		synchronized (this) {
			currentRoundNo = i;
		}
		plan.resetToRound(currentRoundNo);
	}

	/**
	 * executes the round of the internal state
	 */
	private void playCurrentRound(final int roundNo) {
		fireNewRoundEvent(roundNo);
		plan.executeRound(roundNo, config);
		delayNextRound();
	}

	/**
	 * wait the time given in {@link PlayerConfiguration#ROUND_DELAY}
	 */
	private void delayNextRound() {
		try {
			Thread.sleep(config.ROUND_DELAY);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * tell all listeners that a new round will start now
	 * 
	 * @param roundNo
	 */
	private void fireNewRoundEvent(final int roundNo) {
		for (final RoundListener listener : listeners) {
			listener.nextRound(roundNo);
		}
	}

	/**
	 * @param listener
	 */
	public void addRoundListener(final RoundListener listener) {
		listeners.add(listener);
	}

	/**
	 * @see de.outstare.fortbattleplayer.player.Player#stop()
	 */
	public void stop() {
		if (isPlaying) {
			LOG.fine("stopping");
			shouldStop = true;
		} else {
			LOG.fine("already stopped");
		}
	}

	/**
	 * A RoundListener waits for Round events.
	 * 
	 * @author daniel
	 */
	public static interface RoundListener {
		/**
		 * a new round will begin immediatly
		 * 
		 * @param roundNo
		 */
		void nextRound(int roundNo);
	}

	/**
	 * @return the number of rounds this player shows
	 */
	public int numberOfRounds() {
		return plan.numberOfRounds();
	}

	/**
	 * toggles pause which holds playing rounds. if this player is currently
	 * paused can be checked with isPaused()
	 */
	public void pause() {
		if (isPaused) {
			config.unlock();
			isPaused = false;
		} else {
			config.lock();
			isPaused = true;
		}
	}

	/**
	 * @return <code>true</code> if this player is playing and is currently hold
	 */
	public boolean isPaused() {
		return isPaused;
	}

	/**
	 * @see de.outstare.fortbattleplayer.player.Player#getRoundNumbers()
	 */
	public List<Integer> getRoundNumbers() {
		// always generating a new list
		final List<Integer> rounds = new ArrayList<Integer>(plan.numberOfRounds());
		// check if first round exists
		final int firstRound = 0;
		if (plan.hasRoundNo(firstRound)) {
			rounds.add(Integer.valueOf(firstRound));
		}
		// add all following rounds
		int roundNo;
		for (roundNo = plan.getNextRound(firstRound); plan.hasMoreRounds(roundNo); roundNo = plan.getNextRound(roundNo)) {
			rounds.add(Integer.valueOf(roundNo));
		}
		// add last round (has no round after it)
		rounds.add(Integer.valueOf(roundNo));
		return rounds;
	}
}
