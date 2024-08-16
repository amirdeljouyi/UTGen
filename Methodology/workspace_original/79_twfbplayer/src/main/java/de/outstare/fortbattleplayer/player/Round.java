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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantState;

/**
 * A Round combines the actions of all {@link Combatant}s for a given point in
 * time. In a Round every player can execute Actions (move and shoot).
 * 
 * @author daniel
 */
public class Round {
	private static final Logger LOG = Logger.getLogger(Round.class.getName());
	private final int roundNo;
	private final Map<Combatant, CombatantState> initialStates;
	private final List<CombatantTurn> players;

	/**
	 * @param no
	 * @param initalStates
	 * @param actions
	 */
	public Round(final int no, final Map<Combatant, CombatantState> initalStates, final List<CombatantTurn> actions) {
		roundNo = no;
		players = actions;
		initialStates = initalStates;
	}

	/**
	 * @return the number of this round in a sequenze of rounds
	 */
	public int getNo() {
		return roundNo;
	}

	/**
	 * applies the actions of this round
	 * 
	 * @param config
	 */
	public void execute(final PlayerConfiguration config) {
		if (roundNo == 1) {
			setInitialState();
		}
		for (final CombatantTurn combatant : players) {
			combatant.execute(config);
			try {
				Thread.sleep(config.PLAYER_DELAY);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ensures, that the state of the {@link Combatant}s is as defined by this
	 * round.
	 */
	public void setInitialState() {
		LOG.fine("setting initial state of round " + roundNo);
		for (final Entry<Combatant, CombatantState> entry : initialStates.entrySet()) {
			final Combatant combatant = entry.getKey();
			final CombatantState state = entry.getValue();
			combatant._setState(state);
			assert combatant._health() == state.getHealth() : "health correctly restored";
		}
	}
}
