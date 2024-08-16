package com.lts.bean.xml.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specify common collection properties.
 * 
 * <P>
 * This annotation should only be applied to a getter method for a bean property.
 * </P>
 * 
 * <P>
 * The annotation has three properties, all of which are optional.  It does not make
 * a whole lot of sense to use this annotation without specifying any properties, but
 * it is allowed.
 * </P>
 * 
 * <P>
 * The properties for this annotation are:
 * <UL>
 * <LI>collectionClass - which subclass of Collection to use.</LI>
 * <LI>elementName - the name to use for child elements.</LI>
 * <LI>elementClass - the class to use for child elements.</LI>
 * </UL>
 * </P>
 * 
 * @author cnh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CollectionProperty {
	public String elementClass();
	public String collectionClass() default "java.util.ArrayList";
	public String elementName() default "element";
	public String collectionReader() 
		default "com.lts.bean.xml.serializer.reader.CollectionReader";
}
