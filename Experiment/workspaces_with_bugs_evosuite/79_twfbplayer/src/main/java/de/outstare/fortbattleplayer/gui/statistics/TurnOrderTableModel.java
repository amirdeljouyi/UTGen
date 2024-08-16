package de.outstare.fortbattleplayer.gui.statistics;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.outstare.fortbattleplayer.gui.Messages;
import de.outstare.fortbattleplayer.model.Combatant;

/**
 * 
 * @author daniel
 */
class TurnOrderTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 2671172602778557827L;
	private final List<Combatant> turnOrder;

	/**
	 * @param order
	 */
	TurnOrderTableModel(final List<Combatant> order) {
		turnOrder = order;
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return turnOrder.size();
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 2;
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int column) {
		String name;
		switch (column) {
		case 0:
			name = "#";
			break;
		case 1:
			name = Messages.getString("StatisticsPanel.Swaps.Player");
			break;
		default:
			name = "";
		}
		return name;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.valueOf(rowIndex + 1);
		case 1:
			return turnOrder.get(rowIndex).getName();
		default:
			throw new IllegalArgumentException("invalid column index: " + columnIndex);
		}
	}

}
