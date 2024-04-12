package com.eaf.orig.ulo.app.view.form.manual.displayvalue;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayValueHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class RekeyCardLinkDisplayValue extends FormDisplayValueHelper {
	private static final transient Logger logger = Logger.getLogger(Rekey2MakerDisplayValue.class);
	@Override
	public Object getValue(String elementFieldId, Object elementValue,HttpServletRequest request) {
		try{
			String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();	
			 String ApplyType = applicationGroup.getApplicationType();
			 boolean existSrcTwoMaker;
			 if(APPLICATION_TYPE_INCREASE.equals(ApplyType)){
				 existSrcTwoMaker = existSrcOfData(CompareDataM.SoruceOfData.CARD_LINK);
			 }else{
				 existSrcTwoMaker = existSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
			 }
			boolean isVetoFlag = isVetoApplication();
			logger.debug("isVetoFlag : "+isVetoFlag);
			if(!isVetoFlag){
				logger.debug("elementFieldId >> "+elementFieldId);
				logger.debug("elementValue >> "+elementValue);
				logger.debug("existSrcTwoMaker >> "+existSrcTwoMaker);
				if(!existSrcTwoMaker){
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
