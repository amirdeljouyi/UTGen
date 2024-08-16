package de.outstare.fortbattleplayer.model.impl;

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
import java.util.logging.Logger;

import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.CharacterClass;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantObserver;
import de.outstare.fortbattleplayer.model.CombatantSide;
import de.outstare.fortbattleplayer.model.CombatantState;
import de.outstare.fortbattleplayer.model.SectorBonus;
import de.outstare.fortbattleplayer.model.Weapon;
import de.outstare.fortbattleplayer.model.WeaponData;

/**
 * A Combatant with basic attributes (position, health)
 * 
 * @author daniel
 */
public class SimpleCombatant implements Combatant {
	private static final transient Logger LOG = Logger.getLogger(SimpleCombatant.class.getName());
	private final int maxHealth;
	private final CombatantSide side;
	private final String name;
	private final String city;
	private final Set<CombatantObserver> observers = new HashSet<CombatantObserver>();
	private final Object stateChangeLock = new Object();
	private final CharacterClass charClass;
	private final Weapon weapon;
	private CombatantState state;
	private Combatant aimingAt = null;

	/**
	 * Create a new {@link Combatant} at the given position and the given amount
	 * of health.
	 * 
	 * @param side
	 *            not null
	 * @param initialState
	 *            not null
	 * @param maxHealth
	 *            > 0
	 * @param name
	 *            the name of the player of this Combatant
	 * @param characterClass
	 *            maybe null for old logs
	 * @param weapon
	 *            not null
	 * @param city
	 *            not null
	 */
	public SimpleCombatant(final CombatantSide side, final CombatantState initialState, final int maxHealth,
	        final String name, final CharacterClass characterClass, final Weapon weapon, final String city) {
		assert side != null && initialState != null && weapon != null && city != null : "parameters may not be null!";
		assert maxHealth > 0 : "health must be positive!";
		assert maxHealth >= initialState.getHealth() && initialState.getHealth() > 0 : "currentHealth must be inbetween 1 and max, is "
		        + initialState.getHealth();

		this.side = side;
		this.maxHealth = maxHealth;
		this.name = name;
		this.city = city;
		state = initialState;
		charClass = characterClass;
		this.weapon = weapon;

		// move to current location
		state.getPosition().occupy(this, null);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getSide() + " " + name;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#getWeapon()
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * internal method for graphical display
	 * 
	 * @return the maximal health
	 */
	public int _getMaxLP() {
		return maxHealth;
	}

	/**
	 * internal method for graphical display
	 * 
	 * @return the current health
	 */
	public int _getCurrentLP() {
		return state.getHealth();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#move(de.outstare.fortbattleplayer.model.Area)
	 */
	public void move(final Area target) {
		LOG.fine(name + " moving to " + target);
		synchronized (stateChangeLock) {
			final Area oldPosition = state.getPosition();
			moveAway(oldPosition);
			state = state.changePosition(target);
			final Combatant swapped = target.occupy(this, oldPosition);
			fireHasMoved();
			if (swapped != null) {
				fireHasSwapped(swapped);
			}
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#shoot(de.outstare.fortbattleplayer.model.Area)
	 */
	public void shoot(final int power) {
		LOG.fine(name + " shooting with " + power);
		aimingAt.hit(power);
		final int sectorBonusDamage = state.getPosition().getSectorBonus(this).additionalDamage;
		if (charClass == CharacterClass.DUELANT && power > weapon.maxDamage() + sectorBonusDamage) {
			final double tenPercent = aimingAt._maxHealth() * 0.1;
			if (power > weapon.minDamage() + tenPercent) {
				fireCriticalShot(power);
			} else {
				LOG.fine("almost crit by " + name + ": " + power);
			}
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#hit(int)
	 */
	public boolean hit(final int damageAmount) {
		LOG.fine(name + " was hit and lost " + damageAmount + " health points");
		synchronized (stateChangeLock) {
			final int oldHealth = state.getHealth();
			state = state.reduceHealthBy(damageAmount);
			fireWasHit(damageAmount, oldHealth);
			if (state.getHealth() <= 0) {
				moveAway(state.getPosition());
				fireIsDead();
			}
			return state.getHealth() > 0;
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#aimAt(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void aimAt(final Combatant target) {
		LOG.fine(name + " aims at " + target);
		aimingAt = target;
		fireAimingAt();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#setDestination(de.outstare.fortbattleplayer.model.Area)
	 */
	public void setDestination(final Area destination) {
		LOG.fine(name + " wants to move to " + destination);
		synchronized (stateChangeLock) {
			state = state.changeTarget(destination);
			fireNewTarget();
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#addObserver(de.outstare.fortbattleplayer.model.CombatantObserver)
	 */
	public void addObserver(final CombatantObserver observer) {
		observers.add(observer);
		// send current state
		observer.hasMoved(this, state.getPosition());
		if (aimingAt != null) {
			observer.aimsAt(this, aimingAt);
		}
		if (state.getHealth() <= 0) {
			observer.isDead(this);
		} else {
			observer.isAlive(this);
		}
		observer.isOnline(this, false);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#removeObserver(de.outstare.fortbattleplayer.model.CombatantObserver)
	 */
	public void removeObserver(final CombatantObserver observer) {
		observers.remove(observer);
	}

	/**
	 * notify all observers about the current position
	 */
	protected void fireHasMoved() {
		for (final CombatantObserver observer : observers) {
			observer.hasMoved(this, state.getPosition());
		}
	}

	/**
	 * notify all observers about the current position
	 * 
	 * @param swappedWith
	 */
	protected void fireHasSwapped(final Combatant swappedWith) {
		for (final CombatantObserver observer : observers) {
			observer.hasSwappedPosition(this, swappedWith);
		}
	}

	/**
	 * notify all observers about the current position
	 */
	protected void fireNewTarget() {
		for (final CombatantObserver observer : observers) {
			observer.newDestination(this, state.getTarget());
		}
	}

	/**
	 * notify all observers that I shot at somebody.
	 */
	protected void fireAimingAt() {
		if (aimingAt != null) {
			for (final CombatantObserver observer : observers) {
				observer.aimsAt(this, aimingAt);
			}
		}
	}

	/**
	 * notify all observers about a loss of health
	 * 
	 * @param damage
	 *            amount of healthpoints lost
	 * @param healthBefore
	 *            original health amount before it was decreased by
	 *            <code>damage</code>
	 */
	protected void fireWasHit(final int damage, final int healthBefore) {
		for (final CombatantObserver observer : observers) {
			observer.isHit(this, damage, healthBefore);
		}
	}

	/**
	 * notify all observers about our dead
	 */
	protected void fireIsDead() {
		for (final CombatantObserver observer : observers) {
			observer.isDead(this);
		}
	}

	/**
	 * notify all observers about our resurrection
	 */
	protected void fireIsAlive() {
		for (final CombatantObserver observer : observers) {
			observer.isAlive(this);
		}
	}

	/**
	 * notify all observers about changed online state
	 * 
	 * @param changed
	 *            if the value was changed or only set
	 */
	protected void fireOnlineChange(final boolean changed) {
		for (final CombatantObserver observer : observers) {
			observer.isOnline(this, changed);
		}
	}

	/**
	 * notify all observers that i have done a critical shot
	 * 
	 * @param damage
	 *            the actual caused damage
	 */
	protected void fireCriticalShot(final int damage) {
		for (final CombatantObserver observer : observers) {
			observer.criticalShot(this, aimingAt, damage);
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#_getLocation()
	 */
	public Area _getLocation() {
		return state.getPosition();
	}

	/**
	 * @return the side
	 */
	public CombatantSide getSide() {
		return side;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#_health()
	 */
	public int _health() {
		return state.getHealth();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#_maxHealth()
	 */
	public int _maxHealth() {
		return maxHealth;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#_setState(de.outstare.fortbattleplayer.model.CombatantState)
	 */
	public void _setState(final CombatantState newState) {
		final CombatantState oldState = state;
		// TODO maybe a PropertyChangeListener for the state
		synchronized (stateChangeLock) {
			state = newState;
			// new pos has not to be null!
			if (state.getPosition() != null) {
				final Area oldPos = oldState.getPosition();
				// fix for swapping:
				// 1. go away from battlefield
				moveAway(oldPos);
				// 2. come from nowhere to new position (avoids swapping)
				state.getPosition().occupy(this, null);
				if (oldPos == null || !oldPos.equals(state.getPosition())) {
					fireHasMoved();
				}
			}
			if (oldState.getHealth() != state.getHealth()) {
				fireWasHit(oldState.getHealth() - state.getHealth(), oldState.getHealth());
			}
			if (state.getHealth() > 0 && oldState.getHealth() <= 0) {
				fireIsAlive();
			}
			if (state.getHealth() <= 0 && oldState.getHealth() > 0) {
				fireIsDead();
			}
			if (oldState.isGamerOnline() != state.isGamerOnline()) {
				fireOnlineChange(true);
			}
			if (!oldState.getTarget().equals(state.getTarget())) {
				fireNewTarget();
			}
		}
	}

	/**
	 * @param position
	 */
	private void moveAway(final Area position) {
		if (position != null && equals(position.getOccupier())) {
			position.free();
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#_state()
	 */
	public CombatantState _state() {
		return state;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#isOnline()
	 */
	public boolean isOnline() {
		return state.isGamerOnline();
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#setOnline()
	 */
	public void setOnline(final boolean isOnline) {
		synchronized (stateChangeLock) {
			final boolean changed = isOnline() != isOnline;
			if (changed) {
				LOG.fine(name + " is now " + (isOnline ? "online" : "offline"));
				state = state.setOnline(isOnline);
			}
			fireOnlineChange(changed);
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#getCharacterClass()
	 */
	public CharacterClass getCharacterClass() {
		return charClass;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#getSectorBonus()
	 */
	public SectorBonus getSectorBonus() {
		return _getLocation().getSectorBonus(this);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#getCity()
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.Combatant#usesBayonet()
	 */
	public boolean usesBayonet() {
		final WeaponData weaponData = new JSWeaponData();
		return weaponData.hasBayonet(getWeapon());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 13;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((charClass == null) ? 0 : charClass.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SimpleCombatant)) {
			return false;
		}
		final SimpleCombatant other = (SimpleCombatant) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (charClass != other.charClass) {
			return false;
		}
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		return true;
	}
}
