package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.model.app.CashTransferDataM;

public class CALL_FOR_CASHProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(CALL_FOR_CASHProperty.class);
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm, Object objectValue) {
		ArrayList<AuditDataM> audits = new ArrayList<>();
		try{
			String cashTransferFlag = OrigConstant.FLAG_N;
			String lastCashTransferFlag = OrigConstant.FLAG_N;
			CashTransferDataM cashTransfer = (CashTransferDataM)objectForm;
			CashTransferDataM lastCashTransfer = (CashTransferDataM)lastObjectForm;
			
			if(!Util.empty(cashTransfer) && !Util.empty(cashTransfer.getCashTransferType())){
				cashTransferFlag = OrigConstant.FLAG_Y;
			}
			if(!Util.empty(lastCashTransfer)&& !Util.empty(lastCashTransfer.getCashTransferType())){
				lastCashTransferFlag = OrigConstant.FLAG_Y;
			}
			if(!cashTransferFlag.equals(lastCashTransferFlag)){
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(LabelUtil.getText(request, "CALL_FOR_CASH"));
				audit.setOldValue(FormatUtil.displayText(OrigConstant.FLAG_Y.equals(lastCashTransferFlag) ? LabelUtil.getText(request, "YES") : ""));
				audit.setNewValue(FormatUtil.displayText(OrigConstant.FLAG_Y.equals(cashTransferFlag) ? LabelUtil.getText(request, "YES") : ""));
				audits.add(audit);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return audits;
	}
}
