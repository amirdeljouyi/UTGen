package de.outstare.fortbattleplayer.model;

/**
 * The actual bonus for a given {@link Combatant} and a given {@link Sector}
 * 
 * @author daniel
 */
public class SectorBonus {
	/**
	 * the different types a {@link SectorBonus} consists of
	 * 
	 * @author daniel
	 */
	public enum BonusType {
		/**
		 * bonus when shooting
		 */
		ATTACK,
		/**
		 * bonus when dodging
		 */
		DEFENSE,
		/**
		 * bonus damage when hitting an enemy
		 */
		DAMAGE
	}

	/**
	 * bonus when shooting
	 */
	public final int attackBonus;
	/**
	 * bonus when dodging
	 */
	public final int defendBonus;
	/**
	 * bonus damage
	 */
	public final int additionalDamage;

	/**
	 * @param attackBonus
	 * @param defendBonus
	 * @param additionalDamage
	 */
	public SectorBonus(final int attackBonus, final int defendBonus, final int additionalDamage) {
		this.attackBonus = attackBonus;
		this.defendBonus = defendBonus;
		this.additionalDamage = additionalDamage;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Sectorbonus [attack=" + attackBonus + ", defense=" + defendBonus + "]";
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 13 * attackBonus + 17 * defendBonus;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SectorBonus)) {
			return false;
		}
		final SectorBonus other = (SectorBonus) obj;
		if (attackBonus != other.attackBonus || defendBonus != other.defendBonus) {
			return false;
		}
		return true;
	}

}
