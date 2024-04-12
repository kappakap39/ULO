package com.eaf.orig.ulo.app.view.form.popup.product.kcc;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.control.util.SaleInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

@SuppressWarnings("serial")
public class MuangThaiPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(MuangThaiPopup.class);
	String SALE_TYPE_MTL_SALES = SystemConstant.getConstant("SALE_TYPE_MTL_SALES");
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	private String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	private String subformId = "MUANG_THAI_POPUP";
	@SuppressWarnings("unchecked")
	@Override
	public void setProperties(HttpServletRequest request, Object appForm){
		String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		String salesId  = request.getParameter("MTL_SALES_ID_"+PRODUCT_CRADIT_CARD);
		String salesPhoneNo  = request.getParameter("MTL_SALES_PHONE_NO_"+PRODUCT_CRADIT_CARD);
			   salesPhoneNo = FormatUtil.removeDash(salesPhoneNo);
		String membershipno = request.getParameter("MTL_CUSTOMER_NO_"+PRODUCT_CRADIT_CARD);
		logger.debug("salesId :"+salesId);
		logger.debug("salesPhoneNo :"+salesPhoneNo);	
		logger.debug("membershipno :"+membershipno);
		HashMap<String,Object> supplementaryApplicant = (HashMap<String,Object>)appForm;
		SaleInfoDataM saleInfo = (SaleInfoDataM)supplementaryApplicant.get("SALE_INFO");
		ApplicationDataM appItem = (ApplicationDataM)supplementaryApplicant.get("APPLICATION");
		CardDataM card = appItem.getCard();
		if(Util.empty(appItem)){
			appItem = new ApplicationDataM();
			supplementaryApplicant.put("APPLICATION",appItem);
		}	
		if(Util.empty(card)){
			card = new CardDataM();
		}
		if(Util.empty(saleInfo)){
			saleInfo = new SaleInfoDataM();
			supplementaryApplicant.put("SALE_INFO",saleInfo);
		}			
		saleInfo.setSalesType(SALE_TYPE_MTL_SALES);
		saleInfo.setSalesId(salesId);
		SaleInfoUtil.mapSaleInfoDetails(saleInfo);
		saleInfo.setSalesPhoneNo(salesPhoneNo);
		if(!Util.empty(card)){
			card.setMembershipNo(membershipno);
			if(Util.empty(membershipno)){
				card.setCompleteFlag(COMPLETE_FLAG_N);
			}else{
				card.setCompleteFlag(COMPLETE_FLAG_Y);
			}
			logger.debug("card complete flag :MT: "+card.getCompleteFlag());
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		HashMap<String,Object> supplementaryApplicant = (HashMap<String,Object>)appForm;
		@SuppressWarnings("unused")
		SaleInfoDataM saleInfo = (SaleInfoDataM)supplementaryApplicant.get("SALE_INFO");
		ApplicationDataM appItem = (ApplicationDataM)supplementaryApplicant.get("APPLICATION");
		CardDataM card = appItem.getCard();
		String subformId = "MUANG_THAI_POPUP";
		logger.debug("card complete flag :validateForm: "+card.getMembershipNo()+" XXXXXX "+card.getCompleteFlag());
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil(appItem);
//		formError.mandatory(subformId,"SEND_DOC",personalInfo.getMailingAddress(),request);	
		formError.mandatory(subformId,"MTL_CUSTOMER_NO",card.getMembershipNo(),request);
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request){
		return false;
	}
	
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {	
		String ApplicationRecordId = getSubformData("ApplicationRecordId");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ApplicationDataM applicationdataM = applicationGroup.getApplicationById(ApplicationRecordId);
		PersonalInfoDataM perssonalApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		CardDataM card = applicationdataM.getCard();
		if(Util.empty(card)){
			card = new CardDataM();
		}
		HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(card.getCardType());
		String cardCode = SQLQueryEngine.display(CardInfoList, "CARD_CODE");
		String product = applicationdataM.getProduct();
		String cardCodeRefId = CompareSensitiveFieldUtil.getCardCodeRefId(perssonalApplicant, product, cardCode);
		logger.debug("cardCodeRefId >>"+cardCodeRefId);
		logger.debug("cardMembershipNo data >>"+card.getMembershipNo());
		card.setMembershipNo(formValue.getValue("MTL_CUSTOMER_NO", "MTL_CUSTOMER_NO", card.getMembershipNo()));
		logger.debug("after set cardMembershipNo data >>"+card.getMembershipNo());
		if(Util.empty(card.getMembershipNo())){
			card.setCompleteFlag(COMPLETE_FLAG_N);
		}
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={"MTL_CUSTOMER_NO"};
		try {
			ApplicationGroupDataM applicationGroup =  FormControl.getOrigObjectForm(request);
			ApplicationDataM application = ((ApplicationDataM)objectForm);	
//			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			logger.debug("personalInfo.getPersonalType() >>"+personalInfo.getPersonalType());
			if(PERSONAL_TYPE.equals(personalInfo.getPersonalType())){
			 for(String elementId:filedNames){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
				 fieldElement.setElementGroupId(application.getApplicationRecordId());
//				 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
				 fieldElements.add(fieldElement);
			 	}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
}
