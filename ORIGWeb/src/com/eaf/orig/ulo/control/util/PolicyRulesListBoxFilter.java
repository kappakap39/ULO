package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.constant.MConstant;

public class PolicyRulesListBoxFilter implements ListBoxFilterInf  {
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {
		ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(cacheId,"ACTIVE");
		ArrayList<HashMap<String,Object>> vPolicyResult = new ArrayList<>();
		String FLAG_YES = MConstant.FLAG.YES;
		String OR_RESULT_FAIL_REFER = SystemConstant.getConstant("CONFIG_ID_OR_RESULT_FAIL_REFER");
		String OR_RESULT_BLANK = SystemConstant.getConstant("CONFIG_ID_OR_RESULT_BLANK");
		String OR_RESULT_FAIL = SystemConstant.getConstant("CONFIG_ID_OR_RESULT_FAIL");
		if(null != List){
			for (HashMap<String, Object> policyRule : List){
								
				if((OR_RESULT_FAIL_REFER).equals(configId)){
					String SYSTEM_ID3 = SQLQueryEngine.display(policyRule,"SYSTEM_ID3");
					if(FLAG_YES.equals(SYSTEM_ID3)){
						vPolicyResult.add(policyRule);
					}
				}
				else if ((OR_RESULT_BLANK).equals(configId)) {
					String SYSTEM_ID4 = SQLQueryEngine.display(policyRule,"SYSTEM_ID4");
					if (FLAG_YES.equals(SYSTEM_ID4)) {
						vPolicyResult.add(policyRule);
					}
				} else if (OR_RESULT_FAIL.equals(configId)) {
					String SYSTEM_ID5 = SQLQueryEngine.display(policyRule,"SYSTEM_ID5");
					if (FLAG_YES.equals(SYSTEM_ID5)) {
						vPolicyResult.add(policyRule);
					}
				}
			}
		}
		return vPolicyResult;
	}
	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId,HttpServletRequest request) {
		
	}
	@Override
	public void init(Object objectForm) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
