package de.outstare.fortbattleplayer.gui.statistics;

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

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;

import de.outstare.fortbattleplayer.gui.Messages;
import de.outstare.fortbattleplayer.model.CombatantSide;
import de.outstare.fortbattleplayer.statistics.AllStatistics;
import de.outstare.fortbattleplayer.statistics.DynamicStatistics;
import de.outstare.fortbattleplayer.statistics.StaticStatistics;

/**
 * A Panel which shows all the statistics
 * 
 * @author daniel
 */
public class StatisticsPanel extends JPanel {
	private static final long serialVersionUID = -3954713448644181795L;
	private static final Color DEFENDER_COLOR = softenColor(CombatantSide.DEFENDER.color());
	private static final Color ATTACKER_COLOR = softenColor(CombatantSide.ATTACKER.color());

	/**
	 * for internal use only
	 */
	final StringBuilder textStats = new StringBuilder(100);

	/**
	 * @param stats
	 * @param stats2
	 */
	public StatisticsPanel(final AllStatistics stats) {
		super(new GridLayout(1, 2, 20, 0));
		setBorder(BorderFactory.createEmptyBorder(4, 16, 8, 16));

		final JPanel teamStats = createTeamStatsPanel(stats);
		final JPanel playerStats = createCombatantStatsPanel(stats);

		add(teamStats);
		add(playerStats);
	}

	/**
	 * @param stats
	 * @param stats2
	 * @return
	 */
	private JPanel createTeamStatsPanel(final AllStatistics allStats) {
		final StaticStatistics stats = allStats.combatant;
		final DynamicStatistics stats2 = allStats.battle;

		final JPanel teamStats = new JPanel(new GridLayout(29, 2, 4, 1));

		teamStats.add(new JSeparator());
		teamStats.add(new JLabel(Messages.getString("StatisticsPanel.0")));

		addBar("StatisticsPanel.1", teamStats, stats.numberOfPlayers(CombatantSide.ATTACKER),
		        stats.numberOfPlayers(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.2", teamStats, stats.survivedPlayers(CombatantSide.ATTACKER),
		        stats.survivedPlayers(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.32", teamStats, stats2.numberOfOffliners(CombatantSide.ATTACKER),
		        stats2.numberOfOffliners(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.3", teamStats, stats.averageLevel(CombatantSide.ATTACKER),
		        stats.averageLevel(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.4", teamStats, stats.numberOfAdventurers(CombatantSide.ATTACKER),
		        stats.numberOfAdventurers(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.5", teamStats, stats.numberOfDuelants(CombatantSide.ATTACKER),
		        stats.numberOfDuelants(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.39", teamStats, stats.numberOfGreenhorns(CombatantSide.ATTACKER),
		        stats.numberOfGreenhorns(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.6", teamStats, stats.numberOfSoldiers(CombatantSide.ATTACKER),
		        stats.numberOfSoldiers(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.7", teamStats, stats.numberOfWorkers(CombatantSide.ATTACKER),
		        stats.numberOfWorkers(CombatantSide.DEFENDER));

		// addBar("StatisticsPanel.8", teamStats,
		// stats.maxPossibleHealthWholeSide(CombatantSide.ATTACKER),
		// stats.maxPossibleHealthWholeSide(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.9", teamStats, stats.initialHealthWholeSide(CombatantSide.ATTACKER),
		        stats.initialHealthWholeSide(CombatantSide.DEFENDER));

		// addBar("StatisticsPanel.10", teamStats,
		// stats.endHealthWholeSide(CombatantSide.ATTACKER),
		// stats.endHealthWholeSide(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.11", teamStats, stats.causedDamageWholeSide(CombatantSide.ATTACKER),
		        stats.causedDamageWholeSide(CombatantSide.DEFENDER));

		// addBar("StatisticsPanel.12", teamStats,
		// stats.takenDamageWholeSide(CombatantSide.ATTACKER),
		// stats.takenDamageWholeSide(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.13", teamStats, stats.shotsFiredWholeSide(CombatantSide.ATTACKER),
		        stats.shotsFiredWholeSide(CombatantSide.DEFENDER));

		final int attackerHitPercent = (int) Math.round(stats.hitsWholeSide(CombatantSide.ATTACKER) * 100
		        / (double) stats.shotsFiredWholeSide(CombatantSide.ATTACKER));
		final int defenderHitPercent = (int) Math.round(stats.hitsWholeSide(CombatantSide.DEFENDER) * 100
		        / (double) stats.shotsFiredWholeSide(CombatantSide.DEFENDER));
		final String hitsLabel = Messages.getString("StatisticsPanel.14");
		saveForExport(hitsLabel, attackerHitPercent, defenderHitPercent);
		teamStats.add(new JLabel(hitsLabel));
		teamStats.add(createPercentPanel(attackerHitPercent, defenderHitPercent));

		final int attackerDodgesPercent = (int) Math.round(stats.dodgesWholeSide(CombatantSide.ATTACKER) * 100
		        / (double) stats.shotsFiredWholeSide(CombatantSide.DEFENDER));
		final int defenderDodgesPercent = (int) Math.round(stats.dodgesWholeSide(CombatantSide.DEFENDER) * 100
		        / (double) stats.shotsFiredWholeSide(CombatantSide.ATTACKER));
		final String dodgesLabel = Messages.getString("StatisticsPanel.15");
		saveForExport(dodgesLabel, attackerDodgesPercent, defenderDodgesPercent);
		teamStats.add(new JLabel(dodgesLabel));
		teamStats.add(createPercentPanel(attackerDodgesPercent, defenderDodgesPercent));

		addBar("StatisticsPanel.42", teamStats, stats2.criticalHits(CombatantSide.ATTACKER),
		        stats2.criticalHits(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.critDamage", teamStats, stats2.criticalHitDamage(CombatantSide.ATTACKER),
		        stats2.criticalHitDamage(CombatantSide.DEFENDER));

		final int percentAbleToShootAtt = stats2.percentAbleToShoot(CombatantSide.ATTACKER);
		final int percentAbleToShootDeff = stats2.percentAbleToShoot(CombatantSide.DEFENDER);
		final String percenAbleToShootLabel = Messages.getString("StatisticsPanel.36");
		saveForExport(percenAbleToShootLabel, percentAbleToShootAtt, percentAbleToShootDeff);
		teamStats.add(new JLabel(percenAbleToShootLabel));
		teamStats.add(createPercentPanel(percentAbleToShootAtt, percentAbleToShootDeff));
		// addBar("StatisticsPanel.36", teamStats, percentAbleToShootAtt,
		// percentAbleToShootDeff);

		addBar("StatisticsPanel.40", teamStats, stats2.amountOfAttackBonus(CombatantSide.ATTACKER),
		        stats2.amountOfAttackBonus(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.41", teamStats, stats2.amountOfDefenseBonus(CombatantSide.ATTACKER),
		        stats2.amountOfDefenseBonus(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.38", teamStats, stats2.totalSwaps(CombatantSide.ATTACKER),
		        stats2.totalSwaps(CombatantSide.DEFENDER));

		teamStats.add(new JLabel(Messages.getString("StatisticsPanel.37")));
		teamStats.add(new JLabel(stats2.targetNoOne(CombatantSide.ATTACKER) + " / "
		        + stats2.targetNoOne(CombatantSide.DEFENDER)));

		addBar("StatisticsPanel.wpnmod.graphit", teamStats, stats2.numberOfGraphitLubricants(CombatantSide.ATTACKER),
		        stats2.numberOfGraphitLubricants(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.wpnmod.hipflask", teamStats, stats2.numberOfHipFlasks(CombatantSide.ATTACKER),
		        stats2.numberOfHipFlasks(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.wpnmod.bayo", teamStats, stats2.numberOfBayonets(CombatantSide.ATTACKER),
		        stats2.numberOfBayonets(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.wpnmod.chamber", teamStats,
		        stats2.numberOfLoadingChamerOrEnhancedPatrons(CombatantSide.ATTACKER),
		        stats2.numberOfLoadingChamerOrEnhancedPatrons(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.wpnmod.greasoil", teamStats, stats2.numberOfFettesOil(CombatantSide.ATTACKER),
		        stats2.numberOfFettesOil(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.wpnmod.shimmer", teamStats, stats2.numberOfSchmierOil(CombatantSide.ATTACKER),
		        stats2.numberOfSchmierOil(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.wpnmod.shiny", teamStats, stats2.numberOfShinyOil(CombatantSide.ATTACKER),
		        stats2.numberOfShinyOil(CombatantSide.DEFENDER));

		return teamStats;
	}

	/**
	 * @param attackerHitPercent
	 * @param defenderHitPercent
	 * @return
	 */
	private JPanel createPercentPanel(final int attackerHitPercent, final int defenderHitPercent) {
		final JPanel hitPercent = new JPanel(new GridLayout(2, 1));
		final JProgressBar attackerHits = new JProgressBar(0, 100);
		final JProgressBar defenderHits = new JProgressBar(0, 100);
		attackerHits.setForeground(ATTACKER_COLOR);
		defenderHits.setForeground(DEFENDER_COLOR);
		attackerHits.setStringPainted(true);
		defenderHits.setStringPainted(true);
		attackerHits.setValue(attackerHitPercent);
		defenderHits.setValue(defenderHitPercent);
		hitPercent.add(attackerHits);
		hitPercent.add(defenderHits);
		return hitPercent;
	}

	/**
	 * @param stats
	 * @param stats2
	 * @return
	 */
	private JPanel createCombatantStatsPanel(final AllStatistics allStats) {
		final StaticStatistics stats = allStats.combatant;
		final DynamicStatistics stats2 = allStats.battle;

		final JPanel playerStats = new JPanel(new GridLayout(16, 2, 4, 1));

		playerStats.add(new JSeparator());
		playerStats.add(new JLabel(Messages.getString("StatisticsPanel.16")));

		addBar("StatisticsPanel.17", playerStats, stats.averageLifetime(CombatantSide.ATTACKER),
		        stats.averageLifetime(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.33", playerStats, stats2.averageRoundsOnline(CombatantSide.ATTACKER),
		        stats2.averageRoundsOnline(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.34", playerStats, (int) Math.round(stats2.roundsTillOnline(CombatantSide.ATTACKER)),
		        (int) Math.round(stats2.roundsTillOnline(CombatantSide.DEFENDER)));

		addBar("StatisticsPanel.18", playerStats, stats.averageWeaponDamage(CombatantSide.ATTACKER),
		        stats.averageWeaponDamage(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.19", playerStats, stats.averageHitDamage(CombatantSide.ATTACKER),
		        stats.averageHitDamage(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.11", playerStats, stats.causedDamagePerPlayer(CombatantSide.ATTACKER),
		        stats.causedDamagePerPlayer(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.12", playerStats, stats.takenDamagePerPlayer(CombatantSide.ATTACKER),
		        stats.takenDamagePerPlayer(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.13", playerStats, stats.shotsFiredPerPlayer(CombatantSide.ATTACKER),
		        stats.shotsFiredPerPlayer(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.14", playerStats, stats.hitsPerPlayer(CombatantSide.ATTACKER),
		        stats.hitsPerPlayer(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.15", playerStats, stats.dodgesPerPlayer(CombatantSide.ATTACKER),
		        stats.dodgesPerPlayer(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.8", playerStats, stats.maxPossibleHealthPerPlayer(CombatantSide.ATTACKER),
		        stats.maxPossibleHealthPerPlayer(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.9", playerStats, stats.initialHealthPerPlayer(CombatantSide.ATTACKER),
		        stats.initialHealthPerPlayer(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.10", playerStats, stats.endHealthPerPlayer(CombatantSide.ATTACKER),
		        stats.endHealthPerPlayer(CombatantSide.DEFENDER));

		addBar("StatisticsPanel.35", playerStats, stats2.numberOfMovesPerPlayer(CombatantSide.ATTACKER),
		        stats2.numberOfMovesPerPlayer(CombatantSide.DEFENDER));

		playerStats.add(new JLabel(Messages.getString("StatisticsPanel.28")));
		playerStats.add(createTextButton());
		return playerStats;
	}

	/**
	 * @return
	 */
	private JComponent createTextButton() {
		final JButton button = new JButton(Messages.getString("StatisticsPanel.29"));
		button.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				JOptionPane.showInputDialog(Messages.getString("StatisticsPanel.30"), textStats.toString());
			}
		});
		return button;
	}

	private void addBar(final String descriptionKey, final JComponent container, final double attackerValue,
	        final double defenderValue) {
		addBar(descriptionKey, container, (int) Math.round(attackerValue), (int) Math.round(defenderValue));
	}

	private void addBar(final String descriptionKey, final JComponent container, final int attackerValue,
	        final int defenderValue) {
		final String description = Messages.getString(descriptionKey);
		final int sum = attackerValue + defenderValue;
		final JProgressBar bar = new JProgressBar(0, sum);
		// force loading defaults
		bar.getUI().installUI(bar);
		bar.setValue(attackerValue);
		bar.setForeground(ATTACKER_COLOR);
		bar.setBackground(DEFENDER_COLOR);
		bar.setString(attackerValue + Messages.getString("StatisticsPanel.31") + defenderValue);
		saveForExport(description, attackerValue, defenderValue);
		bar.setStringPainted(true);

		container.add(new JLabel(description));
		container.add(bar);
	}

	/**
	 * @param description
	 * @param attackerValue
	 * @param defenderValue
	 */
	private void saveForExport(final String description, final int attackerValue, final int defenderValue) {
		textStats.append(description);
		textStats.append('\t');
		textStats.append(attackerValue);
		textStats.append('\t');
		textStats.append(defenderValue);
		textStats.append("\r\n"); //$NON-NLS-1$
	}

	private static Color softenColor(final Color color) {
		return new Color(addToColor(color.getRed()), addToColor(color.getGreen()), addToColor(color.getBlue()));
	}

	private static int addToColor(final int singleColor) {
		return Math.min(singleColor + 0x33, 255);
	}
}
