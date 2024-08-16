package de.outstare.fortbattleplayer.gui.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.table.AbstractTableModel;

import de.outstare.fortbattleplayer.gui.Messages;
import de.outstare.fortbattleplayer.statistics.PositionSwitch;

/**
 * Model of all critical hits of a team for a tabular view.
 * 
 * @author daniel
 */
class SwapTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 763241650174071557L;
	private final List<PositionSwitch> swaps = new ArrayList<PositionSwitch>();

	/**
	 * @param swaps
	 */
	SwapTableModel(final Collection<PositionSwitch> swaps) {
		super();
		final SortedSet<PositionSwitch> sorted = new TreeSet<PositionSwitch>();
		sorted.addAll(swaps);
		this.swaps.addAll(sorted);
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return swaps.size();
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int column) {
		final String name;
		switch (column) {
		case 0:
			name = "";
			break;
		case 1:
			name = Messages.getString("StatisticsPanel.Crits.Round");
			break;
		case 2:
		case 4:
			name = Messages.getString("StatisticsPanel.Swaps.Player");
			break;
		case 3:
		case 5:
			name = Messages.getString("StatisticsPanel.Swaps.HP");
			break;
		default:
			name = super.getColumnName(column);
		}
		return name;
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 6;
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		final Class<?> result;
		switch (columnIndex) {
		case 0:
		case 1:
		case 3:
		case 5:
			result = Integer.class;
			break;
		case 2:
		case 4:
			result = String.class;
			break;
		default:
			result = super.getColumnClass(columnIndex);
		}
		return result;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final PositionSwitch swap = swaps.get(rowIndex);
		final Object result;
		switch (columnIndex) {
		case 0:
			result = Integer.valueOf(rowIndex + 1);
			break;
		case 1:
			result = Integer.valueOf(swap.round);
			break;
		case 2:
			result = swap.player1.getName();
			break;
		case 3:
			result = swap.health1;
			break;
		case 4:
			result = swap.player2.getName();
			break;
		case 5:
			result = swap.health2;
			break;
		default:
			result = null;
		}
		return result;
	}

}