package com.eaf.orig.ulo.app.view.form.cis.compare;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.common.utility.CompareSensitiveUtility;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class CISCompareAction {
	
	public static final String CIS_COMPARE = "CIS_COMPARE";
	private static transient Logger logger = Logger.getLogger(CISCompareAction.class);
	
	public HashMap<String, CompareDataM> processAction(ApplicationGroupDataM applicationGroup, String role) {
		String lastRoleId = getLastRole(role);
		logger.info("lastRoleId >> "+lastRoleId);
//		#rawi comment change getComparisonField form applicationGroup.getComparisonField this data get form screen input exists customer
		ComparisonGroupDataM cisCompareData = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.CIS);
		HashMap<String, CompareDataM> currentCompareMap = getCompareFields(applicationGroup,cisCompareData,role);
		logger.info("currentCompareMap >> "+currentCompareMap);
		if (!Util.empty(currentCompareMap)) {
			Collection<CompareDataM> currentCompareDataList = currentCompareMap.values();
			logger.info("cisCompareData >> " + cisCompareData);
			logger.info("currentCompareDataList >> " + currentCompareDataList);
			if (!Util.empty(cisCompareData)) {
				cisCompareData.setComparisonFields(CompareSensitiveUtility.changeKeyCompareListGroupCIS(cisCompareData.getComparisonFields()));
				if (!Util.empty(cisCompareData.getComparisonFields())) {
					HashMap<String, CompareDataM> prevCompareDataList = cisCompareData.getComparisonFields();
					for (CompareDataM currRoleCompareData : currentCompareDataList) {
							CompareDataM prevRoleCompareData = prevCompareDataList.get(currRoleCompareData.getFieldName());
						if (!Util.empty(prevRoleCompareData)) {
							logger.info("sensitive field >> "+ prevRoleCompareData.getFieldName());
							if(Util.empty(currRoleCompareData.getValue()) && !Util.empty(prevRoleCompareData.getValue())){
								prevRoleCompareData.setCompareFlag(MConstant.FLAG.YES);
							}else{
								boolean isSame = CompareObject.compare(prevRoleCompareData.getValue(),currRoleCompareData.getValue(), null);
								if (!isSame) {
									logger.debug("found different >> "+ currRoleCompareData.getFieldName());
									prevRoleCompareData.setCompareFlag(MConstant.FLAG.WRONG);
								} else {
									prevRoleCompareData.setCompareFlag(MConstant.FLAG.YES);
								}
							}

						}
					}
				} else {
					return currentCompareMap;
				}
			} else {
				return currentCompareMap;
			}
		}
		return (null!=cisCompareData)?cisCompareData.getComparisonFields():null;
	}
		
	private HashMap<String, CompareDataM> getCompareFields(ApplicationGroupDataM applicationGroup,ComparisonGroupDataM comparisonGroup, String role){		
		ArrayList<ElementInf> compareCISElements = CompareSensitiveUtility.getAllCISElements(comparisonGroup,CIS_COMPARE, null);
		if(null != compareCISElements){
			HashMap<String, CompareDataM> compareDataMap = new HashMap<String, CompareDataM>();
			for(ElementInf element: compareCISElements) {
				ArrayList<CompareDataM> compareDataList = (ArrayList<CompareDataM>)element.getObjectElement(null,applicationGroup);
				if(!Util.empty(compareDataList)) {
					for(CompareDataM compareData: compareDataList) {
						compareData.setRole(role);
						compareDataMap.put(compareData.getFieldName(),compareData);
					}
				}
			}
			return compareDataMap;
		}
		return null;
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
}
