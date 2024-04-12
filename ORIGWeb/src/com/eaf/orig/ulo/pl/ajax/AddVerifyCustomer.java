package com.eaf.orig.ulo.pl.ajax;

import java.sql.Timestamp; 
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPhoneVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class AddVerifyCustomer implements AjaxDisplayGenerateInf{
   
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		PLApplicationDataM appM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
				
		String personal = request.getParameter("vercus-personal-type");		
		String addressType  = request.getParameter("vercus-address-type");
		String telStatus = request.getParameter("vercus-phonestatus");
		String phoneNo = request.getParameter("vercus-phoneno"); 
		String remark = request.getParameter("vercus-remark");		
				
		logger.debug("[getDisplayObject] PersonalType "+personal);
		
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}
		
		Vector<PLXRulesPhoneVerificationDataM> phoneVerVect = xrulesVerM.getVXRulesPhoneVerificationDataM();
		
		if(null == phoneVerVect){
			phoneVerVect = new Vector<PLXRulesPhoneVerificationDataM>();
			xrulesVerM.setVXRulesPhoneVerificationDataM(phoneVerVect);
		}
				
		PLXRulesPhoneVerificationDataM xrulesPhoneM = new PLXRulesPhoneVerificationDataM();
		int num = (null == phoneVerVect)?1:phoneVerVect.size();
			xrulesPhoneM.setSeq(phoneVerVect.size()+1);
			xrulesPhoneM.setCreateDate(new Timestamp((new Date()).getTime()));
			xrulesPhoneM.setUpdateDate(new Timestamp(new Date().getTime()));
			xrulesPhoneM.setPhoneVerStatus(telStatus);
			xrulesPhoneM.setContactType(personal);
			xrulesPhoneM.setAddressType(addressType);
			xrulesPhoneM.setTelno(phoneNo);
			xrulesPhoneM.setRemark(remark);
			xrulesPhoneM.setCreateBy(userM.getUserName());		
		
		xrulesVerM.add(xrulesPhoneM);
		
		JsonObjectUtil jObjectUtil = new JsonObjectUtil();
		
		jObjectUtil.CreateJsonObject("verify-cutomer-table", CreateHtmlVerifyCustomerResult(xrulesPhoneM,num));
		
		return jObjectUtil.returnJson();
	}
	
	public String CreateHtmlVerifyCustomerResult(PLXRulesPhoneVerificationDataM xrulesPhoneM ,int num){		
		ORIGCacheUtil origCache = new ORIGCacheUtil();
		StringBuilder str = new StringBuilder();	
		String styleTr = (num%2==0)?"ResultEven":"ResultOdd";
		str.append("<tr class=\""+styleTr+"\">");
			str.append("<td>");
			str.append(String.valueOf(xrulesPhoneM.getSeq()));
			str.append("</td>");
			str.append("<td>");
			str.append(DataFormatUtility.TimestampEnToStringDateTh(xrulesPhoneM.getCreateDate(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS24));
			str.append("</td>");
			str.append("<td>");
			str.append(HTMLRenderUtil.displayHTMLFieldIDDesc(xrulesPhoneM.getPhoneVerStatus(),68));
			str.append("</td>");
			str.append("<td>");	
			str.append(HTMLRenderUtil.displayHTMLFieldIDDesc(xrulesPhoneM.getContactType(),66));
			str.append("</td>");
			str.append("<td>");
			str.append(HTMLRenderUtil.displayHTMLFieldIDDesc(xrulesPhoneM.getAddressType(),12));	
			str.append("</td>");
			str.append("<td>");
			str.append(HTMLRenderUtil.displayHTML(xrulesPhoneM.getTelno()));
			str.append("</td>");					
			str.append("<td><div class='textL'>");
			str.append(HTMLRenderUtil.displayHTML(xrulesPhoneM.getRemark()));	
			str.append("</div></td>");
			str.append("<td>");
			str.append(HTMLRenderUtil.displayHTML(origCache.GetUserNameByuserID(xrulesPhoneM.getCreateBy())));	
			str.append("</td>");
		str.append("</tr>");		
		return str.toString();
	}
}
