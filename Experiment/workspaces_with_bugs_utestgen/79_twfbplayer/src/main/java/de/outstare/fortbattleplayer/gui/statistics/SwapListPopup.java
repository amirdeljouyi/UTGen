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
class SwapListPopup extends ListPopup {
	/**
	 * @param buttonTitle
	 * @param attackModel
	 * @param defferModel
	 * @param parent
	 */
	SwapListPopup(final String buttonTitle, final TableModel attackModel, final TableModel defferModel,
	        final Container parent) {
		super(buttonTitle, attackModel, defferModel, parent);
	}

	/**
	 * @param tableColumnModel
	 */
	@Override
	protected void setColumnWidths(final TableColumnModel cols) {
		int i = 0;
		// row number
		TableColumn col = cols.getColumn(i++);
		col.setMinWidth(10);
		col.setMaxWidth(200);
		col.setPreferredWidth(50);

		// round
		col = cols.getColumn(i++);
		col.setMinWidth(10);
		col.setMaxWidth(200);
		col.setPreferredWidth(50);

		// player1
		col = cols.getColumn(i++);
		col.setMinWidth(30);
		col.setMaxWidth(500);
		col.setPreferredWidth(150);

		// health1
		col = cols.getColumn(i++);
		col.setMinWidth(20);
		col.setMaxWidth(150);
		col.setPreferredWidth(100);

		// player2
		col = cols.getColumn(i++);
		col.setMinWidth(30);
		col.setMaxWidth(500);
		col.setPreferredWidth(150);

		// health2
		col = cols.getColumn(i++);
		col.setMinWidth(20);
		col.setMaxWidth(150);
		col.setPreferredWidth(100);
	}
}