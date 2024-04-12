package com.eaf.orig.ulo.app.view.form.income.sensitive;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.CompareIncomeHelper;
import com.eaf.core.ulo.common.display.CompareIncomeInf;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BundleKLDataM;

public class BundleKLVerifyDate  extends CompareIncomeHelper implements CompareIncomeInf{
	@Override
	public void maskField(HttpServletRequest request,Object objectElement) {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		if(Util.empty(applicationGroup.getReprocessFlag())){
			if(MConstant.FLAG.YES.equals(getClearFlag())) {
				BundleKLDataM bundleKLM = (BundleKLDataM) objectElement;
				bundleKLM.setVerifiedDate(null);
			}
		}
	}
	
	@Override
	public HashMap<String,Object> compareField(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		BundleKLDataM bundleKLM = (BundleKLDataM) objectElement;
		
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ApplicationDataM appM = applicationGroup.getApplicationById(bundleKLM.getApplicationRecordId());
		BundleKLDataM bundleOrig = appM.getBundleKL();
		
		if(!Util.empty(bundleOrig)){
			if( !CompareObject.compare(bundleKLM.getVerifiedDate(), bundleOrig.getVerifiedDate(),null)){
				formError.clearElement(getFieldName());
			}
		} else {
			formError.clearElement(getFieldName());
		}
		return formError.getFormError();
	}
}
