package com.eaf.orig.ulo.app.view.form.question.answerofquestion;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.dih.VcCardDataM;

public class PaymentMethodResult extends FormHelper implements FormAction {
	private static transient Logger logger = Logger.getLogger(PaymentMethodResult.class);
	private	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	@Override
	public String processForm(){
		return super.processForm();
	}
	@Override
	public Object getObjectForm(){
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		String cisNo = personalInfo.getCisNo();
		logger.debug("cisNo : "+cisNo);
		if(!Util.empty(cisNo)){
			 DIHProxy dihProxy = new DIHProxy();
			 DIHQueryResult<List<VcCardDataM>> queryResult =  dihProxy.searchCardInfoByCisNo(cisNo);
			 List<VcCardDataM> vcCardDataMList = queryResult.getResult();
			 if(!Util.empty(vcCardDataMList)){
				 Iterator<VcCardDataM> iterator = vcCardDataMList.iterator();
				 while(iterator.hasNext()){
					 VcCardDataM vcCardData= iterator.next();
						if(Util.empty(vcCardData.getACH_F()) && Util.empty(vcCardData.getCARD_TP())){
							iterator.remove();
						} 
					}
			 }
			 return vcCardDataMList;
		}
		return null;
	}
}
