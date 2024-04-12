package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.AddressDataM;

public class ADDRESS_STYLEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(ADDRESS_STYLEProperty.class);
	String FIELD_ID_ADDRESS_STYLE = SystemConstant.getConstant("FIELD_ID_ADDRESS_STYLE");
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		try{
			
			AddressDataM address = (AddressDataM)objectForm;
			if(Util.empty(address)){
				address = new AddressDataM();
			}
			AddressDataM lastaddress = (AddressDataM)lastObjectForm;
			if(Util.empty(lastaddress)){
				lastaddress = new AddressDataM();
			}
			
			boolean compareFlag = CompareObject.compare(address.getBuildingType(), lastaddress.getBuildingType(),null);
			if(!compareFlag){
				AuditDataM audit = new AuditDataM();
				String personalType = (String) objectValue;
				logger.debug("personalType >> "+personalType);
				audit.setFieldName(AuditFormUtil.getAuditFieldName(personalType, LabelUtil.getText(request, "ADDRESS_STYLE"), request));
				audit.setOldValue(ListBoxControl.getName(FIELD_ID_ADDRESS_STYLE, lastaddress.getBuildingType()));
				audit.setNewValue(ListBoxControl.getName(FIELD_ID_ADDRESS_STYLE, address.getBuildingType()));
				audits.add(audit);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		
		return audits;
	}
}
