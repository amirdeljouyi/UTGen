package de.outstare.fortbattleplayer;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

import org.json.JSONException;

import de.outstare.fortbattleplayer.gui.GuiPlayer;
import de.outstare.fortbattleplayer.gui.MainWindow;
import de.outstare.fortbattleplayer.model.Fortbattle;
import de.outstare.fortbattleplayer.parser.JSONDataParser;

/**
 * a starter for GuiPlayer with given data as parameter.
 * 
 * @author daniel
 */
public class Applet extends JApplet {
	/**
	 * generated serial
	 */
	private static final long serialVersionUID = 4521503728443765768L;

	/**
	 * visible for inner classes
	 */
	static final transient Logger LOG = Logger.getLogger(MainWindow.class.getName());

	/**
	 * visible for inner classes
	 */
	GuiPlayer player = null;

	/**
	 * @see java.applet.Applet#init()
	 */
	@Override
	public void init() {
		super.init();
		try {
			Logger.getLogger(Main.class.getPackage().getName()).setLevel(Level.WARNING);
		} catch (final SecurityException e) {
			e.printStackTrace();
		}
		final String data = getParameter("fbdata");
		showFortBattle(data);
	}

	/**
	 * @see java.applet.Applet#start()
	 */
	@Override
	public void start() {
		super.start();
		if (player != null) {
			// play must be called outside of AWT/Swing
			new Thread(new Runnable() {
				public void run() {
					LOG.info("simulating " + player.getTitle());
					player.play();
					LOG.info("end of battle " + player.getTitle());
				}
			}).start();
		} else {
			LOG.warning("not properly initialized");
		}
	}

	/**
	 * @see java.applet.Applet#stop()
	 */
	@Override
	public void stop() {
		player.stop();
		super.stop();
	}

	/**
	 * same as in MainWindow
	 * 
	 * @param data
	 * @throws JSONException
	 */
	private void showFortBattle(final String data) {
		JSONDataParser parser;
		try {
			parser = new JSONDataParser(data);
			final Fortbattle battle = parser.getBattle();

			player = new GuiPlayer(battle, Applet.class.getPackage().getImplementationVersion());
			setContentPane(player);
		} catch (final JSONException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(Applet.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
