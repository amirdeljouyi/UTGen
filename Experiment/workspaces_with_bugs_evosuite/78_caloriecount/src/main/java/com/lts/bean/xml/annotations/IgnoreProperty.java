package com.lts.bean.xml.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Ignore the associated bean property.
 * <P>
 * This annotation should be used with an accessor declaration. Using it denotes
 * that the associated property should not be serialized or deserialized. 
 * </P>
 * 
 * 
 * @author cnh
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreProperty {
}
