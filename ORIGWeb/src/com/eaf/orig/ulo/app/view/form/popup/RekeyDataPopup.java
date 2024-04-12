package com.eaf.orig.ulo.app.view.form.popup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.model.CompareFieldElement;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.util.manual.SensitiveFieldAction;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.compare.CompareDataM;

@SuppressWarnings("serial")
public class RekeyDataPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(RekeyDataPopup.class);
	@SuppressWarnings("unchecked")
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		HashMap<String,ArrayList<CompareFieldElement>> hCompareFieldElementList = (HashMap<String,ArrayList<CompareFieldElement>>)request.getSession().getAttribute(SensitiveFieldAction.REKEY_FIELDS);
		if(!Util.empty(hCompareFieldElementList)){
			for(ArrayList<CompareFieldElement> compareFieldElementList : hCompareFieldElementList.values()){
				for(CompareFieldElement compareElement : compareFieldElementList){ 
		 			ArrayList<CompareDataM> compareDatas =compareElement.getCompareDatas();
		 			if(!Util.empty(compareDatas)){
		 				for(CompareDataM compareDataM : compareDatas){
		 					if(!CompareDataM.CompareDataType.DUMMY.equals(compareDataM.getCompareDataType())){
			 					logger.debug("compareDataM.getRefLevel()>>"+compareDataM.getRefLevel());
			 					logger.debug("compareDataM.getFieldName()>>"+compareDataM.getFieldName());
			 					ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, compareDataM.getFieldNameType());	 					
			 					element.processElement(request, compareDataM);
		 					}		 					
		 				}
		 			}
				}
			}
		}		
		
		/*HashMap<String, CompareDisplayHelperDataM> compareFields = (HashMap<String, CompareDisplayHelperDataM>)
				request.getSession().getAttribute(SensitiveFieldAction.REKEY_FIELDS);		
		if(!Util.empty(compareFields)) {
			CompareDisplayHelperDataM appLevelHelper = compareFields.get(CompareDataM.RefLevel.APPLICATION);
			if(!Util.empty(appLevelHelper)) {
				Set<String> appFields = appLevelHelper.getComparisonKeys();
				if(!Util.empty(appFields)) {
					for(String refId: appFields) {
						ArrayList<CompareDataM> productDataMs = appLevelHelper.getComparisonFields(refId);
						if(!Util.empty(productDataMs)) {
							for(CompareDataM compareDataM: productDataMs) {
								ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, compareDataM.getFieldNameType());
								element.processElement(request, compareDataM);
							}
						}
					}
				}
			}
			CompareDisplayHelperDataM personalLevelHelper =  compareFields.get(CompareDataM.RefLevel.PERSONAL);
			if(!Util.empty(personalLevelHelper)) {
				Set<String> personalInfoFields = personalLevelHelper.getComparisonKeys();
				if(!Util.empty(personalInfoFields)) {
					for(String refId: personalInfoFields) {
						ArrayList<CompareDataM> personalDataMs = personalLevelHelper.getComparisonFields(refId);
						if(!Util.empty(personalDataMs)) {
							for(CompareDataM compareDataM: personalDataMs) {
								ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, compareDataM.getFieldNameType());
								element.processElement(request, compareDataM);
							}
						}
					}
				}
			}
			
			CompareDisplayHelperDataM appGroupLevelHelper =  compareFields.get(CompareDataM.RefLevel.APPLICATION_GROUP);
			if(!Util.empty(appGroupLevelHelper)) {
				Set<String> appGroupFields = appGroupLevelHelper.getComparisonKeys();
				if(!Util.empty(appGroupFields)) {
					for(String refId: appGroupFields) {
						ArrayList<CompareDataM> differenceFields = appGroupLevelHelper.getComparisonFields(refId);
						if(!Util.empty(differenceFields)) {
							for(CompareDataM compareDataM: differenceFields) {
								ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, compareDataM.getFieldNameType());
								element.processElement(request, compareDataM);
							}
						}
					}
				
				}
			}
		}*/
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
//		HashMap<String,ArrayList<CompareFieldElement>> hCompareFieldElementList = (HashMap<String,ArrayList<CompareFieldElement>>)request.getSession().getAttribute(SensitiveFieldAction.REKEY_FIELDS);
//		if(!Util.empty(hCompareFieldElementList)){
//			if(!Util.empty(hCompareFieldElementList)){
//				for(ArrayList<CompareFieldElement> compareFieldElementList :hCompareFieldElementList.values()){
//					for(CompareFieldElement compareElement : compareFieldElementList){ 
//			 			ArrayList<CompareDataM> compareDatas =compareElement.getCompareDatas();
//			 			if(!Util.empty(compareDatas)){
//			 				for(CompareDataM compareDataM : compareDatas){
//			 					ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, compareDataM.getFieldNameType());
//			 					if(MConstant.FLAG.YES.equals(element.renderElementFlag(request, compareDataM))){
//									HashMap<String,Object> errors = element.validateElement(request, compareDataM);
//									if(!Util.empty(errors)) {
//										formError.addAll(errors);
//									}
//								}
//			 				}
//			 			}
//					}
//				}
//			}
//		}
		
		HashMap<String,ArrayList<CompareFieldElement>> hCompareFieldElementList = (HashMap<String,ArrayList<CompareFieldElement>>)request.getSession().getAttribute(SensitiveFieldAction.REKEY_FIELDS);
		if(!Util.empty(hCompareFieldElementList)){
			for(ArrayList<CompareFieldElement> compareFieldElementList : hCompareFieldElementList.values()){
				for(CompareFieldElement compareElement : compareFieldElementList){ 
		 			ArrayList<CompareDataM> compareDatas =compareElement.getCompareDatas();
		 			if(!Util.empty(compareDatas)){
		 				for(CompareDataM compareDataM : compareDatas){
		 					if(!CompareDataM.CompareDataType.DUMMY.equals(compareDataM.getCompareDataType())){
			 					logger.debug("compareDataM.getRefLevel()>>"+compareDataM.getRefLevel());
			 					logger.debug("compareDataM.getFieldName()>>"+compareDataM.getFieldName());
			 					ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, compareDataM.getFieldNameType());	 					
			 					if(MConstant.FLAG.YES.equals(element.renderElementFlag(request, compareDataM))){
									HashMap<String,Object> errors = element.validateElement(request, compareDataM);
									if(!Util.empty(errors)) {
										formError.addAll(errors);
									}
								}
		 					}		 					
		 				}
		 			}
				}
			}
		}
		return formError.getFormError();
	}
		
	@Override
	public boolean validateSubForm(HttpServletRequest request){
		return false;
	}
}
