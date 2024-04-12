package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.cache.properties.UserNameProperties;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFUVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class SaveFollowDetailWebAction  extends WebActionHelper implements WebAction {
	
	static Logger logger = Logger.getLogger(SaveFollowDetailWebAction.class);
	
	public static final String OTHER_PHONENO_CODE = "99";
	@Override
	public Event toEvent(){
		return null;
	}

	@Override
	public boolean requiredModelRequest(){
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest() {
		try{
			UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			
			PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
			PLApplicationDataM applicationM = formHandler.getAppForm();
				applicationM.setUpdateBy(userM.getUserName());
			
			String personal = getRequest().getParameter("fdPersonalType");		
			String addressType  = getRequest().getParameter("fdAddressType");
			String telStatus = getRequest().getParameter("fdPhoneStatus");
			String phoneNo = getRequest().getParameter("fdPhoneNo"); 
			String remark = getRequest().getParameter("fdRemark");
				
			if(OTHER_PHONENO_CODE.equals(phoneNo)){
				phoneNo = getRequest().getParameter("fdOtherPhoneNo");
			}
			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);			
			}
			
			Vector<PLXRulesFUVerificationDataM> xRulesFUVerVect = xrulesVerM.getxRulesFUVerificationDataMs();
			
			if(xRulesFUVerVect == null){
				xRulesFUVerVect = new Vector<PLXRulesFUVerificationDataM>();
				xrulesVerM.setxRulesFUVerificationDataMs(xRulesFUVerVect);
			}
			
			PLXRulesFUVerificationDataM xrulesFuVerM = new PLXRulesFUVerificationDataM();		
				xrulesFuVerM.setSeq(xRulesFUVerVect.size()+1);
				
//				#septemwi comment change to set create date at save
//				xrulesFuVerM.setCreateDate(new Timestamp((new Date()).getTime()));
				
				xrulesFuVerM.setCreateBy(userM.getUserName());
				xrulesFuVerM.setUpdateBy(userM.getUserName());
				xrulesFuVerM.setPhoneVerStatus(telStatus);
				xrulesFuVerM.setContactType(personal);
				xrulesFuVerM.setAddressType(addressType);
				xrulesFuVerM.setPhoneNo(phoneNo);
				xrulesFuVerM.setRemark(remark);	
				xrulesFuVerM.setPersonalID(personalM.getPersonalID());
			
			ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();	
				origBean.saveFollowDetail(applicationM,xrulesFuVerM);
			
			xrulesVerM.add(xrulesFuVerM);	
			
			JsonObjectUtil json = new JsonObjectUtil();		
				json.CreateJsonObject("TableFrame", CreateHTML(xrulesFuVerM));	
				json.CreateJsonObject("sms-followup", applicationM.getSmsFollowUp());
				json.ResponseJsonArray(getResponse());
				
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return true;
	}
	
	public String CreateHTML(PLXRulesFUVerificationDataM xrulesFuVerM){
		StringBuilder HTML = new StringBuilder();						
		HTML.append("<tr class=\"ResultData\">");
			HTML.append("<td  width=\"3%\">");
			HTML.append(String.valueOf(xrulesFuVerM.getSeq()));
			HTML.append("</td>");
			HTML.append("<td  width=\"15%\">");
			HTML.append(DataFormatUtility.DateEnToStringDateTh(xrulesFuVerM.getCreateDate(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS24));
			HTML.append("</td>");
			HTML.append("<td  width=\"17%\">");
			HTML.append(HTMLRenderUtil.displayHTMLFieldIDDesc(xrulesFuVerM.getPhoneVerStatus(),68));
			HTML.append("</td>");
			HTML.append("<td  width=\"10%\">");	
			HTML.append(HTMLRenderUtil.displayHTMLFieldIDDesc(xrulesFuVerM.getContactType(),83));
			HTML.append("</td>");
			HTML.append("<td  width=\"15%\">");
			HTML.append(HTMLRenderUtil.displayHTMLFieldIDDesc(xrulesFuVerM.getAddressType(),12));	
			HTML.append("</td>");
			HTML.append("<td  width=\"15%\">");
			HTML.append(HTMLRenderUtil.displayHTML(xrulesFuVerM.getPhoneNo()));
			HTML.append("</td>");					
			HTML.append("<td  width=\"15%\">");
			HTML.append(HTMLRenderUtil.displayHTML(xrulesFuVerM.getRemark()));	
			HTML.append("</td>");
			HTML.append("<td  width=\"15%\">");
			HTML.append(HTMLRenderUtil.displayHTML(displayThFullName(xrulesFuVerM.getCreateBy())));	
			HTML.append("</td>");
		HTML.append("</tr>");		
		return HTML.toString();
	}
	
	private String displayThFullName(String userName){
		ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
		try{
			Vector<UserNameProperties> vUser = (Vector<UserNameProperties>)origCache.loadUserNameCache(OrigConstant.ACTIVE_FLAG);
			return HTMLRenderUtil.displayThFullName(userName, vUser);
		}catch(Exception e){
			return OrigConstant.NOT_HAVE_NAME;
		}
	}	
	
	@Override
	public int getNextActivityType() {
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken() {		
		return false;
	}

}
