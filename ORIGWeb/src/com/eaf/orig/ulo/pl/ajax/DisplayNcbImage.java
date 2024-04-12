package com.eaf.orig.ulo.pl.ajax;

import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.ncb.util.NCBConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
public class DisplayNcbImage implements AjaxDisplayGenerateInf{
	
	Logger logger = Logger.getLogger(DisplayNcbImage.class);

	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {		
		PLOrigFormHandler  formHandler = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		PLApplicationDataM applicationM = formHandler.getAppForm();	
		if(null == applicationM){
			applicationM = new PLApplicationDataM();	
		}		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		String formID = formHandler.getFormID();
		String searchType = (String) request.getSession().getAttribute("searchType");		
		
		ORIGFormUtil formUtil = new ORIGFormUtil();
		String displayMode = formUtil.getDisplayMode("VERIFICATION_SUBFORM", userM.getRoles(), applicationM.getJobState(), formID, searchType);
		
		/**#SeptemWi Fix NcbResult Not Empty Cannot Select Image**/		
		if(!OrigUtil.isEmptyString(xrulesVerM.getNCBCode()) && !NCBConstant.ncbResultCode.SEND_BACK.equals(xrulesVerM.getNCBCode())){
			displayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
		}
		
		logger.debug("displayMode >> "+displayMode);
		
		Vector<PLApplicationImageDataM> appImageVect = applicationM.getApplicationImageVect();		
		if(OrigUtil.isEmptyVector(appImageVect)){
			return null;		
		}
		Vector<PLApplicationImageSplitDataM> imageVect = new Vector<PLApplicationImageSplitDataM>();		
		for(PLApplicationImageDataM imageM :appImageVect){
			if(!ORIGUtility.isEmptyVector(imageM.getAppImageSplitVect())){
				for(PLApplicationImageSplitDataM imageSplitM :imageM.getAppImageSplitVect()){
					imageVect.add(imageSplitM);
				}
			}				
		}				
		if(OrigUtil.isEmptyVector(imageVect)){
			return null;	
		}
		
		StringBuilder str = new StringBuilder("");		
		str.append("<table border='0' width='100%' cellpadding='0' cellspacing='0'><tr width='100%' align='center' valign='middle'>");
		for(int i=1; i<=imageVect.size(); i++){					
			PLApplicationImageSplitDataM splitM = (PLApplicationImageSplitDataM) imageVect.get(i-1);			
			str.append("<td width='25%' align='center' valign='middle'>");
			if(i==1) str.append("<input id='ncb-active-image' type='hidden' value='"+splitM.getImgID()+"'/>");
			str.append(CreateElementImage(splitM, request ,displayMode));
			str.append("</td>");
			if(i == imageVect.size() && imageVect.size()%4 !=0)
				for(int s=0; s<4-imageVect.size()%4; s++)
					str.append("<td width='25%' align='center' valign='middle'></td>");		
			if(i!=1 && i%4==0 && i!=imageVect.size())
				str.append("</tr><tr>");			
		}		
		str.append("</tr></table>");		
		return str.toString();
	} 
	public String CreateElementImage(PLApplicationImageSplitDataM splitM ,HttpServletRequest request ,String displayMode){
		StringBuilder str = new StringBuilder();
		String javascript = "onclick=\"LoadNcbImg('"+splitM.getImgID()+"')\"";		
		str.append("<table border='0' width='100%' cellpadding='0' cellspacing='0'>");
			str.append("<tr height='15'><td align='center' valign='middle'>&nbsp;</td></tr>");
			str.append("<tr width='100%' align='center' valign='middle'> ");
				str.append("<td width='100%' align='center' valign='middle'>");
				str.append("<div "+javascript+">");
				long time = new Date().getTime();
					str.append("<img id='ncbdoc-"+splitM.getFileName()+"' src='GetImage?imgID="+splitM.getImgID()+"&type="+OrigConstant.TypeImage.TYPE_IMAGE_S+"&"+time+"' class='target'/>");
				str.append("</div>");
				str.append("</td>");
			str.append("</tr>");
			str.append("<tr width='100%' align='center' valign='middle'>");
			str.append("<td width='100%' align='center' valign='middle' height='15'>");	
			str.append(getImageNcbCheckBox(splitM.getImgID(), request ,displayMode));
			str.append("</td>");
			str.append("</tr>");
		str.append("</table>");		
		return str.toString();
	}
	public String getImageNcbCheckBox(String imageId ,HttpServletRequest request ,String displayMode){
		StringBuilder str = new StringBuilder();
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);		
		PLPersonalInfoDataM personInfoM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		Vector<PLNCBDocDataM> ncbDocVect = personInfoM.getNcbDocVect();	
		if(OrigUtil.isEmptyVector(ncbDocVect)) ncbDocVect = new Vector<PLNCBDocDataM>();
		boolean isCheck = false;
		for(PLNCBDocDataM ncbM :ncbDocVect){
			if(imageId.equals(ncbM.getImgID())){
				isCheck = true;
				break;
			}
		}
		if(isCheck){
			str.append(HTMLRenderUtil.displayCheckBox(HTMLRenderUtil.COMPARE_CHECKBOX_VALUE,"checkBox_"+imageId
										,HTMLRenderUtil.COMPARE_CHECKBOX_VALUE,displayMode,"") );
		}else{
			str.append(HTMLRenderUtil.displayCheckBox("","checkBox_"+imageId,HTMLRenderUtil.COMPARE_CHECKBOX_VALUE,displayMode,""));
		}		
		return str.toString();
	}
}
