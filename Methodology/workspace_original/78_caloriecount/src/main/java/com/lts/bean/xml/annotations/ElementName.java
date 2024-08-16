package com.lts.bean.xml.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specify the element name to use when serializing this property.
 * <P>
 * This annotation should only be applied to accessor methods for properties that subclass
 * Collection.
 * </P>
 * <P>
 * Normally, the serializer will use the name "element" for the elements of a collection.
 * If this property is present, then the serializer will use the specified name for each
 * element instead.
 * </P>
 * 
 * @author cnh
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementName {
	public String value();
}
