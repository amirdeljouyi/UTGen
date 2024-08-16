package de.outstare.fortbattleplayer.statistics;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link RoundStatistics} has a value for each round of the battle.
 * 
 * @author daniel
 */
class RoundStatistics implements LabeledData {
	private final List<Double> values = new ArrayList<Double>();
	private final String description;
	private final String type;

	/**
	 * @param name
	 *            what this statistic is
	 * @param type
	 *            of the values
	 */
	RoundStatistics(final String name, final String type) {
		assert name != null && type != null : "parameters may not be null!";
		description = name;
		this.type = type;
	}

	/**
	 * @param value
	 *            to add to this set of data
	 */
	void addValue(final double value) {
		values.add(Double.valueOf(value));
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.LabeledData#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.LabeledData#getValueType()
	 */
	public String getValueType() {
		return type;
	}

	/**
	 * @see de.outstare.fortbattleplayer.statistics.LabeledData#toArray()
	 */
	public double[] toArray() {
		final double[] primitives = new double[values.size()];
		int i = 0;
		for (final Double value : values) {
			primitives[i] = value.doubleValue();
			i++;
		}
		return primitives;
	}

}
