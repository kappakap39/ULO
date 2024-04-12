package com.eaf.orig.ulo.app.view.form.income.sensitive;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.CompareIncomeHelper;
import com.eaf.core.ulo.common.display.CompareIncomeInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;

public class BundleSMEIndividualRatio  extends CompareIncomeHelper implements CompareIncomeInf{
	@Override
	public void maskField(HttpServletRequest request,Object objectElement) {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		if(Util.empty(applicationGroup.getReprocessFlag())){
			if(MConstant.FLAG.YES.equals(getClearFlag())) {
				BundleSMEDataM bundleSMEM = (BundleSMEDataM) objectElement;
				bundleSMEM.setIndividualRatio(null);
			}
		}
	}
	
	@Override
	public HashMap<String,Object> compareField(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		BundleSMEDataM bundleSMEM = (BundleSMEDataM) objectElement;
		String borrowing_type = SystemConstant.getConstant("LOAN_TYPE_COBORROWER_JOINT");
		
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ApplicationDataM appM = applicationGroup.getApplicationById(bundleSMEM.getApplicationRecordId());
		BundleSMEDataM bundleOrig = appM.getBundleSME();
		
		if(!Util.empty(bundleOrig)){
			if( !CompareObject.compare(bundleSMEM.getIndividualRatio(), bundleOrig.getIndividualRatio(),null)){
				if(borrowing_type.equals(bundleSMEM.getBorrowingType())) {
					formError.clearElement(getFieldName());
				} else {
					formError.element(getFieldName());
				}
			}
		} else {
			if(borrowing_type.equals(bundleSMEM.getBorrowingType())) {
				formError.clearElement(getFieldName());
			} else {
				formError.element(getFieldName());
			}
		}
		return formError.getFormError();
	}
}
