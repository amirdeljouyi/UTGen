package de.outstare.fortbattleplayer.gui.battlefield;

import java.util.Set;

import de.outstare.fortbattleplayer.model.Combatant;
import de.outstare.fortbattleplayer.model.Weapon;

/**
 * A PlayerDrawingDB knows all {@link PlayerDrawing}s and can be searched
 * 
 * @author daniel
 */
public interface PlayerDrawingDB {
	/**
	 * @param drawing
	 *            to add to this database
	 */
	void addPlayer(PlayerDrawing drawing);

	/**
	 * @param namePart
	 * @return all contained {@link PlayerDrawing}s which represent a
	 *         {@link Combatant} whose name contains the given
	 *         <code>namePart</code>.
	 */
	Set<PlayerDrawing> findUsers(String namePart);

	/**
	 * @param weaponNamePart
	 * @return all contained {@link PlayerDrawing}s which represent a
	 *         {@link Combatant} whose {@link Weapon} name contains the given
	 *         <code>weaponNamePart</code>.
	 */
	Set<PlayerDrawing> findUsersWithWeapon(String weaponNamePart);
}
