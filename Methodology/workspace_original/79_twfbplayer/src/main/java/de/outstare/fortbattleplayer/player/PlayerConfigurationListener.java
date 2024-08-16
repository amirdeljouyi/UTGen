package de.outstare.fortbattleplayer.player;

/**
 * A PlayerConfigurationListener is informed if a {@link PlayerConfiguration} is
 * changed.
 * 
 * @author daniel
 */
public interface PlayerConfigurationListener {
	/**
	 * @param newValue
	 *            if move target lines should be shown now
	 */
	void changedShowMoveTarget(boolean newValue);

	/**
	 * @param newValue
	 *            if shooting lines should be shown now
	 */
	void changedShowShootingLine(boolean newValue);
}
