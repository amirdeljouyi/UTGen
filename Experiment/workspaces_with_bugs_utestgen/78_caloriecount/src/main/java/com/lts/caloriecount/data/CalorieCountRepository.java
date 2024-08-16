package com.lts.caloriecount.data;

import java.io.File;

import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationData;

public class CalorieCountRepository extends SingleFileRepository
{
	public CalorieCountRepository(File file) throws ApplicationException
	{
		super(file, "cc_temp_data", null);
	}
	
	
	@Override
	public ApplicationData getApplicationData() throws ApplicationException
	{
		CalorieCountSerdser ccs = new CalorieCountSerdser();
		ApplicationData data = ccs.deserializeCalorieCount(getTempFile());
		return data;
	}

	@Override
	public void storeApplicationData(ApplicationData data) throws ApplicationException
	{
		CalorieCountSerdser ccs = new CalorieCountSerdser();
		CalorieCountData ccd = (CalorieCountData) data;
		ccs.serializeCalorieCount(getTempFile(), ccd);
	}

}
