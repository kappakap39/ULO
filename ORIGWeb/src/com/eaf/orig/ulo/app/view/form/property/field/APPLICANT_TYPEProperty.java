package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;

public class APPLICANT_TYPEProperty  extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(APPLICANT_TYPEProperty.class);
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		String personalType = (String)objectValue;
		ApplicationDataM application = (ApplicationDataM)objectForm;
		ApplicationDataM lastapplication = (ApplicationDataM)lastObjectForm;
		
		if(Util.empty(application)){
			application = new ApplicationDataM();
		}
		if(Util.empty(lastapplication)){
			lastapplication = new ApplicationDataM();
		}
		
		boolean compareApplicationType = CompareObject.compare(application.getApplicationType(), lastapplication.getApplicationType(),null);
		if(!compareApplicationType){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalType, LabelUtil.getText(request, "APPLICANT_TYPE"), request));
			audit.setOldValue(FormatUtil.displayText(lastapplication.getApplicationType()));
			audit.setNewValue(FormatUtil.displayText(application.getApplicationType()));
			audits.add(audit);
		}		
		return audits;
	}
}
