package com.eaf.service.rest.controller.followup.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FollowUpResultSLADocInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.model.DocumentCheckListDataM;
import com.eaf.orig.shared.model.DocumentScenarioDataM;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.util.ServiceUtil;

public class FollowUpWaitDocAtFU implements FollowUpResultSLADocInf{
	private static transient Logger logger = Logger.getLogger(FollowUpWaitDocAtDV.class);
	@Override
	public ArrayList<String> getSlaDocumentType(ArrayList<DocumentCheckListDataM> documentCheckListDataM,ArrayList<DocumentScenarioDataM>documentScenarioDataM,String followUpStatus) {
		ArrayList<String> docSLAtype = new ArrayList<String>();
		if(SystemConstant.getConstant("OVER_SLA").equals(followUpStatus) 
				|| SystemConstant.getConstant("UNABLE_TO_CONTACT").equals(followUpStatus)
				|| SystemConstant.getConstant("CUSTOMER_DENIED_TO_SEND").equals(followUpStatus)){
			
			for(DocumentScenarioDataM scen : documentScenarioDataM){
				String docCompleteFlag = scen.getDocCompleteFlag();
				String personalType = scen.getPersonalType();
				String scenarioType = scen.getScenarioType();
				if(!ServiceUtil.empty(docCompleteFlag) 
					&& !ServiceUtil.empty(personalType) 
					&& !ServiceUtil.empty(scenarioType)
					&& ServiceConstant.NO.equals(docCompleteFlag)){
					if(SystemConstant.getConstant("INCOME_DOC").equals(scenarioType) && !docSLAtype.contains(SystemConstant.getConstant("DOC_SLA_TYPE04"))){
						docSLAtype.add(SystemConstant.getConstant("DOC_SLA_TYPE04"));
					}else if(!docSLAtype.contains(SystemConstant.getConstant("DOC_SLA_TYPE01"))
							&& !SystemConstant.getConstant("INCOME_DOC").equals(scenarioType)){
						docSLAtype.add(SystemConstant.getConstant("DOC_SLA_TYPE01"));
					}
				}
			}

			for(DocumentCheckListDataM doc : documentCheckListDataM)
				if(!Util.empty(doc.getDocTypeId()) 
						&& doc.getDocTypeId().equals(SystemConstant.getConstant("INCOME_DOC"))
						&& !docSLAtype.contains(SystemConstant.getConstant("DOC_SLA_TYPE04"))
						&& !ServiceConstant.YES.equals(doc.getGenarateFlag())){
						logger.debug("DocType#######"+doc.getDocTypeId());
						logger.debug("LoopFor04##############");
							docSLAtype.add(SystemConstant.getConstant("DOC_SLA_TYPE04"));
				}else{
						if(!docSLAtype.contains(SystemConstant.getConstant("DOC_SLA_TYPE01"))
							&& !docSLAtype.contains(SystemConstant.getConstant("DOC_SLA_TYPE01"))
							&& !ServiceConstant.YES.equals(doc.getGenarateFlag())){
							docSLAtype.add(SystemConstant.getConstant("DOC_SLA_TYPE01"));
						}
					}
		}
		return docSLAtype;
	}

}
