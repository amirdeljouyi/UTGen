package de.outstare.fortbattleplayer.logging;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A RestoreLogLevels sets the level for a set of given loggers by name.
 * 
 * @author daniel
 */
class RestoreLogLevels extends LogIterator {
	private final Map<String, Level> lastLogLevels;

	/**
	 * @param backup
	 *            the levels for each logger which should be restored (key is
	 *            logger name, value is the log level)
	 */
	public RestoreLogLevels(final Map<String, Level> backup) {
		lastLogLevels = backup;
	}

	/**
	 * @see de.outstare.fortbattleplayer.logging.LogIterator#doWithLogger(java.util.logging.Logger)
	 */
	@Override
	void doWithLogger(final Logger logger) {
		final Level backupLevel = lastLogLevels.get(logger.getName());
		if (backupLevel != null) {
			logger.setLevel(backupLevel);
		}
	}
}