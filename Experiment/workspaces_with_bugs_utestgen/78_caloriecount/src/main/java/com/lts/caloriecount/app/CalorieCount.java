package com.lts.caloriecount.app;

import java.io.File;
import java.io.IOException;

import com.lts.LTSException;
import com.lts.application.ApplicationException;
import com.lts.application.ApplicationRepository;
import com.lts.application.checkpoint.CheckPointService;
import com.lts.application.data.ApplicationData;
import com.lts.caloriecount.data.CalorieCountData;
import com.lts.caloriecount.data.CalorieCountRepository;
import com.lts.caloriecount.data.CalorieCountSerdser;
import com.lts.caloriecount.data.entry.Entry;
import com.lts.caloriecount.data.entry.EntryList;
import com.lts.caloriecount.data.food.Food;
import com.lts.caloriecount.data.food.FoodList;
import com.lts.caloriecount.data.frequent.FrequentFood;
import com.lts.caloriecount.data.meal.Meal;
import com.lts.caloriecount.ui.gatherwin.CalorieCountGatherService;
import com.lts.caloriecount.ui.gatherwin.GatherUIThread;
import com.lts.caloriecount.ui.mainframe.MainFrame;
import com.lts.pest.Pest;
import com.lts.util.DefaultSharedQueue;
import com.lts.util.FileUtils;
import com.lts.util.SharedQueue;
import com.lts.util.notifyinglist.NotifyingList;
import com.lts.util.notifyinglist.sublist.SublistInclusionTest;
import com.lts.util.notifyinglist.sublist.SublistProxy;
import com.lts.util.system.SystemUtils;
import com.lts.util.system.SystemUtils.StandardProperties;

public class CalorieCount extends Pest
{
	public static final String CALORIE_COUNT_PROPERTY_FILE = "caloriecount.txt";
	public static final String PROPERTY_RECORDING = "CalorieCount.Recording";
	public static final String DATABASE_FILE = "data.xml";
	
	private GatherUIThread myUIThread;
	private SharedQueue myQueue;
	private CalorieCountProperties myInstanceProperties;
	
	public CalorieCount()
	{
		initialize();
	}
	
	public void initialize()
	{
		myShortPropertyFileName = CALORIE_COUNT_PROPERTY_FILE;
		super.initialize();
	}
	
	
	static public CalorieCountProperties getProps()
	{
		return getApp().getInstanceProperties();
	}
	
	
	public GatherUIThread getUIThread()
	{
		return myUIThread;
	}


	public void setUIThread(GatherUIThread thread)
	{
		myUIThread = thread;
	}


	public CalorieCountProperties getInstanceProperties()
	{
		if (null == myInstanceProperties)
			initializeInstanceProperties();
		
		return myInstanceProperties;
	}


	synchronized protected void initializeInstanceProperties()
	{
		if (null != myInstanceProperties)
			return;
		
		myInstanceProperties = new CalorieCountProperties(getProperties());
	}


	@Override
	public ApplicationData createApplicationData() throws ApplicationException
	{
		CalorieCountData data = new CalorieCountData();
		
		try
		{
			FoodList list = loadInitialDatabase();
			if (null != list)
				data.setFoods(list);
		}
		catch (ApplicationException e)
		{
			String msg = 
				"Error loading initial food database.\n"
				+ "An empty list will be used instead.";
			showException(msg, e);
		}
		
		return data;
	}

	@Override
	public ApplicationRepository createRepository() throws ApplicationException
	{
		File tempFile = createTempFile();
		return new CalorieCountRepository(tempFile);
	}
	
	protected FoodList loadInitialDatabase() throws ApplicationException
	{
		FoodList list = null;
		String s = System.getProperty("user.dir");
		File file = new File(s, DATABASE_FILE);
		if (file.exists())
		{
			CalorieCountSerdser serdeser = new CalorieCountSerdser();
			list = serdeser.deserializeFoods(file);
		}
		
		return list;
	}

	protected void copyInitialDatabase(File tempFile) throws ApplicationException
	{
		String s = System.getProperty("user.dir");
		File file = new File(s, DATABASE_FILE);
		try
		{
			FileUtils.copyFromToFile(file, tempFile);
		}
		catch (IOException e)
		{
			String msg =
				"Error trying to create initial datafile.  Source: " 
				+ file + " destination: " + tempFile;
			throw new ApplicationException(msg,e);
		}
	}

	@Override
	public ApplicationRepository createRepository(File zipfile, File tempdir) throws ApplicationException
	{
		return new CalorieCountRepository(zipfile);
	}
	
	
	@Override
	public String getApplicationName()
	{
		return "CalorieCount";
	}

	@Override
	public void startApplication() throws ApplicationException
	{
		setupNativeLibraries();
		processCheckPoints();
		normalProcessing();
	}
	

	/**
	 * Setup the appropriate java paths so that the native library for menu bar
	 * flashing will be found.
	 * 
	 */
	private void setupNativeLibraries()
	{
		String s = StandardProperties.UserDir.getValue();
		SystemUtils.appendNativeLibrarySearchPath(s);
	}

	private void normalProcessing() throws ApplicationException
	{
		try
		{
			setupGathering();		
			CheckPointService.startService(this);
			if (wasRecording())
				startGathering();
			
			MainFrame main = MainFrame.getInstance();
			main.getWindow().setVisible(true);
		}
		catch (LTSException e)
		{
			throw new ApplicationException("Error during startup", e);
		}
	}


	/**
	 * Was the application in recording mode the last time it was stopped?
	 * 
	 * <P>
	 * This method checks the property {@link #PROPERTY_RECORDING} to see if the 
	 * application was recording or not.
	 * </P>
	 * 
	 * @return true if the application was recording, false otherwise.
	 */
	protected boolean wasRecording()
	{
		String value = getProperty(PROPERTY_RECORDING);
		if (null == value)
			return false;

		return value.equalsIgnoreCase("true");
	}

	private void setupGathering()
	{
		myQueue = new DefaultSharedQueue();
		CalorieCountGatherService.createInstance(myQueue);
		CalorieCountGatherService.getInstance().start();
		myUIThread = new GatherUIThread(myQueue);
		myUIThread.start();
	}
	
	
	@SuppressWarnings("unused")
	private void throwRuntimeException()
	{
		throw new RuntimeException("kaboom");
	}
	
	
	public static void main (String[] argv)
	{
		CalorieCount cc = new CalorieCount();
		cc.startApplication(argv);
	}
	
	
	public static CalorieCount getApp()
	{
		return (CalorieCount) ourApplication;
	}

	synchronized public void stopGathering()
	{
		setProperty(PROPERTY_RECORDING, "false");
		CalorieCountGatherService.getInstance().setGenerateEvents(null);
		myQueue.clear();
	}

	public static CalorieCountData getData()
	{
		return (CalorieCountData) getApp().getApplicationData();
	}

	public void startGathering()
	{
		CalorieCountData data = getData();
		long value = data.getBudget().getInterval();
		if (value < 1)
			return;
		
		startGathering(value);
	}
	
	
	public void startGathering(Long cycleTime)
	{
		CalorieCountData data = CalorieCount.getData();
		
		if (null == cycleTime || cycleTime < 1)
		{
			cycleTime = data.getBudget().getInterval();
		}
		
		if (cycleTime < 1)
			return;
		
		CalorieCount.setAppProperty(PROPERTY_RECORDING, "true");
		data.getBudget().setInterval(cycleTime);
		CalorieCountGatherService.getInstance().setGenerateEvents(cycleTime);
	}

	public Long getNextPollTime()
	{
		Long time = CalorieCountGatherService.getInstance().nextPollOrNull();
		return time;
	}

	public static NotifyingList<Entry> getMeals()
	{
		EntryList entryList = getData().getEntryList();
		SublistInclusionTest<Entry> isMeal = new SublistInclusionTest<Entry>()
		{
			public boolean include(Entry o)
			{
				return o instanceof Entry;
			}	
		};
		
		SublistProxy<Entry> sublist = new SublistProxy<Entry>(entryList, isMeal);
		return sublist;
	}
	
	public static EntryList getEntries()
	{
		return getData().getEntryList();
	}
	
	
	public Meal createMeal(FrequentFood freq)
	{
		Food food = freq.getFood();
		return createMeal(food);
	}


	public static void setData(CalorieCountData data)
	{
		getData().replaceWith(data);
	}


	public Meal createMeal(Food food)
	{
		return createMeal(food, System.currentTimeMillis());
	}


	public Meal createMeal(Food food, long time)
	{
		Meal meal = new Meal(-1, food, time);
		
		getData().getEntryList().add(meal);
		getData().getFrequentFoods().increment(food);
		
		return meal;
	}
	
	public Meal createMeal(FrequentFood freq, long time)
	{
		Food food = freq.getFood();
		if (null == food)
			food = getData().getFoods().getFood(freq.getFoodId());
		
		return createMeal(food, time);
	}

	public static int getBalance()
	{
		int budget = getData().getBudget().getBudgetUpToNow();
		int spent = getData().getEntryList().getTotalUpToNow();
		return budget - spent;
	}

	public static int getBudgetUpToNow()
	{
		return getData().getBudget().getBudgetUpToNow();
	}
	
	
	public static int getSpentUpToNow()
	{
		return getData().getEntryList().getTotalUpToNow();
	}
}
