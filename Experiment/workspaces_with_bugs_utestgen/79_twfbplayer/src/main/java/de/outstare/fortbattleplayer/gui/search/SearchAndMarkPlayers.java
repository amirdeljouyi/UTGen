package de.outstare.fortbattleplayer.gui.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.JTextComponent;

import de.outstare.fortbattleplayer.gui.battlefield.PlayerDrawing;
import de.outstare.fortbattleplayer.gui.battlefield.PlayerDrawingDB;

/**
 * 
 * @author daniel
 */
public class SearchAndMarkPlayers implements ActionListener {
	private final PlayerDrawingDB players;
	private final JTextComponent text;

	/**
	 * what to search for
	 * 
	 * @author daniel
	 */
	enum SearchType {
		/**
		 * name of player
		 */
		NAME,
		/**
		 * name of weapon
		 */
		WEAPON
	}

	/**
	 * never <code>null</code>!
	 */
	private Set<PlayerDrawing> lastResult = new HashSet<PlayerDrawing>();

	/**
	 * @param playerDb
	 * @param input
	 */
	public SearchAndMarkPlayers(final PlayerDrawingDB playerDb, final JTextComponent input) {
		players = playerDb;
		text = input;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		final String searchString = text.getText();
		if (searchString == null) {
			return;
		}

		final SearchType searchType = SearchType.valueOf(e.getActionCommand());

		clearMarks(lastResult);
		lastResult = searchByType(searchString, searchType);
		mark(lastResult);
	}

	/**
	 * @param searchString
	 * @param actionCommand
	 * @return
	 */
	private Set<PlayerDrawing> searchByType(final String searchString, final SearchType type) {
		Set<PlayerDrawing> result;
		switch (type) {
		case NAME:
			result = players.findUsers(searchString);
			break;
		case WEAPON:
			result = players.findUsersWithWeapon(searchString);
			break;
		default:
			result = new HashSet<PlayerDrawing>();
		}
		return result;
	}

	/**
	 * @param drawings
	 */
	private void clearMarks(final Set<PlayerDrawing> drawings) {
		for (final PlayerDrawing drawing : drawings) {
			drawing.unmark();
		}
	}

	/**
	 * @param drawings
	 */
	private void mark(final Set<PlayerDrawing> drawings) {
		for (final PlayerDrawing drawing : drawings) {
			drawing.mark();
		}
	}

}
