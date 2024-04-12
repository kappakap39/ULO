package com.eaf.orig.ulo.app.view.form.sensitive;

//import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
//import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
//import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
//import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
//import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class RekeySensitiveHeader  extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(RekeySensitiveHeader.class);
	String DOC_TYPE_SP_SIGN_OFF = SystemConfig.getGeneralParam("DOC_TYPE_SP_SIGN_OFF");
	String PRODUCT_CODE_KEC = SystemConstant.getConstant("PRODUCT_CODE_KEC");
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {		
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
		CompareDataM compareData = (CompareDataM)objectElement;
		logger.debug(compareData);
		StringBuffer HTML = new StringBuffer();
		if(CompareDataM.RefLevel.PERSONAL.equals(compareData.getRefLevel())) {
			PersonalInfoDataM personalInfoM = CompareSensitiveFieldUtil.getPersonalInfoByUniqueId(applicationGroup, compareData);
			logger.debug("personalInfoM >> "+personalInfoM);
			if(null != personalInfoM){
				String personalType = personalInfoM.getPersonalType();
				logger.debug("personalType >> "+personalType);
				HTML.append("<div class='col-sm-12'>")
				.append("<div class='form-group'>")
				.append("<div class='row'><div class='col-xs-12'>")
				.append("<div class='col-sm-3' style='font-weight: bold;' >"+FormatUtil.display(CacheControl.getName(FIELD_ID_PERSONAL_TYPE,personalType))+"</div>")
				.append("<div class='col-sm-9'><div class='input-group'>");
				if(!Util.empty(personalInfoM)) {
					HTML.append(HtmlUtil.getFieldLabel(request, "M_TH_NAME","control-label input-group-btn"))
					.append(HtmlUtil.span("personal", FormatUtil.display(personalInfoM.getThFirstName())+"&nbsp;"
					+FormatUtil.display(personalInfoM.getThLastName()),"input-group-btn"));
				}
				HTML.append("</div></div>")
				.append("</div></div>")
				.append("</div></div>")
				.append("<div class='clearfix'></div>");		
			}
		}else if(CompareDataM.RefLevel.APPLICATION.equals(compareData.getRefLevel())) {
			String product = CompareSensitiveFieldUtil.getProductByRefId(compareData);
			HTML.append("<div class='col-sm-12'>")
			.append("<div class='form-group'>")
			.append("<div class='row'><div class='col-xs-12'>")
			.append("<div style='font-weight: bold;'>"+HtmlUtil.getFieldLabel(request, "PRODUCT","control-label")
					+":"+HtmlUtil.getHeaderLabel(request,"","",product)+"</div>")
			.append("</div></div>")
			.append("</div></div>")
			.append("<div class='clearfix'></div>");
		} else if(CompareDataM.RefLevel.ADDRESS.equals(compareData.getRefLevel())) {
//			String refID = compareData.getCurrentRefId();
//			String personalType = null;
//			String addressType = null;
//			if(!Util.empty(refID)) {
//				String[] tokens = refID.split(CompareDataM.MARKER);
//				personalType = tokens[0];
//				addressType = tokens[tokens.length-1];
//			}
//			HTML.append("<div class='col-sm-12'>");
//			HTML.append("<div class='form-group'>");
//			HTML.append("<div class='row'><div class='col-xs-12'>");
//			HTML.append("<div class='col-sm-8' style='font-weight: bold;'>"+HtmlUtil.getHeaderLabel(request,"", "","ADDRESS_DETAILS_"+addressType)+"</div>");
//			HTML.append("<div class='col-sm-4' style='font-weight: bold;'>"+FormatUtil.display(CacheControl.getName(SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE"),personalType))+"</div>");
//			HTML.append("</div></div>");
//			HTML.append("</div></div>");
//			HTML.append("<div class='clearfix'></div>");
		} else if(CompareDataM.RefLevel.CARD.equals(compareData.getRefLevel())) {
/*			String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");	 
			String refID = compareData.getRefId();
			if(Util.empty(refID)) {
				refID = compareData.getCurrentRefId();
			}
			String idNo = null;
			String product = null;
			String cardType = null;
			if(!Util.empty(refID)) {
				String[] tokens = refID.split(CompareDataM.MARKER);
				idNo = tokens[0];
				product = tokens[1];
				if(tokens.length > 2) {
					cardType = tokens[2];
				}
			}
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByIDNo(idNo);
			if(personalInfo == null) {
				personalInfo = new PersonalInfoDataM();
				personalInfo.setPersonalType(PersonalInfoDataM.PersonalType.APPLICANT);
			}
			String appType = null;
			if(PersonalInfoDataM.PersonalType.APPLICANT.equals(personalInfo.getPersonalType())) {
				appType = MAIN;
			} else {
				appType = SUP;
			}
			
			String CARD_TYPE_DESC =	CacheControl.getName(FIELD_ID_CARD_TYPE, cardType, "DISPLAY_NAME");
			String PERSONAL_TYPE_DESC =	CacheControl.getName(FIELD_ID_PERSONAL_TYPE, personalInfo.getPersonalType());
			HTML.append("<div class='col-sm-12'>");
			HTML.append("<div class='form-group'>");
			HTML.append("<div class='row'><div class='col-xs-12'>");
			HTML.append("<div  class='col-sm-6' style='font-weight: bold;' >"+FormatUtil.display(PERSONAL_TYPE_DESC)+"</div>");
			HTML.append("<div  class='col-sm-6' class='input-group'>");
			if(!Util.empty(personalInfo)) {
				HTML.append(FormatUtil.display(personalInfo.getThFirstName())+"&nbsp;"+FormatUtil.display(personalInfo.getThLastName()));
			}
			HTML.append("</div>");
			HTML.append("</div></div>");
			HTML.append("</div></div>");
			HTML.append("<div class='clearfix'></div>");
			
			HTML.append("<div class='col-sm-12'>");
			HTML.append("<div class='form-group'>");
			HTML.append("<div class='row'><div class='col-xs-12'>");
			HTML.append("<div class='col-sm-6' style='font-weight: bold;'>"+HtmlUtil.getFieldLabel(request, "REKEY_"+product+"_"+appType,"control-label")+" </div>");
			HTML.append("<div class='col-sm-6' style='font-weight: bold;'>"+CARD_TYPE_DESC+"</div>");
			HTML.append("</div></div>");
			HTML.append("</div></div>");
			HTML.append("<div class='clearfix'></div>");*/
		} else if(CompareDataM.RefLevel.PAYMENT_METHOD.equals(compareData.getRefLevel())) {
//			String refID = compareData.getCurrentRefId();
			String product = CompareSensitiveFieldUtil.getProductByRefId(compareData);
			String label = "PAYMENT_METHOD";
			if(!Util.empty(product)) {
				label = label+CompareDataM.MARKER+product;
			}
			HTML.append("<div class='col-sm-12'>")
			.append("<div class='form-group'>")
			.append("<div class='row'><div class='col-xs-12'>")
			.append("<div style='font-weight: bold;'>"+HtmlUtil.getHeaderLabel(request,"","", label)+"</div>")
			.append("</div></div>")
			.append("</div></div>")
			.append("<div class='clearfix'></div>");
		}
		
		logger.info(HTML.toString());
		return HTML.toString();
	}
//	
//	@Override
//	public String renderElementFlag(HttpServletRequest request,	Object objectElement) {
//		String FLAG = MConstant.FLAG.YES;
//		CompareDataM compareData = (CompareDataM)objectElement;
//		if(CompareDataM.RefLevel.APPLICATION.equals(compareData.getRefLevel())) {
//			String refID = compareData.getRefId();
//			String product = null;
//			if(!Util.empty(refID)) {
//				String[] tokens = refID.split(CompareDataM.MARKER);
//				if(tokens.length >= 2){
//					product = tokens[1];
//				}
//			}
//			if(PRODUCT_CODE_KEC.equals(product)){
//				ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
//				ArrayList<ApplicationImageDataM> applicationImages = applicationGroup.getApplicationImages();
//				if(null != applicationImages){
//					for (ApplicationImageDataM applicationImage : applicationGroup.getApplicationImages()) {
//						ArrayList<ApplicationImageSplitDataM> applicationImageSplits = applicationImage.getApplicationImageSplits();
//							if(null != applicationImageSplits){
//							for (ApplicationImageSplitDataM applicationImageSplit : applicationImage.getApplicationImageSplits()) {
//								if(null != applicationImageSplit){
//									if(!DOC_TYPE_SP_SIGN_OFF.equals(applicationImageSplit.getDocType())){
//										FLAG = MConstant.FLAG.NO;
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		return FLAG;
//	}
}
