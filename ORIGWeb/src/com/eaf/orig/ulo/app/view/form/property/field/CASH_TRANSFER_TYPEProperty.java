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
import com.eaf.orig.ulo.model.app.CashTransferDataM;

public class CASH_TRANSFER_TYPEProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(CASH_TRANSFER_TYPEProperty.class);
	private String FIELD_ID_CASH_TRANSFER_TYPE = SystemConstant.getConstant("FIELD_ID_CASH_TRANSFER_TYPE");
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm, Object objectValue) {
		logger.debug("CASH_TRANSFER_TYPEProperty.auditForm");
		String personalType = (String)objectValue;
		ArrayList<AuditDataM> audits = new ArrayList<>();
		try{
			CashTransferDataM cashTransfer = (CashTransferDataM)objectForm;
			CashTransferDataM lastCashTransfer = (CashTransferDataM)lastObjectForm;
			
			if(Util.empty(cashTransfer)){
				cashTransfer = new CashTransferDataM();
			}
			if(Util.empty(lastCashTransfer)){
				lastCashTransfer = new CashTransferDataM();
			}
			
			boolean compareFlag = CompareObject.compare(cashTransfer.getCashTransferType(), lastCashTransfer.getCashTransferType(),null);
			if(!compareFlag){
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(AuditFormUtil.getAuditFieldName(
						personalType, LabelUtil.getText(request, "CASH_DAY_ONE"), request));
				audit.setOldValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_CASH_TRANSFER_TYPE, lastCashTransfer.getCashTransferType())));
				audit.setNewValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_CASH_TRANSFER_TYPE, cashTransfer.getCashTransferType())));
				audits.add(audit);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return audits;
	}
}
