package com.lts.swing.table.dragndrop.example;

import java.util.Arrays;

public class ArrayUtils
{
	/**
	 * Sort an array of integers in descending order.
	 * 
	 * <P>
	 * Same as {@link Arrays#sort(int[])} except that the array is sorted with the 
	 * largest int at index 0, then the second-largest at 1, etc.  The method has 
	 * an O(n) cost on top of the cost incurred by the regular sort, since it makes 
	 * a pass over the array after sorting.
	 * </P>
	 * 
	 * @param inputArray The array to sort.
	 */
	static public void sortDescending (int[] inputArray)
	{
		Arrays.sort(inputArray);
		int low = 0;
		int high = inputArray.length - 1;
		while (low < high)
		{
			int temp = inputArray[high];
			inputArray[high] = inputArray[low];
			inputArray[low] = temp;
			low++;
			high--;
		}
	}
}
