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

public class ADDRESS_NOProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(ADDRESS_NOProperty.class);
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		logger.debug("ADDRESS_NOProperty.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		AddressDataM address = (AddressDataM)objectForm;
		AddressDataM lastaddress = (AddressDataM)lastObjectForm;
		
		if(Util.empty(address)){
			address = new AddressDataM();
		}
		if(Util.empty(lastaddress)){
			lastaddress = new AddressDataM();
		}
		
		boolean compareAccountNo = CompareObject.compare(address.getAddress(), lastaddress.getAddress(),null);
		if(!compareAccountNo){
			AuditDataM audit = new AuditDataM();
			String personalType = (String)objectValue;
			if(ADDRESS_TYPE_CURRENT.equals(lastaddress.getAddressType())){
				audit.setFieldName(AuditFormUtil.getAuditFieldName(
						personalType, LabelUtil.getText(request, "ADDRESS_CURRENT_AUDIT"), request));
			}else if(ADDRESS_TYPE_DOCUMENT.equals(lastaddress.getAddressType())){
				audit.setFieldName(AuditFormUtil.getAuditFieldName(
						personalType, LabelUtil.getText(request, "ADDRESS_DOCUMENT_AUDIT"), request));
			}
			else if(ADDRESS_TYPE_WORK.equals(lastaddress.getAddressType())){
				audit.setFieldName(AuditFormUtil.getAuditFieldName(
						personalType, LabelUtil.getText(request, "ADDRESS_WORK_AUDIT"), request));
			}
			audit.setOldValue(FormatUtil.displayText(lastaddress.getAddress()));
			audit.setNewValue(FormatUtil.displayText(address.getAddress()));
			audits.add(audit);
		}	
		return audits;
	}
}
