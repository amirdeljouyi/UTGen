package com.lts.bean.xml.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specify the class to use for the elements of a collection.
 * <P>
 * This annotation specifies which class to instantiate for elements of of a collection
 * property such as a List.
 * </P>
 * <P>
 * The annotation should only be used on getter methods that return a colleciton
 * of some kind.
 * </P>
 * 
 * @author cnh
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementClass {
	public String value();
}
