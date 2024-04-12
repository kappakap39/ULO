/**
 * Create Date Apr 11, 2012 
 * Create By Sankom
 * Copyright (c) 2012 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15 th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 */
package com.eaf.orig.ulo.pl.app.utility;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGAccountUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLPaymentMethodDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLProjectCodeDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.XrulesResponseDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesExistCustDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;

/**
 * @author Sankom
 * 
 */
public class PLOrigUtility {

	private static PLOrigUtility me;
	private static Logger logger = Logger.getLogger(ORIGUtility.class);

	public synchronized static PLOrigUtility getInstance() {
		if (me == null) {
			me = new PLOrigUtility();
		}
		return me;
	}

	/**
	 * Function Find Diff Month #SeptemWi
	 */
	public static int MonthsBetween(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int startMonth = cal.get(Calendar.MONTH) + 1;
		int startYear = cal.get(Calendar.YEAR);
		cal.setTime(endDate);
		int endMonth = cal.get(Calendar.MONTH) + 1;
		int endYear = cal.get(Calendar.YEAR);
		int m1 = endYear * 12 + endMonth;
		int m2 = startYear * 12 + startMonth;
		return m2 - m1 + 1;
	}

	public static String RoleSupDisplayMode(String role) {
		if (OrigConstant.ROLE_DE_SUP.equals(role) || OrigConstant.ROLE_DC_SUP.equals(role) || OrigConstant.ROLE_DF_SUP.equals(role)
				|| OrigConstant.ROLE_CA_SUP.equals(role) || OrigConstant.ROLE_FU_SUP.equals(role) || OrigConstant.ROLE_VC_SUP.equals(role)) {
			return HTMLRenderUtil.DISPLAY_MODE_EDIT;
		}
		return HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}

	public PLProjectCodeDataM getProjectData(String projectCode) {
		PLProjectCodeDataM projectCodeDataM = new PLProjectCodeDataM();
		try {
			projectCodeDataM = PLORIGEJBService.getORIGDAOUtilLocal().Loadtable(projectCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectCodeDataM;
	}

	/**
	 * #SeptemWi
	 * {@link PLOrigUtility#SetDecisionModelAppM(PLApplicationDataM, ORIGCacheDataM)}
	 * 
	 * @deprecated
	 */
	public static void setDecisionFromRole(PLApplicationDataM plAppdataM, String userRole, String decision_option) {
		if (!ORIGUtility.isEmptyString(userRole) && (userRole.equalsIgnoreCase(OrigConstant.ROLE_DE)
		// ||userRole.equalsIgnoreCase(OrigConstant.ROLE_DE_FULL)
				)) {
			plAppdataM.setDeDecision(decision_option);
		} else if (!ORIGUtility.isEmptyString(userRole) && userRole.equalsIgnoreCase(OrigConstant.ROLE_DE_SUP)) {
			plAppdataM.setDeSupDecision(decision_option);
		} else if (!ORIGUtility.isEmptyString(userRole) && userRole.equalsIgnoreCase(OrigConstant.ROLE_DC)) {
			plAppdataM.setDcDecision(decision_option);
		} else if (!ORIGUtility.isEmptyString(userRole) && userRole.equalsIgnoreCase(OrigConstant.ROLE_DC_SUP)) {
			plAppdataM.setDcSupDecision(decision_option);
		} else if (!ORIGUtility.isEmptyString(userRole) && userRole.equalsIgnoreCase(OrigConstant.ROLE_CA)) {
			plAppdataM.setCaDecision(decision_option);
		} else if (!ORIGUtility.isEmptyString(userRole) && userRole.equalsIgnoreCase(OrigConstant.ROLE_FU)) {
			plAppdataM.setFuDecision(decision_option);
		} else if (!ORIGUtility.isEmptyString(userRole) && userRole.equalsIgnoreCase(OrigConstant.ROLE_VC)) {
			plAppdataM.setVcDecision(decision_option);
		} else if (!ORIGUtility.isEmptyString(userRole) && userRole.equalsIgnoreCase(OrigConstant.ROLE_DF)) {
			plAppdataM.setDF_Decision(decision_option);
		}
	}

	public static String GetDecisionModelAppM(PLApplicationDataM applicationM, ORIGCacheDataM listBoxM) {
		/**#septemwi fix validate null before set decision*/
		if(null == listBoxM || OrigUtil.isEmptyString(listBoxM.getSystemID7())
				|| OrigUtil.isEmptyString(listBoxM.getSystemID8())){
			return "";
		}
		ObjectEngine objEngine = new ObjectEngine();
		Object appObj = objEngine.FindObject(listBoxM.getSystemID7(), applicationM);
		ClassEngine classEngine = new ClassEngine();
		Field fAppObj = classEngine.getField(appObj, listBoxM.getSystemID8());
		Object aObjValue = classEngine.getValue(fAppObj, appObj);
		if (OrigUtil.isEmptyString((String) aObjValue))
			return "";
		return (String) aObjValue;
	}

	public static void SetDecisionModelAppM(PLApplicationDataM applicationM, ORIGCacheDataM listBoxM){
		/**#septemwi fix validate null before set decision*/
		if(null == listBoxM || OrigUtil.isEmptyString(listBoxM.getSystemID7())
				|| OrigUtil.isEmptyString(listBoxM.getSystemID8())){
			return;
		}
		ObjectEngine objEngine = new ObjectEngine();
		Object appObj = objEngine.FindObject(listBoxM.getSystemID7(), applicationM);
		ClassEngine classEngine = new ClassEngine();
		Field fAppObj = classEngine.getField(appObj, listBoxM.getSystemID8());
		String typeObj = classEngine.getType(fAppObj);
		Object ObjInput = classEngine.ConvertValue(typeObj, applicationM.getAppDecision());
		classEngine.setValue(fAppObj, appObj, ObjInput);
	}

	public static String getSaleTypeFormBussClass(String bussClass) {
		String result = null;
		if (bussClass != null) {
			result = bussClass.substring(bussClass.lastIndexOf("_") + 1);
		}
		return result;
	}

	public static Vector<ORIGCacheDataM> filterDecision(PLApplicationDataM applicationM, Vector<ORIGCacheDataM> vDecision, String role) {
		Vector<ORIGCacheDataM> vResult = null;
		String saleType = applicationM.getSaleType();
		String reopenFlag = applicationM.getReopenFlag();
		if (OrigConstant.ROLE_DC.equals(role)
				&& (OrigConstant.ORIG_FLAG_Y.equals(reopenFlag) || (OrigConstant.SaleType.BUNDING_AUTO_LOAN.equals(saleType)
						|| OrigConstant.SaleType.BUNDING_HOME_LOAN.equals(saleType) || OrigConstant.SaleType.BUNDING_CREDIT_CARD.equals(saleType)
							|| OrigConstant.SaleType.BUNDING_CREDIT_CARD_GENERIC.equals(saleType) || OrigConstant.SaleType.BUNDING_SME.equals(saleType)))) {
			vResult = new Vector<ORIGCacheDataM>();
			for (int i = 0; i < vDecision.size(); i++) {
				ORIGCacheDataM origCashData = vDecision.get(i);
				if (!OrigConstant.wfProcessState.SEND_BACK.equals(origCashData.getCode())) {
					vResult.add(origCashData);
				}
			}
			return vResult;
		} else {
			return vDecision;
		}
	}

	public void setDefaultPaymentMethod(PLApplicationDataM applicationM) {
		PLPaymentMethodDataM paymentMethodM = applicationM.getPaymentMethod();
		if (paymentMethodM != null) {
			if (paymentMethodM.getPayMethod() == null || "".equals(paymentMethodM.getPayMethod())) {
				// check if project code ==payroll
				boolean payroll = isPayrollProjectCode(applicationM.getProjectCode());
				if(payroll){
					paymentMethodM.setPayMethod("02");
					paymentMethodM.setPayRatio(new BigDecimal(5));
				}
			}
		}
	}

	public boolean isPayrollProjectCode(String projectCode) {
		boolean bResult = false;
		PLProjectCodeDataM projectcodeData = PLORIGEJBService.getORIGDAOUtilLocal().Loadtable(projectCode);
		if (projectcodeData != null) {
			if (OrigConstant.PROJECT_CODE_PAYROLL.equals(projectcodeData.getApplicationProperty())) {
				bResult = true;
			}
		}
		return bResult;
	}
	
	/*
	 * Praisan Khunkaew 20121118
	 * For call ILog by service
	 */
	public XrulesResponseDataM executeILog(PLApplicationDataM appM, int serviceId, UserDetailM userM, ORIGXRulesTool xrulesTool) throws Exception{
		ExecuteServiceManager xRulesService = null;
		if(xrulesTool == null){
			xrulesTool = new ORIGXRulesTool();
		}
		try {
			xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM xrulesRequest = xrulesTool.MapXrulesRequestDataM(appM, serviceId, userM);
			return xRulesService.ILOGServiceModule(xrulesRequest);
		} catch (NamingException e1) {
			logger.fatal("error:", e1);
			throw new Exception(e1.getMessage());
		}
	}
	
	public String getCardNoForSummary(PLApplicationDataM applicationM){
	  	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	  	
	 	String cardNo = null;
	 	if (OrigBusinessClassUtil.getInstance().isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW)) { //ICDC
	 		PLCardDataM cardM = personalM.getCardInformation();
	 		if (cardM != null) {
	 			cardNo = cardM.getCardNo();
	 		}
	 	} else if(OrigConstant.FLAG_Y.equals(applicationM.getICDCFlag())){ //Bundle ICDC
	 		PLXRulesVerificationResultDataM verM = personalM.getXrulesVerification();
	 		if(verM != null){
	 			PLXRulesExistCustDataM existCusM = verM.getxRulesExistCustM();
	 			if(existCusM != null){
	 				cardNo = existCusM.getCardNo();
	 			}
	 		}
	 	}else{// Normal and Bundle normal
	 		ORIGAccountUtil origacc = ORIGAccountUtil.getInstance();
	 		PLAccountCardDataM accountCard = origacc.loadAccountCardByAppNo(applicationM.getApplicationNo());
	 		if (accountCard == null) {
	 			accountCard = new PLAccountCardDataM();
	 		}
	 		cardNo = accountCard.getCardNo();
	 	}
	 	return cardNo;
	}
}
