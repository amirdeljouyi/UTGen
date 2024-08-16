package de.outstare.fortbattleplayer.gui.statistics;

import java.awt.Container;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * 
 * @author daniel
 */
class TurnOrderPopup extends ListPopup {

	/**
	 * @param buttonTitle
	 * @param attackModel
	 * @param defferModel
	 * @param parent
	 */
	TurnOrderPopup(final String buttonTitle, final TableModel attackModel, final TableModel defferModel,
	        final Container parent) {
		super(buttonTitle, attackModel, defferModel, parent);
	}

	/**
	 * @see de.outstare.fortbattleplayer.gui.statistics.ListPopup#setColumnWidths(javax.swing.table.TableColumnModel)
	 */
	@Override
	protected void setColumnWidths(final TableColumnModel cols) {
		// round
		TableColumn col = cols.getColumn(0);
		col.setMinWidth(10);
		col.setMaxWidth(200);
		col.setPreferredWidth(50);

		// player
		col = cols.getColumn(1);
		col.setMinWidth(30);
		col.setMaxWidth(500);
		col.setPreferredWidth(150);

	}
}
