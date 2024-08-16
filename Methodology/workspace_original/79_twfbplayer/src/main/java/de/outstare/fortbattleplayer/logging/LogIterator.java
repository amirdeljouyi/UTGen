package de.outstare.fortbattleplayer.logging;

import java.util.Enumeration;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * A LogIterator goes through all known {@link Logger}s giving the possibility
 * to do something with each of them.
 * 
 * @author daniel
 */
abstract class LogIterator {
	/**
	 * iterate over all known loggers and calls {@link #doWithLogger(Logger)}
	 * for each.
	 */
	void applyToAll() {
		final Enumeration<String> loggerNames = LogManager.getLogManager().getLoggerNames();
		while (loggerNames.hasMoreElements()) {
			final String loggerName = loggerNames.nextElement();
			final Logger logger = Logger.getLogger(loggerName);
			doWithLogger(logger);
		}
	}

	/**
	 * Do something with the given logger
	 * 
	 * @param logger
	 */
	abstract void doWithLogger(Logger logger);
}