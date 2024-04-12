package com.eaf.orig.ulo.app.view.form.sensitive;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.FieldConfigDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class SPSignOffAuthorizedBy extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(SPSignOffAuthorizedBy.class);
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String DOC_TYPE_SP_SIGN_OFF = SystemConfig.getGeneralParam("DOC_TYPE_SP_SIGN_OFF");	
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);
		ApplicationDataM applicationInfo = CompareSensitiveFieldUtil.getApplicationByUniqueId(applicationGroup, compareData);
		if(null == applicationInfo){
			applicationInfo = new ApplicationDataM();
		}
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoByRelation(applicationInfo.getApplicationRecordId());
		if(null == personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}
		String product = CompareSensitiveFieldUtil.getProductByRefId(compareData);
		String elementId = FormatUtil.getProductElementId(personalInfoM,product);
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(),elementId);			
		StringBuffer HTML = new StringBuffer();
		String fieldLabel =LabelUtil.getText(request, compareData.getFieldNameType())+" ("+LabelUtil.getText(request, "PRODUCT_"+product)+")";
		HTML.append("<div class='col-sm-12'>")
		.append("<div class='form-group'>")
		.append(HtmlUtil.geCustomFieldLabel(fieldLabel, "col-sm-4 col-md-5 control-label"))
		.append(HtmlUtil.textBox(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData),"","","50",HtmlUtil.EDIT,tagId,"col-sm-8 col-md-7",request))
		.append("</div></div>")
		.append("<div class='clearfix'></div>");		
		return HTML.toString();
	}
	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ArrayList<String> products = applicationGroup.getProducts();
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();	
		PersonalInfoDataM personalInfoM = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		if(!Util.empty(products)){
			for(String product :products){
				ApplicationDataM applicationM = applicationGroup.filterApplicationRelationLifeCycle(personalInfoM.getPersonalId(), product).get(0);
				if(!Util.empty(applicationM)){
					String productRefId = CompareSensitiveFieldUtil.getProductRefId(personalInfoM, applicationM.getProduct());	
					CompareDataM compareData  = new CompareDataM();
					compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					compareData.setFieldNameType(getImplementId());
					compareData.setValue(applicationM.getSpSignoffAuthBy());
					compareData.setRole(roleId);
					compareData.setRefId(productRefId);
					compareData.setRefLevel(CompareDataM.RefLevel.APPLICATION);
					compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
					compareData.setUniqueId(applicationM.getApplicationRecordId());
					compareData.setUniqueLevel(CompareDataM.UniqueLevel.APPLICATION);
					compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
					compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfoM,product));
					compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
					compareList.add(compareData);
				}
			}
		}
		return compareList;
	}
		
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = (CompareDataM)objectElement;

		FieldConfigDataM filedConfigId = CompareSensitiveFieldUtil.JSonStringToFieldConfigDataM(compareData.getConfigData());
	    ArrayList<ApplicationDataM>	  applications =  applicationGroup.filterApplicationLifeCycleByPersonalId(filedConfigId.getPersonalId(), filedConfigId.getProduct());
		String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
		logger.debug("paramName>>"+paramName);
		String spSignOff = request.getParameter(paramName);
		logger.debug("spSignOff>>"+spSignOff);
	    if(!Util.empty(applications)) {
			for(ApplicationDataM applicationInfo : applications){
				applicationInfo.setSpSignoffAuthBy(spSignOff);
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
		CompareDataM compareData = (CompareDataM)objectElement;
		ApplicationDataM application = CompareSensitiveFieldUtil.getApplicationByRefId(applicationGroup, compareData);
		if(application == null) {
			application = new ApplicationDataM();
		}
		if(Util.empty(application.getSpSignoffAuthBy())) {
			formError.error(compareData.getFieldName(),PREFIX_ERROR+LabelUtil.getText(request,compareData.getFieldNameType()));
		}*/
		return formError.getFormError();
	}
	
	@Override
	public String renderElementFlag(HttpServletRequest request,	Object objectElement) {
		String FLAG = MConstant.FLAG.NO;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		ArrayList<ApplicationImageDataM> applicationImages = applicationGroup.getApplicationImages();
		if(null != applicationImages){
			for (ApplicationImageDataM applicationImage : applicationGroup.getApplicationImages()) {
				ArrayList<ApplicationImageSplitDataM> applicationImageSplits = applicationImage.getApplicationImageSplits();
					if(null != applicationImageSplits){
					for (ApplicationImageSplitDataM applicationImageSplit : applicationImage.getApplicationImageSplits()) {
						if(null != applicationImageSplit){
							if(DOC_TYPE_SP_SIGN_OFF.equals(applicationImageSplit.getDocType())){
								FLAG = MConstant.FLAG.YES;
							}
						}
					}
				}
			}
		}
		return FLAG;
	}
}
