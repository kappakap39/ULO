package com.eaf.orig.ulo.app.view.form.property.field;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;

public class TERMProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(TERMProperty.class);
	String FIELD_ID_TERM = SystemConstant.getConstant("FIELD_ID_TERM");
	String ELEMENT_ID;
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		logger.debug("TERMProperty.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();	
		try{	
			LoanDataM loan = (LoanDataM)objectForm;	
			LoanDataM lastLoan = (LoanDataM)lastObjectForm;
			
			if(Util.empty(loan)){
				loan = new LoanDataM();
			}
			if(Util.empty(lastLoan)){
				lastLoan = new LoanDataM();
			}
			
			boolean compareFlag = CompareObject.compare(loan.getRequestTerm(), lastLoan.getRequestTerm(),null);
			if(!compareFlag){
				AuditDataM audit = new AuditDataM();
				String term = FormatUtil.display(loan.getRequestTerm());
				String lastTerm = FormatUtil.display(lastLoan.getRequestTerm());
				audit.setFieldName(AuditFormUtil.getAuditFieldName(
						OrigConstant.PERSONAL_TYPE_APPLICANT, LabelUtil.getText(request, "TERM"), request));
				audit.setOldValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_TERM, lastTerm)));
				audit.setNewValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_TERM, term)));
				audits.add(audit);
			}
		}catch(Exception e){
			logger.fatal("ERROR >> ",e);
		}
		return audits;
	}
	
	@Override
	public void auditInfo(String subformId, String elementId) {
		this.ELEMENT_ID = elementId;
	}
}
