package de.outstare.fortbattleplayer.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Converts the list of numbers to somewhat meaningfuller names
 * 
 * @author daniel
 */
public class LogToText {
	private static final String NEWLINE = System.getProperty("line.separator");
	private static boolean WRITE_EACH_ROUND_TO_FILE = false;

	/**
	 * entry point
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		if (args.length == 0) {
			System.out
			        .println("Please give a file with fortbattle data! Optionally append the parameter '-r' to write each round to a separate file (named round<n>.txt)");
		} else {
			final String filename = args[0];
			if (args.length > 1 && "-r".equals(args[1])) {
				WRITE_EACH_ROUND_TO_FILE = true;
			}
			final File file = new File(filename);
			if (file.canRead() && file.isFile()) {
				final LogToText parser = new LogToText();
				JSONObject data;
				try {
					data = parser.parseFile(file);
					System.out.println(parser.getLog(data));
				} catch (final JSONException e) {
					e.printStackTrace();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("The given argument is not an readable file!");
			}
		}
	}

	/**
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	private CharSequence getLog(final JSONObject data) throws JSONException {
		final Map<Integer, String> players = getCombatants(data);
		final String log = getTextLog(data, players);
		return log;
	}

	/**
	 * @param data
	 * @param players
	 * @return
	 * @throws JSONException
	 */
	private String getTextLog(final JSONObject data, final Map<Integer, String> players) throws JSONException {
		final JSONArray typeNames = data.getJSONArray("logtypes");
		final JSONArray log = data.getJSONArray("log");
		final StringBuilder formattedLog = new StringBuilder();

		try {
			Writer output = null;
			try {
				if (WRITE_EACH_ROUND_TO_FILE) {
					output = new FileWriter("round0.txt");
				}
				// System.out.println("formatting log data...");
				for (int i = 0; i < log.length(); i += 2) {
					final int typeValue = (int) log.getLong(i);
					final int value = (int) log.getLong(i + 1);
					try {
						final LogType type = LogType.valueOf(typeNames.getString(typeValue));
						final String formattedValue = formatValue(players, value, type);

						if (WRITE_EACH_ROUND_TO_FILE && output != null) {
							if (type == LogType.ROUNDSTART) {
								output.close();
								output = new FileWriter("round" + value + ".txt");
							}
							output.write(type.toString());
							output.write(' ');
							output.write(formattedValue);
							output.write('\n');
						}

						appendLogLine(formattedLog, type, formattedValue);
					} catch (final IllegalArgumentException e) {
						System.err.println("unknown log type: " + typeNames.getString(typeValue) + " [" + typeValue
						        + "]");
						continue;
					}
				}
			} finally {
				if (WRITE_EACH_ROUND_TO_FILE && output != null) {
					output.close();
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return formattedLog.toString();
	}

	/**
	 * @param players
	 * @param value
	 * @param type
	 * @return
	 */
	private String formatValue(final Map<Integer, String> players, final int value, final LogType type) {
		String formattedValue;
		switch (type) {
		case SHOOTAT:
		case CHARTURN:
			formattedValue = players.get(Integer.valueOf(value));
			break;
		default:
			formattedValue = Integer.toString(value);
		}
		return formattedValue;
	}

	/**
	 * @param formattedLog
	 * @param type
	 * @param formattedValue
	 */
	private void appendLogLine(final StringBuilder formattedLog, final LogType type, final String formattedValue) {
		formattedLog.append(type.toString());
		formattedLog.append(' ');
		formattedLog.append(formattedValue);
		formattedLog.append(NEWLINE);
	}

	/**
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	private Map<Integer, String> getCombatants(final JSONObject data) throws JSONException {
		final Map<Integer, String> combatants = new HashMap<Integer, String>();
		final String[] playerArrays = new String[] { "attackerlist", "defenderlist" };
		// System.out.println("reading player names...");
		for (final String playerArray : playerArrays) {
			final JSONArray list = data.getJSONArray(playerArray);
			for (int i = 0; i < list.length(); i++) {
				final JSONObject playerData = list.getJSONObject(i);

				final String playerName = playerData.getString("name");
				final Integer playerID = Integer.valueOf(playerData.getInt("westid"));

				combatants.put(playerID, playerName);
			}
		}

		return combatants;
	}

	/**
	 * @param file
	 * @return the json data of the file
	 * @throws JSONException
	 * @throws IOException
	 */
	public JSONObject parseFile(final File file) throws JSONException, IOException {
		final String text = readFile(file);
		return new JSONObject(text);
	}

	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private String readFile(final File file) throws IOException {
		final Reader reader = new BufferedReader(new FileReader(file));
		try {
			final CharBuffer buffer = CharBuffer.allocate((int) file.length());
			int count;
			// System.out.println("reading file...");
			do {
				count = reader.read(buffer);
			} while (count > 0);

			buffer.position(0);
			return buffer.toString();
		} finally {
			reader.close();
		}
	}

}
