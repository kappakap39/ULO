package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.constant.MConstant;
//import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
//import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class CardTypeMainAppListBoxFilter implements ListBoxFilterInf {
	private static transient Logger logger = Logger.getLogger(CardTypeMainAppListBoxFilter.class);
	String PRODUCT_CREDIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");	
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private String displayMode = HtmlUtil.EDIT;
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId, String cacheId, String typeId, HttpServletRequest request) {
//		 String productCode = request.getParameter("PRODUCT_TYPE");
//		 String CACHE_CARD_PRODUCT_INFO = SystemConstant.getConstant("CACHE_CARD_PRODUCT_INFO");
//		 String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
//		 String applicationGroupId = request.getParameter("APPLICATION_GROUP_ID");
//		ApplicationGroupDataM applicationGroup = ((ORIGFormHandler) request.getSession().getAttribute(ORIGFormHandler.ORIGForm))
//				.getObjectForm();
		
		ApplicationGroupDataM applicationGroup =  FormControl.getOrigObjectForm(request);	
		ArrayList<ApplicationDataM> borrowerApplications = applicationGroup
				.filterApplicationCardTypeLifeCycle(PRODUCT_CREDIT_CARD,APPLICATION_CARD_TYPE_BORROWER);
		ArrayList<HashMap<String, Object>> cardTypeLists = new ArrayList<>();
		if (!Util.empty(borrowerApplications)) {
			for (ApplicationDataM borrowerItem : borrowerApplications) {
				CardDataM borrowerCard = borrowerItem.getCard();
				// String borrowerApplicationCardType = borrowerCard.getApplicationType();
				// String borrowerUniqueId = borrowerItem.getApplicationRecordId();
				String borrowerCardTypeId = borrowerCard.getCardType();
				HashMap<String, Object> borrowerCardInfo = CardInfoUtil.getCardInfo(borrowerCardTypeId);
				logger.debug("borrowerCardInfo >>" + borrowerCardInfo);
				String borrowerCardCode = SQLQueryEngine.display(borrowerCardInfo, "CARD_CODE");
				String borrowerCardDesc = CacheControl.getName(FIELD_ID_CARD_TYPE, borrowerCardCode, "DISPLAY_NAME");
				// String borrowerCardLevel = SQLQueryEngine.display(borrowerCardInfo, "CARD_LEVEL");
				HashMap<String, Object> card = new HashMap<String, Object>();
				card.put("CODE", borrowerItem.getApplicationRecordId() + "_" + borrowerCardCode);
				card.put("VALUE", borrowerCardDesc);
				
				String roleId = FormControl.getFormRoleId(request);
				if(ADD_CARD_NO_APPLICATION_ROLE.equals(roleId)){
					PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
					if(!Util.empty(personalInfo)){
						if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getVerificationResult()) && !Util.empty(personalInfo.getVerificationResult().getPolicyRules())){
							cardTypeLists.add(card);
						}
					}
				}
				else if(!DECISION_FINAL_DECISION_CANCEL.equals(borrowerItem.getFinalAppDecision()) && !DECISION_FINAL_DECISION_REJECT.equals(borrowerItem.getFinalAppDecision())){
					cardTypeLists.add(card);
				}
								
			}
		}
		String formId = FormControl.getFormId(request);
		logger.debug("formId : "+formId);		
		if(!Util.empty(cardTypeLists) && !HtmlUtil.VIEW.equals(displayMode) && !"REKEY_SENSITIVE_FORM".equals(formId)){
			logger.debug("cardTypeLists : "+cardTypeLists);		
			removeCardSelect(cardTypeLists, request);
		}
		logger.debug("cardTypeLists Size:" + cardTypeLists.size());
		return cardTypeLists;
	}

	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId, HttpServletRequest request) {

	}

	public ArrayList<HashMap<String, Object>> removeCardSelect(ArrayList<HashMap<String, Object>> cardSelect, HttpServletRequest request) {
//		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
//		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM) EntityForm.getObjectForm();
		Object masterObjectForm = FormControl.getMasterObjectForm(request);
		ArrayList<ApplicationDataM> supplemantaryApplications = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			supplemantaryApplications = applicationGroup.filterApplicationLifeCycle();
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			supplemantaryApplications = personalApplicationInfo.filterApplicationLifeCycle();
		}
		if(null != supplemantaryApplications){
			for (ApplicationDataM supplemantaryItem : supplemantaryApplications) {
				CardDataM card = supplemantaryItem.getCard();
				if(!Util.empty(card)){
					if(null !=card && !Util.empty(card.getCardType())){
						if(!APPLICATION_CARD_TYPE_BORROWER.equals(card.getApplicationType())){
							HashMap<String,Object> CardInfo = CardInfoUtil.getCardInfo(card.getCardType());	
							String CardCode = SQLQueryEngine.display(CardInfo,"CARD_CODE");
							if(!Util.empty(cardSelect)){
								for(int i=0;i<cardSelect.size();i++){
									String selectCardCode = (String)cardSelect.get(i).get("CODE");
									selectCardCode = selectCardCode.split("_")[1];
									if(selectCardCode.equals(CardCode)){
										logger.debug("CARD REMOVE CODE : "+CardCode);
										cardSelect.remove(i);
									}						
								}
							}
						}
					}
				}
			}	
		}
		logger.debug("removeCardSelect >>> "+cardSelect.size());	
		return cardSelect;
	}

	@Override
	public void init(Object objectForm) {
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}
}
