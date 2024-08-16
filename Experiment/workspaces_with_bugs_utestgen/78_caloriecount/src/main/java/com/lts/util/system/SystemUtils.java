package com.lts.util.system;

import com.lts.util.ArrayUtils;
import com.lts.util.FileUtils;

public class SystemUtils
{
	public enum StandardProperties
	{
		JavaVersion("java.version"),
		JavaVendor("java.vendor"),
		JavaVendorUrl("java.vendor.url"),
		JavaHome("java.home"),
		JavaVmSpecificationVersion("java.vm.specification.version"),
		JavaVmSpecificationVendor("java.vm.specification.vendor"),
		JavaVmSpecificationName("java.vm.specification.name"),
		JavaVmVersion("java.vm.version"),
		JavaVmVendor("java.vm.vendor"),
		JavaVmName("java.vm.name"),
		JavaSpecificationVersion("java.specification.version"),
		JavaSpecificationVendor("java.specification.vendor"),
		JavaSpecificationName("java.specification.name"),
		JavaClassVersion("java.class.version"),
		JavaClassPath("java.class.path"),
		JavaLibraryPath("java.library.path"),
		JavaIoTmpdir("java.io.tmpdir"),
		JavaCompiler("java.compiler"),
		JavaExtDirs("java.ext.dirs"),
		OsName("os.name"),
		OsArch("os.arch"),
		OsVersion("os.version"),
		FileSeparator("file.separator"),
		PathSeparator("path.separator"),
		LineSeparator("line.separator"),
		UserName("user.name"),
		UserHome("user.home"),
		UserDir("user.dir");
		
		public final String propertyKey;
		
		StandardProperties(String s)
		{
			propertyKey = s;
		}
		
		public String getValue()
		{
			return System.getProperty(propertyKey);
		}
		
		public void setValue(String value)
		{
			System.setProperty(propertyKey, value);
		}
	}
	
	
	public static String getNativeLibrarySearchPath()
	{
		return StandardProperties.JavaLibraryPath.getValue();
	}
	
	public static void setNativeLibrarySearchPath(String value)
	{
		StandardProperties.JavaLibraryPath.setValue(value);
	}
	
	public static void appendNativeLibrarySearchPath(String value)
	{
		String s = getNativeLibrarySearchPath();
		
		if (null != s)
		{
			String[] comps = s.split(StandardProperties.PathSeparator.getValue());
			if (ArrayUtils.arrayContains(comps, value))
				return;
		}
		
		StringBuffer sb = new StringBuffer();
		
		if (null != s)
		{
			sb.append(s);
			sb.append(StandardProperties.PathSeparator.getValue());
		}
		
		sb.append(value);
		
		s = sb.toString();
		StandardProperties.JavaLibraryPath.setValue(s);
	}

	public static void appendDirectoryContaining(String name, String[] path)
	{
		String s = FileUtils.findFirstDirectoryContaining(name, path);
		if (null == s)
			throw new RuntimeException("Could not find " + name);
		
		appendNativeLibrarySearchPath(s);
	}
	
	
	
}
