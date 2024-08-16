package de.outstare.fortbattleplayer;

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

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.outstare.fortbattleplayer.gui.MainWindow;

/**
 * starting point
 * 
 * @author daniel
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		initLogging();
		// Locale.setDefault(Locale.GERMAN);
		// final LookAndFeelInfo[] lfs = UIManager.getInstalledLookAndFeels();
		// for (final LookAndFeelInfo info : lfs) {
		// System.out.println(info.getName());
		// }
		setNativeLookAndFeel();
		// setCrossPlatformLookAndFeel();
		// set text color for JProgressBars (before any ProgressBar is created!)
		UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
		UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
		new MainWindow(Main.class.getPackage().getImplementationVersion());
	}

	private static void initLogging() {
		try {
			final String rootPackageName = Main.class.getPackage().getName();
			final LogManager logManager = LogManager.getLogManager();
			final String rootLoggerLevel = logManager.getProperty(rootPackageName + ".level");
			if (rootLoggerLevel == null) {
				// if not configured reduce log level to WARNING
				Logger.getLogger(rootPackageName).setLevel(Level.WARNING);
			}
		} catch (final SecurityException e) {
			e.printStackTrace();
		}
	}

	private static void setNativeLookAndFeel() {
		final String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		setLookAndFeel(lookAndFeel);
	}

	// private static void setCrossPlatformLookAndFeel() {
	// final String lookAndFeel =
	// UIManager.getCrossPlatformLookAndFeelClassName();
	// setLookAndFeel(lookAndFeel);
	// }

	/**
	 * @param lookAndFeel
	 */
	private static void setLookAndFeel(final String lookAndFeel) {
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
