package com.lts.bean.xml.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specify the serializer to use when reading or writing a particular property.
 * 
 * <P>
 * This annotation should only be used on the getter method of the corresponding
 * property.
 * </P>
 * 
 * @author cnh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertySerializer {
	public Class value() default PropertySerializer.class;
	public String className() default "default";
}
