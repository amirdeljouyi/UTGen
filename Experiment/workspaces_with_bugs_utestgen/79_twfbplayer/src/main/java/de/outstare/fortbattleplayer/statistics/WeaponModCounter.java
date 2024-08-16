package de.outstare.fortbattleplayer.statistics;

import de.outstare.fortbattleplayer.model.Weapon;
import de.outstare.fortbattleplayer.model.WeaponData;
import de.outstare.fortbattleplayer.model.impl.JSWeaponData;

abstract class WeaponModCounter {
	static WeaponData data = new JSWeaponData();

	abstract boolean counts(Weapon w);

	static class BayonetCounter extends WeaponModCounter {
		/**
		 * @see de.outstare.fortbattleplayer.statistics.WeaponModCounter#counts(de.outstare.fortbattleplayer.model.Weapon)
		 */
		@Override
		boolean counts(final Weapon w) {
			return data.hasBayonet(w);
		}
	}

	static class LoadingchamberOrEnhancedPatronsCounter extends WeaponModCounter {
		/**
		 * @see de.outstare.fortbattleplayer.statistics.WeaponModCounter#counts(de.outstare.fortbattleplayer.model.Weapon)
		 */
		@Override
		boolean counts(final Weapon w) {
			return data.hasLoadingchamberOrEnhancedPatrons(w);
		}
	}

	static class GraphitLubricantCounter extends WeaponModCounter {
		/**
		 * @see de.outstare.fortbattleplayer.statistics.WeaponModCounter#counts(de.outstare.fortbattleplayer.model.Weapon)
		 */
		@Override
		boolean counts(final Weapon w) {
			return data.hasGraphitLubricant(w);
		}
	}

	static class FettesOilCounter extends WeaponModCounter {
		/**
		 * @see de.outstare.fortbattleplayer.statistics.WeaponModCounter#counts(de.outstare.fortbattleplayer.model.Weapon)
		 */
		@Override
		boolean counts(final Weapon w) {
			return data.hasFettesWeaponOil(w);
		}
	}

	static class SchmierOilCounter extends WeaponModCounter {
		/**
		 * @see de.outstare.fortbattleplayer.statistics.WeaponModCounter#counts(de.outstare.fortbattleplayer.model.Weapon)
		 */
		@Override
		boolean counts(final Weapon w) {
			return data.hasSchmierendesWeaponOil(w);
		}
	}

	static class ShinyOilCounter extends WeaponModCounter {
		/**
		 * @see de.outstare.fortbattleplayer.statistics.WeaponModCounter#counts(de.outstare.fortbattleplayer.model.Weapon)
		 */
		@Override
		boolean counts(final Weapon w) {
			return data.hasShinyWeaponOil(w);
		}
	}

	static class HipFlaskCounter extends WeaponModCounter {
		/**
		 * @see de.outstare.fortbattleplayer.statistics.WeaponModCounter#counts(de.outstare.fortbattleplayer.model.Weapon)
		 */
		@Override
		boolean counts(final Weapon w) {
			return data.hasHipFlask(w);
		}
	}
}