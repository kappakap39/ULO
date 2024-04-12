package com.eaf.orig.ulo.pl.app.view.webaction;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class SaveEditPopupWebAction extends WebActionHelper implements WebAction{
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public Event toEvent(){
		return null;
	}

	@Override
	public boolean requiredModelRequest(){
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response){
		return false;
	}

	@Override
	public boolean preModelRequest(){		
		logger.debug("preModelRequest >> ");	
		JsonObjectUtil jobj = new JsonObjectUtil();
		try{
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			
			PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(getRequest());			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);			
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();			
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}	
			
			String displayModeCisNo = getRequest().getParameter("display-mode-cisno");
			String displayModeClassifyLevel = getRequest().getParameter("display-mode-classifylevel");
			String displayModeBlockCode = getRequest().getParameter("display-mode-worstblockcode");
			String displayModeNpl = getRequest().getParameter("display-mode-npl");
			String displayModeAmcTamc = getRequest().getParameter("display-mode-amctamc");
			String displayModeBankruptcy = getRequest().getParameter("display-mode-bankruptcy");
						
			if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayModeCisNo)){
				String editCisNo = getRequest().getParameter("edit-cis-no");
				xrulesVerM.setEditCisNo(editCisNo);
				personalM.setCisNo(editCisNo);
				jobj.CreateJsonObject("result_1038", HTMLRenderUtil.displayHTML(HTMLRenderUtil.DisplayReplaceLineWithNull(editCisNo)));
			}
			
			if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayModeClassifyLevel)){
				String editClassifyLevel = getRequest().getParameter("edit-classify-level");
				xrulesVerM.setEditClassifyLevel(editClassifyLevel);
			}
			
			if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayModeBlockCode)){
				String editBlockCode = getRequest().getParameter("edit-cc-blockcode");
				xrulesVerM.setEditBlockCode(editBlockCode);
			}
			
			if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayModeNpl)){
				String editNpl = getRequest().getParameter("edit-npl");
				xrulesVerM.setEditNpl(editNpl);
			}
			
			if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayModeAmcTamc)){
				String editAmcTamc = getRequest().getParameter("edit-amctamc");
				xrulesVerM.setEditAmcTamc(editAmcTamc);
			}
			
			if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayModeBankruptcy)){
				String editBankruptcy = getRequest().getParameter("edit-bankruptcy");
				xrulesVerM.setEditBankruptcy(editBankruptcy);
			}
						
			BigDecimal cCCurrentBalance = DataFormatUtility.StringToBigDecimalEmptyNull(getRequest().getParameter("edit-cc-current-balance"));
			
			Date cCLastPaymentDate = DataFormatUtility.StringEnToDateEn(getRequest().getParameter("edit-cc-lastpayment-date")
																,DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S);
			
			BigDecimal kecCurrentBalance = DataFormatUtility.StringToBigDecimalEmptyNull(getRequest().getParameter("edit-kec-current-balance"));
			Date kecLastPaymentDate = DataFormatUtility.StringEnToDateEn(getRequest().getParameter("edit-kec-lastpayment-date")
																,DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S);
					
			xrulesVerM.setcCCurrentBalance(cCCurrentBalance);
			xrulesVerM.setcCLastPaymentDate(cCLastPaymentDate);
			xrulesVerM.setKecCurrentBalance(kecCurrentBalance);
			xrulesVerM.setKecLastPaymentDate(kecLastPaymentDate);
			xrulesVerM.setEditUpdateBy(userM.getUserName());
			xrulesVerM.setEditUpdateDate(new Timestamp(new Date().getTime()));
			
		}catch (Exception e) {
			logger.fatal("Exception ",e);
		}
		jobj.ResponseJsonArray(getResponse());
		return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
