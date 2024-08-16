package de.outstare.fortbattleplayer.gui.battlefield;

import de.outstare.fortbattleplayer.gui.Messages;
import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.CombatantSide;

/**
 * A position where a {@link Combatant} wants to go to.
 * 
 * @author daniel
 */
public class MoveTarget extends CellContent {
	private static final long serialVersionUID = -5338905292475534326L;
	/**
	 * the player who wants to move here
	 */
	private final Combatant player;

	/**
	 * @param combatant
	 */
	MoveTarget(final Combatant combatant) {
		player = combatant;
		setVisible(false);
	}

	/**
	 * @see de.outstare.fortbattleplayer.gui.battlefield.CellContent#getDescription()
	 */
	@Override
	public String getDescription() {
		final String localizedTarget = Messages.getString("Battlefield.moveTarget");
		String localizedSide;
		String color;
		if (player.getSide() == CombatantSide.ATTACKER) {
			localizedSide = Messages.getString("General.attacker");
			color = "red";
		} else {
			localizedSide = Messages.getString("General.defender");
			color = "blue";
		}
		return "<font color=\"" + color + "\">" + localizedTarget + " " + player.getName() + " (" + localizedSide + ")"
		        + "</font>";
	}

}
