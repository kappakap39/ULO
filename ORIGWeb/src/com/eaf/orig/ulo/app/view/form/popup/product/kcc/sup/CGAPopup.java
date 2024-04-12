package com.eaf.orig.ulo.app.view.form.popup.product.kcc.sup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class CGAPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CGAPopup.class);
	
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	String ROLE_DE1_1 = SystemConstant.getConstant("ROLE_DE1_1");
	
	private String subformId = "SUP_CGA_POPUP";
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String	supName =request.getParameter("SUB_NAME_"+PRODUCT_CRADIT_CARD);
	String	cardLevel =request.getParameter("CARD_LEVEL_"+PRODUCT_CRADIT_CARD);
	String	percentLimitMaincard =request.getParameter("PERCENT_LIMIT_MAINCARD_"+PRODUCT_CRADIT_CARD);
	String	percentLimit =request.getParameter("PERCENT_LIMIT");
	String referralCardNo = request.getParameter("REFERRAL_CARD_NO_"+PRODUCT_CRADIT_CARD);
	String	channelNo =request.getParameter("CHANNEL_NO_"+PRODUCT_CRADIT_CARD);
	
	
	FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
	String objectRoleId = flowControl.getRole();

	logger.debug("supName >> "+supName);
	logger.debug("cardLevel >> "+cardLevel);
	logger.debug("percentLimitMaincard >> "+percentLimitMaincard);
	logger.debug("percentLimit >> "+percentLimit);
	logger.debug("referralCardNo >> "+referralCardNo);
	logger.debug("channelNo >> "+channelNo);
	ApplicationDataM application = (ApplicationDataM)appForm;
	LoanDataM loan = application.getLoan();
	loan.setRequestLoanAmt(FormatUtil.toBigDecimal(percentLimit,true));
	CardDataM card = application.getCard();
	card.setCgaCode(referralCardNo);
	card.setCgaApplyChannel(channelNo);
	card.setPercentLimitMaincard(percentLimitMaincard);		
	card.setCardLevel(cardLevel);
	
		if(Util.empty(channelNo) || Util.empty(referralCardNo)){
			if(ROLE_DE1_1.equals(objectRoleId)){
				card.setCompleteFlag(COMPLETE_FLAG_Y);
			}else{
				card.setCompleteFlag(COMPLETE_FLAG_N);
			}
		}else{
			card.setCompleteFlag(COMPLETE_FLAG_Y);
		}
	}
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationDataM applicationItem =(ApplicationDataM)appForm;
		CardDataM card = applicationItem.getCard();
		LoanDataM loan = applicationItem.getLoan();
		FormErrorUtil formError = new FormErrorUtil(applicationItem);
		formError.mandatory(subformId,"CHANNEL_NO",card.getCgaCode(),request);
		formError.mandatory(subformId,"CHANNEL_NO",card.getCgaApplyChannel(),request);
		formError.mandatory(subformId,"PERCENT_LIMIT_MAINCARD",card.getPercentLimitMaincard(),request);
		formError.mandatory(subformId,"PERCENT_LIMIT",applicationItem,request);
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={};
		try {
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			ApplicationDataM application = ((ApplicationDataM)objectForm);	
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			logger.debug("personalInfo.getPersonalType() >>"+personalInfo.getPersonalType());
			logger.debug("applicationGroup.getApplicationGroupId() >>"+applicationGroup.getApplicationGroupId());
			 for(String elementId:filedNames){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
				 fieldElement.setElementGroupId(application.getApplicationRecordId());
//				 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
				 fieldElements.add(fieldElement);
			 }
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	
	}
	
	

}
