package com.eaf.orig.ulo.app.view.util.manual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.CompareSensitiveUtility;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.model.CompareFieldElement;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
//import com.eaf.orig.ulo.model.app.ApplicationDataM;

public class SensitiveFieldAction implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(SensitiveFieldAction.class);
	public static final String REKEY_FIELDS = "REKEY_FIELDS";
	private static final String POPUP_FORM = "REKEY_SENSITIVE_FORM";
	private static final String SUCCESS = "SUCCESS";
	private String PRODUCT_ELEMENT = SystemConstant.getConstant("PRODUCT_ELEMENT");
	private String SUP_CARD_INFO = SystemConstant.getConstant("SUP_CARD_INFO");
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.SENSITIVE_FIELD_ACTION);
		try{
			boolean isDefaultConditionType = CompareSensitiveUtility.isFilterFormCondition(ORIGFormHandler.getForm(request).getFormId(), ORIGFormHandler.CONDITION_TYPE_DEFAULT);
			logger.debug("isDefaultConditionType >> "+ isDefaultConditionType);
			String PROCESS_STATUS = POPUP_FORM;
			request.getSession().removeAttribute(REKEY_FIELDS);
			HashMap<String,ArrayList<CompareFieldElement>>    compareFieldElementList = new HashMap<String,ArrayList<CompareFieldElement>>();	
			ORIGFormHandler ORIGForm = ORIGFormHandler.getForm(request);
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();				
			if(isDefaultConditionType){	
				HashMap<String, ArrayList<CompareDataM>> currentCompareFields = CompareSensitiveUtility.getCompareDataFieldNameTypes(applicationGroup,ORIGForm.getSubForm(),request);
				if(!Util.empty(currentCompareFields)){
					ArrayList<String> compareFieldNameTypes = new ArrayList<String>(currentCompareFields.keySet());
					for(String fieldNameType : compareFieldNameTypes){
						ArrayList<CompareDataM> compareDataMList=(ArrayList<CompareDataM>)currentCompareFields.get(fieldNameType);
						logger.debug("fieldNameType>>>"+fieldNameType);
						ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, fieldNameType);
						ArrayList<CompareFieldElement>  compareFieldElements= elementInf.compareFieldElement(request, compareDataMList);
						if(!Util.empty(compareFieldElements)){
							setCompareFieldElement(compareFieldElementList,compareFieldElements);
						}
					}
				}
			}
			Set<String> keyElement = compareFieldElementList.keySet();
			if(!Util.empty(keyElement) && Util.empty(applicationGroup.getProductElementFlag())){
				for(String key : keyElement){
					ArrayList<CompareFieldElement> compareFieldElements = compareFieldElementList.get(key);
					if(!Util.empty(compareFieldElements)){
						for(CompareFieldElement compareFieldElement : compareFieldElements){
							ArrayList<CompareDataM> compareDatas = compareFieldElement.getCompareDatas();
							for(CompareDataM compareData : compareDatas){
								if(PRODUCT_ELEMENT.equals(compareData.getFieldNameType()) || SUP_CARD_INFO.equals(compareData.getFieldNameType())){
									logger.debug("ApplicationRecordId : "+compareData.getUniqueId());
									logger.debug("Confirm Application : appGroupId >> "+applicationGroup.getApplicationGroupId());
									PROCESS_STATUS = PRODUCT_ELEMENT;
									break;
								}
							}
						}
					}
				}
			}
			logger.debug("compareFieldElementList size >> "+compareFieldElementList.size());
			logger.debug("compareFieldElementList field >> "+ compareFieldElementList);
			if(!Util.empty(compareFieldElementList)) {
				request.getSession().setAttribute(REKEY_FIELDS, compareFieldElementList);
				return responseData.success(PROCESS_STATUS);
			}else{
				ORIGForm.checkCompareDataChangeForPrevRole(request);
			}
			return responseData.success(SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
	
	private String getLastRole(String roleId){
		String[] SENSITIVE_ROLE_MAPPING = SystemConstant.getArrayConstant("SENSITIVE_ROLE_MAPPING");
		if(null != SENSITIVE_ROLE_MAPPING){
			for (String ROLE_MAPPING : SENSITIVE_ROLE_MAPPING) {
				String CURRENT_ROLE = ROLE_MAPPING.split("\\|")[0];
				String LAST_ROLE = ROLE_MAPPING.split("\\|")[1];
				if(CURRENT_ROLE.equals(roleId)){
					return LAST_ROLE;
				}
			}
		}
		return "";
	}
	private void setCompareFieldElement(HashMap<String,ArrayList<CompareFieldElement>>    hCompareFieldElementList, ArrayList<CompareFieldElement> compareFieldList){
		 if(!Util.empty(compareFieldList)){
			 for(CompareFieldElement compareFieldElement :compareFieldList){
				 String compareFieldType =compareFieldElement.getCompareFieldType();
				 logger.debug("compareFieldType>>>"+compareFieldType);
				 ArrayList<CompareFieldElement> existingCompareFieldElement = hCompareFieldElementList.get(compareFieldType);
				 if(Util.empty(existingCompareFieldElement)){
					 existingCompareFieldElement = new ArrayList<CompareFieldElement>();
					 hCompareFieldElementList.put(compareFieldType, existingCompareFieldElement);
				 }
				 existingCompareFieldElement.add(compareFieldElement);				 
			 }
		 }
	}
}
