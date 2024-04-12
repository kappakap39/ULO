package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.orig.ulo.constant.MConstant;

public class CashTransferTypeListBoxFilter implements ListBoxFilterInf{

	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,
			String cacheId, String typeId, HttpServletRequest request) {
		if ("FR".equals(configId)) {
			return ListBoxControl.getListBox(cacheId, typeId);
		}
		return ListBoxControl.search(cacheId,typeId,"SYSTEM_ID1",MConstant.FLAG_Y);
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
