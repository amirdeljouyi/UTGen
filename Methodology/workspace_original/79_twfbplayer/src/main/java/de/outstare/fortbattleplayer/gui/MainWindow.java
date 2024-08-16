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
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.json.JSONException;

import de.outstare.fortbattleplayer.model.Fortbattle;
import de.outstare.fortbattleplayer.parser.JSONDataParser;

/**
 * The main window of the fortbattle GUI consinst of a menu for common tasks and
 * a main area for showing fortbattles
 * 
 * @author daniel
 */
public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 9075406547291857807L;
	/**
	 * visible for inner classes
	 */
	static final transient Logger LOG = Logger.getLogger(MainWindow.class.getName());
	private static final String OPEN_FILE = Messages.getString("MainWindow.openFile"); //$NON-NLS-1$
	private static final String QUIT = Messages.getString("MainWindow.quit"); //$NON-NLS-1$
	private static final String EXPORT_DATA_HELP = Messages.getString("MainWindow.exportScript"); //$NON-NLS-1$
	private static final String IMPORT_TEXT = Messages.getString("MainWindow.importText"); //$NON-NLS-1$
	private final JDesktopPane desktop = new JDesktopPane();
	private File lastFile = new File(".").getAbsoluteFile(); //$NON-NLS-1$
	private final Set<GuiPlayer> players = new HashSet<GuiPlayer>();
	/**
	 * The version of the program
	 */
	final String version;

	/**
	 * visible for inner class
	 */
	final JLabel statusMessage = new JLabel("", SwingConstants.CENTER);

	/**
	 * @param version
	 *            to show to the user
	 */
	public MainWindow(final String version) {
		super(Messages.getString("MainWindow.title") + MainWindow.class.getPackage().getImplementationVersion()); //$NON-NLS-1$
		this.version = version;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setJMenuBar(createMenu());

		final JPanel content = new JPanel(new BorderLayout());
		content.add(desktop, BorderLayout.CENTER);
		content.add(createStatusBar(), BorderLayout.PAGE_END);
		setContentPane(content);

		setSize(800, 600);
		// center the window on the screen
		setLocationRelativeTo(null);
		setVisible(true);
		setExtendedState(MAXIMIZED_BOTH);
	}

	private JPanel createStatusBar() {
		final JPanel statusPanel = new JPanel();
		statusPanel.add(statusMessage);
		return statusPanel;
	}

	/**
	 * @return
	 */
	private JMenuBar createMenu() {
		final JMenuBar menus = new JMenuBar();

		final JMenu fileMenu = new JMenu(Messages.getString("MainWindow.menuPlayer")); //$NON-NLS-1$
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menus.add(fileMenu);

		fileMenu.add(menuEntryTextInput());
		fileMenu.add(menuEntryOpenFile());
		fileMenu.add(menuEntryQuit());

		final JMenu helpMenu = new JMenu(Messages.getString("MainWindow.menuHelp")); //$NON-NLS-1$
		helpMenu.setMnemonic(KeyEvent.VK_H);
		menus.add(helpMenu);

		helpMenu.add(menuEntryDataExportHelp());

		return menus;
	}

	private JMenuItem createMenuItem(final String label, final int mnemonic) {
		final JMenuItem item = new JMenuItem(label);
		item.setActionCommand(label);
		item.addActionListener(this);
		item.setMnemonic(mnemonic);
		// global shortcut with Control key
		item.setAccelerator(KeyStroke.getKeyStroke(mnemonic, ActionEvent.CTRL_MASK));
		return item;
	}

	/**
	 * @return
	 */
	private JMenuItem menuEntryDataExportHelp() {
		return createMenuItem(EXPORT_DATA_HELP, KeyEvent.VK_E);
	}

	private JMenuItem menuEntryTextInput() {
		return createMenuItem(IMPORT_TEXT, KeyEvent.VK_I);
	}

	/**
	 * @return
	 */
	private JMenuItem menuEntryOpenFile() {
		final JMenuItem openFile = createMenuItem(OPEN_FILE, KeyEvent.VK_O);
		return openFile;
	}

	/**
	 * @return
	 */
	private JMenuItem menuEntryQuit() {
		return createMenuItem(QUIT, KeyEvent.VK_Q);
	}

	/**
	 * show the given window inside this frame
	 * 
	 * @param internalWindow
	 */
	public void addWindow(final JInternalFrame internalWindow) {
		desktop.add(internalWindow);
		try {
			internalWindow.setSelected(true);
		} catch (final PropertyVetoException e) {
			// it is not allowed to select the window
		}
		try {
			internalWindow.setMaximum(true);
		} catch (final PropertyVetoException e) {
			// maximize not allowed
		}
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		if (OPEN_FILE.equals(e.getActionCommand())) {
			final JFileChooser chooser = new JFileChooser(lastFile.getParent());
			final int userAction = chooser.showOpenDialog(this);
			if (JFileChooser.APPROVE_OPTION == userAction) {
				final File dataFile = chooser.getSelectedFile();
				lastFile = dataFile;
				playFile(dataFile);
			}
		} else if (QUIT.equals(e.getActionCommand())) {
			killAllRunningPlayers();
			dispose();
		} else if (EXPORT_DATA_HELP.equals(e.getActionCommand())) {
			showExportScript();
		} else if (IMPORT_TEXT.equals(e.getActionCommand())) {
			newBattleFromTextImport();
		}
	}

	/**
	 * terminate simulation threads
	 */
	private void killAllRunningPlayers() {
		showStatusMessage("stopping all players...");
		for (final GuiPlayer player : players) {
			player.stop();
			player.dispose();
		}
		clearStatusMessage();
	}

	/**
	 * asks the user for the data as text
	 */
	private void newBattleFromTextImport() {
		final String userInput = JOptionPane.showInputDialog(Messages.getString("MainWindow.inputMessage")); //$NON-NLS-1$
		if (userInput != null) {
			showFortBattle(userInput);
		}
	}

	/**
	 * @param dataFile
	 */
	private void playFile(final File dataFile) {
		showStatusMessage("loading file...");
		try {
			final BufferedReader in = new BufferedReader(new FileReader(dataFile));
			try {
				final String data = in.readLine();
				showStatusMessage("finished loading file.");
				if (data != null) {
					showFortBattle(data);
				}
			} finally {
				in.close();
			}
		} catch (final FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (final IOException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * @throws HeadlessException
	 */
	private void showExportScript() throws HeadlessException {
		try {
			final InputStream scriptInput = ClassLoader.getSystemResourceAsStream("getData.js"); //$NON-NLS-1$
			final StringBuilder scriptText = new StringBuilder();
			if (scriptInput != null) {
				final BufferedReader scriptReader = new BufferedReader(new InputStreamReader(scriptInput));
				try {
					String line;
					while ((line = scriptReader.readLine()) != null) {
						scriptText.append(line).append('\n');
					}
				} finally {
					scriptReader.close();
				}
			} else {
				scriptText.append("Not found. This is a bug!"); //$NON-NLS-1$
			}
			final JTextArea textField = createTextField(scriptText);
			JOptionPane.showMessageDialog(this, textField, Messages.getString("MainWindow.scriptMessage"), //$NON-NLS-1$
			        JOptionPane.INFORMATION_MESSAGE);
		} catch (final FileNotFoundException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, e1.getMessage(),
			        Messages.getString("MainWindow.errorTitle"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		} catch (final IOException e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(this, e2.getMessage(),
			        Messages.getString("MainWindow.errorTitle"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		}
	}

	/**
	 * @param scriptText
	 * @return
	 */
	private JTextArea createTextField(final StringBuilder scriptText) {
		final JTextArea textField = new JTextArea(scriptText.toString());
		textField.setEditable(false);
		textField.setDragEnabled(true);
		textField.addFocusListener(new SelectAllOnFocus(textField));
		textField.requestFocusInWindow();
		addContextMenu(textField);
		return textField;
	}

	/**
	 * @param textField
	 */
	private void addContextMenu(final JTextArea textField) {
		final JPopupMenu contextMenu = new JPopupMenu(Messages.getString("MainWindow.contextMenuTitle")); //$NON-NLS-1$
		final JMenuItem copy = new JMenuItem(Messages.getString("MainWindow.contextMenuCopy")); //$NON-NLS-1$
		copy.addActionListener(new CopyAction(textField));
		final JMenuItem paste = new JMenuItem(Messages.getString("MainWindow.contextMenuPaste")); //$NON-NLS-1$
		paste.addActionListener(new PasteAction(textField));
		contextMenu.add(copy);
		contextMenu.add(paste);
		textField.setComponentPopupMenu(contextMenu);
	}

	/**
	 * @param data
	 * @throws JSONException
	 */
	private void showFortBattle(final String data) {
		showStatusMessage("wait for parsing data...");
		// the parsing runs not in the GUI/AWT thread, because it would block
		// the UI!
		new Thread(new Runnable() {
			public void run() {
				showStatusMessage("parsing data...");
				try {
					final JSONDataParser parser = new JSONDataParser(data);
					final Fortbattle battle = parser.getBattle();

					showStatusMessage("building player GUI...");
					final GuiPlayer player = new GuiPlayer(battle, version);
					addPlayer(player);
					addWindow(player);
					clearStatusMessage();
					// play must be called outside of AWT/Swing
					LOG.info("simulating " + player.getTitle()); //$NON-NLS-1$
					player.play();
					LOG.info("end of battle " + player.getTitle()); //$NON-NLS-1$
				} catch (final JSONException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(MainWindow.this, e.getMessage(),
					        Messages.getString("MainWindow.errorTitle"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
				}
			}
		}).start();
	}

	/**
	 * @param player
	 */
	void addPlayer(final GuiPlayer player) {
		players.add(player);
	}

	/**
	 * Displays the given message in the status bar.
	 * 
	 * @param message
	 */
	void showStatusMessage(final String message) {
		System.out.println("status: " + message);
		statusMessage.setText(message);
	}

	/**
	 * Removes the current status message
	 */
	void clearStatusMessage() {
		statusMessage.setText("");
	}
}
