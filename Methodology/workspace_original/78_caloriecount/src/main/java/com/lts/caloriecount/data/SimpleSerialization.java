package com.lts.caloriecount.data;

import com.lts.xml.simple.SimpleElement;

public interface SimpleSerialization
{
	public void serializeTo (SimpleElement elem);
	public SimpleElement createSerializationElement();
	public void deserializeFrom (SimpleElement elem);
}
