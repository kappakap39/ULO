package com.eaf.inf.batch.ulo.notification.inf;

import com.eaf.inf.batch.ulo.notification.model.NotificationTemplateRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationTemplateResponseDataM;

public interface NotificationTemplateInf {
	public NotificationTemplateResponseDataM processNotifyTemplate(NotificationTemplateRequestDataM templateRequest) throws Exception;
}
