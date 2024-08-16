package de.outstare.fortbattleplayer.gui.statistics;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.outstare.fortbattleplayer.gui.PopupWindow;

/**
 * A Popup that contains two tables (i.e. one for the attackers, one for the
 * defenders).
 * 
 * @author daniel
 */
public class ListPopup implements ActionListener {
	/**
	 * the data of the first table
	 */
	protected final TableModel _attackModel;
	/**
	 * the data of the second table
	 */
	protected final TableModel _defferModel;
	private final String _buttonTitle;
	private final Container _parent;

	/**
	 * @param buttonTitle
	 * @param attackModel
	 * @param defferModel
	 * @param parent
	 * 
	 */
	public ListPopup(final String buttonTitle, final TableModel attackModel, final TableModel defferModel,
	        final Container parent) {
		_buttonTitle = buttonTitle;
		_attackModel = attackModel;
		_defferModel = defferModel;
		_parent = parent;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		final JFrame dialog = new PopupWindow(_buttonTitle, _parent);
		final JTable attackerTable = createTable(_attackModel);
		final JTable defenderTable = createTable(_defferModel);
		final JComponent content = new JPanel(new GridLayout(1, 2));
		content.add(new JScrollPane(attackerTable));
		content.add(new JScrollPane(defenderTable));
		dialog.add(content);
		// dialog.pack();
		// dialog.setSize(content.getSize());
		final int maxRows = Math.max(attackerTable.getRowCount(), defenderTable.getRowCount());
		int headerHeight = 22;
		int rowHeight = 17;
		dialog.setSize(600, Math.min(headerHeight + rowHeight * maxRows, 800));
	}

	/**
	 * @param model
	 * @return
	 */
	private JTable createTable(final TableModel model) {
		final JTable table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		setColumnWidths(table.getColumnModel());
		return table;
	}

	/**
	 * sets the width for each column to fith its header
	 * 
	 * @see TableColumn#sizeWidthToFit()
	 * @param columnModel
	 */
	protected void setColumnWidths(final TableColumnModel columnModel) {
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			columnModel.getColumn(i).sizeWidthToFit();
		}
	}

}