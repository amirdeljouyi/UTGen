package com.lts.application.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lts.application.ApplicationException;
import com.lts.caloriecount.data.CalorieCountData;
import com.lts.caloriecount.data.SimpleSerialization;
import com.lts.datalist.DataList;
import com.lts.xml.simple.SimpleElement;


abstract public class IdApplicationDataList<E extends IdApplicationDataElement> 
	extends DataList<E>
	implements SimpleSerialization
{
	private static final long serialVersionUID = 1L;
	
	abstract protected String getSerializationElementName();
	abstract protected E createListElement(String name);
	
	transient protected int myNextId;
	/**
	 * A map from meal ID to the value that can be used with methods like {@link #get(int)}.
	 * That is, the ID to the position in the list.
	 */
	protected transient Map<Integer, Integer> myIdToListPosition;
	
	protected transient Map<Integer, E> myIdToElement;
	
	public E idToElement(int id)
	{
		return myIdToElement.get(id);
	}
	
	

	
	@Override
	public void postDeserialize() throws ApplicationException
	{
		super.postDeserialize();
		renumber();
		rebuildIdToListPosition();
		rebuildIdToElement();
	}
	
	public IdApplicationDataList()
	{
		initialize();
	}
	
	
	protected void initialize()
	{
		myIdToListPosition = new HashMap<Integer, Integer>();
		myIdToElement = new HashMap<Integer, E>();
		myNextId = 0;
	}
	
	protected void initialize(List<E> list)
	{
		super.initialize(list);
		rebuildIdToElement();
		rebuildIdToListPosition();
	}
	
	
	/**
	 * Rebuild transient data members.
	 * 
	 * <P>
	 * This method should be called prior to using an instance of the class after
	 * deserializing.  The only reason it is not in the {@link #postDeserialize()}
	 * method is that it avoids introducing a direct dependency between this class
	 * and {@link CalorieCountData}. 
	 * </P>
	 * 
	 * @param list The list of foods to use when rebuilding.
	 */
	protected void rebuildIdToListPosition()
	{
		myIdToListPosition = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < myList.size(); i++)
		{
			IdApplicationDataElement element = myList.get(i);
			myIdToListPosition.put(element.getId(), i);
		}
	}
	
	
	protected void rebuildIdToElement()
	{
		myIdToElement = new HashMap<Integer, E>();
		for (E element : this)
		{
			myIdToElement.put(element.getId(), element);
		}
	}
	

	protected void renumber() 
	{
		myNextId = 0;
		for (IdApplicationDataElement element : myList)
		{
			if (null != element)
			{
				element.setId(myNextId);
				myNextId++;
			}
		}
	}


	@Override
	public void deserializeFrom(SimpleElement elem)
	{
		myIdToListPosition = new HashMap<Integer, Integer>();
		List<E> list = new ArrayList<E>();
		
		for (SimpleElement child : elem.getChildren())
		{
			E childElement = deserializeChildElement(child);
			if (null != childElement)
				list.add(childElement);
		}
		
		replaceWith(list);
	}

	protected E deserializeChildElement(SimpleElement elem)
	{
		String name = elem.getName();
		E child = createListElement(name);
		
		if (null != child)
			child.deserializeFrom(elem);
		
		return child;
	}


	@Override
	public void serializeTo(SimpleElement elem)
	{
		for (IdApplicationDataElement element : this)
		{
			SimpleElement child = element.createSerializationElement();
			elem.addChild(child);
		}
	}
	
	public void add(int index, E element)
	{
		element.setId(myNextId);
		myNextId++;
		
		int theSize = size();
		myIdToElement.put(element.getId(), element);

		if (index == theSize)
		{
			myIdToListPosition.put(element.getId(), index);
		}
		else
		{
			rebuildIdToListPosition();
		}
		
		super.add(index, element);
	}
	
	public boolean add(E element)
	{
		add(size(), element);
		return true;
	}

	public E remove (int index)
	{
		E element = super.remove(index);
		
		if (null != element)
		{
			myIdToListPosition.remove(element.getId());
			myIdToElement.remove(element.getId());
		}
		
		return element;
	}

	public boolean remove (Object o)
	{
		E element = null;
		
		if (super.remove(o))
		{
			element = (E) o;
			myIdToListPosition.remove(element.getId());
			myIdToElement.remove(element.getId());
		}
		
		return null != element;
	}

	@Override
	public E set(int index, E element)
	{
		E old = super.set(index, element);
		if (null != old)
		{
			myIdToListPosition.remove(old.getId());
			myIdToElement.remove(old.getId());
		}
		
		myIdToListPosition.put(element.getId(), index);
		myIdToElement.put(element.getId(), element);
		return old;
	}

	@Override
	public SimpleElement createSerializationElement()
	{
		SimpleElement root = new SimpleElement(getSerializationElementName());
		serializeTo(root);
		return root;
	}

	protected void baiscReplaceWith(List<E> list)
	{
		super.basicReplaceWith(list);
		rebuildIdToListPosition();
	}	
}
