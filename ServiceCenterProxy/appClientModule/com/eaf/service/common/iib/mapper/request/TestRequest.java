package com.eaf.service.common.iib.mapper.request;

import java.util.ArrayList;
import java.util.List;

import com.eaf.core.ulo.common.api.InfBatchManager;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.iib.mapper.DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceCacheControl;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.google.gson.Gson;
import com.ibm.ws.webservices.xml.wassysapp.systemApp;

public class TestRequest extends InfBatchManager{
	public TestRequest(){
		super();
	}
	public static void main(String[] args) {
//		execute(getInfBatchRequest(args));
//		DecisionServiceRequestMapper test = new IA1DecisionServiceRequestMapper();
//		ApplicationGroupDataM uloApplicationGroup = new ApplicationGroupDataM();
		
		
//		ApplicationDataM uloAPplication =  new ApplicationDataM();
//		uloAPplication.setLifeCycle(1);
//		uloAPplication.setApplicationRecordId("1");
//		uloAPplication.setApplicationType("NEW");
/*		PersonalRelationDataM perRelation  = new PersonalRelationDataM();
		perRelation.setRefId("1");
		perRelation.setPersonalId("1");
		perRelation.setRelationLevel("A");*/
		
//		PersonalInfoDataM personalInfo = new PersonalInfoDataM();
//		personalInfo.setPersonalId("1");
//		personalInfo.setPersonalType("A");
//		personalInfo.addPersonalRelation("1", "A", "A");
//		uloApplicationGroup.addApplications(uloAPplication);
//		uloApplicationGroup.addPersonalInfo(personalInfo);
//		
//		decisionservice_iib.ApplicationGroupDataM iibApplicationGroup = new decisionservice_iib.ApplicationGroupDataM();
		try {
//			test.iibApplicationGroupMapper( uloApplicationGroup, iibApplicationGroup);
			
			 ApplicationGroupDataM appRGroup = new ApplicationGroupDataM();
			 appRGroup.setApplicationGroupId("1");
			 appRGroup.setDocumentSLAType("04");
			 
			 ArrayList<PersonalInfoDataM> pers = new ArrayList<PersonalInfoDataM>();
			 PersonalInfoDataM  p = new PersonalInfoDataM();
			 p.setPersonalId("2");
			 p.setApplicationGroupId("1");
			 p.setPegaPhoneType("A");
			 VerificationResultDataM v  = new VerificationResultDataM();
			 v.setRefId("2");
			 p.setVerificationResult(v);
			 pers.add(p);
			 appRGroup.setPersonalInfos(pers);
			 appRGroup.setDecisionAction("DC");
 	
 
//			System.out.println(".."+new Gson().toJson(DecisionServiceUtil.getDocumentSLAType(appRGroup, p)));
 
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void test1() throws Exception {
		try {
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
