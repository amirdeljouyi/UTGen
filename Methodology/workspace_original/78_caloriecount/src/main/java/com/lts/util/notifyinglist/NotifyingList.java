package com.lts.util.notifyinglist;

import java.util.List;

public interface NotifyingList<E> extends List<E>
{
	public void addListener(NotifyingListListener listener);
	public boolean removeListener(NotifyingListListener listener);
}
