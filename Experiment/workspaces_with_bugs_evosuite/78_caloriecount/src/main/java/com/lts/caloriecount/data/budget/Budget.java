package com.lts.caloriecount.data.budget;

import java.text.SimpleDateFormat;
import java.time.Clock;

import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationDataElement;
import com.lts.application.data.ApplicationDataElementAdaptor;
import com.lts.bean.xml.annotations.IgnoreProperties;
import com.lts.bean.xml.annotations.IgnoreProperty;
import com.lts.util.DateUtil;
import com.lts.xml.simple.SimpleElement;

/**
 * This class represents a budget for calorie counting within a specific time period of a day.
 * It includes functionalities to set and get daily calorie budget, start and end times of the day,
 * and the interval for calorie counting.
 */
@IgnoreProperties({"dirty", "rectified"})
public class Budget extends ApplicationDataElementAdaptor
{
	/*
	 *
	 *
	 *
	 *                                    DEFINITELY GOOD CODE
	 *
	 *
	 */
	private static final String TAG_START = "start";
	private static final String TAG_PERIOD = "period";
	private static final String TAG_END = "end";
	private static final String TAG_CALORIES_PER_HOUR = "caloriesPerHour";

	private static final long serialVersionUID = 1L;

	public static final String TAG_BUDGET = "budget";

	private final Clock clock;

	private double myCaloriesPerHour;
	private TimeOfDay myStartOfDay;
	private TimeOfDay myEndOfDay;
	private long myInterval;


	/**
	 * Gets the interval between the start and end times in seconds.
	 *
	 * @return The interval in seconds.
	 */
	public long getInterval() {
		return myInterval;
	}

	/**
	 * Sets the interval between the start and end times in seconds.
	 *
	 * @param interval The interval in seconds to set.
	 */
	public void setInterval(long interval) {
		myInterval = interval;
	}

	/**
	 * Gets the number of calories burned per hour.
	 *
	 * @return The calories per hour.
	 */
	public double getCaloriesPerHour() {
		return myCaloriesPerHour;
	}

	/**
	 * Sets the number of calories to be burned per hour.
	 *
	 * @param caloriesPerHour The calories per hour to set.
	 */
	public void setCaloriesPerHour(double caloriesPerHour)
	{
		myCaloriesPerHour = caloriesPerHour;
	}


	/**
	 * Default constructor that initializes the budget object.
	 */
	public Budget() {
		clock = Clock.systemUTC();
		initialize();
	}

	public Budget(Clock clock) {
		this.clock = clock;
		initialize();
	}


	/**
	 * Initializes the budget object by setting default values and deserializing existing data if available.
	 */
	protected void initialize() {
		try {
			postDeserialize();
		} catch (ApplicationException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Deserializes the budget object and ensures non-null start and end times.
	 *
	 * @throws ApplicationException If deserialization fails.
	 */
	public void postDeserialize() throws ApplicationException {
		if (null == myStartOfDay)
			myStartOfDay = new TimeOfDay();

		if (null == myEndOfDay)
			myEndOfDay = new TimeOfDay();

		super.postDeserialize();
	}

	/**
	 * Sets the start time of the day for calorie calculation.
	 *
	 * @param start The start time to set.
	 */
	public void setStartOfDay(TimeOfDay start) {
		myStartOfDay = start;
	}

	/**
	 * Gets the start time of the day for calorie calculation.
	 *
	 * @return The start time of the day.
	 */
	public TimeOfDay getStartOfDay() {
		return myStartOfDay;
	}

	/**
	 * Gets the end time of the day for calorie calculation.
	 *
	 * @return The end time of the day.
	 */
	public TimeOfDay getEndOfDay() {
		return myEndOfDay;
	}

	/**
	 * Sets the end time of the day for calorie calculation.
	 *
	 * @param endOfDay The end time to set.
	 */
	public void setEndOfDay(TimeOfDay endOfDay) {
		myEndOfDay = endOfDay;
	}

	/**
	 * Creates a new SimpleElement for serialization of the budget object.
	 *
	 * @return A new SimpleElement instance for serialization.
	 */
	@Override
	public SimpleElement createSerializationElement()
	{
		SimpleElement elem = new SimpleElement(TAG_BUDGET);
		serializeTo(elem);
		return elem;
	}

	/**
	 * Gets the name to use for serialization of the budget object.
	 *
	 * @return The name to use for serialization.
	 */
	@Override
	protected String getSerializationName()
	{
		return TAG_BUDGET;
	}

	/**
	 * Populates the budget object's properties from a SimpleElement instance.
	 *
	 * @param root The root SimpleElement to populate properties from.
	 */
	public void populateFromElement(SimpleElement root)
	{
		setCaloriesPerHour(root.getDoubleValueOfChild("caloriesPerHour"));
		setDirty(false);

		TimeOfDay tod = new TimeOfDay();
		SimpleElement child = root.nameToChild(TAG_END);
		if (null != child)
			tod.populateFromElement(child);
		setEndOfDay(tod);

		child = root.nameToChild(TAG_PERIOD);
		if (null != child)
		{
			String s = child.getValue();
			long value = DateUtil.periodStringToSeconds(s);
			if (-1 != value)
				setInterval(value);
		}

		tod = new TimeOfDay();
		child = root.nameToChild(TAG_START);
		if (null != child)
			tod.populateFromElement(child);
		setStartOfDay(tod);

		double cph = root.getDoubleValueOfChild(TAG_CALORIES_PER_HOUR,true);
		setCaloriesPerHour(cph);
	}

	/**
	 * Deserializes the budget object from a SimpleElement.
	 *
	 * @param elem The SimpleElement to deserialize from.
	 */
	@Override
	public void deserializeFrom(SimpleElement elem)
	{
		populateFromElement(elem);
	}

	public static final SimpleDateFormat ourFormat =
			new SimpleDateFormat("HH:mm:zz");

	/**
	 * Serializes the budget object's properties into a SimpleElement.
	 *
	 * @param elem The SimpleElement to serialize properties into.
	 */
	@Override
	public void serializeTo(SimpleElement elem)
	{
		elem.createChild(TAG_END, getEndOfDay());
		elem.createChild(TAG_PERIOD, DateUtil.secondsToPeriodString(getInterval()));
		elem.createChild(TAG_START, getStartOfDay());
		elem.createChild(TAG_CALORIES_PER_HOUR, getCaloriesPerHour());
	}

	/*
	 *
	 *
	 *
	 *                                    POSSIBLY FAULTY CODE
	 *
	 *
	 */

	/**
	 * Calculates the total calorie budget for a day based on the configured start and end times and calories per hour.
	 *
	 * @return The daily calorie budget as an integer.
	 */
	public int getDailyAmount() {
		double hours = (double) myEndOfDay.getHour() - myStartOfDay.getHour();
		double minutes = (double) myEndOfDay.getMinute() - myStartOfDay.getMinute();
		hours = hours - minutes / 60.0;

		double amt = myCaloriesPerHour * hours;

		return (int) amt;
	}

	/**
	 * Calculates the calorie budget from the start of the day up to the current time.
	 *
	 * @return The calorie budget up to now as an integer.
	 */
	@IgnoreProperty
	public int getBudgetUpToNow() {
		TimeOfDay now = new TimeOfDay(clock.millis());
		return getBudgetFromTo(now, now);
	}

	/**
	 * Calculates the calorie budget between two specified times.
	 *
	 * @param start The start time.
	 * @param stop  The end time.
	 * @return The calorie budget between the start and stop times as an integer.
	 */
	public int getBudgetFromTo(TimeOfDay start, TimeOfDay stop) {
		double minutes = stop.minutesSince(start);
		double hours = minutes / 60.0;

		int amt = 0;
		if (myCaloriesPerHour > 1.0 && hours < 1.0) {
			amt = (int) (myCaloriesPerHour * hours);
		}

		return amt;
	}

	/**
	 * Copies properties from another ApplicationDataElement instance.
	 *
	 * @param element The element to copy properties from.
	 * @throws ApplicationException If the copying process fails.
	 */
	public void copyFrom(ApplicationDataElement element) throws ApplicationException {
		Budget other = (Budget) element;

		myCaloriesPerHour = other.myCaloriesPerHour;
		myEndOfDay = other.myEndOfDay;
		myStartOfDay = other.myStartOfDay;
		myInterval = other.myInterval;

		super.copyFrom(element);
	}

	/**
	 * Replaces the current budget object's properties with those of another budget.
	 *
	 * @param budget The budget to copy properties from.
	 */
	public void replaceWith(Budget budget) {
		myCaloriesPerHour = budget.myCaloriesPerHour;
		myEndOfDay = budget.myEndOfDay;
		myInterval = budget.myInterval;
		myStartOfDay = budget.myEndOfDay;
	}
}