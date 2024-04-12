package com.eaf.inf.batch.ulo.letter.reject.inf;

import com.eaf.core.ulo.common.exception.InfBatchException;

public interface NotifyRejectLetterInf {
	public boolean validateTaskTransaction(Object object) throws InfBatchException;
}
