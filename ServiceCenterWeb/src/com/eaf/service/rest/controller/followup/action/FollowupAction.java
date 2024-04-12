package com.eaf.service.rest.controller.followup.action;

import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpResultApplicationDataM;

public interface FollowupAction {

	public ProcessActionResponse processAction(FollowUpResultApplicationDataM followUpResultApplication,ApplicationGroupDataM applicationGroup);
}
