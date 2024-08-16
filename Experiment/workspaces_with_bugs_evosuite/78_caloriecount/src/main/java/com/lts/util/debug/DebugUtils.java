package com.lts.util.debug;

import java.io.PrintWriter;

/**
 * A collection of methods that are useful in debugging.
 * 
 * @author cnh
 */
public class DebugUtils
{
	
	static public void logMethod(PrintWriter out)
	{
		Exception e = new Exception();
		StackTraceElement[] stack = e.getStackTrace();
		StackTraceElement last = stack[1];
		out.println(last);
	}
	
	
	static public void logMethod()
	{
		PrintWriter out = new PrintWriter(System.out);
		logMethod(out);
		out.flush();
	}
	
}
