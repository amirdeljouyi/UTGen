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

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * A plan which determines each step of the battle. A battle is divided into
 * {@link Round}s.
 * 
 * @author daniel
 */
public class Battleplan {
	private static final transient Logger LOG = Logger.getLogger(Battleplan.class.getName());
	private final SortedMap<Integer, Round> rounds = new TreeMap<Integer, Round>();

	/**
	 * Create a new plan with the given rounds
	 * 
	 * @param roundsOfPlan
	 */
	public Battleplan(final Collection<Round> roundsOfPlan) {
		mapRoundsToNumbers(roundsOfPlan);
	}

	/**
	 * @param roundsOfPlan
	 */
	private void mapRoundsToNumbers(final Collection<Round> roundsOfPlan) {
		for (final Round round : roundsOfPlan) {
			final int no = round.getNo();
			final Integer roundNo = Integer.valueOf(no);
			if (rounds.containsKey(roundNo)) {
				LOG.warning("multiple rounds with number " + roundNo + "! overwriting");
			}
			rounds.put(roundNo, round);
		}
	}

	/**
	 * @return the number of rounds this plan has
	 */
	public int numberOfRounds() {
		return rounds.size();
	}

	/**
	 * executes the given {@link Round} of this plan
	 * 
	 * @param roundNo
	 *            0 <= roundNo <= numberOfRounds()
	 * @param config
	 */
	public void executeRound(final int roundNo, final PlayerConfiguration config) {
		getRound(roundNo).execute(config);
	}

	/**
	 * @param roundNo
	 * @return
	 * @throws IllegalArgumentException
	 */
	private Round getRound(final int roundNo) throws IllegalArgumentException {
		final Integer roundKey = Integer.valueOf(roundNo);
		if (!rounds.containsKey(roundKey)) {
			throw new IllegalArgumentException("the given round number does not exist: " + roundNo);
		}

		final Round round = rounds.get(roundKey);
		return round;
	}

	/**
	 * @param no
	 * @return <code>true</code> if this plan contains a round with the given
	 *         number
	 */
	public boolean hasRoundNo(final int no) {
		final Integer roundKey = Integer.valueOf(no);
		return rounds.containsKey(roundKey);
	}

	/**
	 * @param roundNo
	 */
	public void resetToRound(final int roundNo) {
		if (hasRoundNo(roundNo)) {
			getRound(roundNo).setInitialState();
		}
	}

	/**
	 * @param roundNo
	 * @return <code>true</code> if more rounds follow after the given round
	 *         number.
	 */
	public boolean hasMoreRounds(final int roundNo) {
		return !getRemainingRounds(roundNo).isEmpty();
	}

	/**
	 * @require hasMoreRounds(currentRoundNo)
	 * @param currentRoundNo
	 * @return the number of the following round
	 */
	public int getNextRound(final int currentRoundNo) {
		final SortedMap<Integer, Round> remainingRounds = getRemainingRounds(currentRoundNo);
		assert remainingRounds.size() > 0 : "more rounds must exist!";
		return remainingRounds.firstKey().intValue();
	}

	/**
	 * @param currentRoundNo
	 * @return all rounds that follow after the given round number
	 */
	private SortedMap<Integer, Round> getRemainingRounds(final int currentRoundNo) {
		final Integer nextRoundNo = Integer.valueOf(currentRoundNo + 1);
		final SortedMap<Integer, Round> remainingRounds = rounds.tailMap(nextRoundNo);
		return remainingRounds;
	}
}
