package com.eaf.service.rest.controller.followup.action;

import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;

public class FollowupActionFactory {

	public static FollowupAction getCallDecisionPointAndSendApplicationBack(String source){
		
		if(ApplicationUtil.eApp(source)){
			return new CallDecisionPointAndSendApplicationBackEAPP();
		}
		
		return new CallDecisionPointAndSendApplicationBackNormal();
	}
	
	public static FollowupAction getCallDecisionPointAndSentToNextStation(String source){
		
		if(ApplicationUtil.eApp(source)){
			return new CallDecisionPointAndSentToNextStationEAPP();
		}
		
		return new CallDecisionPointAndSentToNextStationNormal();
	}
	
}
