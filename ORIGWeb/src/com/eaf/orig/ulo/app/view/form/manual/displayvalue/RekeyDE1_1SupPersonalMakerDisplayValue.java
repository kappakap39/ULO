package com.eaf.orig.ulo.app.view.form.manual.displayvalue;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayValueHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class RekeyDE1_1SupPersonalMakerDisplayValue extends FormDisplayValueHelper {
	private static final transient Logger logger = Logger.getLogger(RekeyDE1_1SupPersonalMakerDisplayValue.class);
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private String COMPLETE_FLAG_FIRST_LOAD_PERSONAL = SystemConstant.getConstant("COMPLETE_FLAG_FIRST_LOAD_PERSONAL");
	@Override
	public Object getValue(String elementFieldId, Object elementValue,HttpServletRequest request) {
		logger.debug("RekeyDE1_1SupPersonalMakerDisplayValue >> ");
		try{
			boolean isVetoFlag = isVetoApplication();
			logger.debug("isVetoFlag : "+isVetoFlag);
			if(!isVetoFlag){
				boolean existSrcOfData = existSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				logger.debug("existSrcOfData >> "+existSrcOfData);
//				if(!existSrcOfData){	
					ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)elementValue;
					ArrayList<PersonalInfoDataM> personals = applicationGroup.getPersonalInfos();
					if(!Util.empty(personals)){
						Iterator<PersonalInfoDataM> iterator = personals.iterator();
						while(iterator.hasNext()){
							PersonalInfoDataM personal = iterator.next();
				 			if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personal.getPersonalType())){
				 				if(Util.empty(personal.getCompleteData())){
				 				personal.setCompleteData(COMPLETE_FLAG_FIRST_LOAD_PERSONAL);
				 				personal.clearValue();
				 				}
							}
						}
					}
//				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return elementValue;
	}
}
