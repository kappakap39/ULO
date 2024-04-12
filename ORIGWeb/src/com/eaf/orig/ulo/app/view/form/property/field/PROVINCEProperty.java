package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.AddressDataM;

public class PROVINCEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(PROVINCEProperty.class);
	private String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	private String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	private String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	private String CACHE_ZIPCODE = SystemConstant.getConstant("CACHE_ZIPCODE");
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm, Object objectValue) {
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		try {

			AddressDataM address = (AddressDataM) objectForm;
			if (Util.empty(address)) {
				address = new AddressDataM();
			}
			AddressDataM lastaddress = (AddressDataM) lastObjectForm;
			if (Util.empty(lastaddress)) {
				lastaddress = new AddressDataM();
			}
			String proviceDesc = CacheControl.getName(CACHE_ZIPCODE, "ZIPCODE", address.getZipcode(),"PROVINCE");
			String lastProviceDesc = CacheControl.getName(CACHE_ZIPCODE, "ZIPCODE", lastaddress.getZipcode(), "PROVINCE");
			
			boolean compareFlag = CompareObject.compare(proviceDesc,lastProviceDesc , null);
			if (!compareFlag) {
				AuditDataM audit = new AuditDataM();
				String personalType = (String) objectValue;
				logger.debug("personalType >> " + personalType);
				if (ADDRESS_TYPE_CURRENT.equals(address.getAddressType())) {
					audit.setFieldName(AuditFormUtil.getAuditFieldName(personalType, LabelUtil.getText(request, "PROVINCE_01"), request));
				} else if (ADDRESS_TYPE_WORK.equals(address.getAddressType())) {
					audit.setFieldName(AuditFormUtil.getAuditFieldName(personalType, LabelUtil.getText(request, "PROVINCE_02"), request));
				} else if (ADDRESS_TYPE_DOCUMENT.equals(address.getAddressType())) {
					audit.setFieldName(AuditFormUtil.getAuditFieldName(personalType, LabelUtil.getText(request, "PROVINCE_03"), request));
				}
				audit.setOldValue(lastProviceDesc);
				audit.setNewValue(proviceDesc);
				audits.add(audit);
			}
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
		}

		return audits;
	}
}
