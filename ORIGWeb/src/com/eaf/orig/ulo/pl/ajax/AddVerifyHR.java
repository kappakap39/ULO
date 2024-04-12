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
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesHRVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class AddVerifyHR implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		PLApplicationDataM appM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		if(null == appM) appM = new PLApplicationDataM();
		
		String telStatus = request.getParameter("verhr-phonestatus");
		String phoneNo = request.getParameter("verhr-phoneno"); 
		String otherPhoneNo = request.getParameter("verhr-other-phoneno"); 
		String remark = request.getParameter("verhr-remark");	
		
		logger.debug("[getDisplayObject]..phoneNo "+phoneNo);
		logger.debug("[getDisplayObject]..telStatus "+telStatus);
		logger.debug("[getDisplayObject]..remark "+remark);
		
		int seq = 0;
		
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		if(null == personM) personM = new PLPersonalInfoDataM();
		
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();			
			personM.setXrulesVerification(xrulesVerM);
		}
		
		Vector<PLXRulesHRVerificationDataM> xrulesVerHRVect = xrulesVerM.getxRulesHRVerificationDataMs();
		
		seq = (xrulesVerHRVect == null)? 1 : xrulesVerHRVect.size()+1;
		
		PLXRulesHRVerificationDataM hRVerM = new PLXRulesHRVerificationDataM();
		
		hRVerM.setSeq(seq);
		hRVerM.setCreateDate(new Timestamp((new Date()).getTime()));
		hRVerM.setUpdateDate(new Timestamp((new Date()).getTime()));
		
		hRVerM.setPhoneVerStatus(telStatus);
		
		if(AddFollowDetail.OTHER_PHONENO_CODE.equalsIgnoreCase(phoneNo)){
			phoneNo = otherPhoneNo;
		}
		
		hRVerM.setPhoneNo(phoneNo);
		hRVerM.setRemark(remark);
		hRVerM.setCreateBy(userM.getUserName());
		
		xrulesVerM.add(hRVerM);
		
		JsonObjectUtil jObjectUtil = new JsonObjectUtil();
		int num = (xrulesVerHRVect == null)?1:xrulesVerHRVect.size();
		jObjectUtil.CreateJsonObject("table-verhr-result", this.createHtmlHRVerifyResult(hRVerM,num));
		
		return jObjectUtil.returnJson();
	}

	public String createHtmlHRVerifyResult(PLXRulesHRVerificationDataM hRVerM , int num){
		StringBuilder str = new StringBuilder();
		String styleTr = (num%2==0)?"ResultEven":"ResultOdd";	
		ORIGCacheUtil origCache = new ORIGCacheUtil();
		str.append("<tr class=\""+styleTr+"\">");
			str.append("<td>");
			str.append(String.valueOf(hRVerM.getSeq()));
			str.append("</td>");
			str.append("<td>");
			str.append(DataFormatUtility.TimestampEnToStringDateTh(hRVerM.getCreateDate(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS24));
			str.append("</td>");
			str.append("<td>");
			str.append(HTMLRenderUtil.displayHTMLFieldIDDesc(hRVerM.getPhoneVerStatus(),68));
			str.append("</td>");
			str.append("<td>");
			str.append(HTMLRenderUtil.displayHTML(hRVerM.getPhoneNo()));
			str.append("</td>");					
			str.append("<td><div class='textL'>");
			str.append(HTMLRenderUtil.displayHTML(hRVerM.getRemark()));	
			str.append("</div></td>");
			str.append("<td>");
			str.append(HTMLRenderUtil.displayHTML(origCache.GetUserNameByuserID(hRVerM.getCreateBy())));	
			str.append("</td>");
		str.append("</tr>");
		
		return str.toString();
	}
}
