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
import java.util.HashSet;
import java.util.Set;

import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantObserver;

/**
 * A CombatantEventDispatcher collects the events of all combatants, to give it
 * to registered listeners. So not every {@link CombatantObserver} has to
 * register itself to every {@link Combatant}, but just to this collection of
 * all combatants.
 * 
 * @author daniel
 */
public class CombatantEventDispatcher implements CombatantObserver {
	private final Set<CombatantObserver> observers = new HashSet<CombatantObserver>();

	/**
	 * @param combatants
	 *            for which this dispatcher will forward events
	 */
	public CombatantEventDispatcher(final Collection<Combatant> combatants) {
		for (final Combatant combatant : combatants) {
			combatant.addObserver(this);
		}
	}

	/**
	 * @param observer
	 */
	public void addCombatantObserver(final CombatantObserver observer) {
		observers.add(observer);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#aimsAt(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void aimsAt(final Combatant combatant, final Combatant target) {
		for (final CombatantObserver obs : observers) {
			obs.aimsAt(combatant, target);
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#hasMoved(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Area)
	 */
	public void hasMoved(final Combatant combatant, final Area newPos) {
		for (final CombatantObserver obs : observers) {
			obs.hasMoved(combatant, newPos);
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isDead(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void isDead(final Combatant combatant) {
		for (final CombatantObserver obs : observers) {
			obs.isDead(combatant);
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isHit(de.outstare.fortbattleplayer.model.Combatant,
	 *      int, int)
	 */
	public void isHit(final Combatant combatant, final int damage, final int oldHealthAmount) {
		for (final CombatantObserver obs : observers) {
			obs.isHit(combatant, damage, oldHealthAmount);
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isAlive(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void isAlive(final Combatant combatant) {
		for (final CombatantObserver obs : observers) {
			obs.isAlive(combatant);
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#newDestination(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Area)
	 */
	public void newDestination(final Combatant combatant, final Area destination) {
		for (final CombatantObserver obs : observers) {
			obs.newDestination(combatant, destination);
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isOnline(de.outstare.fortbattleplayer.model.Combatant,
	 *      boolean)
	 */
	public void isOnline(final Combatant combatant, final boolean changed) {
		for (final CombatantObserver obs : observers) {
			obs.isOnline(combatant, changed);
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#hasSwappedPosition(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void hasSwappedPosition(final Combatant combatant, final Combatant swappedWith) {
		for (final CombatantObserver obs : observers) {
			obs.hasSwappedPosition(combatant, swappedWith);
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#criticalShot(de.outstare.fortbattleplayer.model.Combatant,
	 *      Combatant, int)
	 */
	public void criticalShot(final Combatant combatant, final Combatant victim, final int damage) {
		for (final CombatantObserver obs : observers) {
			obs.criticalShot(combatant, victim, damage);
		}
	}
}
