package com.eaf.inf.batch.ulo.applicationdate;

import com.eaf.core.ulo.common.exception.InfBatchException;

public interface UpdateApplicationDateDAO {
	public void updateApplicationDate(int numDate) throws InfBatchException;
}
