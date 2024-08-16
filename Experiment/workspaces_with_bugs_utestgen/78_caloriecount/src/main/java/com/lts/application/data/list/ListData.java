package com.lts.application.data.list;

import com.lts.application.data.ApplicationDataElement;
import com.lts.util.notifyinglist.NotifyingList;

/**
 * A list of ApplicationDataElement objects.
 * 
 * <P>
 * This interface requires the myData element to have basic list-like characteristics:
 * it must have a sequence, elements can be inserted or deleted from specified 
 * locations in the sequence, etc.
 * </P>
 * 
 * @author cnh
 *
 */
public interface ListData<E> extends ApplicationDataElement, NotifyingList<E>
{
}
