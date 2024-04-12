package com.eaf.orig.ulo.app.view.form.subform.increase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;

public class IncreaseCardRequestElement extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(IncreaseCardRequestElement.class);
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String BUTTON_ACTION_EXECUTE = SystemConstant.getConstant("BUTTON_ACTION_EXECUTE");
	String FIELD_ID_APPLICATION_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_APPLICATION_CARD_TYPE");
	
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();		
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		String BTN_ACTION  = request.getParameter("buttonAction");
		if(!Util.empty(applications) && !BUTTON_ACTION_EXECUTE.equals(BTN_ACTION)){
			boolean WITH_OUT_MAIN = true;
			for(ApplicationDataM application:applications){
				if(APPLICATION_CARD_TYPE_BORROWER.equals(application.getCard().getApplicationType())){
					WITH_OUT_MAIN = false;	
				}
			}
			if(WITH_OUT_MAIN){
				formError.error(MessageErrorUtil.getText(request,"ERROR_PERSONAL_SUP_CARD"));
			}
		}
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		if (applications.size() == 0 && !BUTTON_ACTION_EXECUTE.equals(BTN_ACTION)) {
			formError.error(PREFIX_ERROR+LabelUtil.getText(request,"CARD_MAIN_REQUEST_INFO_SUBFORM"));
		}
		return formError.getFormError();
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm, Object objectValue) {
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();

		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) objectForm;
		ApplicationGroupDataM lastApplicationGroup = (ApplicationGroupDataM) lastObjectForm;
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		ArrayList<ApplicationDataM> lastApplications = lastApplicationGroup.filterApplicationLifeCycle();

		ArrayList<String> cardNoList = new ArrayList<String>();
		ArrayList<String> lastCardNoList = new ArrayList<String>();

		HashMap<String, LoanDataM> cardHm = new HashMap<String, LoanDataM>();
		HashMap<String, LoanDataM> lastCardHm = new HashMap<String, LoanDataM>();

		if (applications.size() > 0) {
			for (ApplicationDataM application : applications) {
				if (Util.empty(application)) {
					application = new ApplicationDataM();
				}
				ArrayList<LoanDataM> loans = application.getLoans();
				if (Util.empty(loans)) {
					loans = new ArrayList<LoanDataM>();
				}
				for (LoanDataM loan : loans) {
					CardDataM card = loan.getCard();
					if (!Util.empty(card) && !Util.empty(card.getCardNo())) {
						cardNoList.add(getCardNo(card.getCardNo()));
						cardHm.put(getCardNo(card.getCardNo()), loan);
					}
				}
			}
		}
		if (lastApplications.size() > 0) {
			for (ApplicationDataM lastApplication : lastApplications) {
				if (Util.empty(lastApplication)) {
					lastApplication = new ApplicationDataM();
				}
				ArrayList<LoanDataM> lastLoans = lastApplication.getLoans();
				if (Util.empty(lastLoans)) {
					lastLoans = new ArrayList<LoanDataM>();
				}
				for (LoanDataM lastLoan : lastLoans) {
					CardDataM lastCard = lastLoan.getCard();
					if (!Util.empty(lastCard) && !Util.empty(lastCard.getCardNo())) {
						lastCardNoList.add(getCardNo(lastCard.getCardNo()));
						lastCardHm.put(getCardNo(lastCard.getCardNo()), lastLoan);
					}
				}
			}
		}

		ArrayList<String> allCardNoList = new ArrayList<String>();
		ArrayList<String> diff = intersact(cardNoList, lastCardNoList);
		allCardNoList.addAll(subtract(cardNoList, diff));
		allCardNoList.addAll(subtract(lastCardNoList, diff));

		for (String cardNo : diff) {
			LoanDataM loan = cardHm.get(cardNo);
			CardDataM card = loan.getCard();
			BigDecimal requestAmount = Util.empty(loan.getRequestLoanAmt()) ? new BigDecimal(0) : loan.getRequestLoanAmt();
			String applicationType = card.getApplicationType();

			LoanDataM lastLoan = lastCardHm.get(cardNo);
			BigDecimal lastRequestAmount = Util.empty(lastLoan.getRequestLoanAmt()) ? new BigDecimal(0) : lastLoan.getRequestLoanAmt();
			compare(request, audits, requestAmount, lastRequestAmount, applicationType, getCardNo(card.getCardNo()), "IC_CURRENT_CREDIT_LIMIT");
		}

		for (String diffCardNo : allCardNoList) {
			if (cardNoList.contains(diffCardNo)) {
				LoanDataM loan = cardHm.get(diffCardNo);
				CardDataM card = loan.getCard();
				BigDecimal requestAmount = Util.empty(loan.getRequestLoanAmt()) ? new BigDecimal(0) : loan.getRequestLoanAmt();
				String applicationType = card.getApplicationType();
				addAuditTrail(request, audits, requestAmount, new BigDecimal(0), applicationType, getCardNo(card.getCardNo()),
						"IC_CURRENT_CREDIT_LIMIT");
			} else {
				LoanDataM lastLoan = lastCardHm.get(diffCardNo);
				CardDataM lastCard = lastLoan.getCard();
				BigDecimal requestAmount = Util.empty(lastLoan.getRequestLoanAmt()) ? new BigDecimal(0) : lastLoan.getRequestLoanAmt();
				String applicationType = lastCard.getApplicationType();
				addAuditTrail(request, audits, new BigDecimal(0), requestAmount, applicationType, getCardNo(lastCard.getCardNo()),
						"IC_CURRENT_CREDIT_LIMIT");
			}
		}
		return audits;
	}

	private void compare(HttpServletRequest request, ArrayList<AuditDataM> audits, BigDecimal requestAmount, BigDecimal lastRequestAmount,
			String applicationType, String cardNo, String textCode) {
		BigDecimal zero = new BigDecimal(0);
		if (zero.compareTo(requestAmount) != 0 || zero.compareTo(lastRequestAmount) != 0) {
			boolean compareFlag = CompareObject.compare(requestAmount, lastRequestAmount, null);
			if (!compareFlag) {
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(LabelUtil.getText(request, textCode) + " " + cardNo + " (" + getCardTypeDesc(applicationType) + ")");
				if(zero.compareTo(lastRequestAmount) != 0){
					audit.setOldValue(FormatUtil.displayCurrency(lastRequestAmount, true));
				}
				if(zero.compareTo(requestAmount) != 0){
					audit.setNewValue(FormatUtil.displayCurrency(requestAmount, true));
				}
				audits.add(audit);
			}
		}
	}

	private void addAuditTrail(HttpServletRequest request, ArrayList<AuditDataM> audits, BigDecimal requestAmount, BigDecimal lastRequestAmount,
			String applicationType, String cardNo, String textCode) {
		AuditDataM audit = new AuditDataM();
		BigDecimal zero = new BigDecimal(0);
		audit.setFieldName(LabelUtil.getText(request, textCode) + " " + cardNo + " (" + getCardTypeDesc(applicationType) + ")");
		if (zero.compareTo(lastRequestAmount) != 0) {
			audit.setOldValue(FormatUtil.displayCurrency(lastRequestAmount, true));
		}
		if (zero.compareTo(requestAmount) != 0) {
			audit.setNewValue(FormatUtil.displayCurrency(requestAmount, true));
		}
		audits.add(audit);
	}

	private ArrayList<String> intersact(ArrayList<String> obj1, ArrayList<String> obj2) {
		ArrayList<String> result = new ArrayList<String>(obj1.size() > obj2.size() ? obj1.size() : obj2.size());
		result.addAll(obj1);
		result.retainAll(obj2);
		return result;
	}

	private ArrayList<String> subtract(ArrayList<String> obj1, ArrayList<String> obj2) {
		obj1.removeAll(obj2);
		return obj1;
	}

	private String getCardTypeDesc(String applicationType) {
		return ListBoxControl.getName(FIELD_ID_APPLICATION_CARD_TYPE, "CHOICE_NO", applicationType, "DISPLAY_NAME");
	}

	private String getCardNo(String cardNo) {
		Encryptor enc = EncryptorFactory.getDIHEncryptor();
		String CARD_NO = "";
		if (!Util.empty(cardNo)) {
			try {
				CARD_NO = enc.decrypt(cardNo);
			} catch (Exception e) {
			}
		}
		return FormatUtil.getCardNo(CARD_NO);
	}
}
