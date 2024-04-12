package com.eaf.orig.ulo.app.view.form.manual.displayvalue;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayValueHelper;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class PrevRole2MakerDisplayValue extends FormDisplayValueHelper {
	private static final transient Logger logger = Logger.getLogger(PrevRole2MakerDisplayValue.class);
	@Override
	public Object getValue(String elementFieldId, Object elementValue,HttpServletRequest request) {
		try{
			boolean isVetoFlag = isVetoApplication();
			logger.debug("isVetoFlag : "+isVetoFlag);
			if(!isVetoFlag){
				boolean existSrcTwoMaker = existSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				boolean existSrcPrevRole = existSrcOfData(CompareDataM.SoruceOfData.PREV_ROLE);
				logger.debug("elementFieldId >> "+elementFieldId);
				logger.debug("elementValue >> "+elementValue);
				logger.debug("existSrcTwoMaker >> "+existSrcTwoMaker);
				logger.debug("existSrcPrevRole >> "+existSrcPrevRole);
				if(!existSrcTwoMaker){
					CompareDataM compareData = getCompareData(CompareDataM.SoruceOfData.PREV_ROLE,elementFieldId);
					if(null != compareData){
						return elementValue;
					}
					elementValue = null;
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		logger.debug(elementFieldId+".elementValue >> "+elementValue);
		return elementValue;
	}
}
