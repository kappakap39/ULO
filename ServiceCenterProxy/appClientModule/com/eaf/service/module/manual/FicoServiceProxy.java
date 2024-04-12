package com.eaf.service.module.manual;

import org.apache.log4j.Logger;

import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;

public class FicoServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(FicoServiceProxy.class);
	public static final String url = "urlWebService";
	public static final String serviceId = "fico";
	public static class requestConstants{
		public static String appId = "applicationId";
		public static String isVETO = "isVETO";
		public static String appCreateDate = "applicationCreateDate";
		public static String channelCode = "channelCode";
		public static String strategyNum1 = "strategySelRandomNum1";
		public static String isFraudApp = "isFraudApplication";
		public static class personalApp{
			public static String isDocumentComp = "isDocumentComplete";
			public static String idType = "IDType";
			public static String idNumber = "IDNumber";
			public static String cisId = "CIS_ID";
			public static String educationLv = "educationLevel";
			public static String salaryLv = "salaryLevel";
			public static String isKGroupStaff = "isKGroupStaff";
			public static String positionType = "positionType";
			public static String isInfoComp = "isInformationComplete";
			public static String cisPrimarySeg = "cisPrimarySegment";
			public static String cisPrimarySubSeg = "cisPrimarySubSegment";
			public static String cisSecondarySeg = "cisSecondarySegment";
			public static String cisSecondarySubSeg = "cisSecondarySubSegment";
			public static class previousEmployment{
				public static String previousEmploymentYears = "previousEmploymentYears";
				public static String previousEmploymentMonths = "previousEmploymentMonths";
			}
			public static class IDAddress{
				public static class address{
					public static String houseNumber = "IDAddress_address_houseNumber";
					public static String village = "IDAddress_address_village";
					public static String buildingName = "IDAddress_address_buildingName";
					public static String subDistrict = "IDAddress_address_subDistrict";
					public static String district = "IDAddress_address_district";
					public static String province = "IDAddress_address_province";
					public static String postalCode = "IDAddress_address_postalCode";
					public static String country = "IDAddress_address_country";
					public static String buildingUnitNumber = "IDAddress_address_buildingUnitNumber";
					public static String fixedLineNumber = "IDAddress_address_fixedLineNumber";
					public static String floorNumber = "IDAddress_address_floorNumber";
					public static String moo = "IDAddress_address_moo";
					public static String soi = "IDAddress_address_soi";
					public static String street = "IDAddress_address_street";							
				}
			}
			public static class currentResidence{
				public static String monthsAtAddress = "currentResidence_monthsAtAddress";
				public static String yearsAtAddress = "currentResidence_yearsAtAddress";
				public static String currentResidentialStatus = "currentResidence_currentResidentialStatus";
				public static class address{
					public static String houseNumber = "currentResidence_address_houseNumber";
					public static String village = "currentResidence_address_village";
					public static String buildingName = "currentResidence_address_buildingName";
					public static String subDistrict = "currentResidence_address_subDistrict";
					public static String district = "currentResidence_address_district";
					public static String province = "currentResidence_address_province";
					public static String postalCode = "currentResidence_address_postalCode";
					public static String country = "currentResidence_address_country";
					public static String buildingUnitNumber = "currentResidence_address_buildingUnitNumber";
					public static String fixedLineNumber = "currentResidence_address_fixedLineNumber";
					public static String type = "currentResidence_address_type";
					public static String floorNumber = "currentResidence_address_floorNumber";
					public static String moo = "currentResidence_address_moo";
					public static String soi = "currentResidence_address_soi";
					public static String street = "currentResidence_address_street";
				}
			}
			public static class person{
				public static String birthDate = "person_birthDate";
				public static String numberOfDependents = "person_numberOfDependents";
				public static String martialStatus = "person_maritalStatus";
				public static String phoneNumber = "person_phoneNumber";
				public static String nationality = "person_nationality";
				public static class name{
					public static String prefix = "person_name_prefix";
					public static String first = "person_name_first";
					public static String middle = "person_name_middle";
					public static String last = "person_name_last";
				}
			}
			public static class currentEmployment{
				public static class employment{
					public static String monthsWithEmployer = "currentEmployment_employment_monthsWithEmployer";
					public static String occupationCode = "currentEmployment_employment_occupationCode";
					public static String professionCode = "currentEmployment_employmen_professionCode";
					public static String employerTitleCode = "currentEmployment_employment_employerTitleCode";
					public static String currentEmploymentYears = "currentEmployment_employment_currentEmploymentYears";
					public static String companyName = "currentEmployment_employment_companyName";
					public static class employerAddress{
						public static class address{
							public static String houseNumber = "currentEmployment_employment_employerAddress_address_houseNumber";
							public static String village = "currentEmployment_employment_employerAddress_address_village";
							public static String buildingName = "currentEmployment_employment_employerAddress_address_buildingName";
							public static String subDistrict = "currentEmployment_employment_employerAddress_address_subDistrict";
							public static String district = "currentEmployment_employment_employerAddress_address_district";
							public static String province = "currentEmployment_employment_employerAddress_address_province";
							public static String postalCode = "currentEmployment_employment_employerAddress_address_postalCode";
							public static String country = "currentEmployment_employment_employerAddress_address_country";
							public static String buildingUnitNumber = "currentEmployment_employment_employerAddress_address_buildingUnitNumber";
							public static String fixedLineNumber = "currentEmployment_employment_employerAddress_address_fixedLineNumber";
							public static String type = "currentEmployment_employment_employerAddress_address_type";
							public static String floorNumber = "currentEmployment_employment_employerAddress_address_floorNumber";
							public static String moo = "currentEmployment_employment_employerAddress_address_moo";
							public static String soi = "currentEmployment_employment_employerAddress_address_soi";
							public static String street = "currentEmployment_employment_employerAddress_address_street";
						}
					}
				}
			}
			public static class HRVerificationResponse{
				public static String HRVerificationResult = "HRVerificationResponse_HRVerificationResult";
				public static String bonus = "HRVerificationResponse_bonus";
				public static String otherVariableIncome = "HRVerificationResponse_otherVariableIncome";
				public static String verifiedIncome = "HRVerificationResponse_verifiedIncome";
			}
			public static class websiteVerificationResponse{
				public static String websiteCode = "websiteVerificationResponse_websiteCode";
				public static String websitVerificationResult = "websiteVerificationResponse_websiteVerificationResult";
			}
			public static class customerVerificationResponse{
				public static String customerVerificationResponse = "customerVerificationResponse_customerVerificationResult";
			}
			public static class inc_info{
				public static String incomeTypeCode = "";
				public static String incomeType = "";
				public static class inc_payslip{
					public static String noMonth = "inc_payslip_no_month";
					public static String accumIncome = "inc_payslip_accum_income";
					public static String bonus = "inc_payslip_bonus";
					public static String sumSSO = "inc_payslip_sum_sso";
					public static String specialPension = "inc_payslip_special_pension";
					public static String accumOthIncome = "inc_payslip_accum_oth_income";
					public static String bonusMonth = "inc_payslip_bonus_month";
					public static String bonusYear = "inc_payslip_bonus_year";
					public static class inc_payslip_monthly{
						public static String salaryDate = "inc_payslip_monthly_salary_date";
						public static String incomeType = "inc_payslip_monthly_income_type";
						public static String incomeDesc = "inc_payslip_monthly_income_desc";
						public static String incomeOtherDesc = "inc_payslip_monthly_income_oth_desc";
						public static class inc_payslip_monthly_detail{
							public static String month = "inc_payslip_monthly_detail_month";
							public static String year = "inc_payslip_monthly_detail_year";
							public static String amount = "inc_payslip_monthly_detail_amount";
						}
					}
				}
				public static class inc_yearly_tawi{
					public static String companyName = "inc_yearly_tawi_company_name";
					public static String year = "inc_yearly_tawi_year";
					public static String noMonth = "inc_yearly_tawi_no_month";
					public static String income40_1 = "inc_yearly_tawi_income40_1";
					public static String income40_2 = "inc_yearly_tawi_income40_2";
					public static String sumSSO = "inc_yearly_tawi_sum_sso";
				}
				public static class inc_taweesap{
					public static String aum = "inc_taweesap_aum";
				}
				public static class inc_saving_acc{
					public static String openDate = "inc_saving_acc_open_date";
					public static String accountNo = "inc_saving_acc_account_no";
					public static String accountName = "inc_saving_acc_account_name";
					public static String holdingRatio = "inc_saving_acc_holding_ratio";
					public static String bankCode = "inc_saving_acc_bank_code";
					public static class inc_saving_acc_detail{
						public static String month = "inc_saving_acc_inc_saving_acc_detail_month";
						public static String year = "inc_saving_acc_inc_saving_acc_detail_year";
						public static String amount = "inc_saving_acc_inc_saving_acc_detail_amount";
					}
				}
				public static class inc_salary_incert{
					public static String income = "inc_salary_cert_income";
					public static String companyName = "inc_salary_cert_company_name";
				}
				public static class inc_previous_income{
					public static String incomeDate = "inc_previous_income_income_date";
					public static String product = "inc_previous_income_product";
					public static String income = "inc_previous_income_income";
				}
				public static class inc_payroll{
					public static String income = "inc_payroll_income";
					public static String noOfEmployee = "inc_payroll_no_of_employee";
				}
				public static class inc_opn_end_fund{
					public static String openDate = "inc_opn_end_fund_open_date";
					public static String accountNo = "inc_opn_end_fund_account_no";
					public static String accountName = "inc_opn_end_fund_account_name";
					public static String holdingRatio = "inc_opn_end_fund_holding_ratio";
					public static String bankCode = "inc_opn_end_fund_bank_code";
					public static String fundName = "inc_opn_end_fund_fund_name";
					public static class inc_opn_end_fund_detail{
						public static String month = "inc_opn_end_fund_detail_month";
						public static String year = "inc_opn_end_fund_detail_year";
						public static String amount = "inc_opn_end_fund_detail_amount";
					}
				}
				public static class inc_monthly_tawi{
					public static String incomeType = "inc_monthly_tawi_income_type";
					public static String companyName = "inc_monthly_tawi_company_name";
					public static String compareFlag = "inc_monthly_tawi_compare_flag";
					public static class inc_month_tawi_detail{
						public static String month = "inc_monthly_tawi_detail_month";
						public static String year = "inc_monthly_tawi_detail_year";
						public static String amount = "inc_monthly_tawi_detail_amount";
					}
				}
				public static class inc_kvi{
					public static String percentChequeReturn = "inc_kvi_percent_cheque_return";
					public static String verifiedIncome = "inc_kvi_verified_income";
				}
				public static class inc_fixed_guarantee{
					public static String accountNo = "inc_fixed_guarantee_account_no";
					public static String sub = "inc_fixed_guarantee_sub";
					public static String accountName = "inc_fixed_guarantee_account_name";
					public static String rententionDate = "inc_fixed_guarantee_retention_date";
					public static String branchNo = "inc_fixed_guarantee_branch_no";
					public static String rentionType = "inc_fixed_guarantee_rentention_type";
					public static String rentionAmount = "inc_fixed_guarantee_rentention_amt";
				}
				public static class inc_fixed_acc{
					public static String openDate = "inc_fixed_acc_open_date";
					public static String accountNo = "inc_fixed_acc_account_no";
					public static String accountName = "inc_fixed_acc_account_name";
					public static String holdingRatio = "inc_fixed_acc_holding_ratio";
					public static String accountBalance = "inc_fixed_acc_account_balance";
					public static String bankCode = "inc_fixed_acc_bank_code";
				}
				public static class inc_fin_instrument{
					public static String openDate = "inc_fin_instrument_open_date";
					public static String expireDate = "inc_fin_instrument_expire_date";
					public static String issuerName = "inc_fin_instrument_issuer_name";
					public static String instrumentType = "inc_fin_instrument_instrument_type";
					public static String holderName = "inc_fin_instrument_holder_name";
					public static String holdingRatio = "inc_fin_instrument_holding_ratio";
					public static String currentBalance = "inc_fin_instrument_current_balance";
					public static String instrumentTypeDesc = "inc_fin_instrument_instrument_type_desc";
				}
				public static class inc_cls_end_fund{
					public static String bankCode = "inc_cls_end_fund_bank_code";
					public static String fundName = "inc_cls_end_fund_fund_name";
					public static String accountNo = "inc_cls_end_fund_account_no";
					public static String accountName = "inc_cls_end_fund_account_name";
					public static String holdingRatio = "inc_cls_end_fund_holding_ratio";
					public static String accountBalance = "inc_cls_end_fund_account_balance";
				}
				public static class inc_bank_statement{
					public static String bankCode = "inc_bank_statement_bank_code";
					public static String statementCode = "inc_bank_statement_statement_code";
					public static String additionalCode = "inc_bank_statement_additional_code";
					public static class inc_bank_statement_detail{
						public static String month = "inc_bank_statement_detail_month";
						public static String year = "inc_bank_statement_detail_year";
						public static String amount = "inc_bank_statement_detail_amount";
					}
				}
				public static class orig_bundle_sme{
					public static String applicantQuality = "orig_bundle_sme_applicant_quality";
					public static String approveLimit = "orig_bundle_sme_approval_limit";
					public static String businessOwnerFlag = "orig_bundle_sme_business_owner_flag";
					public static String coperateRatio = "orig_bundle_sme_corporate_ratio";
					public static String gDscrReq = "orig_bundle_sme_g_dscr_req";
					public static String gTotalExistPayment = "orig_bundle_sme_g_total_exist_payment";
					public static String gTotalNewpayReq = "orig_bundle_sme_g_total_newpay_req";
					public static String individualRatio = "orig_bundle_sme_corporate_ratio";
				}
				public static class orig_bundle_kl{
					public static String verifiedIncome = "orig_bundle_kl_verfied_income";
					public static String verifiedDate = "orig_bundle_kl_verfied_date";
					public static String esimatedIncome = "orig_bundle_kl_estimated_income";
				}
				public static class orig_bundle_hl{
					public static String approveCreditLine = "orig_bundle_hl_approve_credit_line";
					public static String verifiedIncome = "orig_bundle_hl_verified_income";
					public static String approvedDate = "orig_bundle_hl_approved_date";
					public static String ccApprovedAmount = "orig_bundle_hl_cc_approved_amt";
					public static String kecApprovedAmount = "orig_bundle_hl_kec_approved_amt";
				}
				public static class documentList{
					public static class document{
						public static String docCode = "documents_documentCode";
					}
				}
				public static class reference{
					public static String title = "reference_title";
					public static String lastName = "reference_lastName";
					public static String firstName = "reference_firstName";
					public static String relationshipWithBorrower = "reference_relationshipWithBorrower";
					public static String fixedLineHome = "reference_fixedLineHome";
					public static String fixedLineWork = "reference_fixedLineWork";
					public static String mobileNumber = "reference_mobileNumber";	
				}
			}
		}
		public static class creditRequests{
			public static class creditDetails{
				public static String typeFromCoverPage = "creditDetails_typeFromCoverPage";
				public static String interestRate = "creditDetails_interestRate";
				public static String projectCode = "creditDetails_projectCode";
				public static String uniqueProductId = "creditDetails_uniqueProductId";
				public static String isAutoPayment = "creditDetails_isAutoPayment";
				public static String tranferKECPercentage = "creditDetails_transferKECPercentage";
				public static String productCode = "creditDetails_productCode";
				public static String cardCode = "creditDetails_cardCode";
				public static String cardType= "creditDetails_cardType";
				public static String plasticCardType = "creditDetails_plasticCardType";
				public static String requestCreditLimitPercent = "creditDetails_requestCreditLimitPercent";
				public static String CCPaymentMethod = "creditDetails_CCPaymentMethod";
				public static String productMailingAddrType = "creditDetails_productMailingAddressType";
				public static String appHLApproveDate = "creditDetails_applicationHLApprovedDate";
				public static String percentChequeReturn = "creditDetails_percentageChequeReturn";
				public static String accountNumberMC = "creditDetails_accountNumberMC";
				public static String smeBorrower = "creditDetails_SMEBorrower";
				public static String approvedLimit = "creditDetails_ApprovedLimit";
				public static String requestedLoanAmount = "creditDetails_requestedLoanAmt";
				public static String requestedTerm = "creditDetails_requestedTerm";
				public static String caFinalDecision = "creditDetails_caFinalDecision";
				public static class mailingAddress{
					public static class address{
						public static String houseNumber = "mailingAddress_address_houseNumber";
						public static String village = "mailingAddress_address_village";
						public static String buildingName = "mailingAddress_address_buildingName";
						public static String subDistrict = "mailingAddress_address_subDistrict";
						public static String district = "mailingAddress_address_district";
						public static String province = "mailingAddress_address_province";
						public static String postalCode = "mailingAddress_address_postalCode";
						public static String country = "mailingAddress_address_country";
						public static String buildingUnitNumber = "mailingAddress_address_buildingUnitNumber";
						public static String fixedLineNumber = "mailingAddress_address_fixedLineNumberc";
						public static String type = "mailingAddress_address_type";
						public static String floorNumber = "mailingAddress_address_floorNumber";
						public static String moo = "mailingAddress_address_moo";
						public static String soi = "mailingAddress_address_soi";
						public static String street = "mailingAddress_address_street";								
					}
				}
			}
			public static class bundleProgram{
				public static String isSMEApproved = "bundleProgram_isSMEApproved";
				public static String smeApprovedDate = "bundleProgram_SMEApprovedDate";
				public static String applicantQuality = "bundleProgram_applicantQuality";
				public static String isSMESecuredApp = "bundleProgram_isSMESecuredAppl";
				public static String smeLoanApprovedAmount = "bundleProgram_SMELoanApprovedAmount";
				public static String isBundleProgram = "bundleProgram_isBundleProgram";
				public static String bundleProductName = "bundleProgram_bundleProductName";
				public static String bundleProductCode = "bundleProgram_bundledProductCode";
			}
		}
		public static class priorityPassQuota{
			public static String existingAssignedCount = "priorityPassQuota_existingAssignedCount";
		}
	}
	@Override
	public ServiceRequestTransaction requestTransaction() {
		return null;
	}
	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		return null;
	}
	@Override
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction) {
		return null;
	}
}
