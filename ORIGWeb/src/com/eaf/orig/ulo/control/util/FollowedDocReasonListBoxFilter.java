package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.orig.ulo.constant.MConstant;

public class FollowedDocReasonListBoxFilter implements ListBoxFilterInf{
	@Override
	public ArrayList<HashMap<String,Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request){
		ArrayList<HashMap<String,Object>> Lists = ListBoxControl.getListBox(cacheId,"ACTIVE");
		ArrayList<HashMap<String,Object>> Select = new ArrayList<>();
		String flag = MConstant.FLAG.YES;		
		if(null != Lists){
			for (HashMap<String, Object> list : Lists) {
				String DOCUMENT_CODE = SQLQueryEngine.display(list,"DOCUMENT_CODE");
				if(null != configId && configId.equals(DOCUMENT_CODE)){
					Select.add(list);
				}
			}
		}
		return Select;
	}
	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId,HttpServletRequest request) {
		
	}
	@Override
	public void init(Object objectForm) {
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
