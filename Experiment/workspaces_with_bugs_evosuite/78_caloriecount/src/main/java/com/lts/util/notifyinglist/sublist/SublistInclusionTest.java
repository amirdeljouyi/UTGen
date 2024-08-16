package com.lts.util.notifyinglist.sublist;

/**
 * Should an element be included in the client interface list?
 * <P>
 * This interface was designed primarily for {@link SublistProxy}, to provide a
 * pluggable way for that class to decide if a particular element should be included in
 * the sublist or not.
 * </P>
 * 
 * @author cnh
 */
public interface SublistInclusionTest<E>
{
	public boolean include(E o);
}
