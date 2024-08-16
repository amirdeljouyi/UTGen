package de.outstare.fortbattleplayer.gui.statistics;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

import de.outstare.fortbattleplayer.gui.Messages;
import de.outstare.fortbattleplayer.gui.PopupWindow;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantSide;
import de.outstare.fortbattleplayer.statistics.AllStatistics;
import de.outstare.fortbattleplayer.statistics.CriticalHit;
import de.outstare.fortbattleplayer.statistics.DynamicStatistics;
import de.outstare.fortbattleplayer.statistics.LabeledData;
import de.outstare.fortbattleplayer.statistics.PositionSwitch;
import de.outstare.fortbattleplayer.statistics.StaticStatistics;

/**
 * A DataPanel holds the buttons to access some data about the battle.
 * 
 * @author daniel
 */
public class DataPanel extends JPanel {
	private static final long serialVersionUID = -6788607598344216203L;

	/**
	 * @param allStats
	 */
	public DataPanel(final AllStatistics allStats) {
		setLayout(new GridLayout(8, 1));

		final StaticStatistics stats = allStats.combatant;
		final DynamicStatistics stats2 = allStats.battle;

		combatants(allStats);
		health(allStats);
		healthDistribution(stats);
		shots(stats2);
		noShots(allStats.battle);
		crits(stats2);
		swaps(stats2);
		turnOrder(stats2);
	}

	/**
	 * @param stats2
	 */
	private void turnOrder(final DynamicStatistics stats2) {
		final List<Combatant> attackers = stats2.turnOrder(CombatantSide.ATTACKER);
		final List<Combatant> defenders = stats2.turnOrder(CombatantSide.DEFENDER);
		final TurnOrderTableModel attackerModel = new TurnOrderTableModel(attackers);
		final TurnOrderTableModel defenderModel = new TurnOrderTableModel(defenders);
		add(turnOrderTableButton("DataPanel.TurnOrder", attackerModel, defenderModel));
	}

	/**
	 * @param allStats
	 */
	void health(final AllStatistics allStats) {
		final Map<CombatantSide, LabeledData> diagramData = allStats.rounds.getHealthAmount();
		add(roundDiagramButton("StatisticsPanel.22", diagramData));
	}

	/**
	 * @param stats
	 */
	void healthDistribution(final StaticStatistics stats) {
		final Map<Number, ? extends Number> attackerLPDistData = stats
		        .healthDistributionRelative(CombatantSide.ATTACKER);
		final Map<Number, ? extends Number> defenderLPDistData = stats
		        .healthDistributionRelative(CombatantSide.DEFENDER);
		final StatisticDiagramm hpDistDiagram = new HealthDistributionDiagram(
		        Messages.getString("StatisticsPanel.healthDistributionTitle"), attackerLPDistData, defenderLPDistData);
		add(diagramButton("StatisticsPanel.healthDistribution", hpDistDiagram));
	}

	/**
	 * @param allStats
	 */
	void combatants(final AllStatistics allStats) {
		final Map<CombatantSide, LabeledData> combatantCounts = allStats.rounds.getLivingCounts();
		add(roundDiagramButton("StatisticsPanel.21", combatantCounts));
	}

	/**
	 * @param stats2
	 */
	void shots(final DynamicStatistics stats2) {
		final Map<CombatantSide, ? extends LabeledData> combatantCounts = stats2.getShotsPerRound();
		final RoundStatsDiagram diagram = new RoundStatsDiagram(combatantCounts);
		final Color attackDarker = CombatantSide.ATTACKER.darkColor();
		final Color defendDarker = CombatantSide.DEFENDER.darkColor();
		diagram.addData(stats2.getHitsPerRound(), defendDarker, attackDarker);
		final JButton diagramButton = diagramButton("DataPanel.RoundShots", diagram);
		add(diagramButton);
	}

	/**
	 * @param rounds
	 * @param battle
	 */
	void noShots(final DynamicStatistics battle) {
		final Map<CombatantSide, ? extends LabeledData> nonShootersPerRound = battle.getNotShootersPerRound();
		add(roundDiagramButton("DataPanel.RoundNonShooters", nonShootersPerRound));
	}

	/**
	 * @param stats2
	 */
	void crits(final DynamicStatistics stats2) {
		final List<CriticalHit> critListAtt = stats2.critList(CombatantSide.ATTACKER);
		final List<CriticalHit> critListDeff = stats2.critList(CombatantSide.DEFENDER);
		final TableModel attackCrits = new CritTableModel(critListAtt);
		final TableModel defferCrits = new CritTableModel(critListDeff);

		add(critTableButton("StatisticsPanel.42", attackCrits, defferCrits));
	}

	/**
	 * @param stats2
	 */
	void swaps(final DynamicStatistics stats2) {
		final Set<PositionSwitch> swapListAtt = stats2.switchedPosList(CombatantSide.ATTACKER);
		final Set<PositionSwitch> swapListDeff = stats2.switchedPosList(CombatantSide.DEFENDER);
		final TableModel attackSwaps = new SwapTableModel(swapListAtt);
		final TableModel defferSwaps = new SwapTableModel(swapListDeff);

		add(swapTableButton("StatisticsPanel.38", attackSwaps, defferSwaps));
	}

	/**
	 * @param defferModel
	 * @param buttonTitle
	 * @param diagramData
	 * @return
	 */
	private JButton turnOrderTableButton(final String buttonTitleId, final TableModel attackModel,
	        final TableModel defferModel) {
		final String buttonTitle = Messages.getString(buttonTitleId);
		final ActionListener action = new TurnOrderPopup(buttonTitle, attackModel, defferModel, getParent());
		return tableButton(buttonTitle, action);
	}

	/**
	 * @param defferModel
	 * @param buttonTitle
	 * @param diagramData
	 * @return
	 */
	private JButton critTableButton(final String buttonTitleId, final TableModel attackModel,
	        final TableModel defferModel) {
		final String buttonTitle = Messages.getString(buttonTitleId);
		final ActionListener action = new CritListPopup(buttonTitle, attackModel, defferModel, getParent());
		return tableButton(buttonTitle, action);
	}

	private JButton swapTableButton(final String buttonTitleId, final TableModel attackModel,
	        final TableModel defferModel) {
		final String buttonTitle = Messages.getString(buttonTitleId);
		final ActionListener action = new SwapListPopup(buttonTitle, attackModel, defferModel, getParent());
		return tableButton(buttonTitle, action);
	}

	/**
	 * @param buttonTitle
	 * @param action
	 * @return
	 */
	JButton tableButton(final String buttonTitle, final ActionListener action) {
		final JButton popupButton = new JButton(buttonTitle);
		popupButton.addActionListener(action);
		return popupButton;
	}

	/**
	 * @param buttonTitle
	 * @param combatantCounts
	 * @return
	 */
	private JButton roundDiagramButton(final String buttonTitleId,
	        final Map<CombatantSide, ? extends LabeledData> combatantCounts) {
		final RoundStatsDiagram diagram = new RoundStatsDiagram(combatantCounts);
		return diagramButton(buttonTitleId, diagram);
	}

	/**
	 * @param buttonTitleId
	 * @param diagram
	 * @return
	 */
	private JButton diagramButton(final String buttonTitleId, final StatisticDiagramm diagram) {
		final String buttonTitle = Messages.getString(buttonTitleId);
		final JButton healthDiagramButton = new JButton(buttonTitle);
		healthDiagramButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				final JFrame dialog = new PopupWindow(buttonTitle, DataPanel.this);
				dialog.add(diagram);
				dialog.setSize(diagram.getSize());
				dialog.addMouseListener(new MouseAdapter() {
					/**
					 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
					 */
					@Override
					public void mouseClicked(final MouseEvent event) {
						super.mouseClicked(event);
						dialog.dispose();
					}
				});
			}
		});
		return healthDiagramButton;
	}
}
