package com.eaf.orig.ulo.pl.ajax;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.UserNameProperties;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFUVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class AddFollowDetail implements AjaxDisplayGenerateInf{   
	Logger logger = Logger.getLogger(this.getClass());	
	public static final String OTHER_PHONENO_CODE = "99";
	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		PLApplicationDataM plApplicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
				
		String personal = request.getParameter("fdPersonalType");		
		String addressType  = request.getParameter("fdAddressType");
		String telStatus = request.getParameter("fdPhoneStatus");
		String phoneNo = request.getParameter("fdPhoneNo"); 
		String remark = request.getParameter("fdRemark");
			
		if(OTHER_PHONENO_CODE.equals(phoneNo)){
			phoneNo = request.getParameter("fdOtherPhoneNo");
		}
		
		logger.debug("[getDisplayObject] PersonalType "+personal);
		
		PLPersonalInfoDataM plPersonalInfoM = plApplicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		
		PLXRulesVerificationResultDataM plXrulesVerResultM = plPersonalInfoM.getXrulesVerification();
		
		if(plXrulesVerResultM == null){
			plXrulesVerResultM = new PLXRulesVerificationResultDataM();
			plPersonalInfoM.setXrulesVerification(plXrulesVerResultM);			
		}
		
		Vector<PLXRulesFUVerificationDataM> xRulesFUVerVect = plXrulesVerResultM.getxRulesFUVerificationDataMs();
		
		if(xRulesFUVerVect == null) xRulesFUVerVect = new Vector<PLXRulesFUVerificationDataM>();
		
		PLXRulesFUVerificationDataM xrulesFuVerM = new PLXRulesFUVerificationDataM();
		
		xrulesFuVerM.setSeq(xRulesFUVerVect.size()+1);
		xrulesFuVerM.setCreateDate(new Timestamp((new Date()).getTime()));
		xrulesFuVerM.setCreateBy(userM.getUserName());
		xrulesFuVerM.setUpdateBy(userM.getUserName());
		xrulesFuVerM.setPhoneVerStatus(telStatus);
		xrulesFuVerM.setContactType(personal);
		xrulesFuVerM.setAddressType(addressType);
		xrulesFuVerM.setPhoneNo(phoneNo);
		xrulesFuVerM.setRemark(remark);	
		
		plXrulesVerResultM.add(xrulesFuVerM);
		
		JsonObjectUtil jObjectUtil = new JsonObjectUtil();
		
		jObjectUtil.CreateJsonObject("TableFrame", createHtmlFollowDetailResult(xrulesFuVerM));
		
		return jObjectUtil.returnJson();
	}
	
	public String createHtmlFollowDetailResult(PLXRulesFUVerificationDataM xrulesFuVerM){
		StringBuilder str = new StringBuilder();						
		str.append("<tr class=\"ResultData\">");
			str.append("<td  width=\"3%\">");
			str.append(String.valueOf(xrulesFuVerM.getSeq()));
			str.append("</td>");
			str.append("<td  width=\"15%\">");
			str.append(DataFormatUtility.DateEnToStringDateTh(xrulesFuVerM.getCreateDate(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMM));
			str.append("</td>");
			str.append("<td  width=\"17%\">");
			str.append(HTMLRenderUtil.displayHTMLFieldIDDesc(xrulesFuVerM.getPhoneVerStatus(),68));
			str.append("</td>");
			str.append("<td  width=\"10%\">");	
			str.append(HTMLRenderUtil.displayHTMLFieldIDDesc(xrulesFuVerM.getContactType(),83));
			str.append("</td>");
			str.append("<td  width=\"15%\">");
			str.append(HTMLRenderUtil.displayHTMLFieldIDDesc(xrulesFuVerM.getAddressType(),12));	
			str.append("</td>");
			str.append("<td  width=\"15%\">");
			str.append(HTMLRenderUtil.displayHTML(xrulesFuVerM.getPhoneNo()));
			str.append("</td>");					
			str.append("<td  width=\"15%\">");
			str.append(HTMLRenderUtil.displayHTML(xrulesFuVerM.getRemark()));	
			str.append("</td>");
			str.append("<td  width=\"15%\">");
			str.append(HTMLRenderUtil.displayHTML(displayThFullName(xrulesFuVerM.getCreateBy())));	
			str.append("</td>");
		str.append("</tr>");		
		return str.toString();
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
}
