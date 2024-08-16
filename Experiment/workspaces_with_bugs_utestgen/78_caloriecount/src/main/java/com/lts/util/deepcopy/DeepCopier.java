package com.lts.util.deepcopy;

import java.util.Map;

/**
 * An object that can make a deep copy of itself.
 * 
 * <H3>Quick Start</H3>
 * <UL>
 * <LI><A href="#three_standard_methods">
 * copy the supplied code to implement the 3 standard methods.
 * </A>
 * 
 * <LI><A href="continue_deep_copy">
 * create the continueDeepCopy method
 * </A>
 * </UL>
 * 
 * <A name="three_standard_methods">
 * <H4>Three Standard Methods</H4>
 * </A>
 * Three of the methods required by this interface have fairly standard blocks 
 * of code.  It cannot be directly supplied because this is an interface, rather
 * than a superclass, but you can implement them by simply copying the following
 * code:
 * 
 * <P>
 * <CODE>
 * <PRE>
 * public DeepCopier continueDeepCopy(Map map, boolean copyTransients) throws DeepCopyException
 * {
 *     return (DeepCopier) DeepCopyUtil.continueDeepCopy(this, map, copyTransients);
 * }
 *
 * public Object deepCopy() throws DeepCopyException
 * {
 *     return DeepCopyUtil.deepCopy(this, false);
 * }
 *
 * public Object deepCopy(boolean copyTransients) throws DeepCopyException
 * {
 *     return DeepCopyUtil.deepCopy(this, copyTransients);
 * }
 * </PRE>
 * </CODE>
 * </p>
 * 
 * <A anchor="#continueDeepCopy.implementation">
 * <H4>Implement continueDeepCopy</H4>
 * </A>
 * This method will look something like this:
 * <P>
 * <CODE>
 * <PRE>
 * public void deepCopyData(Object ocopy, Map map, boolean copyTransients) throws DeepCopyException
 * {
 *     &lt;containing class name&gt; copy = (&lt;containing class name&gt;) ocopy;
 * 
 *     copy.&lt;data member 1&gt; = &lt;copy data member 1&gt;;
 *     copy.&lt;data member 2&gt; = &lt;copy data member 2&gt;;
 *     copy.&lt;data member 3&gt; = &lt;copy data member 3&gt;;
 *     ...
 *     copy.&lt;data member n&gt; = &lt;copy data member n&gt;;
 *
 *     super.deepCopyData(ocopy, map, copyTransients);
 * }
 * </PRE>
 * </CODE>
 * </P>
 * 
 * <P>
 * A deep copy of an object is completely independent of the original.  Changes 
 * to any data member have no effect on the original.
 * </P>
 * 
 * <P>
 * Care must be taken when making a deep copy because you can end up copying 
 * the entire java VM's worth of data, especially when deailing with classes 
 * that have listeners.
 * </P>
 * 
 * <P>
 * For this reason, when making a deep copy, the method has a flag for whether 
 * or not transient data memembers, like lists of listener objects, should be copied
 * along with the non-transients.  It is almost always a good idea to set this 
 * flag to false so that transients are <I>not</I> copied.
 * </P>
 * 
 * @author cnh
 */
public interface DeepCopier
{
	/**
	 * Start a deep copy of the receiver.
	 * <P>
	 * By default, this does not copy transients.
	 * </P>
	 * 
	 * @return The deep copy of the receiver.
	 * @throws DeepCopyException 
	 */
	public Object deepCopy () throws DeepCopyException;
	
	/**
	 * Start a deep copy, conditionally copying transients.
	 * 
	 * @param copyTransients true if transients should be copied, false otherwise.
	 * 
	 * @return The deep copy of the receiver.
	 * @throws DeepCopyException 
	 */
	public Object deepCopy (boolean copyTransients) throws DeepCopyException;
	
	/**
	 * Have the receiver make a deep copy of itself, using the supplied map to check
	 * for objects before making copies.
	 * 
	 * <H3>Note</H3>
	 * The receiver should check the map to see if it has already been copied!  If 
	 * the receiver has been copied, then it should return that instance rather than
	 * making a new instance.
	 * @throws DeepCopyException 
	 */
	public DeepCopier continueDeepCopy (Map map, boolean copyTransients) throws DeepCopyException;
	
	/**
	 * Copy of the data members of the class.
	 * 
	 * <P>
	 * This is the method that does the real "work" of copying an object.  It is 
	 * a good idea for each class that defines any data members to implement this 
	 * method, copy the members that they define, and then call the superclass version
	 * of the method.
	 * </P>
	 * 
	 * <P>
	 * Before copying a data member, the supplied map should be checked to see if the 
	 * object has already been copied.  The map should be an identity map of some kind
	 * that maps original objects to copies.  If an object is present in the map, then
	 * the object has already been copied.
	 * </P>
	 * 
	 * <P>
	 * Before copying an object, the method should check the map to see if the original
	 * has already been copied.  If it has, then the previous copy should be used instead
	 * of creating another copy.
	 * </P>
	 * 
	 * <P>
	 * Once a data member is copied, the original and the copy should be put into 
	 * the supplied map so that all copying methods do not try to copy the object
	 * again.
	 * </P>
	 * 
	 * @param copy The copy of the receiver.
	 * @param map The map from original objects to copies.
	 * @param copyTransients true if transient objects should be copied, false 
	 * otherwise.
	 * @throws DeepCopyException 
	 */
	public void deepCopyData (Object copy, Map map, boolean copyTransients) throws DeepCopyException;
}
