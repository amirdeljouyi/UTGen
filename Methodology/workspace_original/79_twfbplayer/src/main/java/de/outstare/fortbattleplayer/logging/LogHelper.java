package de.outstare.fortbattleplayer.logging;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A LogHelper helps configure the logging during runtime
 * 
 * @author daniel
 */
public abstract class LogHelper {
	private static final Logger LOG = Logger.getLogger("de.outstare.fortbattleplayer.logging.LogHelper");

	/**
	 * store for backup of log levels
	 */
	static final Map<String, Level> LAST_LOG_LEVELS = new HashMap<String, Level>();

	/**
	 * Set the given {@link Level} for each logger.
	 * 
	 * <p>
	 * ATTENTION: After doing this, all loggers will have the same level which
	 * may sabotage an existing log configuration!
	 * </p>
	 * 
	 * @param loglevel
	 */
	public static void setGlobalLogLevel(final Level loglevel) {
		try {
			new GlobalLogLevelSetter(loglevel).applyToAll();
		} catch (final Exception e) {
			LOG.severe("failed to backup log levels: " + e);
		}
	}

	/**
	 * saves the current log level of all known loggers
	 */
	public static void backupLogLevels() {
		try {
			new BackupLogLevels(LAST_LOG_LEVELS).applyToAll();
		} catch (final Exception e) {
			LOG.severe("failed to backup log levels: " + e);
		}
	}

	/**
	 * restores the current log level of all known loggers
	 */
	public static void restoreLogLevels() {
		try {
			new RestoreLogLevels(LAST_LOG_LEVELS).applyToAll();
		} catch (final Exception e) {
			LOG.severe("failed to backup log levels: " + e);
		}
	}
}
