package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;

public class FinalDecisionCAListBoxFilter implements ListBoxFilterInf  {
	private static transient Logger logger = Logger.getLogger(FinalDecisionListBoxFilter.class);
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {
		ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(cacheId,"ACTIVE");
		ArrayList<String> ROLE_ALL_CA_INBOX = SystemConstant.getArrayListConstant("ROLE_ALL_CA_INBOX");
		ArrayList<HashMap<String,Object>> vFinalDecision = new ArrayList<>();
		String roleId = FormControl.getFormRoleId(request);
		String CONDITION_SYSTEM_ID3 = SystemConstant.getConstant("CONDITION_SYSTEM_ID3");
		 logger.debug("cacheId>>"+cacheId);
		 logger.debug("configId>>"+configId);
		 logger.debug("CONDITION_SYSTEM_ID3>>"+CONDITION_SYSTEM_ID3);
		 logger.debug("roleId : "+roleId);
		if(null != List && ROLE_ALL_CA_INBOX.contains(roleId)){
			for (HashMap<String, Object> finalResult : List){							
				String SYSTEM_ID3 = SQLQueryEngine.display(finalResult,"SYSTEM_ID3");
				logger.debug("SYSTEM_ID3>>"+SYSTEM_ID3);
				logger.debug("finalResult>>"+finalResult);
				if(CONDITION_SYSTEM_ID3.equals(SYSTEM_ID3)){
					vFinalDecision.add(finalResult);
				}
			}
		}else{
			return List;
		}
		return vFinalDecision;
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
