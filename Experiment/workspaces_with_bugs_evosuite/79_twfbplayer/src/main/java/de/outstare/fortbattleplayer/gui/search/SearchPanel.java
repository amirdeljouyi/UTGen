package de.outstare.fortbattleplayer.gui.search;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.outstare.fortbattleplayer.gui.Messages;
import de.outstare.fortbattleplayer.gui.battlefield.PlayerDrawingDB;

/**
 * A SearchPanel offers the ability to search for a playername and the result
 * will be highlighted
 * 
 * @author daniel
 */
public class SearchPanel extends JPanel {
	private static final long serialVersionUID = 9170312117649810353L;

	/**
	 * @param players
	 */
	public SearchPanel(final PlayerDrawingDB players) {
		super(new BorderLayout());

		final JTextField input = new JTextField();
		final SearchAndMarkPlayers searcher = new SearchAndMarkPlayers(players, input);
		final JButton weaponButton = new JButton(Messages.getString("SearchPanel.weaponButton")); //$NON-NLS-1$
		weaponButton.setActionCommand(SearchAndMarkPlayers.SearchType.WEAPON.toString());
		weaponButton.addActionListener(searcher);
		final JButton playerButton = new JButton(Messages.getString("SearchPanel.button")); //$NON-NLS-1$
		playerButton.setActionCommand(SearchAndMarkPlayers.SearchType.NAME.toString());
		playerButton.addActionListener(searcher);
		// TODO use a better LayoutManager
		add(input, BorderLayout.NORTH);
		add(playerButton, BorderLayout.CENTER);
		add(weaponButton, BorderLayout.SOUTH);
	}
}
