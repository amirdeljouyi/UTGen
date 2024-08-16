package de.outstare.fortbattleplayer.parser;

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

import java.util.Map;
import java.util.logging.Logger;

import de.outstare.fortbattleplayer.model.Area;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.player.Action;
import de.outstare.fortbattleplayer.player.Round;
import de.outstare.fortbattleplayer.player.actions.AimAction;
import de.outstare.fortbattleplayer.player.actions.HitAction;
import de.outstare.fortbattleplayer.player.actions.MoveAction;
import de.outstare.fortbattleplayer.player.actions.OnlineAction;
import de.outstare.fortbattleplayer.player.actions.TargetAction;

/**
 * <p>
 * type of logEntry and mapped action. The names must equal the ones in
 * "logtypes" of the JSON data
 * </p>
 * TODO should be package protected (default visibilty) to abstract from JS-data
 * 
 * @author daniel
 */
public enum LogType {
	/**
	 * The {@link Round} number <code>value</code> begins
	 */
	ROUNDSTART {
		@Override
		public Action parse(final int value, final Combatant player, final Map<Integer, Area> areas,
		        final Map<Integer, Combatant> players) {
			return null;
		}
	},
	/**
	 * The character with west id <code>value</code> is now active
	 */
	CHARTURN {
		@Override
		public Action parse(final int value, final Combatant player, final Map<Integer, Area> areas,
		        final Map<Integer, Combatant> players) {
			return null;
		}
	},
	/**
	 * The current character is trying to go to the cell <code>value</code>
	 */
	CHARTARGET {
		@Override
		public Action parse(final int value, final Combatant player, final Map<Integer, Area> areas,
		        final Map<Integer, Combatant> players) {
			final Area target = areas.get(Integer.valueOf(value));
			return new TargetAction(player, target);
		}
	},
	/**
	 * The current character has a health of <code>value</code> hitpoints
	 */
	CHARHEALTH {
		@Override
		public Action parse(final int value, final Combatant player, final Map<Integer, Area> areas,
		        final Map<Integer, Combatant> players) {
			return null;
		}
	},
	/**
	 * The player for current character was online (1) during this round or not
	 * online (0)
	 */
	CHARONLINE {
		@Override
		public Action parse(final int value, final Combatant player, final Map<Integer, Area> areas,
		        final Map<Integer, Combatant> players) {
			return new OnlineAction(player, value == 1);
		}
	},
	/**
	 * The current character shoots at the given other character.
	 */
	SHOOTAT {
		@Override
		public Action parse(final int value, final Combatant player, final Map<Integer, Area> areas,
		        final Map<Integer, Combatant> westPlayers) {
			final Combatant target = westPlayers.get(Integer.valueOf(value));
			if (target == null) {
				LOG.warning("player aimed at not existent: " + value);
				return null;
			}
			return new AimAction(player, target);
		}
	},
	/**
	 * The current character was killed due to a hit with <code>value</code>
	 * damage.
	 */
	KILLED {
		@Override
		public Action parse(final int value, final Combatant player, final Map<Integer, Area> areas,
		        final Map<Integer, Combatant> players) {
			// same Action as HIT because Combatant knows when he is dead
			return HIT.parse(value, player, areas, players);
		}
	},
	/**
	 * The current character has received the given amount of damage.
	 */
	HIT {
		@Override
		public Action parse(final int value, final Combatant player, final Map<Integer, Area> areas,
		        final Map<Integer, Combatant> players) {
			return new HitAction(player, value);
		}
	},
	/**
	 * The current character moved to an area given as index
	 */
	MOVED {
		@Override
		public Action parse(final int value, final Combatant player, final Map<Integer, Area> areas,
		        final Map<Integer, Combatant> players) {
			return new MoveAction(player, areas.get(Integer.valueOf(value)));
		}
	};
	/**
	 * enable enum types to log messages
	 */
	protected static final transient Logger LOG = Logger.getLogger(LogType.class.getName());

	/**
	 * @param value
	 * @param player
	 *            which executes this action
	 * @param areas
	 *            mapping from west id to model object
	 * @param players
	 *            mapping from west id to model object
	 * @return a action for this {@link LogType} or null
	 */
	public abstract Action parse(final int value, Combatant player, Map<Integer, Area> areas,
	        Map<Integer, Combatant> players);
}