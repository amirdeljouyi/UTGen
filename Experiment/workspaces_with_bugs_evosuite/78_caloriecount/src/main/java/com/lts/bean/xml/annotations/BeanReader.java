package com.lts.bean.xml.annotations;


/**
 * Specify a class to deserialize the bean from XML.
 * 
 * <P>
 * The class must implement {@link PropertyReader}.  See that class for details on
 * what a PropertyReader needs to do.
 * </P>
 * 
 * @author cnh
 * @see PropertyReader
 */
public @interface BeanReader {
	public String value();
}
