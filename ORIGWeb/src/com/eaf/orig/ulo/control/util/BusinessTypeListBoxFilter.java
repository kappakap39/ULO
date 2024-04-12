package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.constant.MConstant;

public class BusinessTypeListBoxFilter implements ListBoxFilterInf
{
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,
			String cacheId, String typeId, HttpServletRequest request) {
		ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(cacheId,"ACTIVE");
		ArrayList<HashMap<String,Object>> BusinessTypeSelect = new ArrayList<>();
		String flag = MConstant.FLAG.YES;
		String mainBusiness = SystemConstant.getConstant("FILTER_BUSINESS_TYPE_MAIN");
		String otherBusiness = SystemConstant.getConstant("FILTER_BUSINESS_TYPE_OTHER");
		
		if(null != List){
			for (HashMap<String, Object> BusinessType : List) {
				if((mainBusiness).equals(configId))
				{
					String SYSTEM_ID1 = SQLQueryEngine.display(BusinessType,"SYSTEM_ID1");
					if(flag.equals(SYSTEM_ID1))
					{
						BusinessTypeSelect.add(BusinessType);
					}
				}
				else if((otherBusiness).equals(configId))
				{
					String SYSTEM_ID2 = SQLQueryEngine.display(BusinessType,"SYSTEM_ID2");
					if(flag.equals(SYSTEM_ID2))
					{
						BusinessTypeSelect.add(BusinessType);
					}
				}
			}
		}
		return BusinessTypeSelect;
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
