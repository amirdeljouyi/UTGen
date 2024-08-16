package com.lts;

/**
 * A class that provides instance equivalents of static enum methods.
 * <P>
 * This class provides access to the static methods/constants that are normally defined by
 * Java when you create an enum type; specifically values and valueOf.
 * </P>
 * <P>
 * This class would be completely unnecessary if the people who had implemented enums had
 * supplied a similar facility. The need seems obvious enough that I feel like there is
 * some class, somewhere that already provides access to these methods. So far, I haven't
 * found it.
 * </P>
 * <P>
 * This means that this class could disappear at any time if and when I find said
 * mechanism. In the meantime, however, here it is...
 * </P>
 */
abstract public class EnumWrapper<E extends Enum<E>>
{
	/**
	 * return the result of calling the underlying Enum's values method.
	 * 
	 * <P>
	 * Note that the return type of this method is Enum[], not E.  This because 
	 * of the weird manner in which enums are implemented under the current JVM.  What this 
	 * means is that yes, you have to cast to the actual enum type.
	 * </P>
	 * 
	 * @return All the elements of the enum.
	 */
	abstract public E[] values();

	/**
	 * (From the Java language specification) Returns the enum constant of this type with the specified name. The string must
	 * match exactly an identifier used to declare an enum constant in this type.
	 * (Extraneous whitespace characters are not permitted.)
	 * 
	 * @return the enum constant with the specified name
	 * @throws IllegalArgumentException
	 *         if this enum type has no constant with the specified name
	 */
	abstract public E valueOf(String s);
}
