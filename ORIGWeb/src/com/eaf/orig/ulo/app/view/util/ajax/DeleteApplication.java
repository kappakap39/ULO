package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.CompareFieldElement;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.util.manual.SensitiveFieldAction;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.google.gson.Gson;

public class DeleteApplication implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(DeleteApplication.class);
	private String PRODUCT_ELEMENT = SystemConstant.getConstant("PRODUCT_ELEMENT");
	private String SUP_CARD_INFO = SystemConstant.getConstant("SUP_CARD_INFO");
	private String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_APPLICATION);
		HashMap<String,ArrayList<CompareFieldElement>> hCompareFieldElementList = (HashMap<String,ArrayList<CompareFieldElement>>)request.getSession().getAttribute(SensitiveFieldAction.REKEY_FIELDS);
		ArrayList<String> personalType = new ArrayList<String>();
		ORIGFormHandler ORIGForm = ORIGFormHandler.getForm(request);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		Set<String> keyElement = hCompareFieldElementList.keySet();
		logger.debug("keyElement : "+keyElement);
//		CompareSensitiveFieldUtil.getCompareElementFieldTypePersonal(PERSONAL_TYPE_APPLICANT,personlSeq)
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM personalInfo : personalInfos){
				ArrayList<CompareFieldElement> compareFieldElements = hCompareFieldElementList.get(CompareSensitiveFieldUtil.getCompareElementFieldTypePersonal(personalInfo.getPersonalType(),FormatUtil.toString(personalInfo.getSeq())));
				ArrayList<ApplicationDataM> applications = applicationGroup.getApplications(personalInfo.getPersonalId(), personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
				if(!Util.empty(applications)){
					if(Util.empty(compareFieldElements)){
						compareFieldElements = new ArrayList<CompareFieldElement>();
					}
					for(ApplicationDataM application : applications){
						ArrayList<CompareFieldElement> compareFieldElementApps = hCompareFieldElementList.get(CompareDataM.UniqueLevel.APPLICATION+application.getApplicationRecordId());
						if(!Util.empty(compareFieldElementApps)){
							compareFieldElements.addAll(compareFieldElementApps);
						}
					}
				}
				if(!Util.empty(compareFieldElements)){
					for(CompareFieldElement compareFieldElement : compareFieldElements){
						ArrayList<CompareDataM> compareDatas = compareFieldElement.getCompareDatas();
						if(!Util.empty(compareDatas)){
							for(CompareDataM compareData : compareDatas){
								if(PRODUCT_ELEMENT.equals(compareData.getFieldNameType()) || SUP_CARD_INFO.equals(compareData.getFieldNameType())){
									personalType.add(personalInfo.getPersonalType());
								}
							}
						}
					}
				}
			}
		}
//		if(!Util.empty(keyElement)){
//			for(String key : keyElement){
//				ArrayList<CompareFieldElement> compareFieldElements = hCompareFieldElementList.get(key);
//				if(!Util.empty(compareFieldElements)){
//					for(CompareFieldElement compareFieldElement : compareFieldElements){
//						ArrayList<CompareDataM> compareDatas = compareFieldElement.getCompareDatas();
//						for(CompareDataM compareData : compareDatas){
//							if(PRODUCT_ELEMENT.equals(compareData.getFieldNameType())){
//								logger.debug("FieldNameType : "+new Gson().toJson(compareData));
//								ApplicationDataM application = applicationGroup.getApplicationById(compareData.getUniqueId());
//								logger.debug("applicationRecordId : "+compareData.getUniqueId());
//								logger.debug("CardId : "+ (!Util.empty(application)?application.getCard():""));
//								if(!Util.empty(application) && !Util.empty(application.getCard())){
//									CardDataM card = application.getCard();
//									applicationTypes.add(card.getApplicationType());
//									logger.debug("defect info : cardType : >> "+card.getApplicationType());
//								}
//							}
//						}
//					}
//				}
//			}
//		}
		
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		int maxLifeCycle = applicationGroup.getMaxLifeCycle();
		for(Iterator<ApplicationDataM> iterator = applications.iterator(); iterator.hasNext();){
			ApplicationDataM application = iterator.next();
			if(!Util.empty(application) && !Util.empty(application.getCard()) && !Util.empty(maxLifeCycle) && maxLifeCycle == application.getLifeCycle()){
				if(!Util.empty(personalType) && personalType.contains(PERSONAL_TYPE_APPLICANT)){
					if(!DECISION_FINAL_DECISION_CANCEL.equals(application.getFinalAppDecision()) && !DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision())){
						iterator.remove();
					}
					applicationGroup.setProductElementFlag(MConstant.FLAG_Y);
				}else if(!Util.empty(personalType) && personalType.contains(application.getCard().getApplicationType())){
					if(!DECISION_FINAL_DECISION_CANCEL.equals(application.getFinalAppDecision()) && !DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision())){
						iterator.remove();
					}
					applicationGroup.setProductElementFlag(MConstant.FLAG_Y);
				}
			}
		}
		ApplicationUtil.clearNotMatchApplicationRelation(applicationGroup);
		return responseData.success();
	}

}
