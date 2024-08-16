package de.outstare.fortbattleplayer.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A GlobalLogLevelSetter sets the level for all {@link Logger}s to a given
 * {@link Level}.
 * 
 * @author daniel
 */
class GlobalLogLevelSetter extends LogIterator {
	private final Level logLevel;

	/**
	 * @param level
	 *            the level to set for each {@link Logger}
	 */
	public GlobalLogLevelSetter(final Level level) {
		logLevel = level;
	}

	/**
	 * @see de.outstare.fortbattleplayer.logging.LogIterator#doWithLogger(java.util.logging.Logger)
	 */
	@Override
	void doWithLogger(final Logger logger) {
		logger.setLevel(logLevel);
	}
}