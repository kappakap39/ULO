package com.eaf.orig.ulo.pl.app.utility;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger; 

import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLBundleCCDataM;
import com.eaf.orig.ulo.pl.model.app.PLCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLCashTransferDataM;
import com.eaf.orig.ulo.pl.model.app.PLKYCDataM;
import com.eaf.orig.ulo.pl.model.app.PLPaymentMethodDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class ObjectEngine {
	static Logger logger = Logger.getLogger(ObjectEngine.class);	
	
	private String system01;
	private String system02;
	private String system03;
	
	public class Model{
		public static final String PLApplicationDataM = "PLApplicationDataM";
		public static final String PLCashTransferDataM = "PLCashTransferDataM";
		public static final String PLPaymentMethodDataM = "PLPaymentMethodDataM";
		public static final String PLPersonalInfoDataM = "PLPersonalInfoDataM";
		public static final String PLAddressDataM = "PLAddressDataM";
		public static final String PLXRulesVerificationResultDataM = "PLXRulesVerificationResultDataM";
		public static final String PLXRulesDebtBdDataM = "PLXRulesDebtBdDataM";
		public static final String PLKYCDataM = "PLKYCDataM";
		public static final String PLBundleCCDataM = "PLBundleCCDataM";
		public static final String PLCardDataM = "PLCardDataM";
	}
	
	public Object FindObject(String ObjModelName , PLApplicationDataM appM){
//		logger.debug("[FindObject] ObjModelName "+ObjModelName);
//		logger.debug("[FindObject] Param1 Value  "+getSystem01());
		
		if(null == getSystem01()){
			setSystem01(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		}		
		
		if(Model.PLApplicationDataM.equals(ObjModelName)){
			if(null == appM) appM = new PLApplicationDataM();
			return appM;
		} else if (Model.PLPersonalInfoDataM.equals(ObjModelName)) {
			if(null == appM) appM = new PLApplicationDataM();
			PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(getSystem01());
			if(null == personM){
				return new PLPersonalInfoDataM();
			}
			return appM.getPLPersonalInfoDataM(getSystem01());
		} else if (Model.PLAddressDataM.equals(ObjModelName)) {
			if(null == appM) appM = new PLApplicationDataM();
			PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(getSystem01());
			if(null == personM){
				return new PLPersonalInfoDataM();
			}
			PLAddressDataM addrM = personM.getAddressDataM(getSystem02());
			if(null == addrM){
				return new PLAddressDataM();
			}
			return addrM;
		} else if (Model.PLCashTransferDataM.equals(ObjModelName)) {
			if(null == appM) appM = new PLApplicationDataM();
			PLCashTransferDataM cashM = appM.getCashTransfer();
			if(null == cashM){
				return new PLCashTransferDataM();
			}
			return cashM;
		} else if (Model.PLPaymentMethodDataM.equals(ObjModelName)) {
			if(null == appM) appM = new PLApplicationDataM();
			PLPaymentMethodDataM paymentM = appM.getPaymentMethod();
			if(null == paymentM){
				return new PLPaymentMethodDataM();
			}
			return paymentM;
		} else if (Model.PLXRulesVerificationResultDataM.equals(ObjModelName)) {
			if(null == appM) appM = new PLApplicationDataM();
			PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(getSystem01());
			PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
			if(null == xrulesVerM){
				return new PLXRulesVerificationResultDataM();
			}
			return xrulesVerM;
		} else if (Model.PLXRulesDebtBdDataM.equals(ObjModelName)) {
			if(null == appM) appM = new PLApplicationDataM();
			PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(getSystem01());
			PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personM.setXrulesVerification(xrulesVerM);
			}
			PLXRulesDebtBdDataM debtM = xrulesVerM.getXRulesDebtBdDataM();
			if(null == debtM){
				return new PLXRulesDebtBdDataM();
			}
			return debtM;
		} else if (Model.PLKYCDataM.equals(ObjModelName)) {
			if(null == appM) appM = new PLApplicationDataM();
			PLKYCDataM kycM = appM.getKycM();
			if(null == kycM){
				return new PLKYCDataM();
			}
			return kycM;
		} else if (Model.PLBundleCCDataM.equals(ObjModelName)) {
			if(null == appM) appM = new PLApplicationDataM();
			PLBundleCCDataM bundleCCM = appM.getBundleCCM();
			if(null == bundleCCM){
				return new PLBundleCCDataM();
			}
			return bundleCCM;
		} else if(Model.PLCardDataM.equals(ObjModelName)){
			if(null == appM) appM = new PLApplicationDataM();
		   	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(getSystem01());
			PLCardDataM cardM = personalM.getCardInformation();
			if(null == cardM){
				return new PLCardDataM();
			}
			return cardM;
		}
		return null;
	}
	public Date getDate(Date date){
		if(null == date) return null;
		Calendar calender = Calendar.getInstance();
			calender.setTime(date);
		calender.set(Calendar.DATE,calender.get(Calendar.DAY_OF_MONTH));
		calender.set(Calendar.MONTH,calender.get(Calendar.MONTH));
		calender.set(Calendar.YEAR,calender.get(Calendar.YEAR));
		calender.set(Calendar.HOUR_OF_DAY,0);
		calender.set(Calendar.MINUTE,0);
		calender.set(Calendar.SECOND, 0);
		calender.set(Calendar.MILLISECOND,0);
		return calender.getTime();
	}
	public boolean CompareValueObj(Object obj1, Object obj2 ,String objType){
		
		if(ClassEngine.STRING.equals(objType)){
			if((OrigUtil.isEmptyString(obj1) && OrigUtil.isEmptyString(obj2))
					||(obj1 != null && obj1.equals(obj2))){
				return true;
			}
			return false;
		} else if (ClassEngine.DATE.equals(objType)) {
			Date date1 = getDate((Date) obj1);			
			Date date2 = getDate((Date) obj2);
			if((date1 == null && date2 == null)
					|| (date1 != null && date2 != null && date1.compareTo(date2) == 0) 
						|| (date2 != null && date1 != null && date2.compareTo(date1) == 0) ){
				return true;
			}
			return false;
		} else if (ClassEngine.TIMESTAMP.equals(objType)) {
			Timestamp date1 = (Timestamp) obj1;
			Timestamp date2 = (Timestamp) obj2;
			if((date1 == null && date2 == null)
					|| (date1 != null && date2 != null && date1.compareTo(date2) == 0) 
						|| (date2 != null && date1 != null && date2.compareTo(date1) == 0) ){
				return true;
			}
			return false;
		} else if (ClassEngine.BIGDECIMAL.equals(objType)) {
			BigDecimal bObj1 = (BigDecimal) obj1;
			BigDecimal bObj2 = (BigDecimal) obj2;
			if((bObj1 == null && bObj2 == null)
					||(bObj1 != null && bObj2 != null && bObj1.compareTo(bObj2) == 0)
						||(bObj1 != null && bObj1.compareTo(BigDecimal.ZERO) == 0 && bObj2 == null)
							||(bObj2 != null && bObj2.compareTo(BigDecimal.ZERO) == 0 && bObj1 == null)){
				return true;
			}
			return false;
		}else if (ClassEngine.BIGDECIMALNULL.equals(objType)) {
			BigDecimal bObj1 = (BigDecimal) obj1;
			BigDecimal bObj2 = (BigDecimal) obj2;
			if((bObj1 == null && bObj2 == null)
					||(bObj1 != null && bObj2 != null && bObj1.compareTo(bObj2) == 0)){
				return true;
			}
			return false;				
		} else if (ClassEngine.INT.equals(objType)) {
			if(obj1 == null && obj2 == null) return true;
			if(obj1 != null && obj2 != null && obj1 == obj2) return true;
			return false;
		}
		return false;
	}
	public String getSystem01() {
		if(null == system01) system01 = PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT;
		return system01;
	}
	public void setSystem01(String system01) {
		if(null == system01) system01 = PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT;
		this.system01 = system01;
	}
	public String getSystem02() {
		return system02;
	}
	public void setSystem02(String system02) {
		this.system02 = system02;
	}
	public String getSystem03() {
		return system03;
	}
	public void setSystem03(String system03) {
		this.system03 = system03;
	}	
}
