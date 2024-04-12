package com.eaf.inf.batch.ulo.notification.eod.sendto.inf;

import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.inf.batch.ulo.notification.model.EODRecipientRequestDataM;

public interface EODSendingInf {
	public void processGetRecipient(EODRecipientRequestDataM eodReceipientRequest,RecipientTypeDataM recipient) throws Exception;
}
