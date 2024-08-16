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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.outstare.fortbattleplayer.player.BattleLog;
import de.outstare.fortbattleplayer.player.Battleplayer;
import de.outstare.fortbattleplayer.player.CombatantEventDispatcher;

/**
 * shows text messages
 * 
 * @author daniel
 */
class LogPanel extends JPanel {
	/**
	 * 
	 * @author daniel
	 */
	private static class LogviewDisplayer implements ActionListener {
		/**
	     * 
	     */
		private final JButton _hideButton;
		/**
	     * 
	     */
		private final JScrollPane _centerComponent;

		/**
		 * @param hideButton
		 * @param centerComponent
		 */
		LogviewDisplayer(final JButton hideButton, final JScrollPane centerComponent) {
			_hideButton = hideButton;
			_centerComponent = centerComponent;
		}

		public void actionPerformed(final ActionEvent e) {
			if (_centerComponent.isVisible()) {
				_centerComponent.setVisible(false);
				_hideButton.setText(Messages.getString("LogPanel.show")); //$NON-NLS-1$
			} else {
				_centerComponent.setVisible(true);
				_hideButton.setText(Messages.getString("LogPanel.hide")); //$NON-NLS-1$
			}

		}
	}

	/**
	 * 
	 * @author daniel
	 */
	private static class LogviewScroller implements ChangeListener {
		/**
	     * 
	     */
		private final int _rowHeight;
		private volatile int lastHeight = 0;

		/**
		 * @param rowHeight
		 */
		LogviewScroller(final int rowHeight) {
			_rowHeight = rowHeight;
		}

		public void stateChanged(final ChangeEvent e) {
			final JViewport vp = (JViewport) e.getSource();
			final int newHeight = vp.getViewSize().height;
			if (newHeight != lastHeight) {
				lastHeight = newHeight;
				final int viewHeight = vp.getExtentSize().height;
				// don't show last line, which is always empty
				vp.setViewPosition(new Point(0, newHeight - viewHeight - _rowHeight));
			}
		}
	}

	/**
	 * copies the data in the {@link OutputStream} to the log window
	 * 
	 * @author daniel
	 */
	private static class LogAppender extends ByteArrayOutputStream {
		private final JTextArea _log;

		/**
		 * @param log
		 */
		LogAppender(final JTextArea log) {
			_log = log;
		}

		/**
		 * move content from stream to JTextArea
		 * 
		 * @see java.io.OutputStream#flush()
		 */
		@Override
		public void flush() throws IOException {
			super.flush();
			_log.append(new String(toByteArray()));
			reset();
		}
	}

	private static final long serialVersionUID = -85232864719836763L;

	/**
	 * @param controller
	 * @param combatants
	 */
	LogPanel(final Battleplayer controller, final CombatantEventDispatcher combatants) {
		super(new BorderLayout());

		final JTextArea log = new JTextArea(5, 20);
		log.setEditable(false);
		final int rowHeight = log.getFontMetrics(log.getFont()).getHeight();
		final OutputStream logBuffer = new LogAppender(log);
		new BattleLog(new PrintStream(logBuffer), controller, combatants);

		final JPanel topPanel = new JPanel();
		topPanel.add(new JLabel(Messages.getString("LogPanel.label"))); //$NON-NLS-1$
		final JButton hideButton = new JButton(Messages.getString("LogPanel.show")); //$NON-NLS-1$
		topPanel.add(hideButton);
		add(topPanel, BorderLayout.NORTH);
		final JScrollPane centerComponent = new JScrollPane(log);
		centerComponent.setVisible(false);
		centerComponent.getViewport().addChangeListener(new LogviewScroller(rowHeight));
		add(centerComponent, BorderLayout.CENTER);
		hideButton.addActionListener(new LogviewDisplayer(hideButton, centerComponent));
	}
}
