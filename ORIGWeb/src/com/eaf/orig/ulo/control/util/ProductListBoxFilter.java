package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class ProductListBoxFilter  implements ListBoxFilterInf{
	private static transient Logger logger = Logger.getLogger(ProductListBoxFilter.class);	
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,
			String cacheId, String typeId, HttpServletRequest request) {
		String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
		String CACHE_TEMPLATE_PRODUCT = SystemConstant.getConstant("CACHE_TEMPLATE_PRODUCT");
		ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ArrayList<HashMap<String, Object>> productSelect = new ArrayList<>();
		if (!Util.empty(applicationGroup)) {
			ArrayList<HashMap<String, Object>> templateProduct = CacheControl.search(CACHE_TEMPLATE_PRODUCT, "CODE",applicationGroup.getApplicationTemplate());
			if (!Util.empty(templateProduct)) {
				for (HashMap<String, Object> templateProductItem : templateProduct) {
					ArrayList<HashMap<String, Object>> productList = ListBoxControl.search(FIELD_ID_PRODUCT_TYPE, "", "SYSTEM_ID1",(String) templateProductItem.get("VALUE"));
					if (!Util.empty(productList)) {
						for (HashMap<String, Object> productItem : productList) {
							//KPL Additional : Add typeId comparing
							if(productItem.get("ACTIVE_STATUS") != null)
							{
								if(((String)productItem.get("ACTIVE_STATUS")).equals("A"))
								{productSelect.add(productItem);}
							}
						}

					}
				}
			}
		}
		return productSelect;
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
