package com.eaf.inf.batch.ulo.notification.inf.helper;

import com.eaf.inf.batch.ulo.notification.inf.NotificationTemplateInf;
import com.eaf.inf.batch.ulo.notification.model.NotificationTemplateRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationTemplateResponseDataM;

public class NotificationTemplateHelper implements NotificationTemplateInf{
	@Override
	public NotificationTemplateResponseDataM processNotifyTemplate(NotificationTemplateRequestDataM templateRequest) throws Exception {
		NotificationTemplateResponseDataM templateResponse = new NotificationTemplateResponseDataM();
		return templateResponse;
	}
}
