package com.eaf.orig.ulo.app.view.form.manual.displayvalue;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayValueHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class RekeyDE1_1MakerDisplayValue extends FormDisplayValueHelper {
	private static final transient Logger logger = Logger.getLogger(RekeyDE1_1MakerDisplayValue.class);
	public String FLIP_TYPE_INC = SystemConstant.getConstant("FLIP_TYPE_INC");	
	@Override
	public Object getValue(String elementFieldId, Object elementValue,HttpServletRequest request) {
		try{
			boolean isVetoFlag = isVetoApplication();
			logger.debug("isVetoFlag : "+isVetoFlag);
			if(!isVetoFlag){
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
				boolean existSrcOfData = existSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				logger.debug("existSrcOfData >> "+existSrcOfData);
				String applicationTemplate = applicationGroup.getApplicationTemplate();
				logger.debug("applicationTemplate >> "+applicationTemplate);
				String flipType = applicationGroup.getFlipType();
				logger.debug("flipType >> "+flipType);
				if(!existSrcOfData && !FLIP_TYPE_INC.equals(flipType)){
					elementValue = null;
				}	
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return elementValue;
	}
}
