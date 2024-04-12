package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class DE2Phone1FormDisplayMode extends FormDisplayModeHelper {
	private static transient Logger logger = Logger.getLogger(DE2Phone1FormDisplayMode.class);

	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("DE2Phone1FormDisplayMode.displayMode");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if (Util.empty(applicationGroup)) {
			applicationGroup = new ApplicationGroupDataM();
		}
		String displayMode = HtmlUtil.VIEW;
		logger.debug("JobState >> " + applicationGroup.getJobState());
		logger.debug("fieldConfigId >> " + fieldConfigId);
		if (SystemConstant.lookup("JOBSTATE_APPROVE", applicationGroup.getJobState())) {
			if (!isHasDataPreviousRole(applicationGroup)) {
				displayMode = HtmlUtil.EDIT;
			} else {
				displayMode = HtmlUtil.VIEW;
			}
		} else if (SystemConstant.lookup("JOBSTATE_REJECT", applicationGroup.getJobState())) {
			displayMode = HtmlUtil.EDIT;
		}
		return displayMode;
	}

	public boolean isHasDataPreviousRole(ApplicationGroupDataM applicationGroup) {
		boolean isContainPrevData = false;
		String fieldNameType = fieldConfigId;
		String fieldName = "";
		ComparisonGroupDataM prevRoleData = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.PREV_ROLE);
		HashMap<String, CompareDataM> prevCompareList = prevRoleData.getComparisonFields();

		if (Util.empty(objectElement)) {
			return false;
		}

		if (objectElement instanceof ApplicationGroupDataM) {
			fieldName = this.getFieldName(fieldNameType);
		}

		logger.debug("fieldNameType >> " + fieldNameType);
		logger.debug("fieldName >> " + fieldName);
		logger.debug("prevCompareList >> " + prevCompareList.get(fieldName));
		CompareDataM prevCompareData = prevCompareList.get(fieldName);
		if (!Util.empty(prevCompareData) && !Util.empty(prevCompareData.getValue())) {
			isContainPrevData = true;
		}
		logger.debug("isContainPrevData >> " + isContainPrevData);
		return isContainPrevData;
	}

	private String getFieldName(String fieldNameType) {
		String[] split = fieldNameType.split("_");
		return split[0] + "_" + split[1] + "_" + split[2] + "_" + CompareDataM.UniqueLevel.APPLICATION_GROUP + "_" + split[3];
	}
}
