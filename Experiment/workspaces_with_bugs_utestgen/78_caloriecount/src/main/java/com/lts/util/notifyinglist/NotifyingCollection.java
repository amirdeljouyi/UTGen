package com.lts.util.notifyinglist;

import java.util.Collection;

import com.lts.util.collection.CollectionListener;

public interface NotifyingCollection<E> extends Collection<E>
{
	public void addListener (CollectionListener listener);
	public boolean removeListener (CollectionListener listener);
}
