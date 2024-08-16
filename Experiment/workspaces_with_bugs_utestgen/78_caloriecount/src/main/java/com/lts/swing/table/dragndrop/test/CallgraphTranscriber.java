package com.lts.swing.table.dragndrop.test;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CallgraphTranscriber extends LogTranscriber
{

	@Override
	public String transcribe(EventLog log)
	{
		CallGraphLog cgl = (CallGraphLog) log;
		
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		
		CallGraphNode root = cgl.getRoot();
		printRoot(1, out, root);
		
		out.close();
		return writer.toString();
	}

	private void printRoot(int prefix, PrintWriter out, CallGraphNode root)
	{
		int count = root.getChildCount();
		for (int i = 0; i < count; i++)
		{
			CallGraphNode call = (CallGraphNode) root.getChildren().get(i);
			printNode(prefix, out, call);
			i++;
			CallGraphNode endCall = (CallGraphNode) root.getChildren().get(i);
			if (endCall.getEvent() instanceof CallLeaveEvent)
			{
				printNode(prefix, out, endCall);
			}
			
			out.println();
		}
	}

	static public String nanosToString(long nanos)
	{
		int length = 9;
		char[] str = new char[length];
		
		for (int i = 0; i < str.length; i++)
		{
			int rem = (int) (nanos % 10);
			str[length - i - 1] = Integer.toString(rem).charAt(0);
			nanos = nanos / 10;
		}
		
		return new String(str);
	}
	
	private String toString(int prefix)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < prefix; i++)
		{
			sb.append(' ');
		}
		
		return sb.toString();
	}
	
	private void printNode(int prefix, PrintWriter out, CallGraphNode node)
	{
		String s = "         ";
		
		if (!node.isRoot())
			s = nanosToString(node.getEvent().getNanoTime());
		
		else if (node.getEvent() instanceof CallLeaveEvent)
			prefix = prefix - 2;
		
		out.print (s);
		out.print (toString(prefix));
		out.print (node);
		out.println();
		
		for (Object o : node.getChildren())
		{
			CallGraphNode child = (CallGraphNode) o;			
			printNode(prefix + 2, out, child);
		}
	}
	
}
