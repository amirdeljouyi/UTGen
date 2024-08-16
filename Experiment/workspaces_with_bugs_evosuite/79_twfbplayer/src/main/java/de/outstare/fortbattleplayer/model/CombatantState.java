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
 * The state of a {@link Combatant}. An instance is immutable, so all modifying
 * methods return a new state.
 * 
 * FIXME this class should not be public
 * 
 * @author daniel
 */
public class CombatantState {
	/**
	 * the current position on the battlefield
	 */
	private final Area position;
	/**
	 * the current amount of health-points
	 */
	private final int health;
	/**
	 * the place the {@link Combatant} wants to go to
	 */
	private final Area target;
	/**
	 * <p>
	 * if the human who controls the avatar is currently connected
	 * </p>
	 * <code>null</code> = unknown (used to ignore first offline event)
	 */
	private final boolean isGamerOnline;

	/**
	 * @param position
	 * @param health
	 * @param target
	 * @param gamerIsOnline
	 */
	public CombatantState(final Area position, final int health, final Area target, final boolean gamerIsOnline) {
		this.position = position;
		this.health = health;
		this.target = target;
		isGamerOnline = gamerIsOnline;
	}

	/**
	 * creates a new state with the given position and a copy of the oldState
	 * 
	 * @param position
	 * @param oldState
	 */
	public CombatantState(final Area position, final CombatantState oldState) {
		this(position, oldState.getHealth(), oldState.getTarget(), oldState.isGamerOnline());
	}

	/**
	 * creates a new state with the given health and a copy of the oldState
	 * 
	 * @param health
	 * @param oldState
	 */
	public CombatantState(final int health, final CombatantState oldState) {
		this(oldState.getPosition(), health, oldState.getTarget(), oldState.isGamerOnline());
	}

	/**
	 * creates a new state with the given target and a copy of the oldState
	 * 
	 * @param oldState
	 * @param target
	 */
	public CombatantState(final CombatantState oldState, final Area target) {
		this(oldState.getPosition(), oldState.getHealth(), target, oldState.isGamerOnline());
	}

	/**
	 * creates a new state with the given isOnline status and a copy of the
	 * oldState
	 * 
	 * @param isOnline
	 * @param oldState
	 */
	public CombatantState(final boolean isOnline, final CombatantState oldState) {
		this(oldState.getPosition(), oldState.getHealth(), oldState.getTarget(), isOnline);
	}

	/**
	 * @param newPosition
	 * @return a new state object with the given position and else the same
	 *         attributes as this state
	 */
	public CombatantState changePosition(final Area newPosition) {
		return new CombatantState(newPosition, this);
	}

	/**
	 * @param amount
	 * @return a new state object with the health decreased by the given amount
	 *         and else the same attributes as this state
	 */
	public CombatantState reduceHealthBy(final int amount) {
		return new CombatantState(getHealth() - amount, this);
	}

	/**
	 * @param moveTarget
	 * @return a new state object with the given target and else the same
	 *         attributes as this state
	 */
	public CombatantState changeTarget(final Area moveTarget) {
		return new CombatantState(this, moveTarget);
	}

	/**
	 * @param isOnline
	 *            in the new state
	 * @return a new state object with the online state inverted and else the
	 *         same attributes as this state
	 */
	public CombatantState setOnline(final boolean isOnline) {
		return new CombatantState(isOnline, this);
	}

	/**
	 * @return the target
	 */
	public Area getTarget() {
		return target;
	}

	/**
	 * @return the position
	 */
	public Area getPosition() {
		return position;
	}

	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @return the gamerIsOnline
	 */
	public boolean isGamerOnline() {
		return isGamerOnline;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " position=" + position + ", healt=" + health + ", target=" + target
		        + ", online=" + isGamerOnline;
	}
}
