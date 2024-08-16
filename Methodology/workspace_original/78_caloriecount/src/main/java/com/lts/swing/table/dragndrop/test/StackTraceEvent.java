package com.lts.swing.table.dragndrop.test;

public class StackTraceEvent extends RecordingEvent
{
	
	static public int CALLER = 2;
	
	protected StackTraceElement[] myTrace;
	protected StackTraceElement myElement;
	
	public StackTraceElement getElement()
	{
		return myElement;
	}
	
	
	public StackTraceEvent()
	{
		initialize();
	}

	protected void initialize()
	{
		super.initialize();
		Exception e = new Exception();
		e.fillInStackTrace();
		myTrace = e.getStackTrace();
	}
	
	/**
	 * Return where in the receiver's stack trace it diverges from the argument's
	 * stack trace.
	 * 
	 * <P>
	 * If the receiver's stack is equivalent to the argument's then the method returns
	 * -1.
	 * </P>
	 * 
	 * <H3>Example</H3>
	 * <CODE>
	 * <PRE>
	 *      Receiver Stack       Argument Stack
	 * [16] foo.bar         [18] foo.bar
	 * [15] liwipi.nerts    [17] liwipi.nerts
	 * [14] liwipi.yuck     [16] liwipi.yuck
	 * ...
	 * [5]  nerts.curley    [7]  nerts.curley
	 * [4]  yuck.bar        [6]  other.method
	 * [3]  feng.shui       [5]  ima.deviant
	 * ...
	 * </PRE>
	 * </CODE>
	 * 
	 * <P>
	 * In this situation, the method would return 3.
	 * </P>
	 * 
	 * <H3>Example 2</H3>
	 * <CODE>
	 * <PRE>
	 *      Receiver Stack       Argument Stack
	 * [16] foo.bar         [18] foo.bar
	 * [15] liwipi.nerts    [17] liwipi.nerts
	 * [14] liwipi.yuck     [16] liwipi.yuck
	 * ...
	 * [2]  nerts.curley    [4]  nerts.curley
	 * [1]  yuck.bar        [3]  yuck.bar
	 * [0]  feng.shui       [2]  feng.shui
	 *                      [1]  another.method
	 *                      [0]  yeanother.method
	 * </PRE>
	 * </CODE>
	 * 
	 * <P>
	 * In this situation, the method would return -1, since the entire receiver stack
	 * would be the same as the argument.
	 * </P>
	 * 
	 * @param other The event to compare with.
	 * @return The index in the receiver's stack where it deviates from the argument's 
	 * stack; or -1 if the receiver stack is the same as or a subset of the argument's
	 * stack.
	 */
	public int divergesAt(StackTraceEvent other)
	{
		StackTraceElement[] trace1 = myTrace;
		StackTraceElement[] trace2 = other.myTrace;
		
		int index1 = trace1.length - 1;
		int index2 = trace2.length - 1;
		
		while (index1 < trace1.length && index2 < trace2.length)
		{
			if (!trace1[index1].equals(trace2[index2]))
				return index1;
			
			index1++;
			index2++;
		}
		
		if (index1 >= trace1.length)
			return trace1.length - 1;
		
		if (index2 >= trace2.length)
			return trace2.length - 1;
		
		return index1;
	}
	
	
	/**
	 * Is the receiver one of the methods that called the sender?
	 * 
	 * <P>
	 * Event A is the ancestor of event B if A.divergesAt(B) returns -1 and if 
	 * A's stack size is strictly less than B's stack size.  Note that 
	 * A.isAncestorOf(A) should return <I>false.</i>
	 * </P>
	 * 
	 * @param other The other event to check against.
	 * @return true if the receiver is an ancestor of other, false otherwise.
	 */
	public boolean isAncestorOf (StackTraceEvent other)
	{
		return 
			(-1 == divergesAt(other)) 
			&& (myTrace.length > other.myTrace.length);
	}
	
	
	public boolean isParentOf (StackTraceEvent other)
	{
		return 
			-1 == divergesAt(other)
			&& (other.myTrace.length == myTrace.length + 1);
	}
	
	
	public boolean equals (StackTraceEvent other)
	{
		return 
			-1 == divergesAt(other)
			&& myTrace.length == other.myTrace.length;
			
	}
	
	static public StackTraceElement[] copySubtrace(int start, StackTraceElement[] trace)
	{
		int length = trace.length - start;
		StackTraceElement[] result = new StackTraceElement[length];
		
		for (int i = start; i < length; i++)
		{
			result[i - start] = trace[i];
		}
		
		return result;
	}
	
	
	public String getSimpleName()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(myTrace[0].getClassName());
		sb.append('.');
		sb.append(myTrace[0].getMethodName());
		sb.append(':');
		sb.append(myTrace[0].getLineNumber());
		
		return sb.toString();
	}
	
	
	protected void initialize(int framesBeforeThis)
	{
		super.initialize();
		
		Exception e = new Exception();
		e.fillInStackTrace();
		StackTraceElement[] stack = e.getStackTrace();
		int length = stack.length - framesBeforeThis;
		myTrace = new StackTraceElement[length];
		
		for (int i = length - 1; i >= 0; i--)
		{
			myTrace[i] = stack[i + framesBeforeThis];
		}
	}


	public StackTraceElement[] getTrace()
	{
		return myTrace;
	}

}
