package com.eaf.inf.batch.ulo.letter.reject.allapp.notify;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.letter.reject.inf.NotifyRejectLetterHelper;

public class RejectLetterPDFProcessSeller extends NotifyRejectLetterHelper {
	@Override
	public boolean validateTaskTransaction(Object object)throws InfBatchException {
		return true;
	}
}
