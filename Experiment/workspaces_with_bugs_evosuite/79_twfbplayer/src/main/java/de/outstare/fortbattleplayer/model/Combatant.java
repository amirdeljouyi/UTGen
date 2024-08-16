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

import java.awt.event.ComponentEvent;

import de.outstare.fortbattleplayer.player.Battleplan;

/**
 * A combatant of the fortbattle. (A player of the-west)
 * 
 * @author daniel
 */
public interface Combatant {
	/**
	 * Set a point where I want to go.
	 * 
	 * @param destination
	 *            where this {@link Combatant} wants to go to
	 */
	void setDestination(Area destination);

	/**
	 * go to the given position
	 * 
	 * @param target
	 *            the position at which the combatant will be
	 */
	void move(Area target);

	/**
	 * Point the weapon at the given target.
	 * 
	 * @param target
	 */
	void aimAt(Combatant target);

	/**
	 * Shoot at the given target. Hopefully I will hit :)
	 * 
	 * @param power
	 *            of the shot (equals the damage dealt if hit)
	 * @require {@link #aimAt(Combatant)} must be called first
	 */
	void shoot(int power);

	/**
	 * Hit em hard - Don't hurt me :D
	 * 
	 * @param damageAmount
	 * @return if this player is still alive
	 */
	boolean hit(int damageAmount);

	/**
	 * Adds an observer which will be notified about all changes. Just on adding
	 * the current status is send as change.
	 * 
	 * @param observer
	 */
	void addObserver(CombatantObserver observer);

	/**
	 * stop sending events to the given observer
	 * 
	 * @param observer
	 */
	void removeObserver(CombatantObserver observer);

	/**
	 * internal method needed for initial drawing
	 * 
	 * @return the current position of this combatant
	 */
	Area _getLocation();

	/**
	 * @return the name of this combatant
	 */
	String getName();

	/**
	 * @return the name of the city the player lives in or empty String if he is
	 *         homeless (not <code>null</code>)
	 */
	String getCity();

	/**
	 * @return the team of this combatant
	 */
	CombatantSide getSide();

	/**
	 * @return <code>true</code> if the player of this combatant is currently
	 *         online
	 */
	boolean isOnline();

	/**
	 * internal method used in the gui
	 * 
	 * @return the amount of HPs (may be negative!)
	 */
	int _health();

	/**
	 * internal method used in the gui
	 * 
	 * @return the maximum amount of HPs
	 */
	int _maxHealth();

	/**
	 * internal method for jumping to a given round. This method has to fire
	 * {@link ComponentEvent}s to notify i.e. the gui about the change!
	 * 
	 * @param newState
	 */
	void _setState(CombatantState newState);

	/**
	 * internal method used while setting up the {@link Battleplan}
	 * 
	 * @return the current state of this {@link Combatant}
	 */
	CombatantState _state();

	/**
	 * sets the current online state
	 * 
	 * @param isOnline
	 *            <code>true</code> if the player of this combatant is currently
	 *            active in the fortbattle
	 */
	void setOnline(boolean isOnline);

	/**
	 * @return the class of this combatant
	 */
	CharacterClass getCharacterClass();

	/**
	 * @return the {@link SectorBonus} for the current location
	 */
	SectorBonus getSectorBonus();

	/**
	 * @return the gun the combatant is using
	 */
	Weapon getWeapon();

	/**
	 * @return <code>true</code> if this combatant uses a bayonet on his weapon
	 *         for increased damage
	 */
	boolean usesBayonet();
}
