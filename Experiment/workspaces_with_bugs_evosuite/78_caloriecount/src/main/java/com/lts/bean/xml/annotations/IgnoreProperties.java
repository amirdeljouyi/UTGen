package com.lts.bean.xml.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Ignore the specified properties.
 * 
 * <P>
 * This annotation should be used on class declarations.  The value(s) of the annotation
 * denote the names of properties that should be ignored when reading or writing the 
 * bean to XML.
 * </P>
 * 
 * @author cnh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreProperties {
	public String[] value();
}
