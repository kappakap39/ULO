package com.eaf.inf.batch.ulo.applicationdate;

import java.util.Date;

import com.eaf.core.ulo.common.exception.InfBatchException;

public interface InfChangeDateDAO {
	public Date getCurrentDate() throws InfBatchException;
	public void updateChangeDate(String currentDate) throws InfBatchException; 
}
