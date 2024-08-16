package com.lts.bean.xml.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specify the class to use when reading the bean.
 * 
 * <P>
 * This annotation causes the bean serializer to instantiate the specified class when 
 * reading the associated property.  An instance of the specified class is created
 * via the default constructor and then populated with the values defined by the 
 * XML for this property.
 * </P>
 * 
 * <P>
 * This annotation should only be used with getter methods.
 * </P>
 * 
 * @author cnh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyClass {
	public String value();
}
