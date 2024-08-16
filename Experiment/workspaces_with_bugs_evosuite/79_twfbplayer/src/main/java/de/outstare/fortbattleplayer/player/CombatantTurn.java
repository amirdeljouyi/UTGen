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

import de.outstare.fortbattleplayer.model.Combatant;

/**
 * The turn of a {@link Combatant} in a Round. It is a list of actions which a
 * {@link Combatant} executes in one round.
 * 
 * @author daniel
 */
public class CombatantTurn {
	private final List<Action> actions;

	/**
	 * @param actions
	 */
	public CombatantTurn(final List<Action> actions) {
		this.actions = actions;
	}

	/**
	 * executes the {@link Action}s of a {@link Combatant}
	 * 
	 * @param config
	 */
	public void execute(final PlayerConfiguration config) {
		for (final Action a : actions) {
			// System.out.println("\t" + a);
			config.lock();
			a.execute();
			config.unlock();
			try {
				Thread.sleep(config.ACTION_DELAY);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
