package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.math.BigDecimal;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCashTransferDataM;

public class CashDayOneSubForm extends ORIGSubForm {
	
	static Logger logger = Logger.getLogger(CashDayOneSubForm.class);

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {

		PLOrigFormHandler origForm = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		PLApplicationDataM applicationM = origForm.getAppForm();
		PLCashTransferDataM cashTransferM = applicationM.getCashTransfer();
		
		if(null == cashTransferM){
			cashTransferM = new PLCashTransferDataM();
			applicationM.setCashTransfer(cashTransferM);
		}
		
		String cash_day1_day1Flag = request.getParameter("cash_day1_day1Flag");
		String cash_day1_account_no = request.getParameter("cash_day1_account_no");
		String cash_day1_account_name = request.getParameter("cash_day1_account_name");
		String cash_day1_percent = request.getParameter("cash_day1_percent");
		String cash_day1_first_tranfer = request.getParameter("cash_day1_first_tranfer");
		String cash_day1_quick_cash = request.getParameter("cash_day1_quick_cash");
		String cash_day1_bank_transfer = request.getParameter("cash_day1_bank_transfer");
		String cash_day1_remark = request.getParameter("cash_day1_remark");

		cashTransferM.setCashTransferType(cash_day1_day1Flag);
		cashTransferM.setTransAcc(cash_day1_account_no);
		cashTransferM.setAccName(cash_day1_account_name);
		
		BigDecimal percentTran = null;
		if(!OrigUtil.isEmptyString(cash_day1_percent)){
			try{
				percentTran = DataFormatUtility.StringToBigDecimalEmptyNull(cash_day1_percent);
			}catch(Exception e){
				logger.fatal("Exception ",e);
			}
		}
		cashTransferM.setPercentTrans(percentTran);
		
		cashTransferM.setFirstTransAmount(DataFormatUtility.StringToBigDecimalEmptyNull(cash_day1_first_tranfer));
		cashTransferM.setExpressTrans(cash_day1_quick_cash);
		cashTransferM.setBankTransfer(cash_day1_bank_transfer);
		cashTransferM.setRemark(cash_day1_remark);
		cashTransferM.setCreateBy(userM.getUserName());
		cashTransferM.setUpdateBy(userM.getUserName());
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		logger.debug("validate Cash day 1 SubForm ");
		boolean result = false;
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		/** New Logic Manual Validate Subform #Sankom */
		String errorMsg = "";
		if(OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType()) && 
				(OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT == formHandler.getMandatoryType())) { //Praisan 20121218 validate only type submit (1)
			String cash_day1_day1Flag = request.getParameter("cash_day1_day1Flag");
			if (OrigConstant.cashDayType.CASH_DAY_1.equals(cash_day1_day1Flag) || OrigConstant.cashDayType.CASH_DAY_5.equals(cash_day1_day1Flag)) {
				String cash_day1_percent = request.getParameter("cash_day1_percent");
				if (cash_day1_percent == null || "".equals(cash_day1_percent)) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "CASH_DAY1_PERCENT");
					formHandler.PushErrorMessage("cash_day1_percent", errorMsg);
					result = true;
				} else {

					if (OrigConstant.CASH_DAY1_OTHER.equals(cash_day1_percent)) {
						String cash_day1_first_tranfer = request.getParameter("cash_day1_first_tranfer");
						BigDecimal bFistTranfer = null;
						try {
							bFistTranfer = DataFormatUtility.StringToBigDecimal(cash_day1_first_tranfer);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (bFistTranfer == null || (bFistTranfer.compareTo(new BigDecimal(0)) == 0)) {
							errorMsg = ErrorUtil.getShortErrorMessage(request, "CASH_DAY1_FIRST_TRANSFER");
							formHandler.PushErrorMessage("cash_day1_first_tranfer", errorMsg);
							result = true;
						}
					}
				}
				if (OrigConstant.cashDayType.CASH_DAY_1.equals(cash_day1_day1Flag)) {
					String cash_day1_bank_transfer = request.getParameter("cash_day1_bank_transfer");
					if (cash_day1_bank_transfer == null || "".equals(cash_day1_bank_transfer)) {
						errorMsg = ErrorUtil.getShortErrorMessage(request, "CASH_DAY1_BANK_TRANSFER");
						formHandler.PushErrorMessage("cash_day1_bank_transfer", errorMsg);
						result = true;
					}
					String cash_day1_account_no = request.getParameter("cash_day1_account_no");
					if (cash_day1_account_no == null || "".equals(cash_day1_account_no)) {
						errorMsg = ErrorUtil.getShortErrorMessage(request, "CASH_DAY1_ACCOUNT_NO");
						formHandler.PushErrorMessage("cash_day1_account_no", errorMsg);
						result = true;
					}
				}
			}
		}
		return result;
	}
}
