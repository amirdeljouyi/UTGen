package de.outstare.fortbattleplayer.gui.battlefield;

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

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import javax.swing.JComponent;

/**
 * A MoveAnimation changes the position of a given JComponent from the current
 * location to the given point in a given time interval. The component will be
 * moved along a direct line between the two points.
 * 
 * @author daniel
 */
class MoveAnimation {
	private static final Logger LOG = Logger.getLogger(MoveAnimation.class.getName());
	private static final int FPS = 25;
	private static final Timer timer = new Timer();

	/**
	 * @param component
	 * @param destination
	 * @param timeOfAnimation
	 *            in ms
	 */
	MoveAnimation(final JComponent component, final Point destination, final int timeOfAnimation) {
		if (component != null && destination != null && timeOfAnimation > 0) {
			scheduleMoves(component, timeOfAnimation, destination);
		}
	}

	/**
	 * @param component
	 * @param distancePerFrame
	 * @param timeOfAnimation
	 * @param destination
	 */
	private void scheduleMoves(final JComponent component, final int timeOfAnimation, final Point destination) {
		final Distance distancePerFrame = calculateDistance(component.getLocation(), destination, timeOfAnimation);
		final MoveTask task = new MoveTask(component, distancePerFrame.x, distancePerFrame.y);
		final Semaphore hasRun = new Semaphore(0);
		final TimerEndTask endTask = new TimerEndTask(component, task, destination, hasRun);
		try {
			timer.schedule(task, 0, 1000 / FPS);
			timer.schedule(endTask, timeOfAnimation);
		} catch (final IllegalStateException e) {
			// if the timer cannot be used, execute immediatly
			LOG.warning("cannot schedule move: " + e.toString());
			task.cancel();
			endTask.run();
		}
		// block during animation
		try {
			hasRun.acquire();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param location
	 * @param destination
	 * @param timeOfAnimation
	 * @return
	 */
	private Distance calculateDistance(final Point location, final Point destination, final int timeOfAnimation) {
		final int distance_x = destination.x - location.x;
		final int distance_y = destination.y - location.y;
		final double timePerFrame = 1000.0 / FPS;
		final double frames = timeOfAnimation / timePerFrame;
		final double perFrame_x = distance_x / frames;
		final double perFrame_y = distance_y / frames;
		return new Distance(perFrame_x, perFrame_y);

	}

	/**
	 * 
	 * @author daniel
	 */
	private static class TimerEndTask extends TimerTask {
		private final JComponent _component;
		private final TimerTask _task;
		private final Point _destination;
		private final Semaphore _hasRun;

		/**
		 * @param component
		 *            the animated component
		 * @param task
		 *            the movement timer which will be stopped
		 * @param destination
		 *            the final destination after the animation
		 * @param hasRun
		 *            the MoveAnimation blocks until this task is done
		 */
		TimerEndTask(final JComponent component, final TimerTask task, final Point destination, final Semaphore hasRun) {
			_component = component;
			_task = task;
			_destination = destination;
			_hasRun = hasRun;
		}

		@Override
		public void run() {
			_task.cancel();
			// ensure the correct end position
			_component.setLocation(_destination);
			_hasRun.release();
		}
	}

	private static class Distance {
		final double x;
		final double y;

		/**
		 * @param x
		 * @param y
		 */
		public Distance(final double x, final double y) {
			this.x = x;
			this.y = y;
		}
	}
}
