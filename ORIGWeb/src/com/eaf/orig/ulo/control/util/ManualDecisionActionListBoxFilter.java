package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;

public class ManualDecisionActionListBoxFilter implements ListBoxFilterInf {
	private static transient Logger logger = Logger.getLogger(ManualDecisionActionListBoxFilter.class);
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request){
//		FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
		String roleId = FormControl.getFormRoleId(request);
		logger.debug("cacheId >> "+cacheId);
		logger.debug("roleId >> "+roleId);
		return ListBoxControl.search(cacheId,typeId,"SYSTEM_ID1",roleId);
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
