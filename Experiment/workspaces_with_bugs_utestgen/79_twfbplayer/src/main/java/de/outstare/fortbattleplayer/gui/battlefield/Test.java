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

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.outstare.fortbattleplayer.model.Battlefield;
import de.outstare.fortbattleplayer.model.CharacterClass;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantSide;
import de.outstare.fortbattleplayer.model.CombatantState;
import de.outstare.fortbattleplayer.model.Weapon;
import de.outstare.fortbattleplayer.model.impl.SimpleBattleField;
import de.outstare.fortbattleplayer.model.impl.SimpleCombatant;
import de.outstare.fortbattleplayer.model.impl.SimpleWeapon;
import de.outstare.fortbattleplayer.player.PlayerConfiguration;

/**
 * 
 * @author daniel
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final JFrame window = new JFrame("graphics test");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Battlefield bf = new SimpleBattleField(40, 30);
		final Weapon weapon = new SimpleWeapon(1, "Hand", 1, 1000);
		final CombatantState state = new CombatantState(bf._getArea(3, 5), 444, bf._getArea(13, 15), false);
		final Combatant attacker = new SimpleCombatant(CombatantSide.ATTACKER, state, 999, "Tusers",
		        CharacterClass.ADVENTURER, weapon, null);

		final BattlefieldDrawing battlefield = new BattlefieldDrawing(bf, null, new PlayerConfiguration(), null);
		battlefield.add(new PlayerDrawing(attacker, battlefield));
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(battlefield, BorderLayout.CENTER);
		window.setContentPane(panel);
		window.setSize(400, 300);
		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					window.setVisible(true);
				}
			});
		} catch (final InterruptedException e1) {
			e1.printStackTrace();
			return;
		} catch (final InvocationTargetException e1) {
			e1.printStackTrace();
			return;
		}

		try {
			Thread.sleep(2000);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		attacker.move(bf._getArea(29, 19));
		System.out.println("moved attacker");
		try {
			Thread.sleep(3000);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		final CombatantState state2 = new CombatantState(bf._getArea(10, 10), 300, bf._getArea(0, 0), true);
		final SimpleCombatant defender = new SimpleCombatant(CombatantSide.DEFENDER, state2, 300, "number2",
		        CharacterClass.GREENHORN, weapon, "TestTown");
		battlefield.add(new PlayerDrawing(defender, battlefield));
		battlefield.validate();
		System.out.println("added defender");
		try {
			Thread.sleep(3000);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		defender.move(bf._getArea(2, 2));
		System.out.println("moved defender");
		try {
			Thread.sleep(3000);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		defender.hit(888);
		System.out.println("defender died");
	}
}
