package com.lts.ui.menuflash;


import java.awt.Component;

public class MenuBarFlash
{
	static
	{
		initializeLibrary();
	}
	
	public static boolean initialized = false;
	
	public static void initializeLibrary()
	{
		if (initialized)
			return;
		
		initialized = true;
		System.loadLibrary("jawt");
		System.loadLibrary("flashmenu");
	}
	
	native public void flash(Component panel, int flashCount, int flashTime);
}
