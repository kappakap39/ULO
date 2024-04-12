package com.eaf.orig.ulo.app.view.form.sensitive;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class CompanyPhoneExt  extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(CompanyPhoneExt.class);
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");
	@Override
	public String getElement(HttpServletRequest request,Object objectElement){		
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);
		AddressDataM address = CompareSensitiveFieldUtil.getAddressByUniqueId(applicationGroup, compareData);
		if(null==address){
			address = new AddressDataM();
		}	
		String personalId = address.getPersonalId();
		logger.debug("personalId>>>"+personalId);
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoId(personalId);
		if(null == personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}
		String displayMode = HtmlUtil.EDIT;	
		String elementId = FormatUtil.getAddressElementId(personalInfoM, ADDRESS_TYPE_WORK);
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(),elementId);
		StringBuilder HTML = new StringBuilder();
		String textLabel = LabelUtil.getText(request, "PHONE_EXT")+" ("+LabelUtil.getText(request, "OFFICE_PHONE")+")";
		HTML.append("<div class='col-sm-12'>")
		.append("<div class='form-group'>")
		.append(HtmlUtil.geCustomFieldLabel(textLabel, "col-sm-4 col-md-5 control-label"))
		.append(HtmlUtil.textBoxExt(CompareSensitiveFieldUtil.getElementName(compareData), CompareSensitiveFieldUtil.getElementSuffix(compareData), "", "", "5", displayMode,
				tagId, "col-sm-8 col-md-7", request))
		.append("</div></div>")
		.append("<div class='clearfix'></div>");		
		return HTML.toString();
	}	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		String elementId = getImplementId();
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalById(fieldElement.getElementGroupId());
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		if(!Util.empty(personalInfoM)) {
			AddressDataM address =   personalInfoM.getAddress(ADDRESS_TYPE_WORK);
			if(!Util.empty(address)) {
				String addressRefId = CompareSensitiveFieldUtil.getAddressRefId(personalInfoM,ADDRESS_TYPE_WORK);
				CompareDataM compareData  = new CompareDataM();
				compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
				compareData.setFieldNameType(elementId);
				compareData.setValue(address.getExt1());
				compareData.setRole(roleId);
				compareData.setRefId(CompareSensitiveFieldUtil.getPersonalRefId(personalInfoM));
				compareData.setCurrentRefId(addressRefId);
				compareData.setRefLevel(CompareDataM.RefLevel.ADDRESS);
				compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				compareData.setUniqueId(address.getAddressId());
				compareData.setUniqueLevel(CompareDataM.UniqueLevel.ADDRESS);
				compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(elementId));
				compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfoM,""));
				compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));

				compareList.add(compareData);
			}
		}
		return compareList;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);
		AddressDataM address = CompareSensitiveFieldUtil.getAddressByUniqueId(applicationGroup, compareData);
		if(!Util.empty(address)) {
			String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
			logger.debug("paramName>>>"+paramName);
			address.setExt1(request.getParameter(paramName));
		}
		return null;
	}	

}
