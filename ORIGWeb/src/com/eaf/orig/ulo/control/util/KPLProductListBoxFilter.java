package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;

public class KPLProductListBoxFilter  implements ListBoxFilterInf{
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,
			String cacheId, String typeId, HttpServletRequest request) {
		String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
		String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
		ArrayList<HashMap<String, Object>> productList = ListBoxControl.search(FIELD_ID_PRODUCT_TYPE, typeId, "SYSTEM_ID1",PRODUCT_K_PERSONAL_LOAN);
		return productList;
	}

	@Override
	public void setFilterProperties(String configId, String cacheId,
			String typeId, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(Object objectForm) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
