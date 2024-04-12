package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;

public class PHONE1_REFERENCE_PERSONProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(PHONE1_REFERENCE_PERSONProperty.class);

	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm, Object objectValue) {
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();

		ArrayList<ReferencePersonDataM> referencePersons = ((ApplicationGroupDataM) objectForm).getReferencePersons();
		ArrayList<ReferencePersonDataM> lastReferencePersons = ((ApplicationGroupDataM) lastObjectForm).getReferencePersons();
		HashMap<Integer, ReferencePersonDataM> lastReferencePersonsMap = new HashMap<Integer, ReferencePersonDataM>();

		for (ReferencePersonDataM lastRefPerson : lastReferencePersons) {
			lastReferencePersonsMap.put(lastRefPerson.getSeq(), lastRefPerson);
		}

		if (!Util.empty(referencePersons)) {
			for (ReferencePersonDataM refPerson : referencePersons) {
				int seq = refPerson.getSeq();
				ReferencePersonDataM lastRefPerson = lastReferencePersonsMap.get(seq);
				if (Util.empty(lastRefPerson)) {
					lastRefPerson = new ReferencePersonDataM();
				}

				boolean compareFlag = CompareObject.compare(refPerson.getPhone1(), lastRefPerson.getPhone1(), null);
				logger.debug("compare >> " + compareFlag);
				if (!compareFlag) {
					AuditDataM audit = new AuditDataM();
					audit.setFieldName(LabelUtil.getText(request, "REFERENCE_" + String.valueOf(seq)) + " " + LabelUtil.getText(request, "REF_PHONE1_PERSON"));
					audit.setOldValue(FormatUtil.displayText(lastRefPerson.getPhone1()));
					audit.setNewValue(FormatUtil.displayText(refPerson.getPhone1()));
					audits.add(audit);
				}
			}
		}

		return audits;
	}
}
