package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.orig.ulo.constant.MConstant;

public class CIDListBoxFilter implements ListBoxFilterInf {
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request){		
		ArrayList<HashMap<String,Object>> listBoxs = ListBoxControl.getListBox(cacheId,"ACTIVE");
		ArrayList<HashMap<String,Object>> cidTypes = new ArrayList<>();
		String flag = MConstant.FLAG.YES;	
		if(null != listBoxs){
			for (HashMap<String, Object> listBox : listBoxs) {
				if("CID_TYPE".equals(configId)){
					String SYSTEM_ID1 = SQLQueryEngine.display(listBox,"SYSTEM_ID1");
					if(flag.equals(SYSTEM_ID1)){
						cidTypes.add(listBox);
					}
				}
			}
		}
		return cidTypes;
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
