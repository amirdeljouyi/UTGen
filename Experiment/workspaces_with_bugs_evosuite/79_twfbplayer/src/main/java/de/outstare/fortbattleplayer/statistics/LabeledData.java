package de.outstare.fortbattleplayer.statistics;

/**
 * 
 * @author daniel
 */
public interface LabeledData {
	/**
	 * @return a description what kind of data is contained in this object
	 */
	String getDescription();

	/**
	 * @return what a value stands for (i.e damage, health, percent)
	 */
	String getValueType();

	/**
	 * @return all values
	 */
	double[] toArray();
}
