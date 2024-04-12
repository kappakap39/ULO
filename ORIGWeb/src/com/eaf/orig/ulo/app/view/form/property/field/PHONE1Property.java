package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.AddressDataM;

public class PHONE1Property extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(PHONE1Property.class);
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		AddressDataM address = (AddressDataM)objectForm;
		AddressDataM lastaddress = (AddressDataM)lastObjectForm;
		
		if(Util.empty(address)){
			address = new AddressDataM();
		}
		if(Util.empty(lastaddress)){
			lastaddress = new AddressDataM();
		}

		boolean compare = CompareObject.compare(address.getPhone1(), lastaddress.getPhone1(),null);
		logger.debug("compare >> "+compare);
		if(!compare){
			AuditDataM audit = new AuditDataM();
			String personalType = (String) objectValue;
			if(ADDRESS_TYPE_CURRENT.equals(lastaddress.getAddressType())){
				audit.setFieldName(AuditFormUtil.getAuditFieldName(
						personalType, LabelUtil.getText(request, "PHONE1_CURRENT"), request));
			}else if(ADDRESS_TYPE_DOCUMENT.equals(lastaddress.getAddressType())){
				audit.setFieldName(AuditFormUtil.getAuditFieldName(
						personalType, LabelUtil.getText(request, "PHONE1_DOCUMENT"), request));
			}
			else if(ADDRESS_TYPE_WORK.equals(lastaddress.getAddressType())){
				audit.setFieldName(AuditFormUtil.getAuditFieldName(
						personalType, LabelUtil.getText(request, "PHONE1_WORK"), request));
			}
			audit.setOldValue(FormatUtil.displayText(lastaddress.getPhone1()));
			audit.setNewValue(FormatUtil.displayText(address.getPhone1()));
			audits.add(audit);
		}		
		return audits;
	}
}
