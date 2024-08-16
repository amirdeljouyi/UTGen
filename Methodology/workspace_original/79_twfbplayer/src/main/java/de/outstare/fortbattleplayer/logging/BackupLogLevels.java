package de.outstare.fortbattleplayer.logging;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A BackupLogLevels stores the current log level of each known {@link Logger}.
 * 
 * @author daniel
 */
class BackupLogLevels extends LogIterator {
	private final Map<String, Level> LOG_LEVEL_BACKUP;

	/**
	 * @param backup
	 *            a map to store the level of each logger by name (key is logger
	 *            name, value is the log level). The {@link Map} should be empty
	 *            else existing values will be overridden.
	 */
	public BackupLogLevels(final Map<String, Level> backup) {
		LOG_LEVEL_BACKUP = backup;
	}

	/**
	 * save the current log level of each logger.
	 * 
	 * @see de.outstare.fortbattleplayer.logging.LogIterator#doWithLogger(java.util.logging.Logger)
	 */
	@Override
	void doWithLogger(final Logger logger) {
		LOG_LEVEL_BACKUP.put(logger.getName(), logger.getLevel());
	}
}