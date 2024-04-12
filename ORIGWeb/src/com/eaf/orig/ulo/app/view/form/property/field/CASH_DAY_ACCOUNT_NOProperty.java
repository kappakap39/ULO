package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;

public class CASH_DAY_ACCOUNT_NOProperty  extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(CASH_DAY_ACCOUNT_NOProperty.class);
	
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
		String CASH_DAY_1 = SystemConstant.getConstant("CASH_DAY_1");	
		String CASH_DAY_5 = SystemConstant.getConstant("CASH_DAY_5");
		String CASH_1_HOUR = SystemConstant.getConstant("CASH_1_HOUR");
		String []cashTransFerType = {CASH_DAY_1,CASH_DAY_5,CASH_1_HOUR};
		ApplicationGroupDataM applicationGroup= (ApplicationGroupDataM)FormControl.getObjectForm(request);
		ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
		
		CashTransferDataM cashTransfer = applicationItem.getCashTransfer(cashTransFerType);	
		if(!Util.empty(cashTransfer)) {
			if(CASH_DAY_1.equals(cashTransfer.getCashTransferType()) || CASH_1_HOUR.equals(cashTransfer.getCashTransferType()) ){
				logger.debug("cashTransfer.getCashTransferType() "+cashTransfer.getCashTransferType());
				return ValidateFormInf.VALIDATE_YES;
			}
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm, Object objectValue) {
		logger.debug("CASH_DAY_ACCOUNT_NOProperty.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<>();
		try{
			String personalType = (String)objectValue;
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
				audit.setFieldName(AuditFormUtil.getAuditFieldName(
						personalType, LabelUtil.getText(request, "ACCOUNT_NO") +" ("+LabelUtil.getText(request, "CASH_TRANSFER_TYPE") +")", request));
				audit.setOldValue(lastCashTransfer.getTransferAccount());
				audit.setNewValue(cashTransfer.getTransferAccount());
				audits.add(audit);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return audits;
	}
}
