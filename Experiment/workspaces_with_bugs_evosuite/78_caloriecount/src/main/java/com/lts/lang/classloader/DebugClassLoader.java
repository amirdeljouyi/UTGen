//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the util library.
//
//  The util library is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published by
//  the Free Software Foundation; either version 2.1 of the License, or (at
//  your option) any later version.
//
//  The util library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//  or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the util library; if not, write to the Free Software Foundation,
//  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.lang.classloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.lts.io.IndentingPrintWriter;

/**
 * A ClassLoader that helps with debugging {@link ClassNotFoundException}s.
 * 
 * <H2>Usage</H2>
 * <CODE>
 * <PRE>
 * 
 * String[] repos = {
 *     "location 1",
 *     "location 2",
 *     ...
 * };
 * 
 * DebugClassLoader.debugClass("class name", repos, "logfile");
 * 
 * </PRE>
 * </CODE>
 * 
 * <H2>Description</H2>
 * This class helps with debugging by allowing developers to see which class actually 
 * caused a {@link java.lang.ClassNotFoundException} as opposed to the default message
 * which just tells you which class the VM was originally asked for.
 * 
 * <P>
 * It works by replacing the "context classloader" for the thread 
 * with one that notes the "load stack" created when trying to load a class.  A load 
 * stack is a stack where the top is the most recently asked for class, the next one 
 * up is the class that depends on it, and so on.  
 * 
 * <P>
 * The class loader will try to instantiate the class asked for.  This is because the 
 * various linkages to other classes are never really resolved unless the class is 
 * actually instantiated.  While the documentation for the 
 * {@link java.lang.ClassLoader#resolveClass(java.lang.Class)} claims that all linkages
 * are resolved when that method is called, in practice I have not found this to be the
 * case.
 * 
 * <P>
 * This class loader tries to load all classes other than system classes.  System 
 * classes are classes whose package name starts with one of the following prefixes:
 * 
 * <UL>
 * <LI>java
 * <LI>javax
 * <LI>com.sun
 * </UL>
 * 
 * <P>
 * The class ignores the system classpath and instead depends on a list of file names
 * that represent the various directories, JAR files, etc. that make up the class path
 * for the loader.
 * 
 * <P>
 * The problem with CNFE errors is that the class reported is often times present, but
 * one of the classes that it depends on is absent.  The CNFE, however, reports on the
 * dependent class, not the one that it actually could not find.
 * 
 * <P>
 * This class helps by logging when an attempt is made to load a class and "remembering"
 * the class that caused the current lookup.  Thus when a CNFE occurs, the developer
 * can trace the dependency down to the class that actually caused the problem, as 
 * opposed to just seeing the one that could not be loaded as a consequence.  
 * 
 * @author cnh
 *
 */
public class DebugClassLoader extends BasicClassLoader
{
	private String[] myRepositoryFiles;
	
	public String[] getRepositoryFiles()
	{
		return myRepositoryFiles;
	}
	
	public void setRepositoryFiles (String[] filenames)
	{
		myRepositoryFiles = filenames;
	}
	
	
	private IndentingPrintWriter myOut;
	
	public IndentingPrintWriter getOut()
	{
		return myOut;
	}
	
	public void setOut (IndentingPrintWriter out)
	{
		myOut = out;
	}
	
	
	public static PrintWriter openLogfile (String filename, boolean append)
		throws IOException
	{
		File outfile = new File(filename);
		File parent = outfile.getParentFile();
		
		if (!parent.isDirectory() || !parent.mkdirs())
		{
			String msg = "Could not create parent director for logfile, " + parent;
			throw new IOException(msg);
		}
		
		
		FileOutputStream fos = new FileOutputStream(filename, append);
		PrintWriter writer = new PrintWriter(fos);
		
		return writer;
	}
	
	
	public void initialize (String[] repositoryFileNames, PrintWriter out)
	{
		ClassRepositoryFactory fact = new ClassRepositoryFactory();
		List list = new ArrayList();
		
		for (int i = 0; i < repositoryFileNames.length; i++)
		{
			File f = new File(repositoryFileNames[i]);
			if (!f.exists())
			{
				out.println("warning: repository does not exist, " + f);
			}
			else
			{
				ClassRepository repos = fact.createRepository(f);
				list.add(repos);
			}
		}
		
		IndentingPrintWriter ipw = new IndentingPrintWriter(out);
		setOut(ipw);
		setRepositories(list);
	}
	
	
	public DebugClassLoader (String[] repositoryFileNames, PrintWriter out, ClassLoader parent)
	{
		super(parent);
		initialize(repositoryFileNames, out);
	}
	
	public static void debugClass (String className, String[] repositoryFileNames, String logfileName) 
		throws IOException
	{
		PrintWriter out = openLogfile(logfileName, true);
		try
		{
			debugClass(className, repositoryFileNames, out);
		}
		finally
		{
			out.close();
		}
	}
	
	
	public static void debugClass (String className, String[] repositoryFileNames, PrintStream pstream) 
	{
		PrintWriter out = new PrintWriter(pstream);
		try
		{
			debugClass(className, repositoryFileNames, out);
		}
		finally
		{
			out.flush();
		}
	}

	public static Class debugClass (String className, String[] repositoryFileNames, PrintWriter out)
	{
		DebugClassLoader loader = new DebugClassLoader(repositoryFileNames, out,
				ClassLoader.getSystemClassLoader());
		Thread.currentThread().setContextClassLoader(loader);
		
		out.println("Trying to load: " + className);
		out.println("\tClasspath: " + System.getProperty("java.class.path"));
		out.println();
		
		out.println("Repositories:");
		for (int i = 0; i < repositoryFileNames.length; i++)
		{
			out.println("\t" + repositoryFileNames[i]);
			File f = new File(repositoryFileNames[i]);
			if (!f.exists())
			{
				out.println("\t\tRepository does not exist!");
			}
		}
		out.println();
		
		try
		{
			Class clazz = Class.forName(className, true, loader);
			clazz.newInstance();
			
			out.println("Successfully loaded class: " + className);
			out.println();
			
			return clazz;
		}
		catch (ClassNotFoundException e)
		{
			out.println("Class not found, lookup trace: ");
			loader.printFindStack(out);
			out.println();
			out.println("Exception:");
			e.printStackTrace(out);
		}
		catch (Exception e)
		{
			e.printStackTrace(out);
		}
		
		return null;
	}
	
	
	public String dataToString (Object[] data, int index)
	{
		String s = null;
		
		if (data.length >= index && data[index] instanceof String)
			s = (String) data[index];
		
		return s;
	}
	
	
	public Class dataToClass (Object[] data, int index)
	{
		Class clazz = null;
		
		if (data.length > index && data[index] instanceof Class)
			clazz = (Class) data[index];
		
		return clazz;
	}
	
	
	public Throwable dataToThrowable (Object[] data, int index)
	{
		Throwable t = null;
		
		if (data.length >= index && data[index] instanceof Throwable)
			t = (Throwable) data[index];
		
		return t;
	}
	
	
	
	public void logStartFind (String name)
	{
		findStack.push(name);
		getOut().println(name);
		getOut().increaseIndent();
	}
	
	
	public void logEndFind (String name, Class clazz, Throwable t)
	{
		findStack.pop();
		getOut().decreaseIndent();
		// getOut().println(name);
	}

	public static final int EVENT_START_LOAD = 1 + BasicClassLoader.EVENT_LAST;
	public static final int EVENT_END_LOAD = 2 + BasicClassLoader.EVENT_LAST;
	
	
	private Stack findStack = new Stack();
	
	public void logEvent (int event, Object[] data)
	{
		String name = dataToString(data, 0);
		Class clazz = dataToClass(data, 1);
		Throwable t = dataToThrowable(data, 2);
		
		String tos = "";
		if (!findStack.isEmpty())
			tos = (String) findStack.peek();

		switch (event)
		{
			case BasicClassLoader.EVENT_START_FIND :
			case EVENT_START_LOAD :
				if (!name.equals(tos))
					logStartFind(name);
				
				break;
				
			case EVENT_END_LOAD :
			case BasicClassLoader.EVENT_END_FIND :
				if (name.equals(tos))
					logEndFind(name, clazz, t);
				
				break;
			
		}
	}

	
	public void printFindStack (PrintWriter out)
	{
		int size = findStack.size();
		for (int i = 0; i < size; i++)
		{
			String name = (String) findStack.get(i);
			out.println(name);
		}
	}
	
	
	public Class loadClass (String className, boolean resolve)
		throws ClassNotFoundException
	{
		logEvent(className, EVENT_START_LOAD);
		
		Class clazz = null;
		Throwable t = null;
		
		try
		{
			clazz = (Class) getClassCache().get(className);
			
			if (
					null == clazz && (
							className.startsWith("java.")
							|| className.startsWith("javax.")
							|| className.startsWith("com.sun.")
					)
			)
			{
				clazz = getParent().loadClass(className);
			}
			
			if (null == clazz)
			{
				clazz = findClass(className);
			}
			
			if (null != clazz && resolve)
				resolveClass(clazz);
			
			return clazz;
		}
		catch (ClassNotFoundException e)
		{
			t = e;
			throw e;
		}
		finally
		{
			logEvent(className, null, t, EVENT_END_LOAD);
		}
	}
	
	
	public Class loadClass (String name) throws ClassNotFoundException
	{
		return loadClass(name, false);
	}
}
