package com.eaf.orig.ulo.app.view.form.sensitive;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class DocumentProvince extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(DocumentProvince.class);
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");	
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {			
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
		String prefixHiddenFiled = CompareSensitiveFieldUtil.getElementName(compareData)+"_"+CompareSensitiveFieldUtil.getElementSuffix(compareData);
		String prefixAddress = CompareSensitiveFieldUtil.getPrefixAddress(personalInfoM,ADDRESS_TYPE_DOCUMENT);
		
		String elementId = FormatUtil.getAddressElementId(personalInfoM, ADDRESS_TYPE_DOCUMENT);
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(),elementId);
		String displayMode = HtmlUtil.EDIT;
		StringBuffer HTML = new StringBuffer();
		HTML.append("<div class='col-sm-12'>")
		.append("<div class='form-group'>")
		.append(HtmlUtil.getFieldLabel(request, "PROVINCE_"+ADDRESS_TYPE_DOCUMENT, "col-sm-4 col-md-5 control-label"))
		/*.append(HtmlUtil.textBox(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData),"","","30", displayMode, 
				tagId,"col-sm-8 col-md-7",request))*/
		
		 .append(HtmlUtil.popupList(CompareSensitiveFieldUtil.getElementName(compareData), CompareSensitiveFieldUtil.getElementSuffix(compareData), "", "", "100", displayMode, tagId, "col-sm-4 col-md-5 control-label", request))
		.append(HtmlUtil.hidden(prefixHiddenFiled+"_REKEY_ADDRESS_TYPE", ADDRESS_TYPE_DOCUMENT))
		.append(HtmlUtil.hidden(prefixHiddenFiled+"_REKEY_PERSONAL_SEQ", personalInfoM.getSeq()))
		.append(HtmlUtil.hidden(prefixHiddenFiled+"_REKEY_PERSONAL_TYPE", personalInfoM.getPersonalType()))
		.append(HtmlUtil.hidden(prefixHiddenFiled+"_REKEY_ADDRESS_ID", address.getAddressId()))
		
		.append(HtmlUtil.hidden(prefixAddress+"_ZIPCODE", ""))
		.append(HtmlUtil.hidden(prefixAddress+"_TAMBOL", ""))
		.append(HtmlUtil.hidden(prefixAddress+"_AMPHUR", ""))
		.append(HtmlUtil.hidden(prefixAddress+"_PROVINCE", ""))
		
		.append("</div></div>")
		.append("<div class='clearfix'></div>");		
		return HTML.toString();
	}	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalById(fieldElement.getElementGroupId());
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		if(!Util.empty(personalInfoM)) {
			AddressDataM address = personalInfoM.getAddress(ADDRESS_TYPE_DOCUMENT);
			if(!Util.empty(address)) {
				String addressRefId = CompareSensitiveFieldUtil.getAddressRefId(personalInfoM,ADDRESS_TYPE_DOCUMENT);
				CompareDataM compareData  = new CompareDataM();
				compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
				compareData.setFieldNameType(getImplementId());
				compareData.setValue(address.getProvinceDesc());
				compareData.setRole(roleId);
				compareData.setRefId(CompareSensitiveFieldUtil.getPersonalRefId(personalInfoM));
				compareData.setCurrentRefId(addressRefId);
				compareData.setRefLevel(CompareDataM.RefLevel.ADDRESS);
				compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				compareData.setUniqueId(address.getAddressId());
				compareData.setUniqueLevel(CompareDataM.UniqueLevel.ADDRESS);
				compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
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
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoId(address.getPersonalId());
		if(null == personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}
		if(!Util.empty(address)) {
//			String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
//			logger.debug("paramName>>"+paramName);
//			String province = request.getParameter(paramName);			
//			logger.debug("paramName>>"+paramName);
			String prefixAddress = CompareSensitiveFieldUtil.getPrefixAddress(personalInfoM,ADDRESS_TYPE_DOCUMENT);
			String zipcode = request.getParameter(prefixAddress+"_ZIPCODE");
			String tambol = request.getParameter(prefixAddress+"_TAMBOL");
			String amphur = request.getParameter(prefixAddress+"_AMPHUR");
			String province = request.getParameter(prefixAddress+"_PROVINCE");
			
			logger.debug("zipcode>>"+zipcode);
			logger.debug("tambol>>"+tambol);
			logger.debug("amphur>>"+amphur);
			logger.debug("province>>"+province);
			address.setZipcode(zipcode);
			address.setTambol(tambol);
			address.setAmphur(amphur);
			address.setProvinceDesc(province);
		}
		return null;
	}
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		/*String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);
		PersonalInfoDataM personalInfoM = CompareSensitiveFieldUtil.getPersonalInfoByRefId(applicationGroup, compareData);
		AddressDataM address = null;
		if(!Util.empty(personalInfoM)) {
			address = personalInfoM.getAddress(ADDRESS_TYPE_DOCUMENT);
		}
		if(Util.empty(address) || Util.empty(address.getProvinceDesc())) {
			formError.error(compareData.getFieldName(),PREFIX_ERROR+LabelUtil.getText(request,"PROVINCE_"+ADDRESS_TYPE_DOCUMENT));
		}*/
		return formError.getFormError();
	}	
}
