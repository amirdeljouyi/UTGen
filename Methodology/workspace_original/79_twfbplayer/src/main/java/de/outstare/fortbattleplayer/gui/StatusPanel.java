package de.outstare.fortbattleplayer.gui;

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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.logging.Logger;

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantObserver;
import de.outstare.fortbattleplayer.model.CombatantSide;

/**
 * A StatusPanel shows the current state of a battle. It's mostly statistics.
 * 
 * @author daniel
 */
class StatusPanel extends JPanel implements CombatantObserver {
	private static final long serialVersionUID = -7599709865202481177L;
	private static final transient Logger LOG = Logger.getLogger(StatusPanel.class.getName());
	/**
	 * accesable for inner class
	 */
	final JProgressBar count_attacker;
	/**
	 * accesable for inner class
	 */
	final JProgressBar count_defender;
	/**
	 * accesable for inner class
	 */
	final JProgressBar hp_attacker;
	/**
	 * accesable for inner class
	 */
	final JProgressBar hp_defender;
	/**
	 * accesable for inner class
	 */
	final JProgressBar percentOnline_attacker = new JProgressBar(0, 100);
	/**
	 * accesable for inner class
	 */
	final JProgressBar percentOnline_defender = new JProgressBar(0, 100);

	/**
	 * @param countAttackers
	 * @param countDefenders
	 * @param sumAttackerHP
	 * @param sumDefenderHP
	 */
	StatusPanel(final int countAttackers, final int countDefenders, final int sumAttackerHP, final int sumDefenderHP) {
		super(new BorderLayout());
		count_attacker = new JProgressBar(0, countAttackers);
		count_defender = new JProgressBar(0, countDefenders);
		hp_attacker = new JProgressBar(0, sumAttackerHP);
		hp_defender = new JProgressBar(0, sumDefenderHP);
		percentOnline_attacker.setModel(new PercentModel(countAttackers));
		percentOnline_defender.setModel(new PercentModel(countDefenders));

		addChangeListener();

		count_attacker.setValue(countAttackers);
		count_defender.setValue(countDefenders);
		hp_attacker.setValue(sumAttackerHP);
		hp_defender.setValue(sumDefenderHP);

		setBarColors();
		enableLabels();
		initGui();
	}

	private void enableLabels() {
		count_attacker.setStringPainted(true);
		count_defender.setStringPainted(true);
		hp_attacker.setStringPainted(true);
		hp_defender.setStringPainted(true);
		percentOnline_attacker.setStringPainted(true);
		percentOnline_defender.setStringPainted(true);
	}

	private void addChangeListener() {
		count_attacker.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent e) {
				final int count = count_attacker.getValue();
				count_attacker.setString(Integer.toString(count));
				count_attacker.setToolTipText(count + Messages.getString("StatusPanel.attackers")); //$NON-NLS-1$
			}
		});
		count_defender.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent e) {
				final int count = count_defender.getValue();
				count_defender.setString(Integer.toString(count));
				count_defender.setToolTipText(count + Messages.getString("StatusPanel.defenders")); //$NON-NLS-1$
			}
		});
		hp_attacker.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent e) {
				hp_attacker.setToolTipText(Messages.getString("StatusPanel.healthAttacker") + hp_attacker.getValue()); //$NON-NLS-1$
			}
		});
		hp_defender.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent e) {
				hp_defender.setToolTipText(Messages.getString("StatusPanel.healthDefender") + hp_defender.getValue()); //$NON-NLS-1$
			}
		});
		percentOnline_attacker.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent e) {
				percentOnline_attacker.setToolTipText(Messages.getString("StatusPanel.onlineAttacker") //$NON-NLS-1$
				        + ((PercentModel) percentOnline_attacker.getModel()).getCurrent());
			}
		});
		percentOnline_defender.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent e) {
				percentOnline_defender.setToolTipText(Messages.getString("StatusPanel.onlineDefender") //$NON-NLS-1$
				        + ((PercentModel) percentOnline_defender.getModel()).getCurrent());
			}
		});
	}

	private void initGui() {
		// using panel to force the components vertically on top
		final JPanel panel = new JPanel(new GridBagLayout());
		final GridBagConstraints layout = new GridBagConstraints();
		// all in column 1
		layout.gridx = 1;
		panel.add(new JLabel(Messages.getString("StatusPanel.playerCount")), layout); //$NON-NLS-1$
		panel.add(count_attacker, layout);
		panel.add(count_defender, layout);
		panel.add(new JLabel(Messages.getString("StatusPanel.healthAmount")), layout); //$NON-NLS-1$
		panel.add(hp_attacker, layout);
		panel.add(hp_defender, layout);
		panel.add(new JLabel(Messages.getString("StatusPanel.onlinePercent")), layout); //$NON-NLS-1$
		panel.add(percentOnline_attacker, layout);
		panel.add(percentOnline_defender, layout);
		add(panel, BorderLayout.NORTH);
	}

	private void setBarColors() {
		final Color attackerColor = CombatantSide.ATTACKER.color();
		final Color defenderColor = CombatantSide.DEFENDER.color();
		count_attacker.setForeground(attackerColor);
		count_defender.setForeground(defenderColor);
		hp_attacker.setForeground(attackerColor);
		hp_defender.setForeground(defenderColor);
		percentOnline_attacker.setForeground(attackerColor);
		percentOnline_defender.setForeground(defenderColor);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#aimsAt(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void aimsAt(final Combatant combatant, final Combatant target) {
		// ignore
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#hasMoved(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Area)
	 */
	public void hasMoved(final Combatant combatant, final Area newPos) {
		// ignore
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isDead(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void isDead(final Combatant combatant) {
		switch (combatant.getSide()) {
		case ATTACKER:
			decrease(count_attacker, 1);
			break;
		case DEFENDER:
			decrease(count_defender, 1);
			break;
		default:
			LOG.severe("unknown side of combatant: " + combatant.getSide()); //$NON-NLS-1$
			throw new UnsupportedOperationException("can only show combatants for ATTACKER or DEFENDER, not for " //$NON-NLS-1$
			        + combatant.getSide());
		}
	}

	/**
	 * reduces the value of the bar by n
	 * 
	 * @param bar
	 */
	private void decrease(final JProgressBar bar, final int n) {
		final int oldValue = bar.getValue();
		bar.setValue(oldValue - n);
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isHit(de.outstare.fortbattleplayer.model.Combatant,
	 *      int, int)
	 */
	public void isHit(final Combatant combatant, final int damage, final int oldHealthAmount) {
		final int diff;
		if (damage < 0 && oldHealthAmount < 0) {
			// don't restore negative LPs (i.e. health -10, damage -25 => adding
			// 15 [decrease by -15]))
			diff = damage - oldHealthAmount;
		} else if (damage > oldHealthAmount) {
			if (damage < 0) {
				System.out.println("! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! !");
				System.out.println("! ! !   This is   N O T   expected   ! ! !");
				System.out.println("! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! !");
			}
			// don't count negative LPs
			diff = oldHealthAmount;
		} else {
			diff = damage;
		}
		switch (combatant.getSide()) {
		case ATTACKER:
			decrease(hp_attacker, diff);
			break;
		case DEFENDER:
			decrease(hp_defender, diff);
			break;
		default:
			LOG.severe("unknown side of combatant: " + combatant.getSide()); //$NON-NLS-1$
			throw new UnsupportedOperationException("can only show combatants for ATTACKER or DEFENDER, not for " //$NON-NLS-1$
			        + combatant.getSide());
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isAlive(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void isAlive(final Combatant combatant) {
		switch (combatant.getSide()) {
		case ATTACKER:
			decrease(count_attacker, -1);
			break;
		case DEFENDER:
			decrease(count_defender, -1);
			break;
		default:
			LOG.severe("unknown side of combatant: " + combatant.getSide()); //$NON-NLS-1$
			throw new UnsupportedOperationException("can only show combatants for ATTACKER or DEFENDER, not for " //$NON-NLS-1$
			        + combatant.getSide());
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#newDestination(de.outstare.fortbattleplayer.model.Combatant,
	 *      de.outstare.fortbattleplayer.model.Area)
	 */
	public void newDestination(final Combatant combatant, final Area destination) {
		// ignore
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#isOnline(de.outstare.fortbattleplayer.model.Combatant,
	 *      boolean)
	 */
	public void isOnline(final Combatant combatant, final boolean changed) {
		if (changed) {
			PercentModel model;
			switch (combatant.getSide()) {
			case ATTACKER:
				model = (PercentModel) percentOnline_attacker.getModel();
				break;
			case DEFENDER:
				model = (PercentModel) percentOnline_defender.getModel();
				break;
			default:
				LOG.severe("unknown side of combatant: " + combatant.getSide()); //$NON-NLS-1$
				throw new UnsupportedOperationException(
				        "can only show online status for ATTACKER or DEFENDER, not for " + combatant.getSide()); //$NON-NLS-1$
			}

			if (combatant.isOnline()) {
				model.addOne();
			} else {
				model.removeOne();
			}
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#hasSwappedPosition(de.outstare.fortbattleplayer.model.Combatant)
	 */
	public void hasSwappedPosition(final Combatant combatant, final Combatant swappedWith) {
		// ignore
	}

	/**
	 * An PercentModel counts a number and has a maximum, but delievers a
	 * percentage as value.
	 * 
	 * @author daniel
	 */
	private static class PercentModel extends DefaultBoundedRangeModel {
		/**
		 * generated serial
		 */
		private static final long serialVersionUID = -7520281131615644159L;
		private int current = 0;
		private final int max;

		PercentModel(final int maxCombatants) {
			super();
			max = maxCombatants;
		}

		int getCurrent() {
			return current;
		}

		void addOne() {
			current++;
			fireStateChanged();
		}

		void removeOne() {
			current--;
			fireStateChanged();
		}

		/**
		 * @see javax.swing.DefaultBoundedRangeModel#getValue()
		 */
		@Override
		public int getValue() {
			if (max == 0) {
				// prevent division by zero
				return 0;
			}
			return current * 100 / max;
		}

		/**
		 * @see javax.swing.DefaultBoundedRangeModel#setValue(int)
		 */
		@Override
		public void setValue(final int n) {
			throw new UnsupportedOperationException("the percentage cannot be set. Use the field 'onliner'"); //$NON-NLS-1$
		}
	}

	/**
	 * @see de.outstare.fortbattleplayer.model.CombatantObserver#criticalShot(de.outstare.fortbattleplayer.model.Combatant,
	 *      Combatant, int)
	 */
	public void criticalShot(final Combatant combatant, final Combatant victim, final int damage) {
		// ignore
	}

}
