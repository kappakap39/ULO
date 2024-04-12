package com.eaf.orig.ulo.app.view.util.decisionservice;

import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;

public class DecisionFactory {
	public static com.eaf.core.ulo.common.display.ProcessActionInf getDecisionDV2(String source){
		if(ApplicationUtil.eApp(source)){
			return new DecisionEDV2();
		}else if(ApplicationUtil.cjd(source)){
			return new DecisionEDV2_CJD();
		}else{
			return new _DecisionDV2();
		}
	}
	public static com.eaf.core.ulo.common.display.ProcessActionInf getDecisionVerifyWebsite(String source){
		if(ApplicationUtil.eApp(source)){
			return new DecisionEDV1();
		}else{
			return new _DecisionVerifyWebsite();
		}
	}
	public static com.eaf.core.ulo.common.display.ProcessActionInf getDecisionVerifyCustomer(String source){
		if(ApplicationUtil.eApp(source)){
			return new DecisionVerifyCustomerEDV1();
		}else{
			return new _DecisionVerifyCustomer();
		}
	}
	public static com.eaf.core.ulo.common.display.ProcessActionInf getDecisionDE2(String source){
		if(ApplicationUtil.eApp(source)){
			return new DecisionEDE2();
		}else{
			return new _DecisionDE2();
		}
	}
}
