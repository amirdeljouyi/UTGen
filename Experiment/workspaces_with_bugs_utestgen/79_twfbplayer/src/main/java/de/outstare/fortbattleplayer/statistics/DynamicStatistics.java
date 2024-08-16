package de.outstare.fortbattleplayer.statistics;

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
import java.util.Set;

import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantSide;

/**
 * DynamicStatistis contains data which is generated during the battle through
 * the actions of the combatants.
 * 
 * @author daniel
 */
public interface DynamicStatistics {

	/**
	 * @param side
	 *            if null the sum of all will be returned
	 * @return the ratio of rounds the player lived and shot at an enemy of the
	 *         total rounds the player lived
	 */
	int percentAbleToShoot(CombatantSide side);

	/**
	 * @param side
	 *            if null the sum of all will be returned
	 * @return sum of bonuses for all players of the given side
	 */
	int amountOfAttackBonus(CombatantSide side);

	/**
	 * @param side
	 *            if null the sum of all will be returned
	 * @return sum of bonuses for all players of the given side
	 */
	int amountOfDefenseBonus(CombatantSide side);

	/**
	 * @param side
	 *            if null the sum of all will be returned
	 * @return the average number of moves done by a combatant of the given side
	 *         in the hole battle
	 */
	double numberOfMovesPerPlayer(CombatantSide side);

	/**
	 * @param side
	 *            if null the sum of all will be returned
	 * @return the number of combatants whose player was never online during the
	 *         whole battle
	 */
	int numberOfOffliners(CombatantSide side);

	/**
	 * @param side
	 * @return the number of swaps for the whole battle (players changing
	 *         positions directly)
	 */
	int totalSwaps(CombatantSide side);

	/**
	 * @param side
	 *            if null the sum of all will be returned
	 * @return the average round in which a player appeared online (only
	 *         counting onliners)
	 */
	double roundsTillOnline(CombatantSide side);

	/**
	 * @param side
	 *            if null the sum of all will be returned
	 * @return the average number of rounds a player was online
	 */
	double averageRoundsOnline(CombatantSide side);

	/**
	 * @param side
	 *            if null the sum of all will be returned
	 * @return the name of the combatant at whom were fired the most shots with
	 *         number of shots
	 */
	String targetNoOne(CombatantSide side);

	/**
	 * @param side
	 *            if null the sum of all will be returned
	 * @return the total amount of critical hits for the given side
	 */
	int criticalHits(CombatantSide side);

	/**
	 * Gives the cumulated value of all critical damages above the weapons
	 * normal damage. So this is the pure bonus of the critical hit.
	 * 
	 * @param side
	 * @return the total critical damage without the normal weapon damage for
	 *         the given side
	 */
	long criticalHitDamage(CombatantSide side);

	/**
	 * @param side
	 * @return all critical hits of the given side
	 */
	List<CriticalHit> critList(final CombatantSide side);

	/**
	 * @param side
	 * @return all switched positions
	 */
	Set<PositionSwitch> switchedPosList(final CombatantSide side);

	/**
	 * @param side
	 * @return the number of used bayonets for the given side
	 */
	int numberOfBayonets(final CombatantSide side);

	/**
	 * @param side
	 * @return the number of used graphit lubricant for the given side
	 */
	int numberOfGraphitLubricants(final CombatantSide side);

	/**
	 * @param side
	 * @return the number of used greasing weapon oil for the given side
	 */
	int numberOfFettesOil(final CombatantSide side);

	/**
	 * @param side
	 * @return the number of used shimmering weapon oil for the given side
	 */
	int numberOfSchmierOil(final CombatantSide side);

	/**
	 * @param side
	 * @return the number of used shiny weapon oil for the given side
	 */
	int numberOfShinyOil(final CombatantSide side);

	/**
	 * @param side
	 * @return the number of used loading chambers and enhanced patrons for the
	 *         given side
	 */
	int numberOfLoadingChamerOrEnhancedPatrons(final CombatantSide side);

	/**
	 * @param side
	 * @return the number of used hip flaks for the given side
	 */
	int numberOfHipFlasks(final CombatantSide side);

	/**
	 * @param side
	 *            if null all will be returned
	 * @return all combatants in the order of the first round
	 */
	public List<Combatant> turnOrder(final CombatantSide side);

	/**
	 * @return the number of shots per round
	 */
	public Map<CombatantSide, ? extends LabeledData> getShotsPerRound();

	/**
	 * @return the number of hits per round
	 */
	public Map<CombatantSide, ? extends LabeledData> getHitsPerRound();

	/**
	 * @return the number of players not shooting per round
	 */
	public Map<CombatantSide, ? extends LabeledData> getNotShootersPerRound();

	/**
	 * @return statistics for every field
	 */
	public Map<Area, AreaStatistic> getFieldStatistics();
}