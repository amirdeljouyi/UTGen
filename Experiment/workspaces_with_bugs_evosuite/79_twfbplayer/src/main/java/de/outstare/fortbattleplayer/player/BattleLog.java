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

import java.io.PrintStream;

import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantObserver;
import de.outstare.fortbattleplayer.player.Battleplayer.RoundListener;

/**
 * <p>
 * A BattleLog provides all battle information as text.
 * </p>
 * TODO internationalize
 * 
 * @author daniel
 */
public class BattleLog implements CombatantObserver, RoundListener {
	private final PrintStream output;

	/**
	 * @param output
	 *            for text
	 * @param controller
	 * @param combatants
	 */
	public BattleLog(final PrintStream output, final Battleplayer controller, final CombatantEventDispatcher combatants) {
		super();
		assert output != null : "output may not be null!";
		this.output = output;

		controller.addRoundListener(this);
		combatants.addCombatantObserver(this);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#aimsAt(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void aimsAt(final Combatant combatant, final Combatant target) {
		output.print(combatant.getName());
		output.print(" shoots at ");
		output.print(target.getName());
		output.println();
		output.flush();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#hasMoved(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Area)
	 */
	public void hasMoved(final Combatant combatant, final Area newPos) {
		output.print(combatant.getName());
		output.print(" moves to ");
		output.print(newPos);
		output.println();
		output.flush();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isDead(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void isDead(final Combatant combatant) {
		output.print(combatant.getName());
		output.print(" has died");
		output.println();
		output.flush();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isHit(de.outstare.fortbattleplayer.model.Combatant,
	 *      int, int)
	 */
	public void isHit(final Combatant combatant, final int damage, final int oldHealthAmount) {
		output.print(combatant.getName());
		output.print(" is hit and looses  ");
		output.print(damage);
		output.print(" HP");
		output.println();
		output.flush();
	}

	/**
	 * @see de.outstare.fortbattleplayer.player.Battleplayer.RoundListener#nextRound(int)
	 */
	public void nextRound(final int roundNo) {
		output.println("========================================");
		output.print("Round ");
		output.print(roundNo);
		output.print(" starts");
		output.println();
		output.flush();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isAlive(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void isAlive(final Combatant combatant) {
		output.print(combatant.getName());
		output.print(" is no longer dead");
		output.println();
		output.flush();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#newDestination(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Area)
	 */
	public void newDestination(final Combatant combatant, final Area destination) {
		output.print(combatant.getName());
		output.print(" wants to go to ");
		output.print(destination);
		output.println();
		output.flush();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isOnline(de.outstare.fortbattleplayer.model.Combatant,
	 *      boolean)
	 */
	public void isOnline(final Combatant combatant, final boolean changed) {
		if (changed) {
			output.print(combatant.getName());
			output.print(" is now ");
			output.print(combatant.isOnline() ? "online" : "offline");
			output.println();
			output.flush();
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#hasSwappedPosition(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void hasSwappedPosition(final Combatant combatant, final Combatant swappedWith) {
		output.print(combatant.getName());
		output.print(" switched position with ");
		output.print(swappedWith.getName());
		output.println();
		output.flush();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#criticalShot(de.outstare.fortbattleplayer.model.Combatant,
	 *      Combatant, int)
	 */
	public void criticalShot(final Combatant combatant, final Combatant victim, final int damage) {
		output.print(combatant.getName());
		output.print(" made a critical hit");
		output.println();
		output.flush();
	}
}
