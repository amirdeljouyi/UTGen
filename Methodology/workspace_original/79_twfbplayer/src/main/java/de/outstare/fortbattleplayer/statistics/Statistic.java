package de.outstare.fortbattleplayer.statistics;

/**
 * A Statistic is a special value based on accumulated data
 * 
 * @author daniel
 */
public interface Statistic {

	/**
	 * @return the name of this statistic
	 */
	String name();

	/**
	 * @return the value of this statistic
	 */
	int value();
}
