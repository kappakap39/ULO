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

public class ZIP_CODEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(ZIP_CODEProperty.class);
	private String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	private String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	private String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		logger.debug("ZIP_CODEProperty.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		try{
			
			AddressDataM address = (AddressDataM)objectForm;
			if(Util.empty(address)){
				logger.debug("address is null");
				address = new AddressDataM();
			}
			AddressDataM lastaddress = (AddressDataM)lastObjectForm;
			if(Util.empty(lastaddress)){
				logger.debug("lastaddress is null");
				lastaddress = new AddressDataM();
			}
			boolean compareAccountNo = CompareObject.compare(address.getZipcode(), lastaddress.getZipcode(),null);
			if(!compareAccountNo){
				AuditDataM audit = new AuditDataM();
				String personalType = (String) objectValue;
				logger.debug("personalType >> "+personalType);
				if(ADDRESS_TYPE_CURRENT.equals(address.getAddressType())){
					audit.setFieldName(AuditFormUtil.getAuditFieldName(
							personalType, LabelUtil.getText(request, "ZIP_CODE_CURRENT"), request));
				}else if(ADDRESS_TYPE_DOCUMENT.equals(address.getAddressType())){
					audit.setFieldName(AuditFormUtil.getAuditFieldName(
							personalType, LabelUtil.getText(request, "ZIP_CODE_DOCUMENT"), request));
				}else if(ADDRESS_TYPE_WORK.equals(address.getAddressType())){
					audit.setFieldName(AuditFormUtil.getAuditFieldName(
							personalType, LabelUtil.getText(request, "ZIP_CODE_WORK"), request));
				}
				audit.setOldValue(FormatUtil.display(lastaddress.getZipcode()));
				audit.setNewValue(FormatUtil.display(address.getZipcode()));
				audits.add(audit);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		
		return audits;
	}
}
