package com.lts.caloriecount.ui.gatherwin;

import com.lts.caloriecount.ui.gatherwin.old.GatherListPanel;

/**
 * A window that contains a GatherListPanel.
 * 
 * <P>
 * This class allows instances of {@link GatherListPanel} to signal when a user is
 * done with the list by calling {@link #doneWithList()}.  See GatherListPanel for
 * details regarding what the container can expect, but generally, if the panel is 
 * functioning in select mode, the done method signals that either the user has 
 * selected an entry, or that the user has opted to cancel/skip/quit/etc.
 * </P>
 * 
 * @author cnh
 *
 */
public interface GatherListContainer
{
	public void doneWithList();
}
