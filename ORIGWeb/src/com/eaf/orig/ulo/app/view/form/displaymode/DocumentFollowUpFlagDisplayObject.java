package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.orig.ulo.control.util.DocumentCheckListUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DocumentFollowUpFlagDisplayObject extends FormDisplayObjectHelper  {
	private static transient Logger logger = Logger.getLogger(DocumentFollowUpFlagDisplayObject.class);
	@Override
	public String getObjectFormMode(String objectId, String objectType,Object objectElement) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String displayMode=FormEffects.Effects.HIDE; 
		if(DocumentCheckListUtil.isReasonApplicantDocumentFollowUp(applicationGroup) || 
		   DocumentCheckListUtil.isReasonSupplementaryDocumentFollowUp(applicationGroup) || DocumentCheckListUtil.containDocumentCheckListCompleteFlagNo(applicationGroup)){
			displayMode=FormEffects.Effects.SHOW; 
		}		
		logger.debug("DocumentFollowUpFlagDisplayObject displayMode >>"+displayMode);
		return displayMode;
	}
}
