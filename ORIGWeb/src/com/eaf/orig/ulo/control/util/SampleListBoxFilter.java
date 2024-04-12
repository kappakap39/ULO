package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.ListBoxControl;

public class SampleListBoxFilter implements ListBoxFilterInf{
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,
			String cacheId, String typeId, HttpServletRequest request) {	
		ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(cacheId,"ACTIVE");
		ArrayList<HashMap<String,Object>> vAddressType = new ArrayList<>();
		if(null != List){
			for (HashMap<String, Object> AddressType : vAddressType) {
				if("X1".equals(configId)){
					String SYSTEM1 = SQLQueryEngine.display(AddressType,"SYSTEM1");
					if("111".equals(SYSTEM1)){
						vAddressType.add(AddressType);
					}
				}
			}
		}
		return vAddressType;
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
