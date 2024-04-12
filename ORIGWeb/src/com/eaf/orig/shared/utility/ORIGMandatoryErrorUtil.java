package com.eaf.orig.shared.utility;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.MandatoryFieldCache;
import com.eaf.cache.TableLookupCache;
import com.eaf.cache.data.MandatoryCacheDataM;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.ChangeNameDataM;
import com.eaf.orig.shared.model.InstallmentDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.wf.shared.ORIGWFConstant;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Administrator
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class ORIGMandatoryErrorUtil {

	private static ORIGMandatoryErrorUtil me;

	public static Logger logger = Logger.getLogger(ORIGMandatoryErrorUtil.class);

	public synchronized static ORIGMandatoryErrorUtil getInstance() {
		if (me == null) {
			me = new ORIGMandatoryErrorUtil();
		}
		return me;
	}

	public boolean getErrorMessage(String subformName, HttpServletRequest request, String customerType) {
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		String menu = (String) request.getSession(true).getAttribute("PROPOSAL_MENU");
		if (form == null)
			form = new ORIGFormHandler();
		ApplicationDataM appM = form.getAppForm();
		if (appM == null)
			appM = new ApplicationDataM();

		String fields = "";
		String value = null;
		boolean resultFlag = false;
		// HashMap error = form.getErrors();
		// if(error==null) {
		// error = new HashMap();
		// }
		Vector vErrors = form.getFormErrors();
		Vector vErrorFields = form.getErrorFields();
		Vector vNotErrorFields = form.getNotErrorFields();
		if (vErrors == null) {
			vErrors = new Vector();
		}
		String errorMsg = null;
		Vector manFields;
		if (!"Y".equals(menu)) {
			manFields = MandatoryFieldCache.getMandatoryFieldsInOneForm(customerType, userM.getRoles(), subformName, "Y");
		} else {
			Vector vRole = new Vector();
			vRole.add(OrigConstant.ROLE_PROPASAL);
			manFields = MandatoryFieldCache.getMandatoryFieldsInOneForm(customerType, vRole, subformName, "Y");
		}
		// logger.debug(">>> manFields : "+manFields);
		for (int i = 0; i < manFields.size(); i++) {
			fields = (String) manFields.elementAt(i);
			value = request.getParameter(fields);
			if (value == null || value.equals("")) {
				errorMsg = getDisplayName(fields, subformName, request);
				// error.put(fields,errorMsg);
				vErrors.add(errorMsg);
				vErrorFields.add(fields);
				resultFlag = true;
			} else {
				vNotErrorFields.add(fields);
			}
		}
		return resultFlag;
	}

	private String getDisplayName(String fields, String subformName, HttpServletRequest request) {
		HashMap hash = TableLookupCache.getCacheStructure();
		Vector vMandatory = (Vector) (hash.get("MandatoryField"));
		MandatoryCacheDataM cacheM = new MandatoryCacheDataM();
		for (int i = 0; i < vMandatory.size(); i++) {
			cacheM = (MandatoryCacheDataM) vMandatory.elementAt(i);
			if ((cacheM.getFieldName()).equals(fields) && cacheM.getSubFormName().equals(subformName)) {
				return MessageResourceUtil.getTextDescription(request, cacheM.getErrorMessage()) + " is required.";
			}
		}
		return "";
	}

	/**
     */
	public boolean getMandateErrorDE(HttpServletRequest request, String customerType) {
		logger.debug("getMandateErrorDE");
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ApplicationDataM appForm = formHandler.getAppForm();
		if (appForm == null) {
			appForm = new ApplicationDataM();
		}
		ORIGUtility utility = new ORIGUtility();
		Vector vErrors = formHandler.getFormErrors();
		Vector vErrorFields = formHandler.getErrorFields();
		Vector vNotErrorFields = formHandler.getNotErrorFields();
		if (vErrors == null) {
			vErrors = new Vector();
		}
		String errorMsg = null;
		String personalType = request.getParameter("appPersonalType");// (String)
		// request.getSession().getAttribute("PersonalType");
		logger.debug("personalType = " + personalType);
		if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
			if (this.getErrorMessage("APPLICANT_SUBFORM", request, customerType)) {
				result = true;
			}
			if (!ORIGUtility.isEmptyString(request.getParameter("m_birth_date"))) {
				int age = utility.stringToInt(request.getParameter("m_age"));
				String nullDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(appForm.getNullDate()));
				if ((age <= 0 || age > 150) && !(nullDate.equals(request.getParameter("m_birth_date")))) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
					vErrors.add(errorMsg + "[Marriage]");
					vErrorFields.add("m_birth_date");
					result = true;
				} else {
					vNotErrorFields.add("m_birth_date");
				}

			}
			if (this.getErrorMessage("CAR_INFO_SUBFORM", request, customerType)) {
				result = true;
			}
			if (OrigConstant.CAR_TYPE_USE.equals(request.getParameter("car"))) {
				if (ORIGUtility.isEmptyString(request.getParameter("car_engine_no"))) {
					errorMsg = "Engine No. is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_engine_no");
					result = true;
				} else {
					vNotErrorFields.add("car_engine_no");
				}
				if (ORIGUtility.isEmptyString(request.getParameter("car_register_no"))) {
					errorMsg = "Register No. is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_register_no");
					result = true;
				} else {
					vNotErrorFields.add("car_register_no");
				}
				if (ORIGUtility.isEmptyString(request.getParameter("car_classis_no"))) {
					errorMsg = "Chassis No. is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_classis_no");
					result = true;
				} else {
					vNotErrorFields.add("car_classis_no");
				}
				if (ORIGUtility.isEmptyString(request.getParameter("car_weight"))) {
					errorMsg = "Car Weight is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_weight");
					result = true;
				} else {
					vNotErrorFields.add("car_weight");
				}
				if (ORIGUtility.isZero(request.getParameter("car_year"))) {
					errorMsg = "Car Year is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_year");
					result = true;
				} else {
					vNotErrorFields.add("car_year");
				}
				if (ORIGUtility.isZero(request.getParameter("car_kilometers"))) {
					errorMsg = "Car Kilometers is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_kilometers");
					result = true;
				} else {
					vNotErrorFields.add("car_kilometers");
				}
				// Occupation
				if (ORIGUtility.isEmptyString(request.getParameter("car_register_date"))) {
					errorMsg = "Register Date is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_register_date");
					result = true;
				} else {
					vNotErrorFields.add("car_register_date");
				}
				if (ORIGUtility.isEmptyString(request.getParameter("car_expiry_date"))) {
					errorMsg = "Expiry Date is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_expiry_date");
					result = true;
				} else {
					vNotErrorFields.add("car_expiry_date");
				}
				if (ORIGUtility.isEmptyString(request.getParameter("car_occupy_date"))) {
					errorMsg = "Occupy Date is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_occupy_date");
					result = true;
				} else {
					vNotErrorFields.add("car_occupy_date");
				}
			} else {
				vNotErrorFields.add("car_engine_no");
				vNotErrorFields.add("car_register_no");
				vNotErrorFields.add("car_classis_no");
				vNotErrorFields.add("car_weight");
				vNotErrorFields.add("car_year");
				vNotErrorFields.add("car_kilometers");
				vNotErrorFields.add("car_register_date");
				vNotErrorFields.add("car_expiry_date");
				vNotErrorFields.add("car_occupy_date");
			}
			if (this.getErrorMessage("INSURENCE_SUBFORM", request, customerType)) {
				result = true;
			}
			if (this.getErrorMessage("DRALER_SUBFORM", request, customerType)) {
				result = true;
			}
			// Check Loan
			if (appForm.getLoanVect() == null || appForm.getLoanVect().size() == 0) {
				errorMsg = "Loan is required.";
				vErrors.add(errorMsg);
				result = true;
			} else {
				LoanDataM loanM = (LoanDataM) appForm.getLoanVect().elementAt(0);
				if (loanM.getCostOfCarPrice().compareTo(new BigDecimal(0)) == 0) {
					errorMsg = "Car Price of Loan is required.";
					vErrors.add(errorMsg);
					result = true;
				}
				if (!OrigConstant.LOAN_TYPE_LEASING.equalsIgnoreCase(loanM.getLoanType())) {
					if (OrigConstant.CAR_TYPE_USE.equals(request.getParameter("car"))) {
						if (new BigDecimal(0).compareTo(loanM.getAppraisalPrice()) == 0) {
							errorMsg = "Appraisal Price of Loan is required.";
							vErrors.add(errorMsg);
							result = true;
						}
					}
				}

			}

			// Check Reason
			// Vector deReasonVect =
			// utility.getReasonByRole(appForm.getReasonVect(),
			// OrigConstant.ROLE_DE);
			String reasons[] = request.getParameterValues("reason_de");
			// logger.debug("reasons = " + reasons);
			String decision = request.getParameter("decision_de");
			/*
			 * if(decision != null){ decision = decision.replaceAll("%20", " ");
			 * }
			 */
			logger.debug("decision = " + decision);
			if (!ORIGUtility.isEmptyString(decision)) {
				if (reasons == null) {
					errorMsg = "Please select at least 1 reason and maximum 3 reasons.";
					vErrors.add(errorMsg);
					result = true;
				} else {
					if (reasons.length > 3) {
						errorMsg = "Please select at least 1 reason and maximum 3 reasons.";
						vErrors.add(errorMsg);
						result = true;
					}
				}
			}

			if (ORIGUtility.isZero(request.getParameter("area_code"))) {
				errorMsg = "Area Code is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("area_code");
				result = true;
			} else {
				vNotErrorFields.add("area_code");
			}

		} else {
			if (ORIGUtility.isEmptyString(request.getParameter("identification_no"))) {
				errorMsg = "Identification No. is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("identification_no");
				result = true;
			} else {
				vNotErrorFields.add("identification_no");
			}
		}
		if (this.getErrorMessage("CUSTOMER_INFO_SUBFORM", request, customerType)) {
			result = true;
		}
		if (!ORIGUtility.isEmptyString(request.getParameter("birth_date"))) {
			int age = utility.stringToInt(request.getParameter("age"));
			if (!OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(customerType)) {
				if (age <= 0 || age > 150) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
					vErrors.add(errorMsg);
					vErrorFields.add("birth_date");
					result = true;
				} else {
					vNotErrorFields.add("birth_date");
				}
			}
			// ===============================
			int workingYear = utility.stringToInt(request.getParameter("year"));
			int workingMonth = utility.stringToInt(request.getParameter("month"));
			if (workingMonth > 0) {
				workingYear = workingYear + 1;
			}
			if (workingYear > age) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "WORKING_YEAR_AGE");
				vErrors.add(errorMsg);
			}
			// ================================
		}
		logger.debug("request.getAttribute(k_consent_flag) = " + request.getParameter("k_consent_flag"));
		if (("Y").equals(request.getParameter("k_consent_flag"))) {
			if (ORIGUtility.isEmptyString((String) request.getParameter("k_consent_date"))) {
				errorMsg = "Application consent date is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("k_consent_date");
				result = true;
			} else {
				vNotErrorFields.add("k_consent_date");
			}
		}

		if (this.getErrorMessage("MAILING_SUBFORM", request, customerType)) {
			result = true;
		}

		if (this.getErrorMessage("OCCUPATION_SUBFORM", request, customerType)) {
			result = true;
		}
		// Check Address
		PersonalInfoDataM personalInfoDataM;
		if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
			personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
		} else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
			personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
			personalInfoDataM = utility.getPersonalInfoByType(appForm, OrigConstant.PERSONAL_TYPE_APPLICANT);
		}
		int addressSize = utility.getAddressSizeByPersonalType(personalInfoDataM.getAddressVect(), personalInfoDataM.getCustomerType(), personalType);
		if (personalInfoDataM.getAddressVect() == null) {
			errorMsg = "Address is required.";
			vErrors.add(errorMsg);
			result = true;
		} else {
			// if (personalInfoDataM.getAddressVect().size() != addressSize) {
			if (personalInfoDataM.getAddressVect().size() < addressSize) {
				errorMsg = "All Address types is required.";
				vErrors.add(errorMsg);
				result = true;
			}
		}
		if (!("Y").equals(appForm.getDrawDownFlag())) {
			if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType())) {
				if (("Y").equals(request.getParameter("consent_flag"))) {
					if (ORIGUtility.isEmptyString((String) request.getParameter("consent_date"))) {
						errorMsg = "NCB consent date is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("consent_date");
						result = true;
					} else {
						vNotErrorFields.add("consent_date");
					}
				}
				XRulesVerificationResultDataM rulesVerificationResultDataM = personalInfoDataM.getXrulesVerification();
				if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType())
						&& ORIGUtility.isEmptyString(rulesVerificationResultDataM.getNCBResult())) {
					errorMsg = "NCB is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("resNCB");
					result = true;
				} else {
					// vNotErrorFields.add("resNCB");
				}
				if (this.getErrorMessage("VERIFICATION_LIST_SUBFORM", request, customerType)) {
					result = true;
				}
			} else {
				boolean result2 = getMandateErrorGuarantorVerDE(request, customerType);
				if (result2) {
					result = true;
				}
			}
		}
		// =====================================
		String paramField = "birth_date";
		boolean addressValid = true;
		String strBirthDate = request.getParameter(paramField);
		Date birthDate = ORIGUtility.parseThaiToEngDate(strBirthDate);
		if (birthDate != null) {
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			Calendar clNow = Calendar.getInstance();
			Calendar clBd = Calendar.getInstance();
			clBd.setTime(birthDate);
			int age = clNow.get(Calendar.YEAR) - clBd.get(Calendar.YEAR);
			if (clNow.get(Calendar.MONTH) < clBd.get(Calendar.MONTH)
					|| (clNow.get(Calendar.MONTH) == clBd.get(Calendar.MONTH) && clNow.get(Calendar.DAY_OF_MONTH) < clBd.get(Calendar.DAY_OF_MONTH))) {
				age = age - 1;
			}
			Vector vAddress = personalInfoDataM.getAddressVect();
			if (vAddress != null) {
				for (int i = 0; i < vAddress.size(); i++) {
					AddressDataM addressDataM = (AddressDataM) vAddress.get(i);
					BigDecimal resideYear = addressDataM.getResideYear();
					if (resideYear != null) {
						int year = resideYear.intValue();
						int month = resideYear.movePointLeft(2).intValue() % 100;
						if (month > 0) {
							year++;
						}
						if (year != 0 && year > age) {
							addressValid = false;
							vErrors.add("Age must not less than reside year/month in Address Type "
									+ cacheUtil.getORIGMasterDisplayNameDataM("AddressType", addressDataM.getAddressType()));
						}
					}
				}
			}

		}
		// =================================
		return result;
	}

	public boolean getMandateErrorCMR(HttpServletRequest request, String customerType) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ApplicationDataM appForm = formHandler.getAppForm();
		ORIGUtility utility = new ORIGUtility();
		if (appForm == null) {
			appForm = new ApplicationDataM();
		}
		PersonalInfoDataM personalInfoDataM;
		String personalType = request.getParameter("appPersonalType");// (String)
		// request.getSession().getAttribute("PersonalType");
		if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
			personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
		} else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
			personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
			personalInfoDataM = utility.getPersonalInfoByType(appForm, OrigConstant.PERSONAL_TYPE_APPLICANT);
		}
		Vector vErrors = formHandler.getFormErrors();
		Vector vErrorFields = formHandler.getErrorFields();
		Vector vNotErrorFields = formHandler.getNotErrorFields();
		if (vErrors == null) {
			vErrors = new Vector();
			formHandler.setFormErrors(vErrors);
		}
		String errorMsg = null;

		int age = 0;
		if (!ORIGUtility.isEmptyString(request.getParameter("birth_date"))) {
			age = utility.stringToInt(request.getParameter("age"));
			if (!OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(customerType)) {
				if (age <= 0 || age > 150) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
					vErrors.add(errorMsg);
					vErrorFields.add("birth_date");
					result = true;
				} else {
					vNotErrorFields.add("birth_date");
				}
			}

		}
		if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType())) {
			if (this.getErrorMessage("APPLICANT_SUBFORM", request, customerType)) {
				result = true;
			}
			if (this.getErrorMessage("CUSTOMER_INFO_SUBFORM", request, customerType)) {
				result = true;
			}
			// ===============================
			if (!OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(personalInfoDataM.getCustomerType())) {
				int workingYear = utility.stringToInt(request.getParameter("pre_score_year"));
				int workingMonth = utility.stringToInt(request.getParameter("pre_score_month"));
				if (workingMonth > 0) {
					workingYear = workingYear + 1;
				}
				if (workingYear > age) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "WORKING_YEAR_AGE");
					vErrors.add(errorMsg);
				}
				String strAddressMonth = request.getParameter("pre_score_time_current_address_month");
				String strAddressYear = request.getParameter("pre_score_time_current_address_year");
				// String strAge=request.getParameter("age");
				if ((strAddressMonth != null && !"".equals(strAddressMonth)) && (strAddressYear != null && !"".equals(strAddressYear))) {
					int timeInCurrentAddressYear = utility.stringToInt(request.getParameter("pre_score_time_current_address_year"));
					int timeInCurrentAddressMonth = utility.stringToInt(request.getParameter("pre_score_time_current_address_month"));
					if (timeInCurrentAddressMonth > 0) {
						timeInCurrentAddressYear = timeInCurrentAddressYear + 1;
					}
					if (timeInCurrentAddressYear > age) {
						errorMsg = ErrorUtil.getShortErrorMessage(request, "TIME_IN_CURRENT_ADDRESS_AGE");
						vErrors.add(errorMsg);
						result = true;
					}
				}
			}
			// ================================

			if (("Y").equals(request.getParameter("k_consent_flag"))) {
				if (ORIGUtility.isEmptyString((String) request.getParameter("k_consent_date"))) {
					errorMsg = "Application consent date is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("k_consent_date");
					result = true;
				} else {
					vNotErrorFields.add("k_consent_date");
				}
			}

			if (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equals(appForm.getJobState()) && !("Y").equals(appForm.getDrawDownFlag())) {
				if (ORIGUtility.isEmptyString(request.getParameter("pre_score_mkt_code"))) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "pre_score_mkt_code");
					vErrors.add(errorMsg);
					vErrorFields.add("pre_score_mkt_code");
					result = true;
				} else {
					vNotErrorFields.add("pre_score_mkt_code");
				}
			}

			if (this.getErrorMessage("DRALER_SUBFORM", request, customerType)) {
				result = true;
			}

			if (ORIGUtility.isZero(request.getParameter("area_code"))) {
				errorMsg = "Area Code is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("area_code");
				result = true;
			} else {
				vNotErrorFields.add("area_code");
			}
			if (this.getErrorMessage("CAR_INFO_SUBFORM", request, customerType)) {
				result = true;
			}
			if (OrigConstant.CAR_TYPE_USE.equals(request.getParameter("car"))) {
				if (ORIGUtility.isEmptyString(request.getParameter("car_engine_no"))) {
					errorMsg = "Engine No. is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_engine_no");
					result = true;
				} else {
					vNotErrorFields.add("car_engine_no");
				}
				if (ORIGUtility.isEmptyString(request.getParameter("car_register_no"))) {
					errorMsg = "Register No. is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_register_no");
					result = true;
				} else {
					vNotErrorFields.add("car_register_no");
				}
				if (ORIGUtility.isEmptyString(request.getParameter("car_classis_no"))) {
					errorMsg = "Chassis No. is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_classis_no");
					result = true;
				} else {
					vNotErrorFields.add("car_classis_no");
				}
				if (ORIGUtility.isEmptyString(request.getParameter("car_weight"))) {
					errorMsg = "Car Weight is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_weight");
					result = true;
				} else {
					vNotErrorFields.add("car_weight");
				}
				if (ORIGUtility.isZero(request.getParameter("car_year"))) {
					errorMsg = "Car Year is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_year");
					result = true;
				} else {
					vNotErrorFields.add("car_year");
				}

				// if
				// (ORIGUtility.isZero(request.getParameter("car_kilometers")))
				// {
				// errorMsg = "Car Kilometers is required.";
				// vErrors.add(errorMsg);
				// vErrorFields.add("car_kilometers");
				// result = true;
				// } else {
				// vNotErrorFields.add("car_kilometers");
				// }
				// if(ORIGUtility.isEmptyString(request.getParameter("car_register_date"))){
				// errorMsg = "Register Date is required.";
				// vErrors.add(errorMsg);
				// vErrorFields.add("car_register_date");
				// result = true;
				// }else{
				// vNotErrorFields.add("car_register_date");
				// }
				// if(ORIGUtility.isEmptyString(request.getParameter("car_expiry_date"))){
				// errorMsg = "Expiry Date is required.";
				// vErrors.add(errorMsg);
				// vErrorFields.add("car_expiry_date");
				// result = true;
				// }else{
				// vNotErrorFields.add("car_expiry_date");
				// }
				// if(ORIGUtility.isEmptyString(request.getParameter("car_occupy_date"))){
				// errorMsg = "Occupy Date is required.";
				// vErrors.add(errorMsg);
				// vErrorFields.add("car_occupy_date");
				// result = true;
				// }else{
				// vNotErrorFields.add("car_occupy_date");
				// }
			} else {
				vNotErrorFields.add("car_engine_no");
				vNotErrorFields.add("car_register_no");
				vNotErrorFields.add("car_classis_no");
				vNotErrorFields.add("car_weight");
				vNotErrorFields.add("car_year");
				// vNotErrorFields.add("car_kilometers");
			}

		} else {
			if (ORIGUtility.isEmptyString(request.getParameter("identification_no"))) {
				errorMsg = "Identification No. is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("identification_no");
				result = true;
			} else {
				vNotErrorFields.add("identification_no");
			}
			if (!("Y").equals(appForm.getDrawDownFlag())) {
				if (this.getErrorMessage("VERIFICATION_LIST_SUBFORM", request, customerType)) {
					result = true;
				}
			}
		}
		return result;
	}

	public boolean getMandateErrorSaveNewApp(HttpServletRequest request, String customerType) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ApplicationDataM appForm = formHandler.getAppForm();
		if (appForm == null) {
			appForm = new ApplicationDataM();
		}

		Vector vErrors = formHandler.getFormErrors();
		Vector vErrorFields = formHandler.getErrorFields();
		Vector vNotErrorFields = formHandler.getNotErrorFields();
		if (vErrors == null) {
			vErrors = new Vector();
			formHandler.setFormErrors(vErrors);
		}
		String errorMsg = null;

		if (ORIGUtility.isEmptyString(request.getParameter("identification_no"))) {
			errorMsg = getDisplayName("identification_no", "APPLICANT_SUBFORM", request);
			vErrors.add(errorMsg);
			vErrorFields.add("identification_no");
			result = true;
		} else {
			vNotErrorFields.add("identification_no");
		}
		if (ORIGUtility.isEmptyString(request.getParameter("office_code"))) {
			errorMsg = getDisplayName("office_code", "APPLICANT_SUBFORM", request);
			vErrors.add(errorMsg);
			vErrorFields.add("office_code");
			result = true;
		} else {
			vNotErrorFields.add("office_code");
		}
		if (ORIGUtility.isEmptyString(request.getParameter("name_thai"))) {
			errorMsg = getDisplayName("name_thai", "CUSTOMER_INFO_SUBFORM", request);
			vErrors.add(errorMsg);
			vErrorFields.add("name_thai");
			result = true;
		} else {
			vNotErrorFields.add("name_thai");
		}
		ORIGUtility utility = new ORIGUtility();
		if (!ORIGUtility.isEmptyString(request.getParameter("birth_date"))) {
			int age = utility.stringToInt(request.getParameter("age"));
			if (!OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(customerType)) {
				if (age <= 0 || age > 150) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
					vErrors.add(errorMsg);
					vErrorFields.add("birth_date");
					result = true;
				} else {
					vNotErrorFields.add("birth_date");
				}
			}
		}
		if (ORIGUtility.isEmptyString(request.getParameter("area_code"))) {
			errorMsg = getDisplayName("area_code", "CUSTOMER_INFO_SUBFORM", request);
			vErrors.add(errorMsg);
			vErrorFields.add("area_code");
			result = true;
		} else {
			vNotErrorFields.add("area_code");
		}
		if (!ORIGUtility.isEmptyString(request.getParameter("m_birth_date"))) {
			int age = utility.stringToInt(request.getParameter("m_age"));
			String nullDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(appForm.getNullDate()));
			if ((age <= 0 || age > 150) && !(nullDate.equals(request.getParameter("m_birth_date")))) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
				vErrors.add(errorMsg + "[Marriage]");
				vErrorFields.add("m_birth_date");
				result = true;
			} else {
				vNotErrorFields.add("m_birth_date");
			}
		}
		if (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equals(appForm.getJobState())) {
			if (ORIGUtility.isEmptyString(request.getParameter("pre_score_mkt_code"))) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "pre_score_mkt_code");
				vErrors.add(errorMsg);
				vErrorFields.add("pre_score_mkt_code");
				result = true;
			} else {
				vNotErrorFields.add("pre_score_mkt_code");
			}
		}
		String menu = (String) request.getSession(true).getAttribute("PROPOSAL_MENU");
		if ("Y".equals(menu)) {
			if (ORIGUtility.isZero(request.getParameter("office_code"))) {
				errorMsg = "Office Code is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("office_code");
				result = true;
			} else {
				vNotErrorFields.add("office_code");
			}
		}
		return result;
	}

	public boolean getMandateErrorUW(HttpServletRequest request, String customerType) {
		logger.debug("getMandateErrorUW");
		boolean result = false;
		String menu = (String) request.getSession(true).getAttribute("PROPOSAL_MENU");
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ApplicationDataM appForm = formHandler.getAppForm();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		if (appForm == null) {
			appForm = new ApplicationDataM();
		}
		Vector vErrors = formHandler.getFormErrors();
		Vector vErrorFields = formHandler.getErrorFields();
		Vector vNotErrorFields = formHandler.getNotErrorFields();
		ORIGUtility utility = new ORIGUtility();
		VehicleDataM vehicleDataM = appForm.getVehicleDataM();
		boolean isMandateCar = true;
		if (vehicleDataM == null) {
			vehicleDataM = new VehicleDataM();
		}
		if (!"NEW".equals(vehicleDataM.getDrawDownStatus()) && !ORIGUtility.isEmptyString(vehicleDataM.getDrawDownStatus())) {
			isMandateCar = false;
		}

		if (vErrors == null) {
			vErrors = new Vector();
			formHandler.setFormErrors(vErrors);
		}

		String errorMsg = null;

		String personalType = request.getParameter("appPersonalType");// (String)
		// request.getSession().getAttribute("PersonalType");
		if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
			if (this.getErrorMessage("APPLICANT_SUBFORM", request, customerType)) {
				result = true;
			}
			// if (OrigConstant.MARITAL_STATUS_MARRY.equals(request
			// .getParameter("marital_status"))
			// || OrigConstant.MARITAL_STATUS_MARRY_NO_SIGN.equals(request
			// .getParameter("marital_status"))) {
			// if (this.getErrorMessage("MARRY_INFO_SUBFORM", request,
			// customerType)) {
			// result = true;
			// }
			// }
			if (!ORIGUtility.isEmptyString(request.getParameter("m_birth_date"))) {
				int age = utility.stringToInt(request.getParameter("m_age"));
				String nullDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(appForm.getNullDate()));
				if ((age <= 0 || age > 150) && !(nullDate.equals(request.getParameter("m_birth_date")))) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
					vErrors.add(errorMsg + "[Marriage]");
					vErrorFields.add("m_birth_date");
					result = true;
				} else {
					vNotErrorFields.add("m_birth_date");
				}
			}
			if (!"Y".equals(menu) && isMandateCar) {
				if (this.getErrorMessage("CAR_INFO_SUBFORM", request, customerType)) {
					result = true;
				}
				if (OrigConstant.CAR_TYPE_USE.equals(request.getParameter("car"))) {
					if (ORIGUtility.isEmptyString(request.getParameter("car_engine_no"))) {
						errorMsg = "Engine No. is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("car_engine_no");
						result = true;
					} else {
						vNotErrorFields.add("car_engine_no");
					}
					if (ORIGUtility.isEmptyString(request.getParameter("car_register_no"))) {
						errorMsg = "Register No. is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("car_register_no");
						result = true;
					} else {
						vNotErrorFields.add("car_register_no");
					}
					if (ORIGUtility.isEmptyString(request.getParameter("car_classis_no"))) {
						errorMsg = "Chassis No. is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("car_classis_no");
						result = true;
					} else {
						vNotErrorFields.add("car_classis_no");
					}
					if (ORIGUtility.isEmptyString(request.getParameter("car_weight"))) {
						errorMsg = "Car Weight is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("car_weight");
						result = true;
					} else {
						vNotErrorFields.add("car_weight");
					}
					if (ORIGUtility.isZero(request.getParameter("car_year"))) {
						errorMsg = "Car Year is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("car_year");
						result = true;
					} else {
						vNotErrorFields.add("car_year");
					}
					if (ORIGUtility.isZero(request.getParameter("car_kilometers"))) {
						errorMsg = "Car Kilometers is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("car_kilometers");
						result = true;
					} else {
						vNotErrorFields.add("car_kilometers");
					}
					if (ORIGUtility.isEmptyString(request.getParameter("car_register_date"))) {
						errorMsg = "Register Date is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("car_register_date");
						result = true;
					} else {
						vNotErrorFields.add("car_register_date");
					}
					if (ORIGUtility.isEmptyString(request.getParameter("car_expiry_date"))) {
						errorMsg = "Expiry Date is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("car_expiry_date");
						result = true;
					} else {
						vNotErrorFields.add("car_expiry_date");
					}
					if (ORIGUtility.isEmptyString(request.getParameter("car_occupy_date"))) {
						errorMsg = "Occupy Date is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("car_occupy_date");
						result = true;
					} else {
						vNotErrorFields.add("car_occupy_date");
					}
				} else {
					vNotErrorFields.add("car_engine_no");
					vNotErrorFields.add("car_register_no");
					vNotErrorFields.add("car_classis_no");
					vNotErrorFields.add("car_weight");
					vNotErrorFields.add("car_year");
					vNotErrorFields.add("car_kilometers");
					vNotErrorFields.add("car_register_date");
					vNotErrorFields.add("car_expiry_date");
					vNotErrorFields.add("car_occupy_date");
				}
				if (this.getErrorMessage("INSURENCE_SUBFORM", request, customerType)) {
					result = true;
				}
			}

			if (this.getErrorMessage("DRALER_SUBFORM", request, customerType)) {
				result = true;
			}

			// Check Loan
			if (!"Y".equals(menu)) {
				if (appForm.getLoanVect() == null || appForm.getLoanVect().size() == 0) {
					errorMsg = "Loan is required.";
					vErrors.add(errorMsg);
					result = true;
				}
			} else {
				if (ORIGUtility.isZero(request.getParameter("office_code"))) {
					errorMsg = "Office Code is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("office_code");
					result = true;
				} else {
					vNotErrorFields.add("office_code");
				}

			}

			// Check Reason
			String reasons[] = request.getParameterValues("reason_uw");
			logger.debug("reasons = " + reasons);
			String decision = request.getParameter("decision_uw");
			/*
			 * if(decision != null){ decision = decision.replaceAll("%20", " ");
			 * }
			 */
			logger.debug("decision=" + decision);
			if (ORIGUtility.isEmptyString(decision)) {
				errorMsg = "Decision is required.";
				vErrors.add(errorMsg);
				result = true;
			} else {
				try {
					decision = URLDecoder.decode(decision, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.debug("Url decode Exception", e);
				}
				logger.debug("decision after decoding=" + decision);
				if (ORIGWFConstant.ApplicationDecision.ESCALATE.equals(decision)) {
					String escalateGroup = request.getParameter("escalateGroup");
					logger.debug("escalateGroup =" + escalateGroup);
					if (ORIGUtility.isEmptyString(escalateGroup)) {
						errorMsg = "Please select Group for Escalate.";
						vErrors.add(errorMsg);
						result = true;
					}
				}
				// Check authorize credit approval
				if (ORIGWFConstant.ApplicationDecision.APPROVE.equals(decision) || ORIGWFConstant.ApplicationDecision.REJECT.equals(decision)
						|| ORIGWFConstant.ApplicationDecision.APPROVE_PROPOSAL.equalsIgnoreCase(decision.trim())
						|| ORIGWFConstant.ApplicationDecision.REJECT_PROPOSAL.equalsIgnoreCase(decision.trim())) {
					String creditArprv = utility.getCreditApproval(userM.getUserName());
					logger.debug("creditArprv=" + creditArprv);
					if (creditArprv == null || "".equals(creditArprv)) {
						errorMsg = "User unauthorized Credit Approval.";
						vErrors.add(errorMsg);
						result = true;
					}
				}

				Vector uwReasonVect = utility.getReasonByRole(appForm.getReasonVect(), OrigConstant.ROLE_UW);
				// Decision XUW,XCMR not check reason
				if (!OrigConstant.ApplicationDecision.SENDTO_POLICY_EXCEPTION.equals(decision)
						&& !ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION.equals(decision)) {
					if (reasons == null) {
						errorMsg = "Please select at least 1 reason and maximum 3 reasons.";
						vErrors.add(errorMsg);
						result = true;
					} else {
						if (reasons.length > 3) {
							errorMsg = "Please select at least 1 reason and maximum 3 reasons.";
							vErrors.add(errorMsg);
							result = true;
						}
					}
				}

			}
			// Check Submit Proposal
			String final_credit_limit = request.getParameter("final_credit_limit");
			String proposal_expiry_date = request.getParameter("proposal_expiry_date");
			String client_group = request.getParameter("client_group");
			if ("Y".equals(menu)) {
				if (ORIGUtility.isEmptyString(final_credit_limit)) {
					errorMsg = "Final Credit Limit is required.";
					vErrors.add(errorMsg);
					result = true;
				}
				if (ORIGUtility.isEmptyString(proposal_expiry_date)) {
					errorMsg = "Expiry Date is required.";
					vErrors.add(errorMsg);
					result = true;
				}
				if (ORIGUtility.isEmptyString(client_group)) {
					errorMsg = "Client Group is required.";
					vErrors.add(errorMsg);
					result = true;
				}
			}

			Vector loanVect = appForm.getLoanVect();
			if (loanVect != null && !"Y".equals(menu)) {
				LoanDataM loanDataM = (LoanDataM) loanVect.elementAt(0);
				if (loanDataM.getActualCarPrice() == null || loanDataM.getActualCarPrice().compareTo(new BigDecimal(0)) == 0) {
					errorMsg = "Actual Car Price is required [Loan]";
					vErrors.add(errorMsg);
					result = true;
				}
				if (loanDataM.getActualDown() == null) {
					errorMsg = ("Actual Down is required [Loan]");
					vErrors.add(errorMsg);
					result = true;
				}
				// appraisal price
				if (!OrigConstant.LOAN_TYPE_LEASING.equalsIgnoreCase(loanDataM.getLoanType())) {
					if (OrigConstant.CAR_TYPE_USE.equals(request.getParameter("car"))) {
						if (new BigDecimal(0).compareTo(loanDataM.getAppraisalPrice()) == 0) {
							errorMsg = "Appraisal Price of Loan is required.";
							vErrors.add(errorMsg);
							result = true;
						}
					}
				}
			}

			if (ORIGUtility.isZero(request.getParameter("area_code"))) {
				errorMsg = "Area Code is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("area_code");
				result = true;
			} else {
				vNotErrorFields.add("area_code");
			}
		} else {
			if (ORIGUtility.isEmptyString(request.getParameter("identification_no"))) {
				errorMsg = "Identification No. is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("identification_no");
				result = true;
			} else {
				vNotErrorFields.add("identification_no");
			}
		}

		if (this.getErrorMessage("CUSTOMER_INFO_SUBFORM", request, customerType)) {
			result = true;
		}
		if (!ORIGUtility.isEmptyString(request.getParameter("birth_date"))) {
			int age = utility.stringToInt(request.getParameter("age"));
			if (!OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(customerType)) {
				if (age <= 0 || age > 150) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
					vErrors.add(errorMsg);
					vErrorFields.add("birth_date");
					result = true;
				} else {
					vNotErrorFields.add("birth_date");
				}
			}
			// ===============================
			int workingYear = utility.stringToInt(request.getParameter("year"));
			int workingMonth = utility.stringToInt(request.getParameter("month"));
			if (workingMonth > 0) {
				workingYear = workingYear + 1;
			}
			if (workingYear > age) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "WORKING_YEAR_AGE");
				vErrors.add(errorMsg);
			}
			// ================================

		}
		if (("Y").equals(request.getParameter("k_consent_flag"))) {
			if (ORIGUtility.isEmptyString((String) request.getParameter("k_consent_date"))) {
				errorMsg = "Application consent date is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("k_consent_date");
				result = true;
			} else {
				vNotErrorFields.add("k_consent_date");
			}
		}
		if (this.getErrorMessage("MAILING_SUBFORM", request, customerType)) {
			result = true;
		}

		if (this.getErrorMessage("OCCUPATION_SUBFORM", request, customerType)) {
			result = true;
		}
		// Check Address
		PersonalInfoDataM personalInfoDataM;
		if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
			personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
		} else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
			personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
			personalInfoDataM = utility.getPersonalInfoByType(appForm, OrigConstant.PERSONAL_TYPE_APPLICANT);
		}
		int addressSize = utility.getAddressSizeByPersonalType(personalInfoDataM.getAddressVect(), personalInfoDataM.getCustomerType(), personalType);
		if (personalInfoDataM.getAddressVect() == null) {
			errorMsg = "Address is required.";
			vErrors.add(errorMsg);
			result = true;
		} else {
			// if (personalInfoDataM.getAddressVect().size() != addressSize) {
			if (personalInfoDataM.getAddressVect().size() < addressSize) {
				errorMsg = "All Address types is required.";
				vErrors.add(errorMsg);
				result = true;
			}
		}
		// ===================Adddress /Birth Date==================
		String paramField = "birth_date";
		boolean addressValid = true;
		String strBirthDate = request.getParameter(paramField);
		Date birthDate = ORIGUtility.parseThaiToEngDate(strBirthDate);
		if (birthDate != null) {
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			Calendar clNow = Calendar.getInstance();
			Calendar clBd = Calendar.getInstance();
			clBd.setTime(birthDate);
			int age = clNow.get(Calendar.YEAR) - clBd.get(Calendar.YEAR);
			if (clNow.get(Calendar.MONTH) < clBd.get(Calendar.MONTH)
					|| (clNow.get(Calendar.MONTH) == clBd.get(Calendar.MONTH) && clNow.get(Calendar.DAY_OF_MONTH) < clBd.get(Calendar.DAY_OF_MONTH))) {
				age = age - 1;
			}
			Vector vAddress = personalInfoDataM.getAddressVect();
			if (vAddress != null) {
				for (int i = 0; i < vAddress.size(); i++) {
					AddressDataM addressDataM = (AddressDataM) vAddress.get(i);
					BigDecimal resideYear = addressDataM.getResideYear();
					if (resideYear != null) {
						int year = resideYear.intValue();
						int month = resideYear.movePointLeft(2).intValue() % 100;
						if (month > 0) {
							year++;
						}
						if (year != 0 && year > age) {
							addressValid = false;
							vErrors.add("Age must not less than reside year/month in Address Type "
									+ cacheUtil.getORIGMasterDisplayNameDataM("AddressType", addressDataM.getAddressType()));
						}
					}
				}
			}

		}
		// =================================
		if (!("Y").equals(appForm.getDrawDownFlag())) {
			if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType())) {
				if (("Y").equals(request.getParameter("consent_flag"))) {
					if (ORIGUtility.isEmptyString((String) request.getParameter("consent_date"))) {
						errorMsg = "NCB consent date is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("consent_date");
						result = true;
					} else {
						vNotErrorFields.add("consent_date");
					}
				}
				XRulesVerificationResultDataM rulesVerificationResultDataM = personalInfoDataM.getXrulesVerification();
				if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType())
						&& ORIGUtility.isEmptyString(rulesVerificationResultDataM.getNCBResult())) {
					errorMsg = "NCB is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("resNCB");
					result = true;
				} else {
					// vNotErrorFields.add("resNCB");
				}
				if (this.getErrorMessage("VERIFICATION_LIST_SUBFORM", request, customerType)) {
					result = true;
				}
			} else {
				boolean result2 = getMandateErrorGuarantorVerUW(request, customerType);
				if (result2) {
					result = true;
				}
			}

			if (!"Y".equals(menu)) {
				if (!OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(personalInfoDataM.getCustomerType())) {
					if (ORIGUtility.isEmptyString((String) request.getParameter("resPolicyRules"))) {
						errorMsg = "Policy Rules is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("resPolicyRules");
						result = true;
					} else {
						vNotErrorFields.add("resPolicyRules");
					}
				}
			}

			if (!OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType) && !"Y".equals(menu)) {
				logger.debug("Check applicaiton Score");
				// appForm.getScorringResult()
				if (ORIGUtility.isEmptyString((String) request.getParameter("resBlacklistVehicle"))) {
					errorMsg = "Blacklist - Car is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("resBlacklistVehicle");
					result = true;
				} else {
					vNotErrorFields.add("resBlacklistVehicle");
				}
				if (ORIGUtility.isEmptyString((String) request.getParameter("resDedupVehicle"))) {
					errorMsg = "Duplicate Vehicle is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("resDedupVehicle");
					result = true;
				} else {
					vNotErrorFields.add("resDedupVehicle");
				}

				if (appForm.getScorringResult() == null) {
					logger.debug("Check execute applicaiton Score");
					errorMsg = ErrorUtil.getShortErrorMessage(request, "executeAppScore");
					vErrors.add(errorMsg);
					vErrorFields.add("application_score");
					result = true;
				} else if (appForm.getScorringResult() != null) {
					// check re excute appscore
					logger.debug("Check re execute applicaiton Score");
					if (OrigReExcuteAppScoreUtil.getInstance().isApplicationReExecuteScorring(appForm, request)) {
						errorMsg = ErrorUtil.getShortErrorMessage(request, "reexecuteAppScore");
						vErrors.add(errorMsg);
						vErrorFields.add("application_score");
						result = true;
					}

				} else {
					logger.debug("Check appscore Case Else ");
					vNotErrorFields.add("application_score");
				}
				if (appForm.getIsReExcuteDebtAmtFlag()) {
					logger.debug("Check re execute Debt Amt");
					errorMsg = ErrorUtil.getShortErrorMessage(request, "reexecute_debt_amt");
					vErrors.add(errorMsg);
					vErrorFields.add("resDebtAmt");
					result = true;
				}

			}
		}
		return result;
	}

	public boolean getMandateErrorPD(HttpServletRequest request, String customerType) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ApplicationDataM appForm = formHandler.getAppForm();
		if (appForm == null) {
			appForm = new ApplicationDataM();
		}
		Vector vErrors = formHandler.getFormErrors();
		if (vErrors == null) {
			vErrors = new Vector();
			formHandler.setFormErrors(vErrors);
		}
		String errorMsg;

		if (this.getErrorMessage("INSURENCE_SUBFORM", request, customerType)) {
			result = true;
		}

		String decision = request.getParameter("decision_pd");
		String reason = request.getParameter("pd_reason");
		if (!ORIGUtility.isEmptyString(decision) && ORIGUtility.isEmptyString(reason)) {
			errorMsg = "Reason is required.";
			vErrors.add(errorMsg);
			result = true;
		}

		return result;
	}

	public boolean getMandateErrorXCMR(HttpServletRequest request, String customerType) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ApplicationDataM appForm = formHandler.getAppForm();
		if (appForm == null) {
			appForm = new ApplicationDataM();
		}
		Vector vErrors = formHandler.getFormErrors();
		if (vErrors == null) {
			vErrors = new Vector();
			formHandler.setFormErrors(vErrors);
		}
		String errorMsg;
		ORIGUtility utility = new ORIGUtility();

		// Check Reason
		String decision = request.getParameter("cmr_decision");
		String reason = request.getParameter("cmr_reason");

		logger.debug("decision = " + decision);
		if (ORIGUtility.isEmptyString(decision)) {
			errorMsg = "Decision is required.";
			vErrors.add(errorMsg);
			result = true;
		} else {
			if (ORIGUtility.isEmptyString(reason)) {
				errorMsg = "Reason Campaign is required.";
				vErrors.add(errorMsg);
				result = true;
			}
		}

		return result;
	}

	public StringBuffer getDisplayError(ORIGFormHandler formHandler) {
		StringBuffer sb = new StringBuffer("");
		ORIGUtility utility = new ORIGUtility();
		Vector err = formHandler.getFormErrors();
		Vector fieldErrors = formHandler.getErrorFields();
		Vector fieldNoError = formHandler.getNotErrorFields();

		StringBuffer error = new StringBuffer("");
		for (int i = 0; i < err.size(); i++) {
			String errorMessage = (String) err.get(i);
			error.append("<span class=\"TextWarningNormal\">*&nbsp;" + errorMessage + "</span><br>");
		}

		String errorStr = utility.convertXMLIllegalChar(error.toString());

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<xml>");
		sb.append("<error_message>" + errorStr + "</error_message>");
		sb.append("<error_field>");
		for (int i = 0; i < fieldErrors.size(); i++) {
			sb.append("<field>" + fieldErrors.get(i) + "</field>");
		}
		sb.append("</error_field>");
		sb.append("<no_error_field>");
		for (int i = 0; i < fieldNoError.size(); i++) {
			sb.append("<field>" + fieldNoError.get(i) + "</field>");
		}
		sb.append("</no_error_field>");
		sb.append("</xml>");
		return sb;
	}

	public boolean getMandateErrorOtherName(HttpServletRequest request, String customerType) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);

		if (this.getErrorMessage("OTHER_NAME_POPUP", request, customerType)) {
			result = true;
		}

		return result;
	}

	public boolean getMandateErrorAddress(HttpServletRequest request, String customerType) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ApplicationDataM appForm = formHandler.getAppForm();
		if (appForm == null) {
			appForm = new ApplicationDataM();
		}
		Vector vErrors = formHandler.getFormErrors();
		Vector vErrorFields = formHandler.getErrorFields();
		Vector vNotErrorFields = formHandler.getNotErrorFields();
		String errorMsg = null;

		if (this.getErrorMessage("ADDRESS_POPUP", request, customerType)) {
			result = true;
		}
		// String phone1 = request.getParameter("phone1");
		// String mobile_no = request.getParameter("mobile_no");
		// String phone2 = request.getParameter("phone2");
		// String fax_no = request.getParameter("fax_no");
		//
		// if (ORIGUtility.isEmptyString(phone1)
		// && ORIGUtility.isEmptyString(mobile_no)
		// && ORIGUtility.isEmptyString(phone2)
		// && ORIGUtility.isEmptyString(fax_no)) {
		// errorMsg = "Phone number is required.";
		// vErrors.add(errorMsg);
		// result = true;
		// }

		return result;
	}

	public boolean getMandateErrorFinance(HttpServletRequest request, String customerType) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);

		if (this.getErrorMessage("FINANCE_POPUP", request, customerType)) {
			result = true;
		}

		return result;
	}

	public boolean getMandateErrorChangeName(HttpServletRequest request, String customerType) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);

		if (this.getErrorMessage("CHANGE_NAME_POPUP", request, customerType)) {
			result = true;
		}
		String change_date = request.getParameter("change_date");
		ORIGUtility utility = new ORIGUtility();
		String personalType = (String) request.getSession().getAttribute("PersonalType");
		PersonalInfoDataM personalInfoDataM;
		ApplicationDataM appForm = formHandler.getAppForm();
		if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
			personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
		} else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
			personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
			personalInfoDataM = utility.getPersonalInfoByType(appForm, OrigConstant.PERSONAL_TYPE_APPLICANT);
		}
		Vector changeNameVect = personalInfoDataM.getChangeNameVect();
		ChangeNameDataM changeNameDataM;
		if (changeNameVect != null) {
			String sDate;
			Vector vErrors = formHandler.getFormErrors();
			Vector vErrorFields = formHandler.getErrorFields();
			Vector vNotErrorFields = formHandler.getNotErrorFields();
			if (vErrors == null) {
				vErrors = new Vector();
			}
			String errorMsg = null;
			for (int i = 0; i < changeNameVect.size(); i++) {
				changeNameDataM = (ChangeNameDataM) changeNameVect.get(i);
				sDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(changeNameDataM.getChangeDate()));
				if (change_date.equals(sDate)) {
					errorMsg = "Duplicate Change Date.";
					vErrors.add(errorMsg);
					// vErrorFields.add("change_date");
					result = true;
				}
			}
		}
		return result;
	}

	public boolean getMandateErrorLoan(HttpServletRequest request, String customerType, String loanType, String haveVat, String haveBalloon) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);

		Vector vErrors = formHandler.getFormErrors();
		Vector vErrorFields = formHandler.getErrorFields();
		Vector vNotErrorFields = formHandler.getNotErrorFields();
		if (vErrors == null) {
			vErrors = new Vector();
		}
		String errorMsg = null;
		String costOfCarPrice = request.getParameter("car_price_cost");
		String costOfDownPayment = request.getParameter("down_payment_cost");
		String totalOfCarPrice = request.getParameter("car_price_total");
		String totalOfDownPayment = request.getParameter("down_payment_total");
		String carType = request.getParameter("car_type");
		String payment_type = request.getParameter("payment_type");
		String downType = request.getParameter("down_type");
		String installment_flag = request.getParameter("installment_flag");
		if (OrigConstant.LOAN_TYPE_LEASING.equals(loanType)) {
			if (this.getErrorMessage("LOAN_LEASING_POPUP", request, customerType)) {
				result = true;
			}

			if ("Y".equals(haveVat)) {
				if (totalOfCarPrice == null || ("").equals(totalOfCarPrice)) {
					errorMsg = "Equipment Cost Total is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_price_total");
					result = true;
				} else {
					vNotErrorFields.add("car_price_total");
				}
				/*
				 * if (totalOfDownPayment == null ||
				 * ("").equals(totalOfDownPayment)) { errorMsg = "Deposit Total
				 * is required."; vErrors.add(errorMsg);
				 * vErrorFields.add("down_payment_total"); result = true; } else
				 * { vNotErrorFields.add("down_payment_total"); }
				 */
			} else {
				if (costOfCarPrice == null || ("").equals(costOfCarPrice)) {
					errorMsg = "Equipment Cost is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_price_cost");
					result = true;
				} else {
					vNotErrorFields.add("car_price_cost");
				}
				/*
				 * if (costOfDownPayment == null ||
				 * ("").equals(costOfDownPayment)) { errorMsg = "Deposit Cost is
				 * required."; vErrors.add(errorMsg);
				 * vErrorFields.add("down_payment_cost"); result = true; } else
				 * { vNotErrorFields.add("down_payment_cost"); }
				 */
			}

			String bookingDateStr = request.getParameter("booking_date");
			String agreeDateStr = request.getParameter("agreement_date");
			Date bookingDate = ORIGUtility.parseThaiToEngDate(bookingDateStr);
			Date agreeDate = ORIGUtility.parseThaiToEngDate(agreeDateStr);
			logger.debug("bookingDate = " + bookingDate);
			logger.debug("agreeDate = " + agreeDate);
			if (bookingDate != null) {
				if (OrigConstant.CAR_TYPE_NEW.equals(carType)) {
					if (bookingDateStr.equals(agreeDateStr)) {
						errorMsg = "Booking Date must equal Agreement Date";
						vErrors.add(errorMsg);
						vErrorFields.add("booking_date");
						vErrorFields.add("agreement_date");
						result = true;
					} else {
						vNotErrorFields.add("booking_date");
						vNotErrorFields.add("agreement_date");
					}
				} else {
					if (bookingDate.compareTo(agreeDate) == -1) {
						errorMsg = "Agreement date must less than or equal to Booking date and still on the same month.";
						vErrors.add(errorMsg);
						vErrorFields.add("booking_date");
						vErrorFields.add("agreement_date");
						result = true;
					} else if (agreeDate != null) {
						if (agreeDate.getMonth() != bookingDate.getMonth()) {
							errorMsg = "Agreement date must less than or equal to Booking date and still on the same month.";
							vErrors.add(errorMsg);
							vErrorFields.add("booking_date");
							vErrorFields.add("agreement_date");
							result = true;
						}
					} else {
						vNotErrorFields.add("booking_date");
						vNotErrorFields.add("agreement_date");
					}
				}
			}
			String first_ins_type = request.getParameter("first_ins_type");
			if (OrigConstant.PAYMENT_BEGINING.equals(payment_type)) {
				if (first_ins_type == null || ("").equals(first_ins_type)) {
					errorMsg = "First Installment Pay Type is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("first_ins_type");
					result = true;
				} else {
					vNotErrorFields.add("first_ins_type");
				}
			}
		} else {
			if (this.getErrorMessage("LOAN_POPUP", request, customerType)) {
				result = true;
			}
			ORIGUtility utility = new ORIGUtility();
			String collectionCode = request.getParameter("collection_code");
			String bank = request.getParameter("bank");
			String branch = request.getParameter("branch");
			String account_no = request.getParameter("account_no");
			String account_name = request.getParameter("account_name");
			String first_installment = request.getParameter("first_installment");
			if (utility.isDirectDebit(collectionCode)) {
				if (ORIGUtility.isEmptyString(bank)) {
					errorMsg = "Bank Code is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("bank");
					result = true;
				} else {
					vNotErrorFields.add("bank");
				}
				if (ORIGUtility.isEmptyString(branch)) {
					errorMsg = "Branch Code is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("branch");
					result = true;
				} else {
					vNotErrorFields.add("branch");
				}
				if (ORIGUtility.isEmptyString(account_no)) {
					errorMsg = "A/C No. is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("account_no");
					result = true;
				} else {
					vNotErrorFields.add("account_no");
				}
				if (ORIGUtility.isEmptyString(account_name)) {
					errorMsg = "A/C Name is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("account_name");
					result = true;
				} else {
					vNotErrorFields.add("account_name");
				}
			} else {
				vNotErrorFields.add("account_name");
				vNotErrorFields.add("account_no");
				vNotErrorFields.add("branch");
				vNotErrorFields.add("bank");
			}
			if (OrigConstant.PAYMENT_BEGINING.equals(payment_type)) {
				if (ORIGUtility.isEmptyString(first_installment)) {
					errorMsg = "First Installment is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("first_installment");
					result = true;
				} else {
					first_installment = first_installment.replaceAll("%2C", "");
					if (Double.parseDouble(first_installment) == 0) {
						errorMsg = "First Installment is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("first_installment");
						result = true;
					} else {
						vNotErrorFields.add("first_installment");
					}
				}
			} else {
				vNotErrorFields.add("first_installment");
			}
			if ("Y".equals(haveVat)) {
				if (totalOfCarPrice == null || ("").equals(totalOfCarPrice)) {
					errorMsg = "Car Price Total is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_price_total");
					result = true;
				} else {
					vNotErrorFields.add("car_price_total");
				}
				if (!OrigConstant.DOWN_TYPE_NODOWN.equals(downType)) {
					if (totalOfDownPayment == null || ("").equals(totalOfDownPayment)) {
						errorMsg = "Down Payment Total is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("down_payment_total");
						result = true;
					} else {
						vNotErrorFields.add("down_payment_total");
					}
				}
			} else {
				if (costOfCarPrice == null || ("").equals(costOfCarPrice)) {
					errorMsg = "Car Price Cost is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_price_cost");
					result = true;
				} else {
					vNotErrorFields.add("car_price_cost");
				}
				if (!OrigConstant.DOWN_TYPE_NODOWN.equals(downType)) {
					if (costOfDownPayment == null || ("").equals(costOfDownPayment)) {
						errorMsg = "Down Payment Cost is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("down_payment_cost");
						result = true;
					} else {
						vNotErrorFields.add("down_payment_cost");
					}
				}
			}
			String totalOfBalloon = request.getParameter("balloon_total");
			String rate = request.getParameter("rate1");
			String balloonFlag = request.getParameter("balloon_flag");
			String balloonTerm = request.getParameter("balloon_amt_term");
			String installment = request.getParameter("installment1");
			if ("Y".equals(balloonFlag)) {
				if (totalOfBalloon == null || ("").equals(totalOfBalloon)) {
					errorMsg = "Balloon Amount Total is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("balloon_total");
					result = true;
				} else {
					vNotErrorFields.add("balloon_total");
				}
			} else {
				vNotErrorFields.add("balloon_total");
			}
			if (ORIGUtility.isEmptyString(rate)) {
				errorMsg = "Rate is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("rate1");
				result = true;
			} else {
				vNotErrorFields.add("rate1");
			}
			if (installment == null || ("").equals(installment)) {
				errorMsg = "Term is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("installment1");
				result = true;
			} else {
				vNotErrorFields.add("installment1");
			}

			String appraisal_price = request.getParameter("appraisal_price");
			if (OrigConstant.CAR_TYPE_USE.equalsIgnoreCase(carType)) {
				if (appraisal_price == null || ("").equals(appraisal_price) || (new BigDecimal(0)).compareTo(utility.stringToBigDecimal(appraisal_price)) == 0) {
					errorMsg = "Appraisal Price must more than zero.";
					vErrors.add(errorMsg);
					vErrorFields.add("appraisal_price");
					result = true;
				} else {
					vNotErrorFields.add("appraisal_price");
				}
			}

			String bookingDateStr = request.getParameter("booking_date");
			String contactDateStr = request.getParameter("contact_date");
			Date bookingDate = ORIGUtility.parseThaiToEngDate(bookingDateStr);
			Date contactDate = ORIGUtility.parseThaiToEngDate(contactDateStr);

			logger.debug("bookingDate = " + bookingDate);
			logger.debug("contactDate = " + contactDate);
			if (bookingDate != null) {
				if (OrigConstant.CAR_TYPE_NEW.equals(carType)) {
					if (!bookingDateStr.equals(contactDateStr)) {
						errorMsg = "Booking Date must equal Contact Date";
						vErrors.add(errorMsg);
						vErrorFields.add("booking_date");
						vErrorFields.add("contact_date");
						result = true;
					} else {
						vNotErrorFields.add("booking_date");
						vNotErrorFields.add("contact_date");
					}
				} else {
					if (bookingDate.compareTo(contactDate) == -1) {
						errorMsg = "Contract date must less than or equal to Booking date and still on the same month.";
						vErrors.add(errorMsg);
						vErrorFields.add("booking_date");
						vErrorFields.add("contact_date");
						result = true;
					} else if (contactDate != null) {
						if (contactDate.getMonth() != bookingDate.getMonth()) {
							errorMsg = "Contract date must less than or equal to Booking date and still on the same month.";
							vErrors.add(errorMsg);
							vErrorFields.add("booking_date");
							vErrorFields.add("contact_date");
							result = true;
						}
					} else {
						vNotErrorFields.add("booking_date");
						vNotErrorFields.add("contact_date");
					}
				}
			}
		}
		if (OrigConstant.LOAN_TYPE_FLEET.equals(loanType)) {
			String collection_code = request.getParameter("collection_code");

			if (collection_code == null || ("").equals(collection_code)) {
				errorMsg = "Collection Code is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("collection_code");
				result = true;
			} else {
				vNotErrorFields.add("collection_code");
			}
		}
		Vector loanVect = formHandler.getAppForm().getLoanVect();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		LoanDataM loanDataM = (LoanDataM) request.getSession().getAttribute("POPUP_DATA");
		if (loanDataM == null) {
			loanDataM = new LoanDataM();
		}
		// 20080724 validate Installment
		if (OrigConstant.INSTALLMENT_TYPE_STEP.equals(installment_flag)) {
			// validate Term
			String strTerm = request.getParameter("installment1");
			int installmentTerm = Integer.parseInt(strTerm);
			int stepTermCount = 0;
			BigDecimal installmentTotal = new BigDecimal(0);
			Vector vStepInstallment = loanDataM.getStepInstallmentVect();
			if (vStepInstallment == null) {
				vStepInstallment = new Vector(0);
			}
			for (int i = 0; i < vStepInstallment.size(); i++) {
				InstallmentDataM installmentDataM = (InstallmentDataM) vStepInstallment.get(i);
				stepTermCount += installmentDataM.getTermDuration();
				installmentTotal = installmentTotal.add(installmentDataM.getInstallmentTotal());
			}
			if (stepTermCount != installmentTerm) {
				errorMsg = ("Step installment Term not equal Term");
				vErrors.add(errorMsg);
				result = true;
			}
			//
			// valid Hire Purchase amount

			String fields = "";
			if ("Y".equals(haveVat)) {
				fields = "hire_purchase_total";
			} else {
				fields = "hire_purchase_cost";
			}
			ORIGUtility utility = ORIGUtility.getInstance();
			BigDecimal hirePurchaseAmt = utility.stringToBigDecimal(request.getParameter(fields));
			logger.debug("request.getParameter(fields)" + fields + " =" + request.getParameter(fields));
			logger.debug("hirePurchaseAmt=" + hirePurchaseAmt);
			logger.debug("installmentTotal=" + installmentTotal);
			if (installmentTotal.compareTo(hirePurchaseAmt) != 0) {
				errorMsg = ("Total Hire Purchase Amount  not equal Gran Total Instalment Amount");
				vErrors.add(errorMsg);
				result = true;
			}
		}
		String actual_car_price = request.getParameter("actual_car_price");
		String actual_down = request.getParameter("actual_down");
		if (OrigConstant.ROLE_UW.equals(userM.getRoles().elementAt(0))) {
			if (actual_car_price == null || "".equals(actual_car_price)) {
				errorMsg = "Actual Car Price is required [Loan]";
				vErrors.add(errorMsg);
				result = true;
			}
			if (actual_down == null || "".equals(actual_down)) {
				errorMsg = ("Actual Down is required [Loan]");
				vErrors.add(errorMsg);
				result = true;
			}
		}

		return result;
	}

	public boolean getMandateErrorLoanForCalulate(HttpServletRequest request, String customerType, String loanType, String haveVat, String haveBalloon) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);

		Vector vErrors = formHandler.getFormErrors();
		Vector vErrorFields = formHandler.getErrorFields();
		Vector vNotErrorFields = formHandler.getNotErrorFields();
		if (vErrors == null) {
			vErrors = new Vector();
		}
		String errorMsg = null;
		String costOfCarPrice = request.getParameter("car_price_cost");
		String costOfDownPayment = request.getParameter("down_payment_cost");
		String totalOfCarPrice = request.getParameter("car_price_total");
		String totalOfDownPayment = request.getParameter("down_payment_total");
		String paymentType = request.getParameter("payment_type");
		String netRate = request.getParameter("net_rate");
		String vat = request.getParameter("vat");
		String rate = request.getParameter("rate1");
		String installment = request.getParameter("installment1");
		String carType = request.getParameter("car_type");
		String rvpercent = request.getParameter("rv_percent");
		String downType = request.getParameter("down_type");

		if (paymentType == null || ("").equals(paymentType)) {
			errorMsg = "Payment Type is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("payment_type");
			result = true;
		} else {
			vNotErrorFields.add("payment_type");
		}

		if (netRate == null || ("").equals(netRate) || Double.parseDouble(netRate) == 0) {
			errorMsg = "Net Rate is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("net_rate");
			result = true;
		} else {
			vNotErrorFields.add("net_rate");
		}

		if (vat == null || ("").equals(vat)) {
			errorMsg = "Vat is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("vat");
			result = true;
		} else {
			vNotErrorFields.add("vat");
		}

		if (ORIGUtility.isEmptyString(rate)) {
			errorMsg = "Rate is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("rate1");
			result = true;
		} else {
			vNotErrorFields.add("rate1");
		}

		if (OrigConstant.LOAN_TYPE_LEASING.equals(loanType)) {

			if ("Y".equals(haveVat)) {
				if (totalOfCarPrice == null || ("").equals(totalOfCarPrice) || Double.parseDouble(totalOfCarPrice) == 0) {
					errorMsg = "Equipment Cost Total is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_price_total");
					result = true;
				} else {
					vNotErrorFields.add("car_price_total");
				}
				/*
				 * if (totalOfDownPayment == null ||
				 * ("").equals(totalOfDownPayment) ||
				 * Double.parseDouble(totalOfDownPayment) == 0) { errorMsg =
				 * "Deposit Total is required."; vErrors.add(errorMsg);
				 * vErrorFields.add("down_payment_total"); result = true; } else
				 * { vNotErrorFields.add("down_payment_total"); }
				 */
			} else {
				if (costOfCarPrice == null || ("").equals(costOfCarPrice) || Double.parseDouble(costOfCarPrice) == 0) {
					errorMsg = "Equipment Cost is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_price_cost");
					result = true;
				} else {
					vNotErrorFields.add("car_price_cost");
				}
				/*
				 * if (costOfDownPayment == null ||
				 * ("").equals(costOfDownPayment) ||
				 * Double.parseDouble(costOfDownPayment) == 0) { errorMsg =
				 * "Deposit Cost is required."; vErrors.add(errorMsg);
				 * vErrorFields.add("down_payment_cost"); result = true; } else
				 * { vNotErrorFields.add("down_payment_cost"); }
				 */
			}
			if (installment == null || ("").equals(installment) || Double.parseDouble(installment) == 0) {
				errorMsg = "Term is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("installment1");
				result = true;
			} else {
				vNotErrorFields.add("installment1");
			}
			if (rvpercent == null || ("").equals(rvpercent) || Double.parseDouble(rvpercent) == 0) {
				errorMsg = "RV is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("rv_percent");
				result = true;
			} else {
				vNotErrorFields.add("rv_percent");
			}
		} else {

			if ("Y".equals(haveVat)) {
				if (totalOfCarPrice == null || ("").equals(totalOfCarPrice) || Double.parseDouble(totalOfCarPrice) == 0) {
					errorMsg = "Car Price Total is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_price_total");
					result = true;
				} else {
					vNotErrorFields.add("car_price_total");
				}
				if (!OrigConstant.DOWN_TYPE_NODOWN.equals(downType)) {
					if (totalOfDownPayment == null || ("").equals(totalOfDownPayment)) {
						errorMsg = "Down Payment Total is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("down_payment_total");
						result = true;
					} else {
						vNotErrorFields.add("down_payment_total");
					}
				}
			} else {
				if (costOfCarPrice == null || ("").equals(costOfCarPrice) || Double.parseDouble(costOfCarPrice) == 0) {
					errorMsg = "Car Price Cost is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("car_price_cost");
					result = true;
				} else {
					vNotErrorFields.add("car_price_cost");
				}
				if (!OrigConstant.DOWN_TYPE_NODOWN.equals(downType)) {
					if (costOfDownPayment == null || ("").equals(costOfDownPayment)) {
						errorMsg = "Down Payment Cost is required.";
						vErrors.add(errorMsg);
						vErrorFields.add("down_payment_cost");
						result = true;
					} else {
						vNotErrorFields.add("down_payment_cost");
					}
				}
			}
			String totalOfBalloon = request.getParameter("balloon_total");
			String balloonFlag = request.getParameter("balloon_flag");
			String balloonTerm = request.getParameter("balloon_amt_term");
			if ("Y".equals(balloonFlag)) {
				if (totalOfBalloon == null || ("").equals(totalOfBalloon) || Double.parseDouble(totalOfBalloon) == 0) {
					errorMsg = "Balloon Amount Total is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("balloon_total");
					result = true;
				} else {
					vNotErrorFields.add("balloon_total");
				}
			} else {
				vNotErrorFields.add("balloon_total");
			}
			if (installment == null || ("").equals(installment) || Double.parseDouble(installment) == 0) {
				errorMsg = "Term is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("installment1");
				result = true;
			} else {
				vNotErrorFields.add("installment1");
			}

		}
		return result;
	}

	public boolean getMandateErrorCarPopup(HttpServletRequest request, String customerType) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		Vector vErrors = formHandler.getFormErrors();
		Vector vErrorFields = formHandler.getErrorFields();
		Vector vNotErrorFields = formHandler.getNotErrorFields();
		if (vErrors == null) {
			vErrors = new Vector();
		}
		String errorMsg = null;

		if (this.getErrorMessage("CAR_INFO_SUBFORM", request, customerType)) {
			result = true;
		}
		if (OrigConstant.CAR_TYPE_USE.equals(request.getParameter("car"))) {
			if (ORIGUtility.isEmptyString(request.getParameter("car_engine_no"))) {
				errorMsg = "Engine No. is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("car_engine_no");
				result = true;
			} else {
				vNotErrorFields.add("car_engine_no");
			}
			if (ORIGUtility.isEmptyString(request.getParameter("car_register_no"))) {
				errorMsg = "Register No. is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("car_register_no");
				result = true;
			} else {
				vNotErrorFields.add("car_register_no");
			}
			if (ORIGUtility.isEmptyString(request.getParameter("car_classis_no"))) {
				errorMsg = "Chassis No. is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("car_classis_no");
				result = true;
			} else {
				vNotErrorFields.add("car_classis_no");
			}
			if (ORIGUtility.isEmptyString(request.getParameter("car_weight"))) {
				errorMsg = "Car Weight is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("car_weight");
				result = true;
			} else {
				vNotErrorFields.add("car_weight");
			}
			if (ORIGUtility.isZero(request.getParameter("car_year"))) {
				errorMsg = "Car Year is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("car_year");
				result = true;
			} else {
				vNotErrorFields.add("car_year");
			}
			if (ORIGUtility.isZero(request.getParameter("car_kilometers"))) {
				errorMsg = "Car Kilometers is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("car_kilometers");
				result = true;
			} else {
				vNotErrorFields.add("car_kilometers");
			}
			if (ORIGUtility.isEmptyString(request.getParameter("car_register_date"))) {
				errorMsg = "Register Date is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("car_register_date");
				result = true;
			} else {
				vNotErrorFields.add("car_register_date");
			}
			if (ORIGUtility.isEmptyString(request.getParameter("car_expiry_date"))) {
				errorMsg = "Expiry Date is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("car_expiry_date");
				result = true;
			} else {
				vNotErrorFields.add("car_expiry_date");
			}
			if (ORIGUtility.isEmptyString(request.getParameter("car_occupy_date"))) {
				errorMsg = "Occupy Date is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("car_occupy_date");
				result = true;
			} else {
				vNotErrorFields.add("car_occupy_date");
			}
		} else {
			vNotErrorFields.add("car_engine_no");
			vNotErrorFields.add("car_register_no");
			vNotErrorFields.add("car_classis_no");
			vNotErrorFields.add("car_weight");
			vNotErrorFields.add("car_year");
			vNotErrorFields.add("car_kilometers");
			vNotErrorFields.add("car_register_date");
			vNotErrorFields.add("car_expiry_date");
			vNotErrorFields.add("car_occupy_date");
		}
		double totalCreditlimit = formHandler.getAppForm().getTotalCreditLimit();
		double totalCrrateCar = formHandler.getAppForm().getCreateCarAmount();
		double available = totalCreditlimit - totalCrrateCar;
		if (available < 0) {
			available = 0;
		}
		if (formHandler.getAppForm().getLoanVect() == null || formHandler.getAppForm().getLoanVect().size() == 0) {
			errorMsg = "Loan Detail is required.";
			vErrors.add(errorMsg);
			result = true;
		} else {
			LoanDataM loanDataM = (LoanDataM) formHandler.getAppForm().getLoanVect().elementAt(0);

			logger.debug("available = " + available);
			logger.debug("loanDataM.getCostOfFinancialAmt() = " + loanDataM.getCostOfFinancialAmt());
			BigDecimal costOfFinanceTmp = loanDataM.getCostOfFinanceTmp();
			if (costOfFinanceTmp == null) {
				costOfFinanceTmp = new BigDecimal(0);
			} else {
				costOfFinanceTmp = loanDataM.getCostOfFinanceTmp();
			}
			if (loanDataM.getCostOfFinancialAmt().doubleValue() > (available + costOfFinanceTmp.doubleValue())) {
				errorMsg = "Finance amount is greater than Available after deduct Created Car Detail Amount.";
				vErrors.add(errorMsg);
				result = true;
			}
		}
		return result;
	}

	public boolean getMandateErrorGuarantorVerDE(HttpServletRequest request, String customerType) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		Vector vErrors = formHandler.getFormErrors();
		Vector vErrorFields = formHandler.getErrorFields();
		Vector vNotErrorFields = formHandler.getNotErrorFields();
		if (vErrors == null) {
			vErrors = new Vector();
		}
		String errorMsg = null;
		ORIGUtility utility = new ORIGUtility();

		if (ORIGUtility.isEmptyString(request.getParameter("resBlaclist"))) {
			errorMsg = "Blacklist - Customer is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resBlaclist");
			result = true;
		} else {
			vNotErrorFields.add("resBlaclist");
		}
		if (ORIGUtility.isEmptyString(request.getParameter("resExistingCustomer"))) {
			errorMsg = "Existing Customer is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resExistingCustomer");
			result = true;
		} else {
			vNotErrorFields.add("resExistingCustomer");
		}

		if (ORIGUtility.isEmptyString(request.getParameter("resDedup"))) {
			errorMsg = "Dedup is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resDedup");
			result = true;
		} else {
			vNotErrorFields.add("resDedup");
		}
		if (ORIGUtility.isEmptyString(request.getParameter("resLPM"))) {
			errorMsg = "LPM is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resLPM");
			result = true;
		} else {
			vNotErrorFields.add("resLPM");
		}
		if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(customerType)) {
			if (ORIGUtility.isEmptyString(request.getParameter("resKhonthai"))) {
				errorMsg = "Khonthai.com is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("resKhonthai");
				result = true;
			} else {
				vNotErrorFields.add("resKhonthai");
			}
		} else if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(customerType)) {
			if (ORIGUtility.isEmptyString(request.getParameter("resThaiRegistration"))) {
				errorMsg = "Thai Registration is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("resThaiRegistration");
				result = true;
			} else {
				vNotErrorFields.add("resThaiRegistration");
			}
			if (ORIGUtility.isEmptyString(request.getParameter("resBOL"))) {
				errorMsg = "BOL is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("resBOL");
				result = true;
			} else {
				vNotErrorFields.add("resBOL");
			}
		}

		if (ORIGUtility.isEmptyString(request.getParameter("resYellowPage"))) {
			errorMsg = "Yellow Page is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resYellowPage");
			result = true;
		} else {
			vNotErrorFields.add("resYellowPage");
		}
		if (ORIGUtility.isEmptyString(request.getParameter("resPhoneBook"))) {
			errorMsg = "Phone Book is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resPhoneBook");
			result = true;
		} else {
			vNotErrorFields.add("resPhoneBook");
		}

		// NCB Guarantor DE/UW

		PersonalInfoDataM personalInfoDataM;
		ApplicationDataM appForm = formHandler.getAppForm();
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
		if (OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())
				|| OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())) {
			XRulesVerificationResultDataM rulesVerificationResultDataM = personalInfoDataM.getXrulesVerification();
			if (("Y").equals(request.getParameter("consent_flag"))) {
				if (ORIGUtility.isEmptyString((String) request.getParameter("consent_date"))) {
					errorMsg = "NCB consent date is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("consent_date");
					result = true;
				} else {
					vNotErrorFields.add("consent_date");
				}
			}
			if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType())
					&& ORIGUtility.isEmptyString(rulesVerificationResultDataM.getNCBResult())) {
				errorMsg = "NCB is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("resNCB");
				result = true;
			} else {
				// vNotErrorFields.add("resNCB");
			}
		}// Co Borrower

		return result;
	}

	public boolean getMandateErrorGuarantorVerUW(HttpServletRequest request, String customerType) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		Vector vErrors = formHandler.getFormErrors();
		Vector vErrorFields = formHandler.getErrorFields();
		Vector vNotErrorFields = formHandler.getNotErrorFields();
		if (vErrors == null) {
			vErrors = new Vector();
		}
		String errorMsg = null;
		ORIGUtility utility = new ORIGUtility();

		if (ORIGUtility.isEmptyString(request.getParameter("resBlaclist"))) {
			errorMsg = "Blacklist - Customer is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resBlaclist");
			result = true;
		} else {
			vNotErrorFields.add("resBlaclist");
		}
		if (ORIGUtility.isEmptyString(request.getParameter("resExistingCustomer"))) {
			errorMsg = "Existing Customer is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resExistingCustomer");
			result = true;
		} else {
			vNotErrorFields.add("resExistingCustomer");
		}

		if (ORIGUtility.isEmptyString(request.getParameter("resDedup"))) {
			errorMsg = "Dedup is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resDedup");
			result = true;
		} else {
			vNotErrorFields.add("resDedup");
		}
		if (ORIGUtility.isEmptyString(request.getParameter("resLPM"))) {
			errorMsg = "LPM is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resLPM");
			result = true;
		} else {
			vNotErrorFields.add("resLPM");
		}
		if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(customerType)) {
			if (ORIGUtility.isEmptyString(request.getParameter("resKhonthai"))) {
				errorMsg = "Khonthai.com is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("resKhonthai");
				result = true;
			} else {
				vNotErrorFields.add("resKhonthai");
			}
		} else if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(customerType)) {
			if (ORIGUtility.isEmptyString(request.getParameter("resThaiRegistration"))) {
				errorMsg = "Thai Registration is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("resThaiRegistration");
				result = true;
			} else {
				vNotErrorFields.add("resThaiRegistration");
			}
			if (ORIGUtility.isEmptyString(request.getParameter("resBOL"))) {
				errorMsg = "BOL is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("resBOL");
				result = true;
			} else {
				vNotErrorFields.add("resBOL");
			}
		}

		if (ORIGUtility.isEmptyString(request.getParameter("resYellowPage"))) {
			errorMsg = "Yellow Page is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resYellowPage");
			result = true;
		} else {
			vNotErrorFields.add("resYellowPage");
		}
		if (ORIGUtility.isEmptyString(request.getParameter("resPhoneBook"))) {
			errorMsg = "Phone Book is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resPhoneBook");
			result = true;
		} else {
			vNotErrorFields.add("resPhoneBook");
		}
		// Debt Amount

		if (ORIGUtility.isEmptyString(request.getParameter("resDebtAmt"))) {
			errorMsg = "Debt Amount is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resDebtAmt");
			result = true;
		} else {
			vNotErrorFields.add("resDebtAmt");
		}
		// Debt BD
		if (ORIGUtility.isEmptyString(request.getParameter((String) XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBTBURDEN))))) {
			errorMsg = "Debt Burden is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("resDebtBurden");
			result = true;
		} else {
			vNotErrorFields.add("resDebtBurden");
		}
		// NCB Guarantor DE/UW

		PersonalInfoDataM personalInfoDataM;
		ApplicationDataM appForm = formHandler.getAppForm();
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
		if (OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())) {
			XRulesVerificationResultDataM rulesVerificationResultDataM = personalInfoDataM.getXrulesVerification();
			if (("Y").equals(request.getParameter("consent_flag"))) {
				if (ORIGUtility.isEmptyString((String) request.getParameter("consent_date"))) {
					errorMsg = "NCB consent date is required.";
					vErrors.add(errorMsg);
					vErrorFields.add("consent_date");
					result = true;
				} else {
					vNotErrorFields.add("consent_date");
				}
			}
			if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType())
					&& ORIGUtility.isEmptyString(rulesVerificationResultDataM.getNCBResult())) {
				errorMsg = "NCB is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("resNCB");
				result = true;
			} else {
				// vNotErrorFields.add("resNCB");
			}
		}// Co Borrower

		return result;
	}

	public boolean getMandateErrorUWSaveDraft(HttpServletRequest request, String customerType) {
		logger.debug("getMandateErrorUWSaveDraft");
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ApplicationDataM appForm = formHandler.getAppForm();
		if (appForm == null) {
			appForm = new ApplicationDataM();
		}

		Vector vErrors = formHandler.getFormErrors();
		Vector vErrorFields = formHandler.getErrorFields();
		Vector vNotErrorFields = formHandler.getNotErrorFields();
		if (vErrors == null) {
			vErrors = new Vector();
			formHandler.setFormErrors(vErrors);
		}
		String errorMsg = null;

		if (ORIGUtility.isEmptyString(request.getParameter("identification_no"))) {
			errorMsg = getDisplayName("identification_no", "APPLICANT_SUBFORM", request);
			vErrors.add(errorMsg);
			vErrorFields.add("identification_no");
			result = true;
		} else {
			vNotErrorFields.add("identification_no");
		}
		if (ORIGUtility.isEmptyString(request.getParameter("office_code"))) {
			errorMsg = getDisplayName("office_code", "APPLICANT_SUBFORM", request);
			vErrors.add(errorMsg);
			vErrorFields.add("office_code");
			result = true;
		} else {
			vNotErrorFields.add("office_code");
		}
		if (ORIGUtility.isEmptyString(request.getParameter("name_thai"))) {
			errorMsg = getDisplayName("name_thai", "CUSTOMER_INFO_SUBFORM", request);
			vErrors.add(errorMsg);
			vErrorFields.add("name_thai");
			result = true;
		} else {
			vNotErrorFields.add("name_thai");
		}
		ORIGUtility utility = new ORIGUtility();
		if (!ORIGUtility.isEmptyString(request.getParameter("birth_date"))) {
			int age = utility.stringToInt(request.getParameter("age"));
			if (!OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(customerType)) {
				if (age <= 0 || age > 150) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
					vErrors.add(errorMsg);
					vErrorFields.add("birth_date");
					result = true;
				} else {
					vNotErrorFields.add("birth_date");
				}
			}
		}
		if (ORIGUtility.isEmptyString(request.getParameter("area_code"))) {
			errorMsg = getDisplayName("area_code", "CUSTOMER_INFO_SUBFORM", request);
			vErrors.add(errorMsg);
			vErrorFields.add("area_code");
			result = true;
		} else {
			vNotErrorFields.add("area_code");
		}
		if (!ORIGUtility.isEmptyString(request.getParameter("m_birth_date"))) {
			int age = utility.stringToInt(request.getParameter("m_age"));
			String nullDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(appForm.getNullDate()));
			if ((age <= 0 || age > 150) && !(nullDate.equals(request.getParameter("m_birth_date")))) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "INVALID_AGE");
				vErrors.add(errorMsg + "[Marriage]");
				vErrorFields.add("m_birth_date");
				result = true;
			} else {
				vNotErrorFields.add("m_birth_date");
			}
		}
		if (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equals(appForm.getJobState())) {
			if (ORIGUtility.isEmptyString(request.getParameter("pre_score_mkt_code"))) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "pre_score_mkt_code");
				vErrors.add(errorMsg);
				vErrorFields.add("pre_score_mkt_code");
				result = true;
			} else {
				vNotErrorFields.add("pre_score_mkt_code");
			}
		}
		String menu = (String) request.getSession(true).getAttribute("PROPOSAL_MENU");
		if ("Y".equals(menu)) {
			if (ORIGUtility.isZero(request.getParameter("office_code"))) {
				errorMsg = "Office Code is required.";
				vErrors.add(errorMsg);
				vErrorFields.add("office_code");
				result = true;
			} else {
				vNotErrorFields.add("office_code");
			}
		}
		String personalType = request.getParameter("appPersonalType");
		if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
			if (appForm.getScorringResult() != null) {
				// check re excute appscore
				logger.debug("Check re execute applicaiton Score");
				if (OrigReExcuteAppScoreUtil.getInstance().isApplicationReExecuteScorring(appForm, request)) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "reexecuteAppScore");
					vErrors.add(errorMsg);
					vErrorFields.add("application_score");
					result = true;
				}

			}
		}

		return result;

	}

	public boolean getMandateErrorGuarantorVerCMR(HttpServletRequest request, String customerType) {
		boolean result = false;
		/*
		 * ORIGFormHandler formHandler = (ORIGFormHandler)
		 * request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		 * Vector vErrors = formHandler.getFormErrors(); Vector vErrorFields =
		 * formHandler.getErrorFields(); Vector vNotErrorFields =
		 * formHandler.getNotErrorFields(); if (vErrors == null) { vErrors = new
		 * Vector(); } String errorMsg = null; ORIGUtility utility = new
		 * ORIGUtility();
		 * 
		 * if (ORIGUtility.isEmptyString(request.getParameter("resBlaclist"))) {
		 * errorMsg = "Blacklist - Customer is required.";
		 * vErrors.add(errorMsg); vErrorFields.add("resBlaclist"); result =
		 * true; } else { vNotErrorFields.add("resBlaclist"); } if
		 * (ORIGUtility.isEmptyString
		 * (request.getParameter("resExistingCustomer"))) { errorMsg =
		 * "Existing Customer is required."; vErrors.add(errorMsg);
		 * vErrorFields.add("resExistingCustomer"); result = true; } else {
		 * vNotErrorFields.add("resExistingCustomer"); }
		 * 
		 * if (ORIGUtility.isEmptyString(request.getParameter("resDedup"))) {
		 * errorMsg = "Dedup is required."; vErrors.add(errorMsg);
		 * vErrorFields.add("resDedup"); result = true; } else {
		 * vNotErrorFields.add("resDedup"); }
		 */
		// if (ORIGUtility.isEmptyString(request.getParameter("resLPM"))) {
		// errorMsg = "LPM is required.";
		// vErrors.add(errorMsg);
		// vErrorFields.add("resLPM");
		// result = true;
		// } else {
		// vNotErrorFields.add("resLPM");
		// }
		/*
		 * if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(customerType)) { if
		 * (ORIGUtility.isEmptyString(request.getParameter("resKhonthai"))) {
		 * errorMsg = "Khonthai.com is required."; vErrors.add(errorMsg);
		 * vErrorFields.add("resKhonthai"); result = true; } else {
		 * vNotErrorFields.add("resKhonthai"); } } else if
		 * (OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(customerType)) { if
		 * (ORIGUtility
		 * .isEmptyString(request.getParameter("resThaiRegistration"))) {
		 * errorMsg = "Thai Registration is required."; vErrors.add(errorMsg);
		 * vErrorFields.add("resThaiRegistration"); result = true; } else {
		 * vNotErrorFields.add("resThaiRegistration"); } if
		 * (ORIGUtility.isEmptyString(request.getParameter("resBOL"))) {
		 * errorMsg = "BOL is required."; vErrors.add(errorMsg);
		 * vErrorFields.add("resBOL"); result = true; } else {
		 * vNotErrorFields.add("resBOL"); } }
		 * 
		 * if (ORIGUtility.isEmptyString(request.getParameter("resYellowPage")))
		 * { errorMsg = "Yellow Page is required."; vErrors.add(errorMsg);
		 * vErrorFields.add("resYellowPage"); result = true; } else {
		 * vNotErrorFields.add("resYellowPage"); } if
		 * (ORIGUtility.isEmptyString(request.getParameter("resPhoneBook"))) {
		 * errorMsg = "Phone Book is required."; vErrors.add(errorMsg);
		 * vErrorFields.add("resPhoneBook"); result = true; } else {
		 * vNotErrorFields.add("resPhoneBook"); }
		 * 
		 * 
		 * //NCB Guarantor DE/UW
		 * 
		 * 
		 * PersonalInfoDataM personalInfoDataM; ApplicationDataM appForm =
		 * formHandler.getAppForm(); personalInfoDataM = (PersonalInfoDataM)
		 * request.getSession(true).getAttribute("MAIN_POPUP_DATA");
		 * if(OrigConstant
		 * .COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag
		 * ())||
		 * OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(personalInfoDataM.
		 * getCoborrowerFlag())){ XRulesVerificationResultDataM
		 * rulesVerificationResultDataM =
		 * personalInfoDataM.getXrulesVerification(); if
		 * (("Y").equals(request.getParameter("consent_flag"))) { if
		 * (ORIGUtility.isEmptyString((String)
		 * request.getParameter("consent_date"))) { errorMsg =
		 * "NCB consent date is required."; vErrors.add(errorMsg);
		 * vErrorFields.add("consent_date"); result = true; } else {
		 * vNotErrorFields.add("consent_date"); } } if
		 * (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL
		 * .equals(personalInfoDataM.getCustomerType()) &&
		 * ORIGUtility.isEmptyString
		 * (rulesVerificationResultDataM.getNCBResult())) { errorMsg =
		 * "NCB is required."; vErrors.add(errorMsg);
		 * vErrorFields.add("resNCB"); result = true; } else {
		 * //vNotErrorFields.add("resNCB"); } }//Co Borrower
		 */
		return result;
	}

	public boolean getErrorMessage(String subformName, HttpServletRequest request, String customerType, String subFormClass) {
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler form = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        String menu = (String) request.getSession(true).getAttribute("PROPOSAL_MENU");
        if (form == null)
            form = new ORIGFormHandler();
        ApplicationDataM appM = form.getAppForm();
        if (appM == null)
            appM = new ApplicationDataM();

        String fields = "";
        String value = null;
        boolean resultFlag = false;
        Vector vErrors = form.getFormErrors();
        Vector vErrorFields = form.getErrorFields();
        Vector vNotErrorFields = form.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;
        Vector manFields;
        if (!"Y".equals(menu)) {
            manFields = MandatoryFieldCache.getMandatoryFieldsInOneForm(customerType, userM.getRoles(), subformName,"Y");
        } else {
            Vector vRole = new Vector();
            vRole.add(OrigConstant.ROLE_PROPASAL);
            manFields = MandatoryFieldCache.getMandatoryFieldsInOneForm(customerType, vRole, subformName,"Y");
        }
        //logger.debug(">>> manFields : "+manFields);
        for (int i = 0; i < manFields.size(); i++) {
            fields = (String) manFields.elementAt(i);
            value = request.getParameter(fields);
            if ("".equals(value)) {
                errorMsg = getDisplayName(fields, subformName, request);
                //					error.put(fields,errorMsg);
                vErrors.add(errorMsg);
                vErrorFields.add(fields);
                resultFlag = true;
            } else if(value!=null){
                vNotErrorFields.add(fields);
            }
        }
        
        try {
            /** Validate by Subform Class **/
        	//skip is cancel
        	
            ORIGSubForm subForm = (ORIGSubForm) ((Class.forName(subFormClass)).newInstance());
            boolean result = subForm.validateSubForm(request);
            if(result){
                resultFlag = result;
            }                    	 
        	 
        } catch (InstantiationException e) {
            logger.error(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        
        return resultFlag;
    }

	public boolean getMandateErrorBySubForm(HttpServletRequest request, String customerType, String formID) {
		boolean result = false;

		logger.debug("getMandateErrorBySubForm");
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);

		ORIGUtility utility = new ORIGUtility();
		ORIGFormUtil origFormUtil = new ORIGFormUtil();
		Vector vErrors = formHandler.getFormErrors();
		Vector vErrorFields = formHandler.getErrorFields();
		Vector vNotErrorFields = formHandler.getNotErrorFields();
		if (vErrors == null) {
			vErrors = new Vector();
		}
		String errorMsg = null;
		String personalType = request.getParameter("appPersonalType");// (String)
		// request.getSession().getAttribute("PersonalType");
		logger.debug("personalType = " + personalType);

		// Get Sub Form
		Vector vSubForms = null;
		if (formID != null && !"".equals(formID)) {
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			vSubForms = origFormUtil.loadSubFormsForValidation(userM.getRoles(), formID);
		} else {
			HashMap subForms = formHandler.getSubForms();
			vSubForms = new Vector(subForms.values());
		}
		request.getSession().setAttribute("CUSTOMET_TYPE", customerType);
		logger.debug(">>>subForms.size=" + vSubForms.size());
		for (int i = 0; i < vSubForms.size(); i++) {
			ORIGSubForm subForm = (ORIGSubForm) vSubForms.get(i);
			if (this.getErrorMessage(subForm.getSubFormID(), request, customerType, subForm.getSubFormClass())) {
				result = true;
			}
		}

		return result;
	}

	public boolean getMandateErrorPLoanForCalulate(HttpServletRequest request) {
		boolean result = false;
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);

		Vector vErrors = formHandler.getFormErrors();
		Vector vErrorFields = formHandler.getErrorFields();
		Vector vNotErrorFields = formHandler.getNotErrorFields();
		if (vErrors == null) {
			vErrors = new Vector();
		}
		String errorMsg = null;
		String loan_type = request.getParameter("loan_type");
		String mkt_code = request.getParameter("mkt_code");
		String campaign = request.getParameter("campaign");
		String scheme_code = request.getParameter("scheme_code");
		String loan_amount_applied = request.getParameter("loan_amount_applied");

		if (loan_type == null || ("").equals(loan_type)) {
			errorMsg = "Loan Type is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("loan_type");
			result = true;
		} else {
			vNotErrorFields.add("loan_type");
		}

		if (mkt_code == null || ("").equals(mkt_code)) {
			errorMsg = "Matketing Code is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("mkt_code");
			result = true;
		} else {
			vNotErrorFields.add("mkt_code");
		}

		if (ORIGUtility.isEmptyString(campaign)) {
			errorMsg = "Campaign is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("campaign");
			result = true;
		} else {
			vNotErrorFields.add("campaign");
		}

		if (ORIGUtility.isEmptyString(scheme_code)) {
			errorMsg = "Scheme Code is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("scheme_code");
			result = true;
		} else {
			vNotErrorFields.add("scheme_code");
		}

		if (loan_amount_applied == null || ("").equals(loan_amount_applied) || Double.parseDouble(loan_amount_applied) == 0) {
			errorMsg = "Loan Amount Applied is required.";
			vErrors.add(errorMsg);
			vErrorFields.add("loan_amount_applied");
			result = true;
		} else {
			vNotErrorFields.add("loan_amount_applied");
		}
		return result;
	}
}
