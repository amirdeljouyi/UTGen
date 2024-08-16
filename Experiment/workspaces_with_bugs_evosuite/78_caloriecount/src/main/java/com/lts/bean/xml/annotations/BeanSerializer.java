package com.lts.bean.xml.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specify the class to use when reading or writing the bean.
 * 
 * <P>
 * This annotation specifies that the serializer should use a specific serializaiton
 * class instead of the default serializer.  This can be indicated by passing a 
 * class object for the serializer class, or by using the className property to 
 * pass the name of the class instead.
 * </P>
 * 
 * <P>
 * In either case, the class should implement BeanSerializer.
 * </P>
 * 
 * <P>
 * This class should be used only on class declarations.
 * </P>
 * @author cnh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanSerializer {
	public Class value() default BeanSerializer.class;
	public String className() default "default";
}
