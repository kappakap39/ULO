package com.eaf.orig.ulo.app.view.form.manual.displayvalue;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayValueHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class Rekey2ProductMakerDisplayValue extends FormDisplayValueHelper {
	private static final transient Logger logger = Logger.getLogger(Rekey2ProductMakerDisplayValue.class);
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
//	String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");	
	@Override
	public Object getValue(String elementFieldId, Object elementValue,HttpServletRequest request) {
		logger.debug("Rekey2ProductMakerDisplayValue >> ");
		try{
			boolean isVetoFlag = isVetoApplication();
			logger.debug("isVetoFlag : "+isVetoFlag);
			if(!isVetoFlag){
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)elementValue;
				boolean existSrcOfData = existSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				logger.debug("existSrcOfData >> "+existSrcOfData);
				if(!existSrcOfData){
					if(!applicationGroup.isVeto()){
						ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
						Iterator<ApplicationDataM> iterator = applications.iterator();	
						logger.debug("before applications size >> "+applications.size());
						if(!Util.empty(applications)){
							while(iterator.hasNext()){
								ApplicationDataM application = iterator.next();
								PersonalInfoDataM personal = applicationGroup
										.getPersonalInfoRelation(application.getApplicationRecordId()
										, PERSONAL_TYPE_SUPPLEMENTARY, PERSONAL_RELATION_APPLICATION_LEVEL);
								if(!Util.empty(personal)){
									logger.debug("applicationRecordId >> "+application.getApplicationRecordId());
									if(!DECISION_FINAL_DECISION_CANCEL.equals(application.getFinalAppDecision()) && !DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision())){
										iterator.remove();
									}
								}
							}
							logger.debug("after applications size >> "+applications.size());
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return elementValue;
	}
}
