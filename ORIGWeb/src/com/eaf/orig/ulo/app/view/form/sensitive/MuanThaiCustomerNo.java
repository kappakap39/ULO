package com.eaf.orig.ulo.app.view.form.sensitive;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class MuanThaiCustomerNo extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(HisHerNoCC.class);
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {	
		logger.debug("Class name >> MuanThaiCustomerNo");
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);
		ApplicationDataM applicationM = CompareSensitiveFieldUtil.getApplicationProductTypeByRefId(applicationGroup, compareData);
		if(applicationM == null) {
			applicationM = new ApplicationDataM();
		}
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoApplication(applicationM.getApplicationRecordId(), PERSONAL_RELATION_APPLICATION_LEVEL);
		String elementId = FormatUtil.getProductElementId(personalInfo,applicationM.getProduct());
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(), elementId);
		String displayMode = HtmlUtil.EDIT;		
		StringBuffer HTML = new StringBuffer();
		String maxLenght="15";
 		
		HTML.append("<div class='col-sm-12'>")
			.append("<div class='form-group'>")
			.append(HtmlUtil.getFieldLabel(request, compareData.getFieldNameType(), "col-sm-4 col-md-5 control-label"))
			.append(HtmlUtil.textBox(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData),"","",maxLenght,displayMode,
					tagId,"col-sm-8 col-md-7",request))
			.append("</div></div>")
			.append("<div class='clearfix'></div>");		
		return HTML.toString();
	}
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();			
			PersonalInfoDataM perssonalApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			ApplicationDataM application = applicationGroup.getApplicationById(fieldElement.getElementGroupId());
			if(!Util.empty(application)) {
				String product = application.getProduct();
				CompareDataM compareData  = new CompareDataM();
				compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
				compareData.setFieldNameType(getImplementId());
				compareData.setUniqueLevel(CompareDataM.UniqueLevel.APPLICATION);
				compareData.setUniqueId(application.getApplicationRecordId()); 
				compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(perssonalApplicant,product));
				compareData.setRole(roleId);
				compareData.setRefLevel(CompareDataM.RefLevel.CARD);
				compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);	
				CardDataM cardM = application.getCard();
					if(!Util.empty(cardM) && !Util.empty(cardM.getCardType())) {
						HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(cardM.getCardType());
						String cardCode = SQLQueryEngine.display(CardInfoList, "CARD_CODE");
						String cardCodeRefId = CompareSensitiveFieldUtil.getCardCodeRefId(perssonalApplicant, product, cardCode);
						String cardTypeRefId = CompareSensitiveFieldUtil.getCardTypeRefId(perssonalApplicant, product, cardM.getCardType());
						compareData.setValue(cardM.getMembershipNo());
						compareData.setRefId(cardCodeRefId);
						compareData.setCurrentRefId(cardTypeRefId);
						compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
					}				
				compareList.add(compareData);
			}
		
		return compareList;
	}		
	@Override
	public String processElement(HttpServletRequest request,Object objectElement){
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
		CompareDataM compareData = ((CompareDataM)objectElement);
		ApplicationDataM application = CompareSensitiveFieldUtil.getApplicationProductTypeByRefId(applicationGroup, compareData);
		CardDataM cardM = application.getCard();
		if(!Util.empty(cardM)) {
			String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
			logger.debug("paramName>>>"+paramName);
			String member = request.getParameter(paramName);
			cardM.setMembershipNo(member);
		}	

		return null;
	}	
}