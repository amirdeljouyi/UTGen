package de.outstare.fortbattleplayer.model;

/**
 * A SectorObserver looks at a sector and gets notified if something happened.
 * 
 * @author daniel
 */
public interface SectorObserver {
	/**
	 * The occupying {@link CombatantSide} of the sector changed.
	 */
	void occupierChanged();
}
