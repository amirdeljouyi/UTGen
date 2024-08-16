package de.outstare.fortbattleplayer.statistics;

import java.text.NumberFormat;

import de.outstare.fortbattleplayer.model.Area;

/**
 * A AreaStatistic holds statistics about one cell on the battlefield.
 * 
 * @author daniel
 */
public class AreaStatistic {
	private final Area field;
	private int shots = 0;
	private int hits = 0;
	private int crits = 0;

	/**
	 * Create a statistic for the given field.
	 * 
	 * @param field
	 */
	AreaStatistic(final Area field) {
		this.field = field;
	}

	/**
	 * increment the amount of shots
	 */
	void addShot() {
		shots++;
	}

	/**
	 * increment the amount of hits
	 */
	void addHit() {
		hits++;
	}

	/**
	 * increment the amount of crits
	 */
	void addCrit() {
		crits++;
	}

	/**
	 * @return the field
	 */
	public Area getField() {
		return field;
	}

	/**
	 * @return the shots
	 */
	public int getShots() {
		return shots;
	}

	/**
	 * @return the hits
	 */
	public int getHits() {
		return hits;
	}

	/**
	 * @return the crits
	 */
	public int getCrits() {
		return crits;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final String hitRatio = formatDecimal(getHitRatio());
		final String critRatio = formatDecimal(getCritRatio());
		return shots + " shots\n" + hits + " hits(" + hitRatio + ")\n" + crits + " crits (" + critRatio + ")";
	}

	/**
	 * @return
	 */
	float getCritRatio() {
		float critRatio;
		if (hits == 0) {
			critRatio = 0;
		} else {
			critRatio = (float) crits / (float) hits;
		}
		return critRatio;
	}

	/**
	 * @return
	 */
	float getHitRatio() {
		float hitRatio;
		if (shots == 0) {
			hitRatio = 0;
		} else {
			hitRatio = (float) hits / (float) shots;
		}
		return hitRatio;
	}

	/**
	 * @param number
	 * @return
	 */
	static String formatDecimal(final double number) {
		return NumberFormat.getPercentInstance().format(number);
	}
}
