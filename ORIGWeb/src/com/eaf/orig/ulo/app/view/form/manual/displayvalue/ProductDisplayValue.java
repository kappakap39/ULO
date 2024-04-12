package com.eaf.orig.ulo.app.view.form.manual.displayvalue;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayValueHelper;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class ProductDisplayValue extends FormDisplayValueHelper {
	private static final transient Logger logger = Logger.getLogger(ProductDisplayValue.class);
	@Override
	public Object getValue(String elementFieldId, Object elementValue,HttpServletRequest request) {
		try{
				boolean existSrcOfData = existSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				logger.debug("existSrcOfData >> "+existSrcOfData);
				if(!existSrcOfData){
					elementValue = null;
				}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return elementValue;
	}
}