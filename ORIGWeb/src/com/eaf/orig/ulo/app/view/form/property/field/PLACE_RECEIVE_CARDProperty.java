package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class PLACE_RECEIVE_CARDProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(PLACE_RECEIVE_CARDProperty.class);
	private String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");	
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private String FIELD_ID_PLACE_RECEIVE_CARD = SystemConstant.getConstant("FIELD_ID_PLACE_RECEIVE_CARD");
	
	
	@Override
	public boolean invokeDisplayMode() {
		return true;
	}
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request){
		
		Object masterObjectForm = FormControl.getMasterObjectForm(request);	
		String templateId = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			templateId = applicationGroup.getApplicationTemplate();
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			templateId = "";
		}
		logger.debug("templateId >> "+templateId);		

		String PRODUCT_CODE = ListBoxControl.getName(CACHE_TEMPLATE,templateId,"PRODUCT_CODE");
		String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");	
		if(PRODUCT_K_PERSONAL_LOAN.equals(PRODUCT_CODE)){
			return HtmlUtil.VIEW;
		}		
		
		return HtmlUtil.EDIT;
	}
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();		
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		PersonalInfoDataM lastpersonalInfo = (PersonalInfoDataM)lastObjectForm;
		
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}
		boolean compareFlag = CompareObject.compare(personalInfo.getPlaceReceiveCard(), lastpersonalInfo.getPlaceReceiveCard(),null);
		if(!compareFlag){
			String placeReceive = "PLACE_RECEIVE_CARD";
			if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfo.getPersonalType())){
				placeReceive = "PLACE_RECEIVE_CARD_SUB";
			}
			logger.debug("placeReceive >> " + placeReceive);
			logger.debug("personalInfo.getPersonalType() >> " + personalInfo.getPersonalType());
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalInfo.getPersonalType(), LabelUtil.getText(request, placeReceive), request));
			audit.setOldValue(FormatUtil.displayText(getPlaceReceiveCardDesc(lastpersonalInfo.getPlaceReceiveCard())));
			audit.setNewValue(FormatUtil.displayText(getPlaceReceiveCardDesc(personalInfo.getPlaceReceiveCard())));
			audits.add(audit);
		}
		return audits;
	}
	private String getPlaceReceiveCardDesc(String code){
		return ListBoxControl.getName(FIELD_ID_PLACE_RECEIVE_CARD, code);
	}
}
