package de.outstare.fortbattleplayer.gui.battlefield;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * A {@link CellContent} can be placed inside a {@link BattlefieldCell}. This
 * interface is for proper location on the {@link BattlefieldDrawing}. The
 * drawing is done as a {@link JComponent} (swing).
 * 
 * @author daniel
 */
abstract class CellContent extends JComponent {
	private static final long serialVersionUID = -7930661054007090962L;
	private BattlefieldCell cell = null;

	/**
	 * initializer
	 */
	CellContent() {
		setToolTipText("has to be non empty, but the tooltip of the cell is used");
	}

	/**
	 * @return the parent where this content is located in. <code>null</code> if
	 *         this content was not added to a {@link BattlefieldCell}.
	 */
	BattlefieldCell getCurrentCell() {
		return cell;
	}

	/**
	 * @param newCell
	 */
	void moveToCell(final BattlefieldCell newCell) {
		if (cell != null) {
			cell.remove(this);
		}
		cell = newCell;
		newCell.addContent(this);
	}

	/**
	 * @return a description of this content for the user
	 */
	public abstract String getDescription();

	/**
	 * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
	 */
	@Override
	public String getToolTipText(final MouseEvent event) {
		// use tooltip of container
		if (cell == null) {
			return null;
		}
		return cell.getToolTipText(event);
	}
}
