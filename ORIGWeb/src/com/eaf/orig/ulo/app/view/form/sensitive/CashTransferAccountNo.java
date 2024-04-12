package com.eaf.orig.ulo.app.view.form.sensitive;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.model.CompareFieldElement;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
//import com.eaf.core.ulo.common.properties.ListBoxControl;

public class CashTransferAccountNo  extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(CashTransferAccountNo.class);	
	String FIELD_ID_CASH_TRANSFER_TYPE =SystemConstant.getConstant("FIELD_ID_CASH_TRANSFER_TYPE");
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String SUFFIX_CASH_TRANSFER = PRODUCT_K_EXPRESS_CASH+"_CASH_TRANSFER";
	@Override
	public String getElement(HttpServletRequest request,Object objectElement){		
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
		String displayMode = HtmlUtil.EDIT;			
		CompareDataM compareData = (CompareDataM)objectElement;
		ApplicationDataM applicationInfo = CompareSensitiveFieldUtil.getApplicationByUniqueId(applicationGroup, compareData);
		if(null == applicationInfo){
			applicationInfo = new ApplicationDataM();
		}
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoByRelation(applicationInfo.getApplicationRecordId());
		if(null == personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}
		String elementId = FormatUtil.getCashTransferElementId(personalInfoM,PRODUCT_K_EXPRESS_CASH,"CASH_TRANSFER");
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(),elementId);
//		ApplicationDataM application = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_EXPRESS_CASH);
		if(!Util.empty(applicationInfo)) {
			StringBuffer HTML = new StringBuffer();
			HTML.append("<div class='col-sm-12'>")
				.append("<div class='form-group'>")
				.append(HtmlUtil.getFieldLabel(request, "CASH_TRANSFER_TYPE_ACCT_NO", "col-sm-4 col-md-5 control-label"))
				.append(HtmlUtil.textBoxAccountNo(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData),"","",displayMode,
					tagId,"col-sm-8 col-md-7",request))
				.append("</div></div>")
			
				.append("<div class='col-sm-12'>")
				.append("<div class='form-group'>")
				.append(HtmlUtil.getFieldLabel(request, "CASH_TRANSFER_TYPE_ACCT_NAME", "col-sm-4 col-md-5 control-label"))
				.append(HtmlUtil.hidden("PRODUCT_NAME_"+CompareSensitiveFieldUtil.getParameterName(compareData),SUFFIX_CASH_TRANSFER))
				.append("<div class='col-sm-8 col-md-7'  id='ACCOUNT_NAME"+CompareSensitiveFieldUtil.getParameterName(compareData) +"'></div>")
				.append("</div>")
				.append("</div>")			
				.append("<div class='clearfix'></div>");			
			return HTML.toString();
		}
		return null;
	}	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
//		String []cashTransferType = ListBoxControl.searchListBox(FIELD_ID_CASH_TRANSFER_TYPE,"CHOICE_NO",request);
		String []cashTransferType = ApplicationUtil.getCashTranferTypes();
		ApplicationDataM application = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_EXPRESS_CASH);
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		if(!Util.empty(application)) {
			CashTransferDataM cashTransfer = application.getCashTransfer(cashTransferType);	
//			if(!Util.empty(cashTransfer)) {
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(application.getApplicationRecordId());
			CompareDataM compareData  = new CompareDataM();
			compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
			compareData.setFieldNameType(getImplementId());
			if(null != cashTransfer){
				compareData.setValue(cashTransfer.getTransferAccount());
			}
			compareData.setRole(roleId);
			compareData.setRefId(CompareSensitiveFieldUtil.getProductRefId(personalInfo,application.getProduct()));
			compareData.setRefLevel(CompareDataM.RefLevel.APPLICATION);
			compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
			compareData.setUniqueId(application.getApplicationRecordId());
			compareData.setUniqueLevel(CompareDataM.UniqueLevel.APPLICATION);
			compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
			compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfo,application.getProduct()));
			compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
			compareList.add(compareData);
//			}
		}
		return compareList;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
//		String []cashTransferType = ListBoxControl.searchListBox(FIELD_ID_CASH_TRANSFER_TYPE,"CHOICE_NO",request);	
		String []cashTransferType = ApplicationUtil.getCashTranferTypes();
		CompareDataM compareData = (CompareDataM)objectElement;
		ApplicationDataM application = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_EXPRESS_CASH);
		if(!Util.empty(application)) {
			CashTransferDataM cashTransfer = application.getCashTransfer(cashTransferType);			
			if(!Util.empty(cashTransfer)) {
				String transferAcct = request.getParameter(CompareSensitiveFieldUtil.getParameterName(compareData));
				transferAcct = FormatUtil.removeDash(transferAcct);
				cashTransfer.setTransferAccount(transferAcct);
			}
		}
		return null;
	}	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		/*String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();
//		String []cashTransferType = ListBoxControl.searchListBox(FIELD_ID_CASH_TRANSFER_TYPE,"CHOICE_NO",request);	
		String []cashTransferType = ApplicationUtil.getCashTranferTypes();
		CompareDataM compareData = (CompareDataM)objectElement;
		ApplicationDataM application = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_EXPRESS_CASH);
		if(null != application){
			CashTransferDataM cashTransfer = application.getCashTransfer(cashTransferType);	
			if(!Util.empty(cashTransfer) && Util.empty(cashTransfer.getTransferAccount())) {
				formError.error(compareData.getFieldName(),PREFIX_ERROR+LabelUtil.getText(request,"CASH_TRANSFER_TYPE_ACCT_NO"));
			}
		}*/
		return formError.getFormError();
	}	
	
	@Override
	public ArrayList<CompareFieldElement> compareFieldElement(HttpServletRequest request, Object objectForm) {
		ArrayList<CompareFieldElement> compareFieldElementList = new ArrayList<CompareFieldElement>();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);	
		ArrayList<CompareDataM> currCompareDataMList = (ArrayList<CompareDataM>)objectForm;
		if(!Util.empty(currCompareDataMList)){
			for(CompareDataM currentCompareData : currCompareDataMList){
				String currentFieldName = currentCompareData.getFieldName();
				String currentFieldNameType = currentCompareData.getFieldNameType();
				logger.debug("currentFieldName>>>"+currentFieldName);
				logger.debug("currentFieldNameType>>>"+currentFieldNameType);
				CompareDataM prevCompareData  = CompareSensitiveFieldUtil.prevCompareDataMRefByProduct(applicationGroup, currentCompareData);
				if(!Util.empty(prevCompareData)){
					boolean isSame = CompareObject.compare(currentCompareData.getValue(), prevCompareData.getValue(),currentCompareData.getCompareFlag());
					logger.debug("isSame>>>>"+isSame);
					if(!isSame) {
						currentCompareData.setCompareFlag(MConstant.FLAG.YES);
						currentCompareData.setOldValue(prevCompareData.getValue());
						
						CompareFieldElement compareFieldElement = new CompareFieldElement();
						compareFieldElement.addCompareDatas(currentCompareData);
						compareFieldElement.setCompareFieldType(CompareSensitiveFieldUtil.getCompareElementFieldType(currentCompareData));
						compareFieldElement.setImplementId(currentFieldNameType);
						compareFieldElementList.add(compareFieldElement);
					}
				}
			}
		}		
		return compareFieldElementList;
	}
}
