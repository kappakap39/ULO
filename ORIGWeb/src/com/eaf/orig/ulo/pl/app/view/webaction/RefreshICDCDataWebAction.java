package com.eaf.orig.ulo.pl.app.view.webaction;

import java.io.PrintWriter;
import java.util.Vector;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLProjectCodeDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class RefreshICDCDataWebAction extends WebActionHelper implements WebAction{
	String nextAction;
	Logger logger = Logger.getLogger(RefreshICDCDataWebAction.class);
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
		
		String cardNo = getRequest().getParameter("card_info_card_no");
		
		logger.debug("..cardno >> "+cardNo);
		
		JsonObjectUtil json = new JsonObjectUtil();
		try{
			
			PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
			PLApplicationDataM applicationM = formHandler.getAppForm();
			
			ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();		

			PLAccountCardDataM accountCardM = origBean.loadAccountCardByCardNo(cardNo);			
			String identification_no = accountCardM.getIdNo();	
			
			logger.debug("..identification_no >> "+identification_no);
			
			if(null != applicationM && !OrigUtil.isEmptyString(identification_no)){
				
				PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
				personalM.setIdNo(identification_no);
				
				PLCardDataM cardM = personalM.getCardInformation();
				if(null == cardM){
					cardM = new PLCardDataM();
					personalM.setCardInformation(cardM);
				}
								
				cardM.setPersonalId(personalM.getPersonalID());
				cardM.setCardNo(accountCardM.getCardNo());
				cardM.setCardFace(accountCardM.getCardFace());
				cardM.setCardType(accountCardM.getCardType());
				cardM.setEmbossName(accountCardM.getEmbossName());
									
				ORIGCacheUtil origc = new ORIGCacheUtil();    
			  	Vector cardInfoMasterVect = origc.loadCacheByActive(OrigConstant.CacheName.CARD_TYPE);
			  
			  	if(cardInfoMasterVect==null){
			  		cardInfoMasterVect = new Vector();
			  	}
			   	Vector cardFrontMasterVect = origc.loadCacheByActive(OrigConstant.CacheName.CARD_FACE);

			  	if(cardFrontMasterVect==null){
			  		cardFrontMasterVect = new Vector();
			  	}
				
			  	String cardType = cardM.getCardType();
			  	String cardFace = cardM.getCardFace();
			  	
			  	if(OrigUtil.isEmptyString(cardType)){cardType = OrigConstant.PRODUCT_KEC;}
			  	if(OrigUtil.isEmptyString(cardFace)){cardFace = OrigConstant.PRODUCT_KEC_NM;}
			  	
			  	json.CreateJsonObject("td_card_info_card_type",HTMLRenderUtil.displaySelectTagScriptAction_ORIG(cardInfoMasterVect,HTMLRenderUtil.displayHTML(cardType),"card_info_card_type","VIEW",""));
				
			  	json.CreateJsonObject("td_card_info_card_face",HTMLRenderUtil.displaySelectTagScriptAction_ORIG(cardFrontMasterVect,HTMLRenderUtil.displayHTML(cardFace),"card_info_card_face","VIEW",""));
				
				personalM.setCardInformation(cardM);
				
				personalM.setXrulesVerification(new PLXRulesVerificationResultDataM());	
				personalM.setCardLinkCustNo(null);
				
				applicationM.setProjectCode(accountCardM.getProjectCode());	
				
				PLProjectCodeDataM projectCodeM  = origBean.Loadtable(applicationM.getProjectCode());
				if(null == projectCodeM){
					projectCodeM = new PLProjectCodeDataM();
				}
				
				applicationM.setProjectCode(HTMLRenderUtil.replaceNull(projectCodeM.getProjectcode()));
				applicationM.setProductFeature(origc.getProductFeature(projectCodeM.getApplicationProperty(), applicationM.getBusinessClassId()));
				
				logger.debug("getProductFeature().. "+applicationM.getProductFeature());
				
				json.CreateJsonObject("tr-product_feature",HTMLRenderUtil.displaySelectTagScriptAction_ORIG(35, applicationM.getBusinessClassId(), applicationM.getProductFeature(), "product_feature", HTMLRenderUtil.DISPLAY_MODE_VIEW, ""));				
				json.CreateJsonObject("startdate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getStartprojectdate())));
				json.CreateJsonObject("div_startdate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getStartprojectdate())));
				json.CreateJsonObject("enddate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getEndprojectdate())));
				json.CreateJsonObject("div_enddate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getEndprojectdate())));
				json.CreateJsonObject("details", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getProjectdesc()));
				json.CreateJsonObject("div_details", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getProjectdesc()));
				json.CreateJsonObject("promotion", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getPromotion()));
				json.CreateJsonObject("div_promotion", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getPromotion()));
				json.CreateJsonObject("approvedate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getApprovedate())));
				json.CreateJsonObject("div_approvedate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getApprovedate())));
				json.CreateJsonObject("customergroup", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getCustomertype()));
				json.CreateJsonObject("div_customergroup", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getCustomertype()));
				json.CreateJsonObject("projectCode", applicationM.getProjectCode());
				json.CreateJsonObject("project_code", applicationM.getProjectCode());					
				json.CreateJsonObject("professionCode", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getProfessionCode()));
				json.CreateJsonObject("Priority", HTMLRenderUtil.displayHTML(projectCodeM.getPriority()));
				json.CreateJsonObject("application_property", origc.getORIGCacheDisplayNameDataM(110, projectCodeM.getApplicationProperty()));
				
				json.CreateJsonObject("div_projectcode", HTMLRenderUtil.DisplayPopUpProjectCode(applicationM.getProjectCode(),HTMLRenderUtil.DISPLAY_MODE_VIEW,"13","project_code","textbox-projectcode","13","...","button-popup"));
				json.CreateJsonObject("projectcode_displaymode", HTMLRenderUtil.DISPLAY_MODE_VIEW);
				json.CreateJsonObject("projectcode_buttonmode", HTMLRenderUtil.DISPLAY_MODE_VIEW);
			
				json.CreateJsonObject("identification_no",HTMLRenderUtil.replaceNull(identification_no));
				
			}						
		}catch(Exception e){
			logger.fatal("Exception >> ",e);
		}		
		
		try{
			getResponse().setContentType("application/json;charset=UTF-8");
			PrintWriter out = getResponse().getWriter();
			out.println(json.returnJson());
			out.close();
		}catch(Exception e){
			logger.fatal("ERROR >> "+e.getMessage());
		}
		return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
