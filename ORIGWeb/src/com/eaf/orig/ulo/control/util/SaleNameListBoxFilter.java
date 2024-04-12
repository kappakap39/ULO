package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
//import com.eaf.core.ulo.common.properties.ListBoxControl;
//import com.eaf.core.ulo.common.properties.SystemConstant;
//import com.eaf.core.ulo.common.util.Util;

@Deprecated
public class SaleNameListBoxFilter implements ListBoxFilterInf {
	private static transient Logger logger = Logger.getLogger(SaleNameListBoxFilter.class);
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {
//		String CACHE_SALE_INFO = SystemConstant.getConstant("CACHE_SALE_INFO");
//		String CACHE_DSA_SALE_INFO = SystemConstant.getConstant("CACHE_DSA_SALE_INFO");
		ArrayList<HashMap<String,Object>> List = new ArrayList<HashMap<String,Object>>();
//		logger.debug("Add all DIH SaleInfo");
//		ArrayList<HashMap<String,Object>> dihLisboxInfo = ListBoxControl.getListBox(CACHE_SALE_INFO,"ACTIVE");
//		if(!Util.empty(dihLisboxInfo)){
//			List.addAll(dihLisboxInfo);
//		}
//
//		logger.debug("Add all DSA SaleInfo");
//		ArrayList<HashMap<String,Object>> dsaLisboxInfo = ListBoxControl.getListBox(CACHE_DSA_SALE_INFO, "ACTIVE");
//		if(!Util.empty(dsaLisboxInfo)){
//			List.addAll(dsaLisboxInfo);
//		}
		return List;
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
