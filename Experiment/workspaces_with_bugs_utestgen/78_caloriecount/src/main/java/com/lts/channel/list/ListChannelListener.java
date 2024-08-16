package com.lts.channel.list;

public interface ListChannelListener
{
	public void addElement (ListChannelEvent event, ListChannel list, int index);
	public void removeElement (ListChannelEvent event, ListChannel list, int index);
	public void moveElement (ListChannelEvent event, ListChannel list, int oldIndex, int newIndex);
	public void allChanged (ListChannelEvent event, ListChannel list);
}
