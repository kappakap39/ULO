package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.orig.ulo.app.dao.LookupDataFactory;

public class ProductCardListBoxFilter implements ListBoxFilterInf{
	private static transient Logger logger = Logger.getLogger(ProductCardListBoxFilter.class);
	
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {
		logger.debug("ProductCardListBoxFilter");
		ArrayList<HashMap<String, Object>> dropdowns = new ArrayList<>();
		try{
			List<String> productCardList = LookupDataFactory.getLookupDataDAO().selectAllProductCard();
			for(String productCard : productCardList){
				HashMap<String,Object> data = new HashMap<>();
				logger.debug("productCard : "+productCard);
				data.put("CODE",productCard);
				data.put("VALUE",productCard);
				dropdowns.add(data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return dropdowns;
	}

	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId, HttpServletRequest request) {
		
	}

	@Override
	public void init(Object objectForm) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
