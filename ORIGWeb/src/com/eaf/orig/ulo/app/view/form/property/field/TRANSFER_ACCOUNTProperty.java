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
import com.eaf.orig.ulo.model.app.CashTransferDataM;


public class TRANSFER_ACCOUNTProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(TRANSFER_ACCOUNTProperty.class);
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){		
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		String personalType = (String)objectValue;
		CashTransferDataM cashTransfer = (CashTransferDataM)objectForm;
		CashTransferDataM lastCashTransfer = (CashTransferDataM)lastObjectForm;
		
		if(Util.empty(cashTransfer)){
			cashTransfer = new CashTransferDataM();
		}
		if(Util.empty(lastCashTransfer)){
			lastCashTransfer = new CashTransferDataM();
		}
		
		boolean compareTransferAcc = CompareObject.compare(cashTransfer.getTransferAccount(), lastCashTransfer.getTransferAccount(),null);
		if(!compareTransferAcc){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalType, LabelUtil.getText(request, "TRANSFER_ACCOUNT_AUDIT"), request));
			audit.setOldValue(FormatUtil.displayText(lastCashTransfer.getTransferAccount()));
			audit.setNewValue(FormatUtil.displayText(cashTransfer.getTransferAccount()));
			audits.add(audit);
		}
		return audits;
	}
}
