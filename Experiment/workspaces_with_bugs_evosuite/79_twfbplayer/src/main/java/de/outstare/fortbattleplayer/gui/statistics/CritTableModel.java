package de.outstare.fortbattleplayer.gui.statistics;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.outstare.fortbattleplayer.gui.Messages;
import de.outstare.fortbattleplayer.statistics.CriticalHit;

/**
 * Model of all critical hits of a team for a tabular view.
 * 
 * @author daniel
 */
class CritTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 763241650174071557L;
	private final List<CriticalHit> crits;

	/**
	 * @param crits
	 */
	CritTableModel(final List<CriticalHit> crits) {
		super();
		this.crits = crits;
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return crits.size();
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int column) {
		final String name;
		switch (column) {
		case 0:
			name = Messages.getString("StatisticsPanel.Crits.Round");
			break;
		case 1:
			name = Messages.getString("StatisticsPanel.Crits.Player");
			break;
		case 2:
			name = Messages.getString("StatisticsPanel.Crits.Damage");
			break;
		case 3:
			name = Messages.getString("StatisticsPanel.Crits.Victim");
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
		return 4;
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		final Class<?> result;
		switch (columnIndex) {
		case 0:
		case 2:
			result = Integer.class;
			break;
		case 1:
		case 3:
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
		final CriticalHit crit = crits.get(rowIndex);
		final Object result;
		switch (columnIndex) {
		case 0:
			result = Integer.valueOf(crit.round);
			break;
		case 1:
			result = crit.playerName;
			break;
		case 2:
			result = Integer.valueOf(crit.damage);
			break;
		case 3:
			result = crit.victimName;
			break;
		default:
			result = null;
		}
		return result;
	}

}