package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;

public class DocumentListAdditionalListBoxFilter implements ListBoxFilterInf {
	
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,
			String cacheId, String typeId, HttpServletRequest request) {
		String CACH_NAME_DOCUMENT_LIST = SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST_TYPE");
		ArrayList<HashMap<String, Object>> documentListAdditional = CacheControl.search(CACH_NAME_DOCUMENT_LIST, "DOCUMENT_TYPE", "ADDITIONAL");
		return documentListAdditional;
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
