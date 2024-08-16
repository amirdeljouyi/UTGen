package de.outstare.fortbattleplayer.model;

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
 * A CombatantObserver wants to be notified about changes of an
 * {@link Combatant}. It is a listener for all events, which a Combatant may
 * fire.
 * 
 * @author daniel
 */
public interface CombatantObserver {
	/**
	 * The position of the {@link Combatant} on the battlefield has changed
	 * 
	 * @param combatant
	 *            which notified this observer
	 * @param newPos
	 */
	void hasMoved(Combatant combatant, Area newPos);

	/**
	 * the last move was to a position which was already hold by another
	 * {@link Combatant}, so they swapped positions
	 * 
	 * @param combatant
	 *            the player who started the swap
	 * @param swappedWith
	 *            the player who was moved to the initiators position
	 */
	void hasSwappedPosition(Combatant combatant, Combatant swappedWith);

	/**
	 * The player wants the given combatant to move to the given destination
	 * 
	 * @param combatant
	 *            which notified this observer
	 * @param destination
	 */
	void newDestination(Combatant combatant, Area destination);

	/**
	 * The combatant will shoot at the given target. (The hit is recognized at
	 * the victim)
	 * 
	 * @param combatant
	 *            which notified this observer
	 * @param target
	 */
	void aimsAt(Combatant combatant, Combatant target);

	/**
	 * The {@link Combatant} received a hit (was shot).
	 * 
	 * @param combatant
	 *            which notified this observer
	 * @param damage
	 *            the loss of healthpoints
	 * @param oldHealthAmount
	 *            TODO
	 */
	void isHit(Combatant combatant, int damage, int oldHealthAmount);

	/**
	 * The healthpoints reached zero.
	 * 
	 * @param combatant
	 *            which notified this observer
	 */
	void isDead(Combatant combatant);

	/**
	 * The Combatant is back from the dead. Needed to reverse rounds.
	 * 
	 * @param combatant
	 */
	void isAlive(Combatant combatant);

	/**
	 * The player of the combatant is online.
	 * 
	 * @param combatant
	 *            which notified this observer
	 * @param changed
	 *            <code>true</code> if the new state is different than the old
	 */
	void isOnline(Combatant combatant, boolean changed);

	/**
	 * The {@link Combatant} last shot was a critical hit.
	 * 
	 * @param combatant
	 *            which notified this observer
	 * @param victim
	 *            TODO
	 * @param damage
	 *            the actual caused damage
	 */
	void criticalShot(Combatant combatant, Combatant victim, int damage);
}
