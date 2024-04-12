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

public class ADRSTSProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(ADRSTSProperty.class);
	String FIELD_ID_ADRSTS = SystemConstant.getConstant("FIELD_ID_ADRSTS");
	
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

			boolean compareFlag = CompareObject.compare(address.getAdrsts(), lastaddress.getAdrsts(), null);
			if (!compareFlag) {
				AuditDataM audit = new AuditDataM();
				String personalType = (String) objectValue;
				logger.debug("personalType >> " + personalType);
				audit.setFieldName(AuditFormUtil.getAuditFieldName(personalType, LabelUtil.getText(request, "ADRSTS_01"), request));
				audit.setOldValue(ListBoxControl.getName(FIELD_ID_ADRSTS, lastaddress.getAdrsts()));
				audit.setNewValue(ListBoxControl.getName(FIELD_ID_ADRSTS, address.getAdrsts()));
				audits.add(audit);
			}
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
		}

		return audits;
	}
}
