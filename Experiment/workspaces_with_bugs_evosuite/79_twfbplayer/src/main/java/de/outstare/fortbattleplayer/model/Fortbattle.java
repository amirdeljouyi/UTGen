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

import java.awt.Image;
import java.util.Map;
import java.util.Set;

import de.outstare.fortbattleplayer.player.Battleplan;
import de.outstare.fortbattleplayer.statistics.AllStatistics;

/**
 * A Fortbattle is a container for the different parts of it
 * 
 * TODO make a proper type out of this
 * 
 * @author daniel
 */
public class Fortbattle {
	private final Map<CombatantSide, Set<Combatant>> combatants;
	private final Battlefield battlefield;
	private final Battleplan actions;
	private final AllStatistics statistic;
	private final String fortname;
	private final Image fortimage;

	/**
	 * @param fortname
	 * @param combatants
	 *            see {@link #combatants}
	 * @param battlefield
	 *            see {@link #battlefield}
	 * @param actions
	 *            see {@link #actions}
	 * @param statistic
	 *            see {@link #statistic}
	 * @param statistic2
	 *            see {@link #statistic2}
	 * @param fortimage
	 */
	public Fortbattle(final String fortname, final Map<CombatantSide, Set<Combatant>> combatants,
	        final Battlefield battlefield, final Battleplan actions, final AllStatistics statistic,
	        final Image fortimage) {
		this.fortname = fortname;
		this.combatants = combatants;
		this.battlefield = battlefield;
		this.actions = actions;
		this.statistic = statistic;
		this.fortimage = fortimage;
	}

	/**
	 * the map
	 * 
	 * @return the battlefield
	 */
	public Battlefield getBattlefield() {
		return battlefield;
	}

	/**
	 * all the dynamics
	 * 
	 * @return the actions
	 */
	public Battleplan getActions() {
		return actions;
	}

	/**
	 * some prepared data
	 * 
	 * @return the other statistics
	 */
	public AllStatistics getStatistic() {
		return statistic;
	}

	/**
	 * name of the fort where this battle took place
	 * 
	 * @return the fortname
	 */
	public String getFortname() {
		return fortname;
	}

	/**
	 * @return all attackers
	 */
	public Set<Combatant> getAttackers() {
		return combatants.get(CombatantSide.ATTACKER);
	}

	/**
	 * @return all defenders
	 */
	public Set<Combatant> getDefenders() {
		return combatants.get(CombatantSide.DEFENDER);
	}

	/**
	 * @return the fortimage
	 */
	public Image getMapImage() {
		return fortimage;
	}

}
