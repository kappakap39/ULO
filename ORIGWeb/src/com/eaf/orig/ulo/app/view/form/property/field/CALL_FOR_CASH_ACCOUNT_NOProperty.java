package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;

public class CALL_FOR_CASH_ACCOUNT_NOProperty  extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(CALL_FOR_CASH_ACCOUNT_NOProperty.class);
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");
	
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("CALL_FOR_CASH_ACCOUNT_NOProperty.validateFlag");
		ApplicationGroupDataM applicationGroup= (ApplicationGroupDataM)FormControl.getObjectForm(request);
		ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
		if(!Util.empty(applicationItem)){
			CashTransferDataM callForCash = applicationItem.getCashTransfer(CALL_FOR_CASH);
			logger.debug("callForCash "+callForCash);
			if(!Util.empty(callForCash) && CALL_FOR_CASH.equals(callForCash.getCashTransferType())) {
				logger.debug("callForCash.getCashTransferType() "+callForCash.getCashTransferType());
				return ValidateFormInf.VALIDATE_YES;
			}
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm, Object objectValue) {
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
			
			boolean compareFlag = CompareObject.compare(cashTransfer.getTransferAccount(), lastCashTransfer.getTransferAccount(),null);
			if(!compareFlag){
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(LabelUtil.getText(request, "TRANSFER_ACCOUNT") + " ("+LabelUtil.getText(request, "CALL_FOR_CASH") + ")");
				audit.setOldValue(FormatUtil.displayText(lastCashTransfer.getTransferAccount()));
				audit.setNewValue(FormatUtil.displayText(cashTransfer.getTransferAccount()));
				audits.add(audit);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return audits;
	}
}
