package de.outstare.fortbattleplayer.gui;

/*
 Copyright (c) 2010 Daniel Raap

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.json.JSONException;

import de.outstare.fortbattleplayer.gui.battlefield.BattlefieldDrawing;
import de.outstare.fortbattleplayer.gui.battlefield.PlayerDrawing;
import de.outstare.fortbattleplayer.gui.battlefield.PlayerDrawingDB;
import de.outstare.fortbattleplayer.gui.search.SearchPanel;
import de.outstare.fortbattleplayer.gui.statistics.DataPanel;
import de.outstare.fortbattleplayer.gui.statistics.StatisticsPanel;
import de.outstare.fortbattleplayer.model.Battlefield;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.Fortbattle;
import de.outstare.fortbattleplayer.player.Battleplan;
import de.outstare.fortbattleplayer.player.Battleplayer;
import de.outstare.fortbattleplayer.player.CombatantEventDispatcher;
import de.outstare.fortbattleplayer.statistics.AllStatistics;

/**
 * A GuiPlayer shows a FortBattle. During execution the player visualizes the
 * battle. Meaning all events have to be handled to the gui.
 * 
 * @author daniel
 */
public class GuiPlayer extends JInternalFrame implements PlayerDrawingDB {
	/**
	 * shows either only the Center of the given panel or all parts
	 * 
	 * @author daniel
	 */
	private static class FullscreenSwitcher implements ActionListener {
		private final JPanel _panel;
		private final JButton _fsButton;

		/**
		 * @param panel
		 *            with {@link BorderLayout}
		 * @param fsButton
		 */
		FullscreenSwitcher(final JPanel panel, final JButton fsButton) {
			_panel = panel;
			_fsButton = fsButton;
		}

		public void actionPerformed(final ActionEvent e) {
			boolean othersVisible;
			if (Messages.getString("GuiPlayer.fullscreen").equals(_fsButton.getText())) { //$NON-NLS-1$
				othersVisible = false;
				_fsButton.setText(Messages.getString("GuiPlayer.showAll")); //$NON-NLS-1$
			} else {
				othersVisible = true;
				_fsButton.setText(Messages.getString("GuiPlayer.fullscreen")); //$NON-NLS-1$
			}
			final LayoutManager mainLayout = _panel.getLayout();
			if (mainLayout instanceof BorderLayout) {
				final BorderLayout layout = (BorderLayout) mainLayout;
				final String[] directions = new String[] { BorderLayout.NORTH, BorderLayout.LINE_START,
				        BorderLayout.LINE_END, BorderLayout.SOUTH };
				for (final String direction : directions) {
					final Component comp = layout.getLayoutComponent(direction);
					setVisible(comp, othersVisible);
				}
			}
		}

		/**
		 * checks if comp may be <code>null</code>
		 * 
		 * @param comp
		 * @param isVisible
		 */
		private void setVisible(final Component comp, final boolean isVisible) {
			if (comp != null) {
				comp.setVisible(isVisible);
			}
		}
	}

	private static final long serialVersionUID = -3770016315108490794L;
	private static final transient Logger LOG = Logger.getLogger(GuiPlayer.class.getName());
	private final Battleplayer controller;
	private final Set<PlayerDrawing> players = new HashSet<PlayerDrawing>();

	/**
	 * creates a new player for the given data
	 * 
	 * @param battle
	 * @param version
	 *            to show to the user
	 * @throws JSONException
	 */
	public GuiPlayer(final Fortbattle battle, final String version) {
		super(Messages.getString("GuiPlayer.title") + battle.getFortname() + " - v" + version); //$NON-NLS-1$ //$NON-NLS-2$
		final Battlefield battlefield = battle.getBattlefield();
		final Set<Combatant> combatants = loadCombatants(battle);
		final Battleplan plan = battle.getActions();
		final CombatantEventDispatcher combatantEvents = new CombatantEventDispatcher(combatants);

		final AllStatistics stats = battle.getStatistic();
		final StatisticsPanel statsPanel = new StatisticsPanel(stats);
		final DataPanel dataPanel = new DataPanel(stats);

		controller = new Battleplayer(plan);
		final JPanel statusPanel = createStatusPanel(combatants, combatantEvents);
		final BattlefieldDrawing gui = new BattlefieldDrawing(battlefield, battle.getMapImage(), controller.config,
		        stats.battle.getFieldStatistics());
		final JPanel log = new LogPanel(controller, combatantEvents);
		createCombatantDrawings(combatants, gui);

		controller.addRoundListener(gui);

		initFrame(gui, statusPanel, log, statsPanel, dataPanel);
	}

	/**
	 * @param combatants
	 * @param combatantEvents
	 * @return
	 */
	private JPanel createStatusPanel(final Set<Combatant> combatants, final CombatantEventDispatcher combatantEvents) {
		int countAttacker = 0;
		int countDefenders = 0;
		int hpAttackers = 0;
		int hpDefenders = 0;
		for (final Combatant combatant : combatants) {
			switch (combatant.getSide()) {
			case ATTACKER:
				countAttacker++;
				hpAttackers += combatant._health();
				break;
			case DEFENDER:
				countDefenders++;
				hpDefenders += combatant._health();
				break;
			default:
				LOG.warning("unknown combatant side: " + combatant.getSide()); //$NON-NLS-1$
			}
		}

		final StatusPanel status = new StatusPanel(countAttacker, countDefenders, hpAttackers, hpDefenders);
		combatantEvents.addCombatantObserver(status);

		return status;
	}

	/**
	 * sets the gui stuff
	 * 
	 * @param gui
	 * @param statusPanel
	 * @param log2
	 * @param statsPanel
	 */
	private void initFrame(final BattlefieldDrawing gui, final JPanel statusPanel, final JPanel log,
	        final JComponent statsPanel, final JPanel dataPanel) {
		final JButton statsButton = new JButton(Messages.getString("GuiPlayer.statisticButton")); //$NON-NLS-1$
		statsButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				final JFrame popup = new PopupWindow(Messages.getString("GuiPlayer.statisticTitle"), GuiPlayer.this); //$NON-NLS-1$
				popup.setContentPane(statsPanel);
				popup.pack();
				// move to center after resize
				popup.setLocationRelativeTo(GuiPlayer.this);
			}
		});
		final JPanel rightSide = new JPanel(new GridLayout(2, 1));
		rightSide.add(statsButton);
		rightSide.add(dataPanel);

		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		final JPanel leftSide = new JPanel(new BorderLayout());
		leftSide.add(statusPanel, BorderLayout.NORTH);
		leftSide.add(new SearchPanel(this), BorderLayout.SOUTH);
		// the panel is needed for correct repainting of the BattlefieldDrawing
		final JPanel panel = new JPanel(new BorderLayout());
		final JPanel center = new JPanel(new BorderLayout());
		center.add(createFullscreenButton(panel), BorderLayout.NORTH);
		center.add(gui, BorderLayout.CENTER);
		panel.add(center, BorderLayout.CENTER);
		panel.add(new ControlPanel(controller), BorderLayout.NORTH);
		panel.add(rightSide, BorderLayout.LINE_END);
		panel.add(leftSide, BorderLayout.LINE_START);
		panel.add(log, BorderLayout.SOUTH);
		setContentPane(panel);
		setSize(600, 450);
		setVisible(true);
	}

	/**
	 * @param panel
	 *            where only the center or all should be visible
	 * @return
	 */
	private JComponent createFullscreenButton(final JPanel panel) {
		final JButton fsButton = new JButton(Messages.getString("GuiPlayer.fullscreen")); //$NON-NLS-1$
		fsButton.addActionListener(new FullscreenSwitcher(panel, fsButton));
		return fsButton;
	}

	private Set<Combatant> loadCombatants(final Fortbattle battle) {
		final Set<Combatant> allCombatants = new HashSet<Combatant>();
		final Set<Combatant> attackers = battle.getAttackers();
		for (final Combatant attacker : attackers) {
			allCombatants.add(attacker);
		}
		final Set<Combatant> defenders = battle.getDefenders();
		for (final Combatant defender : defenders) {
			allCombatants.add(defender);
		}
		return allCombatants;
	}

	/**
	 * @param combatants
	 * @param battlefield
	 * @throws JSONException
	 */
	private void createCombatantDrawings(final Set<Combatant> combatants, final BattlefieldDrawing battlefield) {
		PlayerDrawing drawing = null;
		for (final Combatant combatant : combatants) {
			// the instance binds itself to the battlefield
			drawing = new PlayerDrawing(combatant, battlefield);
			addPlayer(drawing);
		}
	}

	/**
	 * show the complete battle
	 */
	public void play() {
		controller.play();
	}

	/**
	 * stop the animation
	 */
	public void stop() {
		controller.stop();
	}

	/**
	 * @see javax.swing.JInternalFrame#dispose()
	 */
	@Override
	public void dispose() {
		stop();
		super.dispose();
	}

	/**
	 * @see de.outstare.fortbattleplayer.gui.battlefield.PlayerDrawingDB#addPlayer(de.outstare.fortbattleplayer.gui.battlefield.PlayerDrawing)
	 */
	public void addPlayer(final PlayerDrawing drawing) {
		players.add(drawing);
	}

	/**
	 * @see de.outstare.fortbattleplayer.gui.battlefield.PlayerDrawingDB#findUsers(java.lang.String)
	 */
	public Set<PlayerDrawing> findUsers(final String namePart) {
		final Set<PlayerDrawing> result = new HashSet<PlayerDrawing>();
		final Locale locale = Locale.getDefault();
		final String lowerCaseSearch = namePart.toLowerCase(locale);
		LOG.info("searching user with " + lowerCaseSearch);
		for (final PlayerDrawing drawing : players) {
			final String lowerCaseName = drawing.getCombatantName().toLowerCase(locale);
			if (lowerCaseName.contains(lowerCaseSearch)) {
				result.add(drawing);
			}
		}
		return result;
	}

	/**
	 * @see de.outstare.fortbattleplayer.gui.battlefield.PlayerDrawingDB#findUsersWithWeapon(java.lang.String)
	 */
	public Set<PlayerDrawing> findUsersWithWeapon(final String namePart) {
		final Set<PlayerDrawing> result = new HashSet<PlayerDrawing>();
		final Locale locale = Locale.getDefault();
		final String lowerCaseSearch = namePart.toLowerCase(locale);
		LOG.fine("searching weapon with " + lowerCaseSearch);
		for (final PlayerDrawing drawing : players) {
			final String lowerCaseName = drawing.getGun().name().toLowerCase(locale);
			if (lowerCaseName.contains(lowerCaseSearch)) {
				result.add(drawing);
			}
		}
		return result;
	}
}
