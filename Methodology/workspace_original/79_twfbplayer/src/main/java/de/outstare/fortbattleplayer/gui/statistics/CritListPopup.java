package de.outstare.fortbattleplayer.gui.statistics;

import java.awt.Container;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * Opens a popup which shows all critical hits
 * 
 * @author daniel
 */
class CritListPopup extends ListPopup {
	/**
	 * @param buttonTitle
	 * @param attackModel
	 * @param defferModel
	 * @param parent
	 */
	CritListPopup(final String buttonTitle, final TableModel attackModel, final TableModel defferModel,
	        final Container parent) {
		super(buttonTitle, attackModel, defferModel, parent);
	}

	/**
	 * @param tableColumnModel
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

		// damage
		col = cols.getColumn(2);
		col.setMinWidth(20);
		col.setMaxWidth(150);
		col.setPreferredWidth(100);

		// victim
		col = cols.getColumn(3);
		col.setMinWidth(30);
		col.setMaxWidth(500);
		col.setPreferredWidth(150);
	}
}