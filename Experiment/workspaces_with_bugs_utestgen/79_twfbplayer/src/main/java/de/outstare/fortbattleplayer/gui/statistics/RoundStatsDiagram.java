package de.outstare.fortbattleplayer.gui.statistics;

import java.awt.Color;
import java.util.Map;

import de.outstare.fortbattleplayer.model.CombatantSide;
import de.outstare.fortbattleplayer.statistics.LabeledData;

/**
 * Draws a diagram for data for each round
 * 
 * @author daniel
 */
class RoundStatsDiagram extends StatisticDiagramm {
	private static final long serialVersionUID = 3435184006921266637L;

	/**
	 * @param map
	 */
	RoundStatsDiagram(final Map<CombatantSide, ? extends LabeledData> map) {
		// we assume all RoundStatistics have the same description and valuetype
		super(map.get(CombatantSide.ATTACKER).getDescription(), "round",
		        map.get(CombatantSide.ATTACKER).getValueType(), new double[][] {
		                map.get(CombatantSide.ATTACKER).toArray(), map.get(CombatantSide.DEFENDER).toArray() });

	}

	/**
	 * @param map
	 * @param color1
	 * @param color2
	 */
	protected void addData(final Map<CombatantSide, ? extends LabeledData> map, final Color color1, final Color color2) {
		final double[][] data = new double[][] { map.get(CombatantSide.ATTACKER).toArray(),
		        map.get(CombatantSide.DEFENDER).toArray() };
		addData(data, color1, color2);
	}
}
