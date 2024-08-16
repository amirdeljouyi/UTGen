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
package com.lts.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import com.lts.lang.reflect.ReflectionUtils;
import com.lts.util.prop.PropertiesUtil;

/**
 * A collection of miscellaneous methods that are useful when working with Java processes.
 * 
 * @author cnh
 *
 */
public class ProcessUtils
{
	/**
	 * The directory where the java VM command can be found.
	 */
	public static final String PROP_JAVA_DIR = "sun.boot.library.path";
	
	/**
	 * The current classpath for libraries other than rt.jar, etc.
	 */
	public static final String PROP_CLASS_PATH="java.class.path";
	
	
	/**
	 * The system property for the current directory.
	 */
	public static final String PROP_CURRENT_DIRECTORY = "user.dir";

	/**
	 * The command line switch to the java VM to tell it where to look for classes.
	 */
	public static final String SWITCH_CLASSPATH = "-cp";
	
	public static void printEnv (String[] argv)
	{
		StringWriter sw = new StringWriter(2048);
		PrintWriter out = new PrintWriter(sw);
		
		for (int i = 0; i < argv.length; i++)
		{
			out.println(argv[i]);
		}
		
		out.close();
		System.out.println(sw.toString());
	}
	
	/**
	 * Run a java class in a separate process and VM.
	 * 
	 * <H2>Notes</H2>
	 * <UL>
	 * <LI>The new process is executed in the provided directory.
	 * <LI>The "java" command must be in the path for this method to work.
	 * <LI>The current classpath is used to find the specified class.
	 * <LI>The {@link Runtime#exec(java.lang.String[], java.lang.String[], java.io.File)}
	 * is used to run the class.
	 * <LI>The process will not terminate until any output produced on the standard 
	 * output file is read.
	 * </UL>
	 * 
	 * @param className The class to execute.
	 * @param argv Arguments to pass on the java command line to the class.
	 * @param dir The directory where the class should be executed.
	 * @return The process corresponding to the executing class.
	 * @throws IOException See Runtime.exec for details.
	 * @see Runtime#exec(java.lang.String[], java.lang.String[], java.io.File)
	 */
	public static Process java (String className, String[] argv, File dir) 
		throws IOException
	{
		//
		// construct the command line --- add the current classpath
		//
		String cpath = System.getProperty(PROP_CLASS_PATH);
		
		String[] cmdArray = {
			"java",
			SWITCH_CLASSPATH,
			cpath,
			className
		};
			
		cmdArray = ArrayUtils.appendStrings(cmdArray, argv);
		
		//
		// construct the environment for the command
		// * add a variable called "SystemRoot" or exec WILL NOT WORK
		//
		Map map = System.getenv();
		Properties p = PropertiesUtil.build(map);
		p.setProperty("SystemRoot", "c:\\windows");
		String[] envArray = CollectionUtils.toStringArray(p);
		Arrays.sort(envArray);
		
		//
		// Run the command
		//
		Runtime run = Runtime.getRuntime();
		Process proc = run.exec(cmdArray, envArray, dir);
		return proc;
	}
	
	/**
	 * Run a class in a seperate process.
	 * 
	 * <H2>Description</H2>
	 * This method creates a new process via {@link Runtime#exec(java.lang.String)} and
	 * runs the class whose name is passed to the method.  As with all "main classes,"
	 * the class should implement public static void main (String[]).
	 * 
	 * <P>
	 * The process executes in the directory specified by the Java property, 
	 * "user.dir".
	 * 
	 * @param className The class to run.
	 * @param arg0 An argument to pass to the class.
	 * @return The process resulting from executing the class.
	 * @throws IOException If the class or Java VM cannot be found.
	 * @see #java(String, String[], File)
	 */
	public static Process java (String className, String arg0) throws IOException
	{
		String[] argv = { arg0 };
		String dirName = System.getProperty("user.dir");
		File cdir = new File(dirName);
		return java(className, argv, cdir);
	}
	
	/**
	 * Execute a static method in another thread.  
	 * 
	 * <P>
	 * This is useful when debugging multiple process programs --- one can simulate each
	 * process with a thread.
	 * 
	 * @param className Name of the class whose method you want to execute.
	 * @param arg0 Command line parameter for the class.
	 * @return The thread the method is executing in.
	 */
	public static Thread thread (String threadName, Class clazz, String arg0) 
	{
		String[] argv = { arg0 };
		Class[] formals = { argv.getClass() };
		Method m = ReflectionUtils.getMethod(clazz, "main", formals);
		MethodThread mthread = new MethodThread(null, m, argv);
		Thread thread = new Thread(mthread, threadName);
		thread.start();
		return thread;
	}
	
	public static final String ENV_WIN32_WINDIR = "windir";
	public static final String ENV_WIN32_SYSTEM = "SYSTEM32";
	
	
	/**
	 * Construct a minimal windows path.
	 * <P>
	 * This is a path as in "%PATH%" on windows XP. The path consists of three three
	 * parts:
	 * <UL>
	 * <LI>Java bin directory
	 * <LI>%WINDIR%\system32
	 * <LI>%WINDIR%
	 * </UL>
	 * <P>
	 * The Jaba bin directory is inferred from the value returned from
	 * {@link System#getProperty(java.lang.String)} for "java.home", and appending "\bin"
	 * to the value. The method uses the environment variable WINDIR as shown above.
	 * 
	 * @return A value suitable for use as the windows PATH.
	 */
	public static String minimalPath ()
	{
		String windir = System.getenv(ENV_WIN32_WINDIR);
		String path = windir + "\\" + ENV_WIN32_SYSTEM;
		path = path + ";" + windir;
		
		String javaHome = System.getProperty("java.home");
		path = javaHome + "\\bin" + ";" + path;
		
		return path;
	}
	
	public static final String ENV_WIN32_PATH = "Path";
	public static final String ENV_WIN32_SYSTEM_ROOT = "SystemRoot";
	
	/**
	 * Create a Properties instance that contains a minimal set of environment variables
	 * that will allow a Java VM to run.
	 * 
	 * <P>
	 * The returned environment is built from that returned from 
	 * {@link System#getenv()} except for these changes:
	 * 
	 * <UL>
	 * <LI>ENV_WIN32_PATH - is defined as by {@link #minimalPath()}.
	 * <LI>ENV_WIN32_SYSTEM_ROOT - set to the value of ENV_WIN32_WINDIR.  Required for
	 * to execute.
	 * </UL>
	 * 
	 * <P>
	 * 
	 * @return
	 */
	public static Properties buildMinmalEnvironment ()
	{
		Map m = System.getenv();
		Properties p = PropertiesUtil.build(m);
		
		String value = minimalPath();
		p.setProperty(ENV_WIN32_PATH, value);
		
		value = System.getenv(ENV_WIN32_WINDIR);
		p.setProperty(ENV_WIN32_SYSTEM_ROOT, value);
		return p;
	}
	
	
	public static Properties buildJNIEnvironment(String libdir)
	{
		String dirName = System.getProperty("user.dir");
		File cwd = new File(dirName);
		File libDir = new File(cwd, libdir);
		String absolutePath = libDir.getAbsolutePath();
		
		Properties p = buildMinmalEnvironment();
		String path = p.getProperty(ENV_WIN32_PATH);
		if (null != path && !"".equals(path))
		{
			path = path + File.pathSeparator + absolutePath;
		}
		
		p.put(ENV_WIN32_PATH, path);
		return p;
	}
	
	/**
	 * Create a Java VM in a separate process and execute a class that requires a new 
	 * element in the system path (such as JNI).
	 * 
	 * <P>
	 * This method does not require that the specified class uses JNI, it is merely a 
	 * typical use for the method --- most classes do not need to change the system 
	 * environment that they run in.  One noteable exception is JNI, which needs to load
	 * shared libraries in order to link correctly.  This method will take care of setting
	 * up the environment so that the specified directory is added to whatever variable 
	 * it needs to be added to in order to be found by a call to 
	 * {@link System#loadLibrary(java.lang.String)}.
	 * 
	 * @param pathElement The directory to add to the environment.
	 * @param className The name of the class to run.
	 * @param args Command line arguments to the class.
	 * @param startDir The working directory that the new process should start in.
	 * @return The created process.
	 */
	public static Process javaJNI(File pathElement, String className, String[] args,
			File startDir) throws IOException
	{
		Properties p = buildMinmalEnvironment();
		String value = p.getProperty(ENV_WIN32_PATH);
		value = value + File.pathSeparator + pathElement;
		p.setProperty(ENV_WIN32_PATH, value);
		return java(className, args, p, startDir);
	}
	
	
	

	/**
	 * Execute a class in a new VM in a new process.
	 * 
	 * <P>
	 * The name of the provided directory is evaluated in the context of the processes
	 * current working directory.
	 * 
	 * <P>
	 * The new VM executes with the current working directory as its starting directory.
	 * 
	 * <P>
	 * The method constructs an environment for the VM as described in 
	 * {@link #buildMinmalEnvironment()}.
	 * 
	 * <P>
	 * The method uses {@link #javaJNI(File, String, String[], File)} to start the 
	 * process.
	 * 
	 * @param relativeName The name of the directory that contains the shared libraries,
	 * relative to the current working directory of the VM.
	 * @param className The name of the class to execute.
	 * @param args Command line parameters to the class.
	 * @return The process for the command.
	 * @throws IOException See {@link #javaJNI(File, String, String[], File)}.
	 */
	public static Process JavaJNI(String relativeName, String className, String[] args)
			throws IOException
	{
		File cwd = new File(".");
		File libdir = new File(cwd, relativeName);
		return javaJNI(libdir, className, args, cwd);
	}
	

	/**
	 * Execute a class in a new VM in a new Process.
	 * <P>
	 * This is a convenience method that puts the single argument into an array of
	 * arguments and than calls {@link #JavaJNI(String, String, String[])}. See that
	 * method for details.
	 * 
	 * @param libdir
	 *        The directory to add to the list of directories to search for shared
	 *        libraries.
	 * @param className
	 *        The name of the class to execute.
	 * @param arg
	 *        The one command line argument to the class.  If null, the method passes
	 *        an empty array of arguments.
	 * @return The process that corresponds to the executing class.
	 * @throws IOException
	 *         If there is a problem executing the class.
	 * @see #JavaJNI(String, String, String[])
	 */
	public static Process JavaJNI(String libdir, String className, String arg)
			throws IOException
	{
		String[] argv = new String[0];
		if (null != arg)
			argv = new String[] { arg };
		
		return JavaJNI(libdir, className, argv);
	}
	
	/**
	 * Run a java class in a separate process and VM.
	 * 
	 * <H2>Notes</H2>
	 * <UL>
	 * <LI>The new process is executed in the provided directory.
	 * <LI>The "java" command must be in the path for this method to work.
	 * <LI>The current classpath is used to find the specified class.
	 * <LI>The {@link Runtime#exec(java.lang.String[], java.lang.String[], java.io.File)}
	 * is used to run the class.
	 * <LI>The process will not terminate until any output produced on the standard 
	 * output file is read.
	 * </UL>
	 * 
	 * @param className The class to execute.
	 * @param argv Arguments to pass on the java command line to the class.
	 * @param dir The directory where the class should be executed.
	 * @return The process corresponding to the executing class.
	 * @throws IOException See Runtime.exec for details.
	 * @see Runtime#exec(java.lang.String[], java.lang.String[], java.io.File)
	 */
	public static Process java (String className, String[] argv, Properties env, File dir) 
		throws IOException
	{
		String[] cmdArray = buildCommand(className, argv);
		String[] envArray = CollectionUtils.toStringArray(env);
		Arrays.sort(envArray);
		
		//
		// Run the command
		//
		Runtime run = Runtime.getRuntime();
		Process proc = run.exec(cmdArray, envArray, dir);
		return proc;
	}

	
	public static String[] buildCommand (String className, String[] argv)
	{
		String cpath = System.getProperty(PROP_CLASS_PATH);
		
		String[] cmdArray = {
			"java",
			SWITCH_CLASSPATH,
			cpath,
			className
		};
				
		cmdArray = ArrayUtils.appendStrings(cmdArray, argv);

		return cmdArray;
	}
	
	
	public static String[] buildCommand (String className, String arg)
	{
		String[] argv = { arg };
		return buildCommand(className, argv);
	}
	
	
	public static String[] buildEnvArray ()
	{
		Properties p = buildMinmalEnvironment();
		String[] envArray = CollectionUtils.toStringArray(p);
		Arrays.sort(envArray);
		return envArray;
	}
	
	
	public static void updateEnvironment (Map map, Properties env)
	{
		Object[] names = env.keySet().toArray();
		for (int i = 0; i < names.length; i++)
		{
			String name = (String) names[i];
			String oldValue = (String) map.get(name);
			String newValue = env.getProperty(name);
			if (null == oldValue || !oldValue.equals(newValue))
			{
//				System.out.print("changed " + name);
//				System.out.println(" from " + oldValue + " to " + newValue);
			}
			
			map.put(name, newValue);
		}
	}
	
	public static Process buildJNI (String[] cmdArray, Properties env, File startDir)
		throws IOException
	{
		ProcessBuilder builder = new ProcessBuilder(cmdArray);
		builder.directory(startDir);
		Map map = builder.environment();
		map.putAll(env);
		return builder.start();
	}
	
	
	public static Process buildJNI(String libdir, String className, String arg)
			throws IOException
	{
		String[] cmdArray = buildCommand(className, arg);
		Properties env = buildJNIEnvironment(libdir);
		String dirName = System.getProperty("user.dir");
		File cwd = new File(dirName);
		return buildJNI(cmdArray, env, cwd);
	}
}
