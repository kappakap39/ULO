package com.eaf.inf.batch.ulo.letter.reject.inf;

import com.eaf.core.ulo.common.exception.InfBatchException;

public class NotifyRejectLetterHelper implements NotifyRejectLetterInf{
	@Override
	public boolean validateTaskTransaction(Object object)throws InfBatchException{ 
		return true;
	}
}
