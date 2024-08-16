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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.outstare.fortbattleplayer.Configuration;
import de.outstare.fortbattleplayer.player.Battleplayer;
import de.outstare.fortbattleplayer.player.Battleplayer.RoundListener;
import de.outstare.fortbattleplayer.player.PlayerConfiguration;

/**
 * A ControlPanel lets the user control the playing of a fortbattle.
 * 
 * @author daniel
 */
class ControlPanel extends JPanel implements RoundListener, ChangeListener, ActionListener {
	private static final long serialVersionUID = -4712885397136619291L;
	/**
	 * visible for inner classes
	 */
	static final transient Logger LOG = Logger.getLogger(ControlPanel.class.getName());
	private final JSpinner roundNo;
	private final JSlider tempoSliderPlayer = new JSlider(0, 2000, Configuration.PLAYER_DELAY);
	private final JSlider tempoSliderRound = new JSlider(0, 10000, Configuration.ROUND_DELAY);
	private final JLabel currentPlayerDelay = new JLabel(tempoSliderPlayer.getValue()
	        + Messages.getString("ControlPanel.milliSeconds")); //$NON-NLS-1$
	private final JLabel currentRoundDelay = new JLabel(tempoSliderRound.getValue()
	        + Messages.getString("ControlPanel.milliSeconds")); //$NON-NLS-1$
	/**
	 * visible for inner class
	 */
	final Battleplayer controller;

	/**
	 * create a InfoPanel for the given {@link Battleplayer}
	 * 
	 * @param control
	 */
	ControlPanel(final Battleplayer control) {
		super(new GridBagLayout());
		controller = control;
		control.addRoundListener(this);

		final GridBagConstraints constraints = new GridBagConstraints();
		// three columns
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridy = 0;
		add(new JLabel(Messages.getString("ControlPanel.round")), constraints); //$NON-NLS-1$
		constraints.anchor = GridBagConstraints.LINE_END;
		final JButton pauseButton = new JButton(Messages.getString("ControlPanel.pause")); //$NON-NLS-1$
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (controller.isPaused()) {
					controller.pause();
					pauseButton.setText(Messages.getString("ControlPanel.pause")); //$NON-NLS-1$
				} else {
					controller.pause();
					pauseButton.setText(Messages.getString("ControlPanel.unpause")); //$NON-NLS-1$
				}
			}
		});
		final int noRounds = control.numberOfRounds();
		roundNo = createRoundNoSpinner(controller.getRoundNumbers());
		final JPanel roundPanel = new JPanel();
		roundPanel.add(pauseButton);
		roundPanel.add(roundNo);
		roundPanel.add(new JLabel("/" + noRounds)); //$NON-NLS-1$
		add(roundPanel, constraints);
		constraints.anchor = GridBagConstraints.LINE_START;
		final JButton changeRound = new JButton(Messages.getString("ControlPanel.setRound")); //$NON-NLS-1$
		changeRound.addActionListener(this);
		add(changeRound, constraints);

		constraints.gridy++;
		add(new JLabel(Messages.getString("ControlPanel.playerDelay")), constraints); //$NON-NLS-1$
		initPlayerTempoSlider();
		add(tempoSliderPlayer, constraints);
		add(currentPlayerDelay, constraints);

		constraints.gridy++;
		add(new JLabel(Messages.getString("ControlPanel.roundDelay")), constraints); //$NON-NLS-1$
		initRoundTempoSlider();
		add(tempoSliderRound, constraints);
		add(currentRoundDelay, constraints);

		constraints.gridy++;
		add(new JLabel(Messages.getString("ControlPanel.optionsLabel")), constraints); //$NON-NLS-1$
		add(createShootlineCheckbox(), constraints);
		add(createTargetlineCheckbox(), constraints);
	}

	/**
	 * We prefer a {@link SpinnerNumberModel} if possible, because the editor of
	 * the {@link SpinnerListModel} does not work.
	 * 
	 * @param roundNumbers
	 * @return
	 */
	private JSpinner createRoundNoSpinner(final List<Integer> roundNumbers) {
		boolean isSequential = true;
		int n = 1;
		for (final Integer round : roundNumbers) {
			if (n != round.intValue()) {
				isSequential = false;
				break;
			}
			n++;
		}
		SpinnerModel model;
		if (isSequential) {
			model = new SpinnerNumberModel(1, 1, n - 1, 1);
		} else {
			model = new SpinnerListModel(roundNumbers);
		}
		return new JSpinner(model);
	}

	private void initPlayerTempoSlider() {
		tempoSliderPlayer.addChangeListener(this);
		tempoSliderPlayer.setMajorTickSpacing(500);
		tempoSliderPlayer.setMinorTickSpacing(100);
		tempoSliderPlayer.setPaintTicks(true);
	}

	private void initRoundTempoSlider() {
		tempoSliderRound.addChangeListener(this);
		tempoSliderRound.setMajorTickSpacing(5000);
		tempoSliderRound.setMinorTickSpacing(1000);
		tempoSliderRound.setPaintTicks(true);
	}

	/**
	 * @return
	 */
	private JCheckBox createTargetlineCheckbox() {
		final JCheckBox targetlineCheckbox = new JCheckBox(
		        Messages.getString("ControlPanel.drawTargetLine"), controller.config.showMoveTargets()); //$NON-NLS-1$
		targetlineCheckbox.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent e) {
				final JCheckBox box = (JCheckBox) e.getSource();
				controller.config.setShowMoveTargets(box.isSelected());
			}
		});
		return targetlineCheckbox;
	}

	/**
	 * @return
	 */
	private JCheckBox createShootlineCheckbox() {
		final JCheckBox shootlineCheckbox = new JCheckBox(
		        Messages.getString("ControlPanel.drawShootingLine"), controller.config.showShootline()); //$NON-NLS-1$
		shootlineCheckbox.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent e) {
				final JCheckBox box = (JCheckBox) e.getSource();
				controller.config.setShowShootline(box.isSelected());
			}
		});
		return shootlineCheckbox;
	}

	/**
	 * @see de.outstare.fortbattleplayer.player.Battleplayer.RoundListener#nextRound(int)
	 */
	public void nextRound(final int newRoundNo) {
		roundNo.setValue(Integer.valueOf(newRoundNo));
	}

	/**
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(final ChangeEvent e) {
		if (e.getSource().equals(tempoSliderPlayer)) {
			controller.config.PLAYER_DELAY = tempoSliderPlayer.getValue();
			currentPlayerDelay.setText(tempoSliderPlayer.getValue() + Messages.getString("ControlPanel.milliSeconds")); //$NON-NLS-1$
		} else if (e.getSource().equals(tempoSliderRound)) {
			controller.config.ROUND_DELAY = tempoSliderRound.getValue();
			currentRoundDelay.setText(tempoSliderRound.getValue() + Messages.getString("ControlPanel.milliSeconds")); //$NON-NLS-1$
		}
	}

	private void setRoundNoInController(final int newRoundNo) {
		// change round outside of AWT thread
		new Thread(new Runnable() {
			public void run() {
				PlayerConfiguration oldConfiguration;
				try {
					oldConfiguration = controller.config.copy();
					controller.config.PLAYER_DELAY = 0;
					controller.config.ACTION_DELAY = 0;
					// just some time to reset the configuration
					controller.config.ROUND_DELAY = 50;
				} catch (final CloneNotSupportedException e) {
					// should never occur
					e.printStackTrace();
					oldConfiguration = controller.config;
				}
				// skip animations, but wait a moment for next round
				controller.gotoRound(newRoundNo);
				controller.config.setTo(oldConfiguration);
				LOG.fine("now round " + newRoundNo + " is started"); //$NON-NLS-1$ //$NON-NLS-2$
				controller.play();
			}
		}).start();
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		// button pressed
		setRoundNoInController(((Integer) roundNo.getValue()).intValue());
	}
}
