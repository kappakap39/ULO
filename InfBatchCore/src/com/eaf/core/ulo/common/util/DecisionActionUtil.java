package com.eaf.core.ulo.common.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DecisionActionUtil {
	private static transient Logger logger = Logger.getLogger(DecisionActionUtil.class);
	
	public static String getAppGroupLastDecision(ApplicationGroupDataM uloAppGroup) {
		if (uloAppGroup == null)
			return null;

		List<ApplicationDataM> appList = uloAppGroup.getActiveApplications();
		if (appList == null || appList.isEmpty())
			return null;

		// Begin BPM Decision logic
		boolean missingDecision = false;
		boolean hasApprove = false;
		boolean hasReject = false;
		boolean hasPreceed = false;
		boolean hasRefer = false;
		boolean hasPreApprove = false;

		String DECISION_SERVICE_APPPROVE = SystemConstant.getConstant("DECISION_SERVICE_APPPROVE");
		String DECISION_SERVICE_REJECTED = SystemConstant.getConstant("DECISION_SERVICE_REJECTED");
		String DECISION_SERVICE_REFERRED = SystemConstant.getConstant("DECISION_SERVICE_REFERRED");
		String DECISION_SERVICE_PROCEED = SystemConstant.getConstant("DECISION_SERVICE_PROCEED");
		String finalDecisionTypeCancel = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
		String DECISION_SERVICE_PRE_APPROVE = SystemConstant.getConstant("DECISION_SERVICE_PRE_APPROVE");
		if (DECISION_SERVICE_APPPROVE == null) {
			throw new NullPointerException("Constant FINAL_APP_DECISION_APPROVE from BPMCacheControl is not presented and loaded correctly");
		}
		if (DECISION_SERVICE_REJECTED == null) {
			throw new NullPointerException("Constant FINAL_APP_DECISION_REJECT from BPMCacheControl is not presented and loaded correctly");
		}

		for (ApplicationDataM app : appList) {
			if (app == null)
				continue;
			if(finalDecisionTypeCancel.equalsIgnoreCase(app.getFinalAppDecision()))
				continue;
			String recommendDecision = app.getRecommendDecision();
			if (recommendDecision == null || recommendDecision.isEmpty()) {
				missingDecision = true;
			}
			if (DECISION_SERVICE_APPPROVE.equalsIgnoreCase(recommendDecision)) {
				hasApprove = true;
			} else if (DECISION_SERVICE_REJECTED.equalsIgnoreCase(recommendDecision)) {
				hasReject = true;
			} else if (DECISION_SERVICE_REFERRED.equalsIgnoreCase(recommendDecision)) {
				hasRefer = true;
			} else if (DECISION_SERVICE_PROCEED.equalsIgnoreCase(recommendDecision)) {
				hasPreceed = true;
			} else if (DECISION_SERVICE_PRE_APPROVE.equalsIgnoreCase(recommendDecision)) {
				hasPreApprove = true;
			}
		}

		if (missingDecision) {
			// throw new IllegalArgumentException("There're some missing decisions in OrigApplication! Verify that all recommend decisions have" +
			// " been sent/mapped from decision service");
			logger.warn("There're some missing decisions in OrigApplication! Verify that all recommend decisions have" + " been sent/mapped from decision service");
		}
		// Set result
		String result = null;
		if (hasRefer) {
			result = DECISION_SERVICE_REFERRED;
		} else if (hasPreceed) {
			result = DECISION_SERVICE_PROCEED;
		} else if (hasPreApprove) {
			result = DECISION_SERVICE_PRE_APPROVE;
		} else if (hasApprove) {
			result = DECISION_SERVICE_APPPROVE;
		} else if (hasReject) {
			result = DECISION_SERVICE_REJECTED;
		} 
		return result;

	}
}
