package com.lts.application.data.coll;

import com.lts.application.data.ApplicationDataElement;

public interface ApplicationDataList extends ApplicationDataElement
{
	public void insert(int index, ApplicationDataElement data);
	public void append(ApplicationDataElement data);
	public ApplicationDataElement get(int index);
	public void update (int index, ApplicationDataElement newValue);
	public void delete (int index);
	
	public int getCount();
	
	public void addListener (ApplicationDataListListener listener);
	public void removeListener (ApplicationDataListListener listener);
}
