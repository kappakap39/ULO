package com.eaf.service.rest.controller.submitapplication.coverpagetype;

import com.eaf.orig.ulo.model.submitapplication.model.SubmitApplicationObjectDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;

public interface ApplicationAppyTypeProcess {
	public ProcessResponse processAction(SubmitApplicationObjectDataM objectForm);
}
