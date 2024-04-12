package com.eaf.orig.ulo.app.view.form.manual.displayvalue;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayValueHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class ClearFlagEditAddressDE2DisplayValue extends FormDisplayValueHelper {
	private static final transient Logger logger = Logger.getLogger(Rekey2MakerDisplayValue.class);
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	@Override
	public Object getValue(String elementFieldId, Object elementValue,HttpServletRequest request) {
		try{
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)elementValue;
			boolean isVetoFlag = isVetoApplication();
			logger.debug("isVetoFlag : "+isVetoFlag);
			if(!isVetoFlag){
				boolean existSrcTwoMaker = existSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				logger.debug("elementFieldId >> "+elementFieldId);
				logger.debug("elementValue >> "+elementValue);
				logger.debug("existSrcTwoMaker >> "+existSrcTwoMaker);
				PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
				if(!existSrcTwoMaker && !Util.empty(personalInfo)){
//					if(ADDRESS_TYPE_CURRENT.equals(personalInfo.getMailingAddress())){
						AddressDataM  addressWork = personalInfo.getAddress(ADDRESS_TYPE_WORK);
						if(!Util.empty(addressWork)){
							addressWork.setEditFlag(null);
						}
//					}else if(ADDRESS_TYPE_WORK.equals(personalInfo.getMailingAddress())){
						AddressDataM  addressCurrent = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
						if(!Util.empty(addressCurrent)){
							addressCurrent.setEditFlag(null);
						}
						
						AddressDataM  addressDocument = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
						if(!Util.empty(addressDocument)){
							addressDocument.setEditFlag(null);
						}
//					}
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
